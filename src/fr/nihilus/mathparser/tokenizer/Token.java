package fr.nihilus.mathparser.tokenizer;

/**
 * 
 * @author Thib
 *
 */
public class Token implements Comparable<Token> {
	
	public static final int TYPE_NUMBER = 0;
	public static final int TYPE_RIGHT_PAR = 1;
	public static final int TYPE_LEFT_PAR = 2;
	public static final int TYPE_ARG_SEPARATOR = 5;
	public static final int TYPE_PLUS_MINUS = 8;
	public static final int TYPE_MULT_DIV = 9;
	public static final int TYPE_FUNCTION = 10;
	
	private int type;
	private String value;
	private int prec;
	
	public Token(int type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public Token(int type, String value, int precedence) {
		this(type, value);
		this.prec = precedence;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getValue() {
		return value;
	}
	
	public int getPrecedence() {
		return this.prec;
	}

	@Override
	public int compareTo(Token another) {
		return Integer.compare(this.prec, another.prec);
	}
	
	public boolean isHigherPrecedence(Token another) {
		return prec > another.prec;
	}
	
	public boolean isLessPrecedenceOrEqual(Token another) {
		return prec <= another.prec;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}