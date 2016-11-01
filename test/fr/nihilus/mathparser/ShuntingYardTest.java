package fr.nihilus.mathparser;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.nihilus.mathparser.tokenizer.Token;

public class ShuntingYardTest {

	private ShuntingYard algo;

	public ShuntingYardTest() {
		algo = new ShuntingYard();
	}

	@Before
	public void setup() throws Exception {
		algo.reset();
	}

	@Test
	public void testSimpleAddition() throws Exception {
		// Expression à tester : 4 + 3
		List<Token> expr = Arrays.asList(new Token(Token.TYPE_NUMBER, "4", 0), new Token(Token.TYPE_PLUS_MINUS, "+", 1),
				new Token(Token.TYPE_NUMBER, "3", 0));

		algo.prepare(expr);
		algo.postfix();
		assertEquals("4 3 +", algo.outputToString());
	}

	@Test
	public void testSimpleMult() throws Exception {
		// Expression à tester : 4 * 3
		List<Token> expr = Arrays.asList(new Token(Token.TYPE_NUMBER, "4", 0), new Token(Token.TYPE_PLUS_MINUS, "*", 2),
				new Token(Token.TYPE_NUMBER, "3", 0));

		algo.prepare(expr);
		algo.postfix();
		assertEquals("4 3 *", algo.outputToString());
	}

	@Test
	public void testAddMultCombination() throws Exception {
		// Expression à tester : 4 + 3 * 6
		List<Token> expr = Arrays.asList(new Token(Token.TYPE_NUMBER, "4", 0), new Token(Token.TYPE_PLUS_MINUS, "+", 1),
				new Token(Token.TYPE_NUMBER, "3", 0), new Token(Token.TYPE_PLUS_MINUS, "*", 3),
				new Token(Token.TYPE_NUMBER, "6", 0));

		algo.prepare(expr);
		algo.postfix();
		assertEquals("4 3 6 * +", algo.outputToString());
	}

	// @Test
	public void testMultAddDivCombination() throws Exception {
		// Expression à tester : 4 * 3 + 8 / 2
		List<Token> expr = Arrays.asList(new Token(Token.TYPE_NUMBER, "4", 0), new Token(Token.TYPE_PLUS_MINUS, "*", 3),
				new Token(Token.TYPE_NUMBER, "3", 0), new Token(Token.TYPE_PLUS_MINUS, "+", 2),
				new Token(Token.TYPE_NUMBER, "8", 0), new Token(Token.TYPE_PLUS_MINUS, "/", 3),
				new Token(Token.TYPE_NUMBER, "2", 0));

		algo.prepare(expr);
		algo.postfix();
		assertEquals("4 3 * 8 2 / +", algo.outputToString());
	}

	@Test
	public void testParenthesis() throws Exception {
		// Expression à tester : (4 + 3) * 6
		List<Token> expr = Arrays.asList(new Token(Token.TYPE_LEFT_PAR, "("), new Token(Token.TYPE_NUMBER, "4"),
				new Token(Token.TYPE_PLUS_MINUS, "+", 2), new Token(Token.TYPE_NUMBER, "3"),
				new Token(Token.TYPE_RIGHT_PAR, ")"), new Token(Token.TYPE_PLUS_MINUS, "*", 2),
				new Token(Token.TYPE_NUMBER, "6"));

		algo.prepare(expr);
		algo.postfix();
		assertEquals("4 3 + 6 *", algo.outputToString());
	}

	@Test
	public void testSerial() throws Exception {
		// Expression à tester
		// Expression à tester : 4 * 3 + 8 / 2
		List<Token> expr = Arrays.asList(new Token(Token.TYPE_NUMBER, "4", 0), new Token(Token.TYPE_PLUS_MINUS, "*", 3),
				new Token(Token.TYPE_NUMBER, "3", 0), new Token(Token.TYPE_PLUS_MINUS, "+", 2),
				new Token(Token.TYPE_NUMBER, "8", 0), new Token(Token.TYPE_PLUS_MINUS, "/", 3),
				new Token(Token.TYPE_NUMBER, "2", 0));
		
		for (Token token : expr) {
			algo.process(token);
		}
		algo.finish();
		assertEquals("4 3 * 8 2 / +", algo.outputToString());
	}
}
