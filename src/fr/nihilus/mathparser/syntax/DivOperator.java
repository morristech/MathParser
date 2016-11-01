package fr.nihilus.mathparser.syntax;

public class DivOperator extends Operator {

	public DivOperator(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double eval() {
		return left.eval() / right.eval();
	}
	
	@Override
	public String toString() {
		return left.toString() + "/" + right.toString();
	}
}
