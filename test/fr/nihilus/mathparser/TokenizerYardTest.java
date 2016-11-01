package fr.nihilus.mathparser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.nihilus.mathparser.tokenizer.Token;
import fr.nihilus.mathparser.tokenizer.Tokenizer;

public class TokenizerYardTest {

	private Tokenizer tokenizer;
	private ShuntingYard shuntingYard;

	public TokenizerYardTest() {
		tokenizer = new Tokenizer();
		shuntingYard = new ShuntingYard();
	}

	@Before
	public void setup() throws Exception {
		tokenizer.reset();
		shuntingYard.reset();
	}

	@Test
	public void testSingleNumber() throws Exception {
		tokenizer.prepare("409.67");
		shuntingYard.process(tokenizer.nextToken());
		String expr = shuntingYard.finish().outputToString();

		assertFalse(tokenizer.hasMoreTokens());
		assertEquals("409.67", expr);
	}

	@Test
	public void testSimpleOperation() throws Exception {
		tokenizer.prepare("4+3");
		while (tokenizer.hasMoreTokens()) {
			Token tok = tokenizer.nextToken();
			shuntingYard.process(tok);
		}
		shuntingYard.finish();
		assertEquals("4 3 +", shuntingYard.outputToString());
	}
	
	@Test
	public void testParenthesis() throws Exception {
		tokenizer.prepare("(4+3)*7");
		while (tokenizer.hasMoreTokens()) {
			Token tok = tokenizer.nextToken();
			shuntingYard.process(tok);
		}
		shuntingYard.finish();
		assertEquals("4 3 + 7 *", shuntingYard.outputToString());
	}
	
	@Test
	public void testSpacedString() throws Exception {
		tokenizer.prepare("  4 + 3 ");
		while (tokenizer.hasMoreTokens()) {
			Token tok = tokenizer.nextToken();
			shuntingYard.process(tok);
		}
		shuntingYard.finish();
		assertEquals("4 3 +", shuntingYard.outputToString());
	}
}
