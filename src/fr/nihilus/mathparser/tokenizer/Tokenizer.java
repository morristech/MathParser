package fr.nihilus.mathparser.tokenizer;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

	private final List<Token> tokens;
	private final TokenInfo[] infos;
	private String expr;

	public Tokenizer() {
		tokens = new LinkedList<>();
		infos = new TokenInfo[] {
				new TokenInfo(Token.TYPE_NUMBER, Patterns.NUMBER, 0),
				new TokenInfo(Token.TYPE_LEFT_PAR, Patterns.LEFT_PAR, 0),
				new TokenInfo(Token.TYPE_RIGHT_PAR, Patterns.RIGHT_PAR, 0),
				new TokenInfo(Token.TYPE_PLUS_MINUS, Patterns.PLUS_MINUS, 2),
				new TokenInfo(Token.TYPE_MULT_DIV, Patterns.MULT_DIV, 3)
		};
	}

	/**
	 * @param tokenizedExpression
	 * @return
	 */
	public Tokenizer prepare(String tokenizedExpression) {
		this.expr = tokenizedExpression;
		return this;
	}

	/**
	 * Traduit l'int�gralit� de l'expression ma�th�matique � �valuer en une
	 * s�rie de Tokens.
	 * 
	 * @return liste des tokens form�s � partir de l'expression pr�cis�e par
	 *         {@link #prepare(String)}.
	 */
	public Tokenizer tokenize() throws ParseException {

		tokens.clear();

		while (hasMoreTokens()) {
			Token token = nextToken();
			tokens.add(token);
		}
		return this;
	}
	
	public boolean hasMoreTokens() {
		return !expr.isEmpty();
	}

	/**
	 * Traduit une portion de la chaine de caract�re en un Token.
	 * 
	 * @throws ParseException
	 */
	public Token nextToken() throws ParseException {
		boolean matched = false;
		Token token = null;

		for (TokenInfo tokInfo : infos) {
			expr = expr.trim();
			Matcher m = tokInfo.pattern.matcher(expr);
			if (m.find()) {
				matched = true;

				String matchedString = m.group().trim();
				token = new Token(tokInfo.type, matchedString, tokInfo.prec);

				expr = m.replaceFirst("");
				break;
			}
		}

		if (!matched)
			throw new ParseException("Unexpected character: " + expr, 0);
		
		return token;
	}

	public List<Token> output() {
		return tokens;
	}

	/** R�initialise le Tokenizer � son �tat d'origine. */
	public void reset() {
		expr = null;
		tokens.clear();
	}

	/**
	 * D�crit les informations associ�es � un Token.
	 * 
	 * @author Thib
	 */
	static class TokenInfo {

		Pattern pattern;
		int type;
		int prec;

		public TokenInfo(int id, String pattern, int precedence) {
			this.type = id;
			this.pattern = Pattern.compile("^(" + pattern + ")");
			this.prec = precedence;
		}
	}
}
