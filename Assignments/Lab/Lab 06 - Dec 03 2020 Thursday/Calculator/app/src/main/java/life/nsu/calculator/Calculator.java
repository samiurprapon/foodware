package life.nsu.calculator;

public class Calculator {
    // Available operations
    public enum Operator {ADD, SUB, DIV, MUL, POW}

    // Addition
    public double addition(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }

    // Subtract
    public double subtraction(double firstOperand, double secondOperand) {
        return firstOperand - secondOperand;
    }

    // Divide
    public double division(double firstOperand, double secondOperand) {
        return firstOperand / secondOperand;
    }

    // Multiply
    public double multiplication(double firstOperand, double secondOperand) {
        return firstOperand * secondOperand;
    }

    // power
    public double power(double firstOperand, double secondOperand) {
        return Math.pow(firstOperand, secondOperand);
    }
}
