package fr.nihilus.mathparser.syntax;

public class MultOperator extends Operator {

	public MultOperator(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double eval() {
		return left.eval() * right.eval();
	}
	
	@Override
	public String toString() {
		return left.toString() + "*" + right.toString();
	}
}
