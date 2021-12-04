package com.shpp.p2p.cs.ibilash.assignment15;

import java.io.*;
import java.util.LinkedList;

/**
 * 1. takes a file, reads the first 2 and 1 bytes as the length of the structure and the final bits.
 * 2. restores the tree using the structure and original bytes.
 * 3. from the coded sequence we restore the original data
 */
public class Unpacking {

    /**
     * counter of out byte
     */
    int countOutByte;
    /**
     * the array contains the structure, the original bytes, and the encoded bits
     */
    private byte[] decompressionArray;

    /**
     * the name of the file in which we will write the unzipped file
     */
    private String outPutStr;


    /*** size of structure */
    private int sizeStructure;

    /**
     * how much element we need to dell in the end
     */
    private int countElement;

    /**
     * count of original bytes
     */
    private int countValue = 0;

    /**
     * structure for recovery Tree
     */
    private String structure;

    /**
     * array of original bytes sequence
     */
    private LinkedList<Byte> originalBytes = new LinkedList<>();


    /**
     * takes the name of the archive file and the archive file, reads bytes with table sizes, dictionary and data.
     *
     * @param str       name of input file
     * @param outPutStr name of decoding outgoing file
     */
    Unpacking(String str, String outPutStr) {
        this.outPutStr = outPutStr;
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(str));
            sizeStructure = dis.readShort();
            countElement = dis.readByte();
            decompressionArray = dis.readAllBytes();
        } catch (IOException exc) {
            System.out.println("error of reads file");
        }
    }

    /**
     * Reads the dictionary, the table. Replaces the data with the original and writes to a file
     */
    int unpackingProcess() {
        final int importantBytes = 3;//two bytes of structure and one byte length of end bit
        StringBuilder stringBuilder = readEncodingByte();
        restoreStructure(stringBuilder);
        Node tree = new Node();
        recoveryTree(tree);
        restoreOriginalFile(stringBuilder, tree);
        return decompressionArray.length + importantBytes;
    }

    /**
     * rewrite the bytes in a string, cut for 8 bit pieces
     */
    private StringBuilder readEncodingByte() {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : decompressionArray) {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toBinaryString(b));
            while (sb.length() < Byte.SIZE) {
                sb.insert(0, '0');  //add 0 if sequence not complete
            }
            stringBuilder.append(sb.substring(sb.length() - Byte.SIZE, sb.length()));
        }
        //remove the bits at the end (those added when writing)
        stringBuilder.delete(stringBuilder.length() - countElement, stringBuilder.length());

        return stringBuilder;
    }

    /**
     * define the structure, add the original bytes to the Linckedlist
     *
     * @param sb string with structure, original bits and coded sequence
     */
    private void restoreStructure(StringBuilder sb) {
        structure = sb.substring(0, sizeStructure);
        for (int i = 0; i < structure.length(); i++) {
            char ch = structure.charAt(i);
            if (ch == '0') {
                countValue++;                   // determine the number of original bytes
            }
        }
        //we fill the List with the data in the necessary sequence
        for (int i = sizeStructure; i < (countValue * Byte.SIZE) + sizeStructure; i = i + Byte.SIZE) {
            originalBytes.addLast((byte) Integer.parseInt(sb.substring(i, i + Byte.SIZE), 2));
        }
    }

    /**
     * build the recovered tree and then collect data from it
     *
     * @param tree the root to which we will add nodes
     */
    private void recoveryTree(Node tree) {
        if (structure.length() == 0) {
            return;
        }
        if (structure.charAt(0) == '1') {
            structure = structure.substring(1);
            tree.left = new Node();
            tree.right = new Node();
            recoveryTree(tree.left);
            recoveryTree(tree.right);
        } else {
            structure = structure.substring(1);
            tree.value = originalBytes.pollFirst();

        }
    }


    /**
     * we go on a tree by help of the coded sequence ,restore bytes and write in file
     *
     * @param sb   data
     * @param tree restored Tree
     */
    private void restoreOriginalFile(StringBuilder sb, Node tree) {
        StringBuilder data = new StringBuilder(sb.substring((countValue * Byte.SIZE) + sizeStructure));// locate the coded sequence of bits
        StringBuilder element = new StringBuilder();
        int firstIndex = 0;

        try (FileOutputStream fos = new FileOutputStream(outPutStr);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            for (int i = 0; i <= data.length(); i++) {
                element.append(data.substring(firstIndex, i));
                Byte b = tree.restoreBytes(tree, element);
                if (b != null) {
                    bos.write(b);      //  we write the coded sequence in file
                    element = new StringBuilder();
                    firstIndex = i;
                    countOutByte++;
                }
            }
        } catch (IOException exc) {
            System.out.println("Error writes file");
        }

    }


}


