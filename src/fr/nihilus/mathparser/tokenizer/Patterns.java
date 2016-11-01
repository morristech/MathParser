package fr.nihilus.mathparser.tokenizer;

interface Patterns {

	/** Nombre entier ou d�cimal. */
	static final String NUMBER = "[0-9]+(?:\\.[0-9]+)?";
	/** Signe PLUS ou MOINS. */
	static final String PLUS_MINUS = "[+-]";
	/** Signe MULTIPLIER ou DIVISER. */
	static final String MULT_DIV = "[*/]";
	/** Parenth�se ouvrante. */
	static final String LEFT_PAR = "\\(";
	/** Parenth�se fermante. */
	static final String RIGHT_PAR = "\\)";
}
