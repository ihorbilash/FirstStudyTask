package com.shpp.p2p.cs.ibilash.assignment14;

import java.io.*;
import java.util.HashMap;

/**
 * unpacking zip file
 */
public class Unpack {
    /**
     * array contains data
     */
    private byte[] arrayData;

    /**
     * length of max bit sequence , cant be more then 8 bit
     */
    private int maxEncodingSequence;

    /**
     * contains encoding values and original bytes
     */
    private HashMap<String, Byte> hashMap = new HashMap<>();

    /**
     * contains dictionary length
     */
    private int sizeInt;
    /**
     * contains length of original bytes sequence
     */
    private long sizeLong;
    /**
     * name of incoming end outgoing files
     */
    private String outNameFile, inNameFile;
    /**
     * count incoming bytes
     */
    private int inPutBytes;
    /**
     * count outgoing bytes
     */
    int outBytes;

    /**
     * this string we puts encoding sequence of bit
     */
    private String basket = "";
    /**
     * number of bytes to writes size dictionary and length encoding bytes
     */
    private final int sizeLongInt = 12;

    /**
     * take only incoming and outgoing file name
     */
    Unpack(String str, String strOut) {
        this.outNameFile = strOut;
        this.inNameFile = str;

    }

    /**
     * translate encoding file: reading 1. length of dictionary; 2. length of original bytes sequence;
     * 3.read dictionary for recovery original bytes sequence.
     * After past events: reading parts of encoding bytes sequence, replaces to original bytes and writing into file,
     * while incoming file not the end.
     */
    int unpackProcess() {
        try (
                DataInputStream dis = new DataInputStream(new FileInputStream(inNameFile));
                DataOutputStream bos = new DataOutputStream(new FileOutputStream(outNameFile))) {
            sizeInt = dis.readInt();
            sizeLong = dis.readLong();
            arrayData = dis.readNBytes(sizeInt); //reads dictionary for recovery original bytes
            restoreDictionary();
            while (dis.available() > 0) {  //process reading parts of bytes, unzip and write in file
                arrayData = dis.readNBytes(64);
                writeBitsToString();
                byte[] b = recoveryData();
                bos.write(b);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        //result count of input bytes
        return inPutBytes + sizeLongInt + sizeInt;

    }

    /**
     * recovery dictionary right bytes sequence to recovery data
     */
    void restoreDictionary() {
        maxEncodingSequence = Integer.toBinaryString(arrayData.length / 2).length(); // max sequence encoding bits
        if (maxEncodingSequence > Byte.SIZE) {
            maxEncodingSequence = Byte.SIZE;
        }
        for (int i = 0; i < arrayData.length; i = i + 2) {   //recovery dictionary original bytes used hashmap
            String str = String.format("%" + maxEncodingSequence + "s", Integer.toBinaryString(arrayData[i + 1]))
                    .replace(" ", "0");
            if (str.length() > maxEncodingSequence) {
                str = str.substring(str.length() - maxEncodingSequence);
            }
            hashMap.put(str, arrayData[i]);
        }
    }

    /**
     * reads encoding sequence of bytes and replaces into string ,
     * after replaces encoding sequence into original bytes and writes byte array.
     */
    private byte[] recoveryData() {
        String[] array = new String[sizeLong > 3 ? basket.length() / maxEncodingSequence : (int) sizeLong];
        //divide string to part encoding sequence bits into byte for subsequent writes in array
        for (int i = 0, j = 0; j < array.length; i = i + maxEncodingSequence, j++) {
            int end = i + maxEncodingSequence;
            array[j] = basket.substring(i, end);
            if (end > basket.length() - maxEncodingSequence) {
                basket = basket.substring(end);
                break;
            }
        }
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < bytes.length; i++) {
            if (hashMap.containsKey(array[i])) { //replace encoding byte to original
                bytes[i] = hashMap.get(array[i]);
                outBytes++;
            }
        }
        return bytes;
    }

    /**
     * recovery data: writes bits sequence to string .
     */
    private void writeBitsToString() {
        inPutBytes += arrayData.length;
        for (int j = 0; j < arrayData.length; j++) {
            String str = String.format("%" + Byte.SIZE + "s", Integer.toBinaryString(arrayData[j]));   // translate byte to bit sequence
            if (str.length() > Byte.SIZE) {    // cut redundant sequence of bits
                str = str.substring(str.length() - Byte.SIZE);
            } else {
                str = str.replace(' ', '0');   // adding '0' if bits sequence less 8
            }
            basket += str;
        }
    }

}
