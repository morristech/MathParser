package fr.nihilus.mathparser.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.nihilus.mathparser.tokenizer.Token;

public class ExpressionTest {

	private ExpressionFactory factory;

	public ExpressionTest() {
		factory = new ExpressionFactory();
	}

	@Test
	public void testSingleNumber() throws Exception {
		Token[] tokens = new Token[] {new Token(Token.TYPE_NUMBER, "4")};
		Expression numberExp = factory.make(tokens);
		assertEquals(4.0, numberExp.eval(), 0);
	}
	
	@Test
	public void testSimpleOperation() throws Exception {
		// 4 3 +
		Token[] tokens = new Token[] {
			new Token(Token.TYPE_NUMBER, "4"),
			new Token(Token.TYPE_NUMBER, "3"),
			new Token(Token.TYPE_PLUS_MINUS, "+", 2)
		};
		Expression expr = factory.make(tokens);
		
		assertEquals(7.0, expr.eval(), 0);
	}
	
	@Test
	public void testMixedOperation() throws Exception {
		// 4 3 + 7 *
		Token[] tokens = new Token[] {
			new Token(Token.TYPE_NUMBER, "4"),
			new Token(Token.TYPE_NUMBER, "3"),
			new Token(Token.TYPE_PLUS_MINUS, "+", 2),
			new Token(Token.TYPE_NUMBER, "7"),
			new Token(Token.TYPE_MULT_DIV, "*", 3),
		};
		Expression expr = factory.make(tokens);
		assertEquals(49.0, expr.eval(), 0);
	}
	
	@Test
	public void testComplexOperation() throws Exception {
		// 4 3 * 8 2 / +
		Token[] tokens = new Token[] {
			new Token(Token.TYPE_NUMBER, "4"),	
			new Token(Token.TYPE_NUMBER, "3"),	
			new Token(Token.TYPE_MULT_DIV, "*", 3),	
			new Token(Token.TYPE_NUMBER, "8"),	
			new Token(Token.TYPE_NUMBER, "2"),	
			new Token(Token.TYPE_MULT_DIV, "/", 3),	
			new Token(Token.TYPE_PLUS_MINUS, "+", 2)
		};
		Expression expr = factory.make(tokens);
		assertEquals(16.0, expr.eval(), 0);
	}
	
	@Test
	public void testNegative() throws Exception {
		// 4 5 -
		Token[] tokens = new Token[] {
				new Token(Token.TYPE_NUMBER, "4"),	
				new Token(Token.TYPE_NUMBER, "5"),	
				new Token(Token.TYPE_PLUS_MINUS, "-", 2)
		};
		Expression expr = factory.make(tokens);
		assertEquals(-1.0, expr.eval(), 0);
	}
}
