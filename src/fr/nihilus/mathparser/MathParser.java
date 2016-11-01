package fr.nihilus.mathparser;

import java.text.ParseException;
import java.util.List;

import fr.nihilus.mathparser.syntax.Expression;
import fr.nihilus.mathparser.syntax.ExpressionFactory;
import fr.nihilus.mathparser.tokenizer.Token;
import fr.nihilus.mathparser.tokenizer.Tokenizer;

/**
 * The Façade class.
 * 
 * @author Thib
 */
public class MathParser {

	private StringBuilder input;
	private Tokenizer tokenizer;
	private ShuntingYard shuntingYard;
	private ExpressionFactory factory;

	public MathParser() {
		input = new StringBuilder();
		tokenizer = new Tokenizer();
		shuntingYard = new ShuntingYard();
		factory = new ExpressionFactory();
	}

	public void update(CharSequence expr) {
		input.append(expr);
	}

	public double parse() throws ParseException {
		tokenizer.reset();
		shuntingYard.reset();

		List<Token> tokens = tokenizer.prepare(input.toString()).tokenize().output();
		Token[] postfixed = shuntingYard.prepare(tokens).postfix().output();
		Expression tree = factory.make(postfixed);
		
		return tree.eval();
	}

	public void reset() {
		input.delete(0, input.length());
		tokenizer.reset();
		shuntingYard.reset();
	}
}
