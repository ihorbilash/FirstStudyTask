package com.shpp.p2p.cs.ibilash.assignment11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contains methods to preparation expression for calculates. Delete spaces, changes coma to point,
 * translate to array or ease to calculate and parsing symbols to compile correct expression.
 * Have checking to brackets.
 */
public class ExpressionProcessing {

    /**
     * data from arguments
     */
     private HashMap<String, String> map = new HashMap<>();
    /**
     * result of the calculation
     */
     static double resultValue;
    /**
     *  checking formula to correct input and parsing.
     * @param args input formula and arguments
     */
     ExpressionProcessing(String[]args){
         try {
             if (args.length != 0) {
                 addArgument(args); // writing arguments in hashmap
                 String expression = removeComaLineGap(args[0]);// cleaned formula to garbage
                 ArrayList<String> formula = addFormulaInArray(expression);// add expression to arraylist
                 formula = installArgs(formula);//replace argument in formula and checking minus
                 if (checkingDivideNull(formula)) {   // check divide on 0
                     throw new Exception("You can't divide 0");
                 }
                 if (checkingBracket(formula)) {  // check brackets presents
                     formula = cutBracketExpression(formula); // opened brackets and calculating
                 }
                 Calculation calc = new Calculation(formula);
                 ArrayList<String> result = calc.calculation();//after parsed - formula calculation

                 resultValue = calc.correctInput ? Double.parseDouble(result.get(0)) : 0;// get result after check


             } else {
                 throw new Exception("formula is empty");
             }
         } catch (Exception e) {
             System.out.println(e);
         }
     }

    /**
     * method for removing brackets and spaces, change coma to point
     *
     * @return clean expression
     */
    private String removeComaLineGap(String string) {
        string = string.replaceAll(",", ".");
        string = string.replaceAll("_", "");
        string = string.replaceAll(" ", "");

        checkingCorrectInput(string);

        return string;
    }

    /**
     * check string to invalid character
     *
     * @param string string where invalid character may contained
     */
    private void checkingCorrectInput(String string) {
        int counterBracketsLeft = 0;
        int counterBracketsRight = 0;
        for (int i = 0; i < string.length() - 1; i++) {
            if (string.charAt(i) == '(') {
                counterBracketsLeft++;
            }
            if (string.charAt(i+1) == ')') {
                counterBracketsRight++;
            }
            if (string.charAt(i) == '.' && string.charAt(i + 1) == '.' ||
                    i > 0 && string.charAt(i) == '.' && "+-/*^".contains(Character.toString(string.charAt(i - 1)))
                    || string.charAt(i) == '.' && "+-/*^".contains(Character.toString(string.charAt(i + 1)))) {
                System.out.println("incorrect dot enter");
                System.exit(0);
            }
            if (Character.isLetter(string.charAt(i)) && Character.isDigit(string.charAt(i + 1)) ||
                    Character.isLetter(string.charAt(i + 1)) && Character.isDigit(string.charAt(i))) {
                System.out.println("incorrect input. Letter and Digit together");
            }
            if ("@!#$%&:;<>`~".contains(Character.toString(string.charAt(i)))) {
                System.out.println("incorrect input. in formula contains some of symbol @!#$%&:;<>`~");
                System.exit(0);
            }
        }
        if (counterBracketsLeft != counterBracketsRight) {
            System.out.println("incorrect input Brackets");
            System.exit(0);
        }

    }

    /**
     * checking minus values, that is two marks in row
     *
     * @param formula input expression
     * @return prepared expression
     */
    private ArrayList<String> checkMinus(ArrayList<String> formula) {
        //if first symbol is minus
        if (formula.get(0).length() == 1 && formula.get(0).charAt(0) == '-') {
            String temp = formula.get(1);
            temp = temp.charAt(0) == '-' ? temp.substring(1) : "-" + temp; //if in first element gone two marks "-" / or not
            formula.set(0, temp);
            formula.remove(1);
        }
        for (int i = 0; i < formula.size() - 1; i++) {
            char one = formula.get(i).length() == 1 ? formula.get(i).charAt(0) : '0';
            char two = formula.get(i + 1).length() == 1 ? formula.get(i + 1).charAt(0) : '0';
            //two marks in row
            if ("*/^+-(".contains(Character.toString(one)) && two == '-') {
                String value = formula.get(i + 2);
                formula.remove(i + 2);
                value = value.charAt(0) != '-' ? '-' + value : value.substring(1);
                formula.set(i + 1, value);
            }
        }
        return formula;
    }

