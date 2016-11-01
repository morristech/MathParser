package fr.nihilus.mathparser;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringJoiner;

import fr.nihilus.mathparser.tokenizer.Token;

/**
 * Implémentation de l'alrgorithme Shunting Yard de Djikstra pour passer de la
 * notation mathématique classique à la Reverse Polnish Notation (ou notation
 * postfixe). L'avantage de la RPN est qu'elle peut être interprétée sans
 * parenthèses, en respectant les priorités opératoires.
 * 
 * @author Thib
 */
public class ShuntingYard {

	private Queue<Token> input;
	private Queue<Token> output;
	private Deque<Token> stack;

	public ShuntingYard() {
		input = new LinkedList<>();
		stack = new LinkedList<>();
		output = new LinkedList<>();
	}

	/**
	 * Prépare la file d'attente des Token à traiter.
	 * 
	 * @param tokens
	 */
	public ShuntingYard prepare(Collection<? extends Token> tokens) {
		input.addAll(tokens);
		return this;
	}
	
	/**
	 * Prépare la file d'attente des Token à traiter.
	 * 
	 * @param tokens
	 */
	public ShuntingYard prepare(Token[] tokens) {
		input.addAll(Arrays.asList(tokens));
		return this;
	}

	/**
	 * Convertit les Tokens placés dans la file d'attente en entrée en notation
	 * postfix, et les place dans la sortie.
	 */
	public ShuntingYard postfix() throws ParseException {
		Token read;
		while ((read = input.poll()) != null) {
			process(read);
		}
		finish();
		return this;
	}

	/**
	 * Lit le token en entrée et exécute immédiatement l'algorithme. Une fois
	 * tous les Tokens passés en paramètre, il faut finaliser l'algorithme en
	 * appelant {@link #finish()}.
	 * 
	 * @param read
	 */
	public void process(Token read) throws ParseException {
		switch (read.getType()) {
		case Token.TYPE_NUMBER:
			// Nombre, placer à la sortie
			output.add(read);
			break;
		case Token.TYPE_FUNCTION:
			// Fonction, placer sur la pile
			stack.push(read);
			break;
		case Token.TYPE_ARG_SEPARATOR:
			// Jusqu'à trouver ( en haut de pile, vider la pile sur la
			// sortie
			while (!stack.isEmpty() && stack.peek().getType() != Token.TYPE_LEFT_PAR) {
				output.add(stack.pop());
			}

			// Si on ne rencontre pas de ( : erreur de parenthèses.
			if (stack.isEmpty())
				throw new ParseException("Mismatched parenthesis.", 0);
			break;
		case Token.TYPE_MULT_DIV:
		case Token.TYPE_PLUS_MINUS:
			// Jusqu'à pile soit vide, précédence est plus petite ou égale
			// Vider la pile sur la sortie.
			while (!stack.isEmpty() && read.isLessPrecedenceOrEqual(stack.peek())) {
				output.add(stack.pop());
			}

			// Placer opérateur sur la pile.
			stack.push(read);
			break;
		case Token.TYPE_LEFT_PAR:
			// Parenthèse ouvrante, placer sur la pile
			stack.push(read);
			break;
		case Token.TYPE_RIGHT_PAR:
			// Jusqu'à trouver ( en haut de pile, vider la pile sur la sortie
			while (stack.peek().getType() != Token.TYPE_LEFT_PAR && !stack.isEmpty()) {
				output.add(stack.pop());
			}

			// Si on ne rencontre pas de ( : erreur de parenthèses.
			if (stack.isEmpty())
				throw new ParseException("Mismatched parenthesis.", 0);

			// Retire ( de la pile
			stack.pop();

			// Fonction en haut de pile, placer sur la sortie
			if (!stack.isEmpty() && stack.peek().getType() == Token.TYPE_FUNCTION) {
				output.add(stack.pop());
			}
		}
	}

	/**
	 * Indique à l'algorithme qu'il n'y a plus de Tokens à traiter en entrée.
	 * cette méthode est à appeler uniquement après des appels répétés à
	 * {@link #process(Token)}.
	 */
	public ShuntingYard finish() throws ParseException {
		// Quand il n'y a plus de tokens sur l'entrée, tant que la pile n'est
		// pas vide
		while (!stack.isEmpty()) {
			Token popped = stack.pop();

			// Si on rencontre ( ou ) : erreur de parenthèses
			if (popped.getType() == Token.TYPE_LEFT_PAR || popped.getType() == Token.TYPE_RIGHT_PAR) {
				throw new ParseException("Mismatched parenthesis.", 0);
			}

			// Placer sur la sortie
			output.add(popped);
		}
		return this;
	}

	/**
	 * Réinitialise la file d'attente d'entrée, vide la sortie et la pile.
	 */
	public void reset() {
		input.clear();
		output.clear();
		stack.clear();
	}

	public Token[] output() {
		return output.toArray(new Token[output.size()]);
	}

	public String outputToString() {
		StringJoiner joiner = new StringJoiner(" ");
		for (Token token : output) {
			joiner.add(token.toString());
		}
		return joiner.toString();
	}
}
