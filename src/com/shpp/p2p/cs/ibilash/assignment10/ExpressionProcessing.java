package com.shpp.p2p.cs.ibilash.assignment10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class contains methods to preparation expression for calculates. Delete spaces, changes coma to point,
 * translate to array or ease to calculate and parsing symbols to compile correct expression.
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
    ExpressionProcessing(String[] args) {
        try {
            if (args.length != 0) {

                addArgument(args); // writing arguments in hashmap
                String expression = removeComaLineGap(args[0]);// cleaned formula to garbage
                ArrayList<String> formula = addFormulaInArray(expression);// add expression to arraylist
                formula = installArgs(formula); //replace argument in formula and checking minus

                if (checkingDivideNull(formula)) {  // checking divide 0
                    throw new Exception("You can't divide 0");
                }

                Calculation calc = new Calculation(formula);
                ArrayList<String> result = calc.calculation(); // parsed formula calculating
                resultValue = Double.parseDouble(result.get(0));
                resultValue = calc.correctInput ? resultValue : 0;
            } else {
                throw new Exception("formula is empty");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * method for change or cleaning or gives error char ,_@!#$%&:;<>`~
     *
     * @return clean expression
     */
    private String removeComaLineGap(String string) {
        string = string.replaceAll(",", ".");
        string = string.replaceAll("_", "");

        String[] str = string.split(" ");
        string = "";
        for (String s : str) {
            string += s;
        }
        checkCorrectInput(string);

        return string;
    }

    /**
     * check string to invalid character
     *
     * @param string string where invalid character may contained
     */
    private void checkCorrectInput(String string) {

        for (int i = 0; i < string.length() - 1; i++) {
            if (string.charAt(i) == '.' && string.charAt(i + 1) == '.') {
                System.out.println("incorrect input. in formula contains two points in a row");
                System.exit(0);
            }
            if (Character.isDigit(string.charAt(i)) && Character.isLetter(string.charAt(i + 1))) {
                System.out.println("incorrect input: " + string.charAt(i) + string.charAt(i + 1));
                System.exit(0);
            }
            if ("@!#$%&:;<>`~".contains(Character.toString(string.charAt(i)))) {
                System.out.println("incorrect input. in formula contains some of symbol @!#$%&:;<>`~");
                System.exit(0);
            }
        }

    }


    /**
     * checking minus values, that is two marks in row
     *
     * @param formula input expression
     * @return prepared expression
     */
    private ArrayList<String> checkMinus(ArrayList<String> formula) {

        if (formula.get(0).length() == 1 && formula.get(0).charAt(0) == '-') { //if minus first symbol
            String temp = formula.get(1);
            temp = temp.charAt(0) == '-' ? temp.substring(1) : "-" + temp; //if in first element gone two marks "-"
            formula.set(0, temp);
            formula.remove(1);

        }
        for (int i = 0; i < formula.size() - 1; i++) {
            char one = formula.get(i).length() == 1 ? formula.get(i).charAt(0) : '0';
            char two = formula.get(i + 1).length() == 1 ? formula.get(i + 1).charAt(0) : '0';

            if ("*/^+-".contains(Character.toString(one)) && two == '-') { // two marks in row
                String value = formula.get(i + 2);
                formula.remove(i + 2);
                value = value.charAt(0) != '-' ? '-' + value : value.substring(1);
                formula.set(i + 1, value);
                i--;
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
        return checkMinus(formula);
    }

    /**
     * in string separate individual mathematics symbol,numbers, arguments and writes in arraylist
     *
     * @param str string formula
     * @return arraylist expression
     */
    private ArrayList<String> addFormulaInArray(String str) {
        ArrayList<String> formula = new ArrayList<>();

        Pattern pattern = Pattern.compile("([a-z]{1,30})|(\\W)|([0-9]+[.,]?[0-9]*)"); // separates to various values
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
    private void addArgument(String[] args) {
        String string = "";
        for (int i = 1; i < args.length; i++) {
            string += removeComaLineGap(args[i]); // clean garbage
        }
        Pattern pattern = Pattern.compile("\\D{1,30}\\W-?[0-9]+[.,]?[0-9]*");
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
    private void addValueInMap(String str) {
        String[] strArr = str.split("=");
        map.put(strArr[0], strArr[1]);
    }

    /**
     * gets arraylist and checking presence mark divide and divisor
     *
     * @param formula input array expression
     * @return if divisor null - return true
     */
    private boolean checkingDivideNull(ArrayList<String> formula) {
        boolean check = false;
        for (int i = 0; i < formula.size(); i++) {
            if (formula.get(i).charAt(0) == '/') {
                check = Double.parseDouble(formula.get(i + 1)) == 0 ? true : false;
            }
        }
        return check;
    }


}
