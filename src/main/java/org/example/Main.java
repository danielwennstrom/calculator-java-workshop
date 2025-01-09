package org.example;

import java.util.*;

/*  A calculator using the shunting yard algorithm (https://en.wikipedia.org/wiki/Shunting_yard_algorithm)
    to parse simple expressions using the four core arithmetic operators (+, -, *, /)

    tl;dr expressions like 2 + 4 * 2 are parsed as 2 4 * 2 + to ensure that the order of operations is correct
*/

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter expression (3 + (3 * 2), etc.), or \"q\" to exit: ");
            String expr = scanner.nextLine();

            if (expr.equals("q")) {
                System.out.println("Exiting.");
                break;
            }

            try {
                double result = evaluate(expr);
                System.out.println("Result: " + result);
            }
            catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }

    public static double evaluate(String expression) {
        List<String> tokens = tokenize(expression);
        Deque<String> postfix = toPostfix(tokens);

        return evaluatePostfix(postfix);
    }

    public static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        expression = expression.replaceAll("\\s+", "");

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '-' || c == '+') {
                if (i == 0 || "+-*/(".indexOf(expression.charAt(i - 1)) != -1) {
                    number.append(c);
                }
                else {
                    if (!number.isEmpty()) {
                        tokens.add(number.toString());
                        number.setLength(0);
                    }
                    tokens.add(Character.toString(c));
                }
            }
            else if (Character.isDigit(c) || c == '.') {
                number.append(c);
            }
            else if ("+-*/()".indexOf(c) != -1) {
                if (!number.isEmpty()) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }

                tokens.add(Character.toString(c));
            }
            else if (!Character.isWhitespace(c)) {
                throw new IllegalArgumentException("Unexpected character: " + c);
            }
        }

        if (!number.isEmpty()) {
            tokens.add(number.toString());
        }

        return tokens;
    }

    public static Deque<String> toPostfix(List<String> tokens) {
        Deque<String> output = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();
        Map<String, Integer> precedence = Map.of("+", 1, "-", 1, "*", 2, "/", 2);

        for (String token : tokens) {
            if (token.matches("[+-]?\\d+(\\.\\d+)?")) {
                output.add(token);
            }
            else if ("+-*/".contains(token)) {
                while (!operators.isEmpty() && precedence.getOrDefault(operators.peek(), 0) >= precedence.get(token)) {
                    output.add(operators.pop());
                }

                operators.push(token);
            }
            else if (token.equals("(")) {
                operators.push(token);
            }
            else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (operators.isEmpty() || !operators.pop().equals("(")) {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    public static double evaluatePostfix(Deque<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();

        while (!postfix.isEmpty()) {
            String token = postfix.poll();

            if (token.matches("[+-]?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            }
            else if ("+-*/".contains(token)) {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(switch (token) {
                    case "+" -> a + b;
                    case "-" -> a - b;
                    case "*" -> a * b;
                    case "/" -> a / b;
                    default -> throw new IllegalStateException("Unexpected value: " + token);
                });
            }
        }

        return stack.pop();
    }
}