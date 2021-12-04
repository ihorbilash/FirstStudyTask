package com.shpp.p2p.cs.ibilash.assignment12;

/**
 * The program for the search silhouette on the picture
 */
public class Assignment12Part1 {

    /**
     * in parameter gets file name, if parameter is empty  -  test.jpg.

     * @param args contains file name
     */
    public static void main(String[] args) {

        String str = args.length != 0 ? args[0] : "assets/test_pictures/3_1.png";  //test_pictures/2.png   test.jpg
        BasicOperations basicOperations = new BasicOperations(str);
        System.out.println("Silhouette: " + basicOperations.silhouette);

    }

}