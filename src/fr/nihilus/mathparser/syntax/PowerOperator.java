package fr.nihilus.mathparser.syntax;

public class PowerOperator extends Operator {

	public PowerOperator(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double eval() {
		return Math.pow(left.eval(), right.eval());
	}
	
	@Override
	public String toString() {
		return left.toString() + "^" + right.toString();
	}

}
