package fr.nihilus.mathparser.tokenizer;

interface Patterns {

	/** Nombre entier ou décimal. */
	static final String NUMBER = "[0-9]+(?:\\.[0-9]+)?";
	/** Signe PLUS ou MOINS. */
	static final String PLUS_MINUS = "[+-]";
	/** Signe MULTIPLIER ou DIVISER. */
	static final String MULT_DIV = "[*/]";
	/** Parenthèse ouvrante. */
	static final String LEFT_PAR = "\\(";
	/** Parenthèse fermante. */
	static final String RIGHT_PAR = "\\)";
}
