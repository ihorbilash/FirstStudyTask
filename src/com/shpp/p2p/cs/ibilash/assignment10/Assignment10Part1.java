package com.shpp.p2p.cs.ibilash.assignment10;


/**
 * Program simply calculator. Gets n entrance in array args parameters:
 * first parameter: expression for calculates ; nest parameters - arguments.
 * On output gets result of expression.
 * Uses methods calculation: +, -, /, *, ^ .
 */
public class Assignment10Part1 {

    /**
     * in main class worked class  ExpressionProcessing, where calculating our expression
     *
     * @param args in zero basket of array puts formula, in enough - arguments
     */
    public static void main(String[] args) {

        new ExpressionProcessing(args);
        System.out.println(ExpressionProcessing.resultValue);
    }
}