    /**
     * replaces arguments from values in hashmap
     *
     * @param formula expression to checks on presents of arguments
     * @return expression ready to calculates
     */
    private ArrayList<String> installArgs(ArrayList<String> formula) {
        for (int i = 0; i < formula.size(); i++) {
            String s = formula.get(i);
            if (map.containsKey(s)) {
                formula.set(i, map.get(formula.get(i)));
            }
        }
        checkFormula(formula); // check formula after added argument
        return checkMinus(formula);
    }

    /**
     * check the correctness of the submission to the power and numbers of the formula
     *
     * @param formula formula check
     */
    private void checkFormula(ArrayList<String> formula) {
        for (int i = 0; i < formula.size() - 1; i++) {
            if (formula.get(i).equals(".")) {
                System.out.println("incorrect input, number has more one dot");
                System.exit(0);
            }
            if (formula.get(i).length() > 2 && Character.isLetter(formula.get(i).charAt(2)) && formula.get(i + 1).charAt(0) == '^') {
                System.out.println("incorrect input: " + formula.get(i) + formula.get(i + 1) + formula.get(i + 2) + "\n" +
                        "please enter something like: cos(2)^2");
                System.exit(0);
            }
        }
    }

    /**
     * in string separate individual mathematics symbol,numbers, arguments and writes in arraylist
     *
     * @param str string formula
     * @return arraylist expression
     */
    public ArrayList<String> addFormulaInArray(String str) {
        ArrayList<String> formula = new ArrayList<>();

        Pattern pattern = Pattern.compile("([a-zA-Z]{1,15})|(\\W)|([0-9]+[.,]?[0-9]*)"); // separates to various values
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            formula.add(str.substring(matcher.start(), matcher.end()));
        }
        return formula;
    }

    /**
     * searching arguments and add in hashmap
     *
     * @param args input array
     */
    public void addArgument(String[] args) {
        String string = "";
        for (int i = 1; i < args.length; i++) {
            string += removeComaLineGap(args[i]); //clean garbage
        }
        Pattern pattern = Pattern.compile("([a-zA-Z]{1,15})\\W-?[0-9]+[.,]?[0-9]*");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            addValueInMap(string.substring(matcher.start(), matcher.end()));
        }
    }

    /**
     * separates key and value , adding in hashmap
     *
     * @param str string where separates key and values
     */
    public void addValueInMap(String str) {
        String[] strArr = str.split("=");
        map.put(strArr[0], strArr[1]);
    }

    /**
     * gets arraylist and checking presence mark divide and divisor
     *
     * @param formula input array expression
     * @return if divisor null - return true
     */
    public boolean checkingDivideNull(ArrayList<String> formula) {

        for (int i = 0; i < formula.size(); i++) {
            if (formula.get(i).charAt(0) == '/' && Character.isDigit(formula.get(i + 1).charAt(0))) {
                if (Double.parseDouble(formula.get(i + 1)) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check expression on brackets
     *
     * @param formula array to be checked
     * @return if find bracket - true, else - false
     */
    public boolean checkingBracket(ArrayList<String> formula) {
        for (int i = 0; i < formula.size(); i++) {
            if (formula.get(i).charAt(0) == '(') {
                return true;
            }
        }
        return false;
    }

    /**
     * find first right bracket and cut expression since right bracket to left bracket.
     * Next step is send expression to calculate and insert result to expression place.
     *
     * @param formula expression were searching brackets
     * @return expression without brackets
     */
    public ArrayList<String> cutBracketExpression(ArrayList<String> formula) {

        for (int i = 0; i < formula.size(); i++) {
            if (formula.get(i).charAt(0) == ')') {
                int point2 = i;
                while (formula.get(i).charAt(0) != '(') {
                    i--;
                }
                int point1 = i;
                Calculation calc = new Calculation(cutPartOfExpression(formula, point1 + 1, point2 - 1)); // cut and calculate
                formula.set(point1, calc.calculation().get(0));  // insert result what was in 0 basket of array
                for (int del = point1 + 1, j = point1 + 1; j <= point2; j++) { //delete from basic array calculated expression
                    formula.remove(del);
                }
            }
            formula = checkMinus(formula);  // check of minus then opening brackets
        }
        return formula;
    }

    /**
     * cut out part of basic array in brackets and create new arraylist
     *
     * @param formula basic array were cut out part of expression
     * @param point1  first element
     * @param point2  end element
     * @return new array with cut expression
     */
    private ArrayList<String> cutPartOfExpression(ArrayList<String> formula, int point1, int point2) {
        ArrayList<String> newExpression = new ArrayList<>();
        for (int i = point1; i <= point2; i++) {
            newExpression.add(formula.get(i));
        }
        return newExpression;
    }


}
