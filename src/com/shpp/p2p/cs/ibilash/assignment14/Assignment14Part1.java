package com.shpp.p2p.cs.ibilash.assignment14;


/**
 * The program executed archive or unzip input file. For file choice using args .
 * The first parameter can come -a or -u :it mean archive or unpacking and next
 * parameter is input and output file.
 * If incoming parameter only name file we will archiving it file(out file will have extension .par in the end)
 * If incoming parameter only name file and file extension had .par  we will unzip file in basic view
 */
public class Assignment14Part1 {
    /**
     * name of input file
     */
   private String inName = "";
    /**
     * name of output file
     */
   private String outName = "";

    /**
     * contained method takeParameter who defines archive or unpack and used classes Archive and Unpack .
     *
     * @param args input parameter , mark and file name
     */
    public static void main(String[] args) {
        new ProcessSelection(args);

    }

}

