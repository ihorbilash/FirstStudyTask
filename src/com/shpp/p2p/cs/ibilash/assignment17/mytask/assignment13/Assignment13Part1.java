package com.shpp.p2p.cs.ibilash.assignment17.mytask.assignment13;


/**
 * The program for the search silhouette on the picture
 */
public class Assignment13Part1 {
    /**
     * In parameter gets file name, if parameter is empty  -  test.jpg.
     * with ReadImage reading file and writes it in array of pixels
     * detect background and return it in class Erosion, where separate silhouette.
     * in FindInBreadth begin find silhouette
     *
     * @param args contains file name
     */
    public static void main(String[] args) {

        String str = args.length != 0 ? args[0] : "assets/test_pictures/tst_46.jpeg";//if args is absent apply "test.jpg"
        BasicOperations basicOperations = new BasicOperations(str);
        System.out.println("silhouette: "+basicOperations.silhouette);

    }

}