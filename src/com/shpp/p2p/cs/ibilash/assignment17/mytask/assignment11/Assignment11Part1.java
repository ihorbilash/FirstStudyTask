package com.shpp.p2p.cs.ibilash.assignment17.mytask.assignment11;


/**
 * Program simply calculator. Gets n entrance in array args parameters:
 * first parameter: expression for calculates ; nest parameters - arguments.
 * On output gets result of expression.
 * Uses methods calculation: +, -, /, *, ^, (), log2, log10, tan, atan, cos, sin.
 * for example: 1+(2+3*(4+5-sin(45*cos(a))))/7 . a=-33
 */
public class Assignment11Part1 {

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
