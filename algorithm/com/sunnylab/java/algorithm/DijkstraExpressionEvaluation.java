package com.sunnylab.java.algorithm;

import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * This class uses two stacks to evaluate arithmetic expressions.
 * It assumes that the expression is fully parenthesized, with numbers
 * and operators separated by whitespace.
 * source: Algorithms 4th Edition Robert Sedgewick
 */
public class DijkstraExpressionEvaluation {
	public static void main(String[] args) {
		Stack<String> operators = new Stack<String>();
		Stack<Double> values = new Stack<Double>();
		Scanner scanner = new Scanner(System.in);
		String line;
		while (!(line = scanner.nextLine()).isEmpty()) {
			Scanner scanner2 = new Scanner(line);
			while (scanner2.hasNext()) {
				//read token, push if operator
				String s = scanner2.next();
				if (s.equals("(")) {
					
				} else if (s.equals("+") || 
						s.equals("-") ||
						s.equals("*") ||
						s.equals("/") ||
						s.equals("sqrt")) {
					operators.push(s);
				} else if (s.equals(")")) { // Pop, evaluate, and push result if token is ")". 
					String operator = operators.pop();
					double v = values.pop();
					if (operator.equals("+")) {v += values.pop();}
					else if (operator.equals("-")) {v -= values.pop();}
					else if (operator.equals("*")) {v *= values.pop();}
					else if (operator.equals("/")) {v /= values.pop();}
					else if (operator.equals("sqrt")) {v = Math.sqrt(v);}
					values.push(v);
				} else {//token not operator or parenthesis: push double value
					values.push(Double.parseDouble(s));
				} 
			}
			System.out.println("=" + values.pop());
			scanner2.close();
			
		}
		scanner.close();
	}	
}
