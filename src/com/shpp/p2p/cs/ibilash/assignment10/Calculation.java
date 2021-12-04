package com.shpp.p2p.cs.ibilash.assignment10;

import java.util.ArrayList;

/**
 * takes array expression and process it and return result to first element arraylist .
 * contain methods calculation: +, -, /, *, ^ .
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
     * @return return arraylist value
     */
    public ArrayList<String> calculation() {
        try {
                calculatePower();
                calculateMultiplyDivision();
                calculatePlusMinus();

        } catch (NumberFormatException nfe) {
            System.out.println(" Incorrect input ");
            correctInput = false;
        }
        return formula;
    }

    /**
     * gets index and mark, send parameter to method "selectOperation" were
     * detect mark and calculates expression. result puts in array and delete two redundant elements
     * @param i  index of mathematics mark
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
     go to array and search mark ^ , if find uses previous and next element for calculate
     */
    private void calculatePower() {

        int i = formula.size() - 1;
        while (i >= 0) {
            String temp = formula.get(i);
            char mark = temp.length() == 1 && temp.charAt(0) == '^' ? temp.charAt(0) : '0'; // power
            if (mark != '0') {
                takeArgument(i, mark);
            }
            i--;
        }
    }

    /**
     * gets two arguments and mark to calculate and return result to string
     * @param first  first argument
     * @param second second argument
     * @param mark   mark to calculate
     * @return result value to string
     */
    private String selectOperation(double first, double second, char mark) {
        double result = 0;
        if (mark == '^') {
            result = first < 0 &&second%2!=0? -Math.pow(Math.abs(first), second) : Math.pow(first, second);
        }
        if (mark == '*' || mark == '/') {
            result = mark == '*' ? first * second : first / second;
        }
        if (mark == '-' || mark == '+') {
            result = mark == '-' ? first - second : first + second;
        }
        return String.valueOf(result);
    }

}
