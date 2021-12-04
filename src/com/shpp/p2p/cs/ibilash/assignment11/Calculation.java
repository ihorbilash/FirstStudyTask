package com.shpp.p2p.cs.ibilash.assignment11;

import java.util.ArrayList;

/**
 * takes array expression and process it and return result to first element arraylist .
 * contain methods calculation: +, -, /, *, ^, (), log2, log10, tan, atan, cos, sin .
 */
public class Calculation {
    /**
     * chance value to false if input incorrect
     */
    boolean correctInput = true;
    /**
     * array to mathematics expression
     */
   private ArrayList<String> formula;


    Calculation(ArrayList<String> formula) {
        this.formula = formula;

    }

    /**
     * gets array with  expression and calculates it
     *
     * @return return arraylist value
     */
    public ArrayList<String> calculation() {
        try {
                calculateLogFunction();
                calculatePower();
                calculateMultiplyDivision();
                calculatePlusMinus();

        } catch (NumberFormatException nfe) {
            System.out.println(" Not correct input");
            correctInput = false;
        }
        return formula;
    }

    /**
     * gets index and mark, send parameter to method "selectOperation" were
     * detect mark and calculates expression. result puts in array and delete two redundant elements
     *
     * @param i    index of mathematics mark
     * @param mark mark of operation
     */
    private void takeArgument(int i, char mark) {
        String result = selectOperation(Double.parseDouble(formula.get(i - 1)),
                Double.parseDouble(formula.get(i + 1)), mark);
        formula.set(i, result);
        formula.remove(i + 1);
        formula.remove(i - 1);
    }


    /**
     * go through the array and look for the logarithm, if we find we use the following elements for
     * calculations
     */
    private void calculateLogFunction() {
        String log = " ";
        int i = 0;
        while (i < formula.size()) {
            log = formula.get(i);
            if (log.length() > 1 && Character.isLetter(log.charAt(1))) {
                boolean minus = log.charAt(0) == '-' ? true : false;
                double value = Double.parseDouble(formula.get(i + 1));
                formula.set(i, calcLog(log, value, minus)); // calculate log and set result in array
                formula.remove(i + 1);
                i--;
            }
            i++;
        }
    }

    /**
     * go to array and search mark + or -, if find uses previous and next element for calculate
     */
    private void calculatePlusMinus() {

        int i = 0;
        while (i < formula.size()) {
            String temp = formula.get(i);
            char mark = temp.length() == 1 && (temp.charAt(0) == '+' ||
                    temp.charAt(0) == '-') ? temp.charAt(0) : '0'; //plus or minus
            if (mark != '0') {
                takeArgument(i, mark);
                i--;
            }
            i++;
        }
    }

    /**
     * go to array and search mark * or / , if find uses previous and next element for calculate
     */
    private void calculateMultiplyDivision() {

        int i = 0;
        while (i < formula.size()) {
            String temp = formula.get(i);
            char mark = temp.length() == 1 && (temp.charAt(0) == '*' ||
                    temp.charAt(0) == '/') ? temp.charAt(0) : '0'; //multiply or division
            if (mark != '0') {
                takeArgument(i, mark);
                i--;
            }
            i++;
        }
    }

    /**
     * go to array and search mark ^ , if find uses previous and next element for calculate
     */
    private void calculatePower() {

        int i = formula.size() - 1;
        while (i >= 0) {
            String temp = formula.get(i);
            char mark = temp.length() == 1 && temp.charAt(0) == '^' ? temp.charAt(0) : '0'; //power
            if (mark != '0') {
                takeArgument(i, mark);
            }
            i--;
        }
    }

    /**
     * gets two arguments and mark to calculate and return result to string
     *
     * @param first  first argument
     * @param second second argument
     * @param mark   mark to calculate
     * @return result value to string
     */
    private String selectOperation(double first, double second, char mark) {
        double result = 0;
        if (mark == '^') {
            result = first < 0 && second % 2 != 0 ? -Math.pow(Math.abs(first), second) : Math.pow(first, second);
        }
        if (mark == '*' || mark == '/') {
            result = mark == '*' ? first * second : first / second;
        }
        if (mark == '-' || mark == '+') {
            result = mark == '-' ? first - second : first + second;
        }
        return String.valueOf(result);
    }

    /**
     * calculate a function of a number and return the result
     *
     * @param log   string with function
     * @param value number
     * @param minus true - first char in string is minus, false - string normal
     * @return result
     */
    private String calcLog(String log, double value, boolean minus) {
        log = minus ? log.substring(1) : log;// if parameter minus true - cut first char in string log, false - leave log normal
        if (log == "log2") {
            return minus ? String.valueOf(-Math.log(value) / Math.log(2)) : String.valueOf(Math.log(value) / Math.log(2));
        }
        if (log == "log10") {
            return minus ? String.valueOf(-Math.log10(value)) : String.valueOf(Math.log10(value));
        }
        if (log == "sqrt") {
            return minus ? String.valueOf(-Math.sqrt(value)) : String.valueOf(Math.sqrt(value));
        }
        if (log.equals("atan")) {
            return minus ? String.valueOf(-Math.atan(value)) : String.valueOf(Math.atan(value));
        }
        if (log.equals("tan")) {
            return minus ? String.valueOf(-Math.tan(value)) : String.valueOf(Math.tan(value));
        }
        if (log.equals("sin")) {
            return minus ? String.valueOf(-Math.sin(value)) : String.valueOf(Math.sin(value));
        }
        if (log.equals("cos")) {
            return minus ? String.valueOf(-Math.cos(value)) : String.valueOf(Math.cos(value));
        }
        return " ";
    }

}
