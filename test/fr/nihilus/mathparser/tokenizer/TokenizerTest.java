package fr.nihilus.mathparser.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TokenizerTest {

	private Tokenizer tokenizer;
	
	public TokenizerTest() {
		tokenizer = new Tokenizer();
	}
	
	@Before
	public void setup() throws Exception {
		tokenizer.reset();
	}
	
	@Test
	public void testNumber() throws Exception {
		List<Token> tokens = tokenizer.prepare("37.546").tokenize().output();
		assertEquals(1, tokens.size());
		Token numberToken = tokens.get(0);
		assertNotNull(numberToken);
		
		assertEquals("37.546", numberToken.getValue());
		assertEquals(Token.TYPE_NUMBER, numberToken.getType());
	}
	
	@Test
	public void testOperator() throws Exception {
		List<Token> tokens = tokenizer.prepare("+-*/").tokenize().output();
		assertEquals(4, tokens.size());
		
		assertEquals("+", tokens.get(0).getValue());
		assertEquals("-", tokens.get(1).getValue());
		assertEquals("*", tokens.get(2).getValue());
		assertEquals("/", tokens.get(3).getValue());
		
		assertEquals(Token.TYPE_PLUS_MINUS, tokens.get(0).getType());
		assertEquals(Token.TYPE_PLUS_MINUS, tokens.get(1).getType());
		assertEquals(Token.TYPE_MULT_DIV, tokens.get(2).getType());
		assertEquals(Token.TYPE_MULT_DIV, tokens.get(3).getType());
	}
	
	@Test
	public void testNumberOperator() throws Exception {
		List<Token> tokens = tokenizer.prepare("3.6*74").tokenize().output();
		assertEquals(3, tokens.size());
		
		assertEquals("3.6", tokens.get(0).getValue());
		assertEquals("*", tokens.get(1).getValue());
		assertEquals("74", tokens.get(2).getValue());
		
		assertEquals(Token.TYPE_NUMBER, tokens.get(0).getType());
		assertEquals(Token.TYPE_MULT_DIV, tokens.get(1).getType());
		assertEquals(Token.TYPE_NUMBER, tokens.get(2).getType());
	}
	
	@Test
	public void testParenthesis() throws Exception {
		List<Token> tokens = tokenizer.prepare("(3+4)/7").tokenize().output();
		assertEquals(7, tokens.size());
		
		assertEquals("(", tokens.get(0).getValue());
		assertEquals("3", tokens.get(1).getValue());
		assertEquals("+", tokens.get(2).getValue());
		assertEquals("4", tokens.get(3).getValue());
		assertEquals(")", tokens.get(4).getValue());
		assertEquals("/", tokens.get(5).getValue());
		assertEquals("7", tokens.get(6).getValue());
		
		assertEquals(Token.TYPE_LEFT_PAR, tokens.get(0).getType());
		assertEquals(Token.TYPE_NUMBER, tokens.get(1).getType());
		assertEquals(Token.TYPE_PLUS_MINUS, tokens.get(2).getType());
		assertEquals(Token.TYPE_NUMBER, tokens.get(3).getType());
		assertEquals(Token.TYPE_RIGHT_PAR, tokens.get(4).getType());
		assertEquals(Token.TYPE_MULT_DIV, tokens.get(5).getType());
		assertEquals(Token.TYPE_NUMBER, tokens.get(6).getType());
	}
	
	@Test
	public void testSerial() throws Exception {
		tokenizer.prepare("4+3");
		Token token = tokenizer.nextToken();
		assertEquals(Token.TYPE_NUMBER, token.getType());
		assertEquals("4", token.getValue());
		
		token = tokenizer.nextToken();
		assertEquals(Token.TYPE_PLUS_MINUS, token.getType());
		assertEquals("+", token.getValue());
		
		token = tokenizer.nextToken();
		assertEquals(Token.TYPE_NUMBER, token.getType());
		assertEquals("3", token.getValue());
		
		assertFalse(tokenizer.hasMoreTokens());		
	}
	
	@Test
	public void testSpacedString() throws Exception {
		tokenizer.prepare("  4 + 3 ");
		Token tok = tokenizer.nextToken();
		assertEquals(Token.TYPE_NUMBER, tok.getType());
		
		tok = tokenizer.nextToken();
		assertEquals(Token.TYPE_PLUS_MINUS, tok.getType());
		
		tok = tokenizer.nextToken();
		assertEquals(Token.TYPE_NUMBER, tok.getType());
		
	}
}
