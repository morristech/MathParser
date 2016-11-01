package fr.nihilus.mathparser.syntax;

import java.util.Deque;
import java.util.LinkedList;

import fr.nihilus.mathparser.tokenizer.Token;

public class ExpressionFactory {
	
	private Deque<Expression> stack;
	
	public ExpressionFactory() {
		stack = new LinkedList<>();
	}
	
	public Expression make(Token[] postfixedTokens) {
		
		for (Token token : postfixedTokens) {
			//System.out.println("Read token: " + token);
			Expression expr = tokenToExpression(token);
			//System.out.println("Resulting expression: " + expr.toString());
			stack.push(expr);
		}
		Expression syntaxTree = stack.pop();
		stack.clear();
		
		return syntaxTree;
	}
	
	private Expression tokenToExpression(Token tok) {
		switch (tok.getType()) {
		case Token.TYPE_NUMBER:
			return new Number(tok.getValue());
		case Token.TYPE_PLUS_MINUS:
			return plusOrMinus(tok);
		case Token.TYPE_MULT_DIV:
			return multOrDiv(tok);
			default:
				throw new UnsupportedOperationException("Unsupported token: " + tok);
		}
	}
	
	private Operator plusOrMinus(Token tok) {
		Expression right = stack.pop();
		Expression left = stack.pop();
		
		if ("+".equals(tok.getValue())) {
			return new PlusOperator(left, right);
		} else if ("-".equals(tok.getValue())) {
			return new MinusOperator(left, right);
		} else {
			throw new UnsupportedOperationException("Unknown operator: " + tok.getValue());
		}
	}
	
	private Operator multOrDiv(Token tok) {
		Expression right = stack.pop();
		Expression left = stack.pop();
		
		if ("*".equals(tok.getValue())) {
			return new MultOperator(left, right);
		} else if ("/".equals(tok.getValue())) {
			return new DivOperator(left, right);
		} else {
			throw new UnsupportedOperationException("Unknown operator: " + tok.getValue());
		}
	}
}
