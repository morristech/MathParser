package fr.nihilus.mathparser.syntax;

public abstract class Operator implements Expression {

	protected Expression left, right;
	
	public Operator(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
}
