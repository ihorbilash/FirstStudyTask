package com.shpp.p2p.cs.ibilash.assignment17.mytask.assignment15;


/**
 * the program archives incoming files to archives, you can use the -a flag as the first argument,
 * argument 2 and 3 names archived and "where archived" file.
 * unpacking is performed on the .par extension or the -u flag with arguments 2 and 3
 */
public class Assignment15Part1 {
    /**
     * contained Class ProcessSelection who defines archive or unpack .
     * @param args input parameter , mark and file name
     */
    public static void main(String[] args) {

             new ProcessSelection(args); //defines archive or unpack and go to process

    }


}
