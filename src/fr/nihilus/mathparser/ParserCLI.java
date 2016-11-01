package fr.nihilus.mathparser;

import java.text.ParseException;
import java.util.Scanner;

public class ParserCLI {

	private boolean isRunning;
	private MathParser parser;

	public static void main(String[] args) {
		new ParserCLI().start();
	}
	
	public ParserCLI() {
		parser = new MathParser();
	}

	private void start() {
		isRunning = true;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please type in a math expression, or type enter to quit.");
		
		while (isRunning) {
			System.out.print("> ");
			String line = sc.nextLine();

			if (!line.isEmpty()) {
				try {
					parser.update(line);
					double result = parser.parse();
					System.out.println(result);
				} catch (ParseException e) {
					System.out.println(e.getMessage());
				} finally {
					parser.reset();
				}
			} else {
				stop();
			}
		}
		System.out.println("Bye !");
		sc.close();
	}

	private void stop() {
		isRunning = false;
	}

}
