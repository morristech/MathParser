package fr.nihilus.mathparser.syntax;

public class Number implements Expression {

	private double value;
	
	public Number(String value) {
		this.value = Double.parseDouble(value);
	}
	
	@Override
	public double eval() {
		return value;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}

}
