package com.shpp.p2p.cs.ibilash.assignment14;

import java.io.*;
import java.util.HashMap;

/**
 * archiving the input file
 */
public class Archive {

    private final int NUMBER_OF_BITS = 128;
    /**
     * array of incoming bytes
     */
    private byte[] byteArray;

    /**
     * dictionary of basic bytes
     */
    private HashMap<Byte, Integer> mapInt = new HashMap<>();
    /**
     * array of dictionary to stored encoding sequence
     */
    private byte[] dictionary;
    /**
     * counts sequence of encoding bytes
     */
    private int countArchByte;
    /**
     * name of incoming and outgoing files
     */
    private String outPutName, inPutName;
    /**
     * length of max bit sequence , cant be more then 8 bit
     */
    private int maxEncodingSequence;
    /**
     * count bit in byte
     */
    private int quantityBite = 8;
    /**
     * encoding value original byte
     */
    private int encodingVal = 0;
    /**
     * number of bytes to writes size dictionary and length encoding bytes
     */
    final int sizeLongInt = 12;
    /**
     * counts incoming bytes
     */
    int countInPutByte;

    /**
     * reads all file in parts  , writes hashmap and create dictionary unique bytes
     *
     * @param inputName  name of incoming file
     * @param outPutName name of outgoing file
     */
    Archive(String inputName, String outPutName) throws IOException {
        this.outPutName = outPutName;
        this.inPutName = inputName;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inPutName));
        while (bis.available() > 0) {
            byteArray = bis.readNBytes(NUMBER_OF_BITS);
            addMap();    // runs bytes from hashmap and create map of unique bytes
        }
        creatingDictionary(); // create dictionary of unique bytes
    }

    /**
     * writes to encoding file: 1. length of dictionary; 2.quantity of encoding bytes;
     * 3.dictionary of unique bytes; . After this reads original file again, replaces original bytes
     * on encoding and writes to file in right sequence.
     */
    int compressionData() {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inPutName));
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(outPutName));
             BufferedOutputStream bos = new BufferedOutputStream(dos)) {

            dos.writeInt(dictionary.length); //size dictionary
            dos.writeLong(countInPutByte);  //length encoding bytes
            dos.write(dictionary);  //this dictionary

            String basket = ""; //string to write bit sequence of encoded bytes

            while (bis.available() > 0) {  // reads and replaces original bytes to encoding
                byteArray = bis.readNBytes(NUMBER_OF_BITS);
                basket += writeNewByteSequence();
                String sb = "";

                if (bis.available() == 0) { //if not enough add '0' in the end of sequence
                    sb = basket;
                    while (sb.length() % quantityBite != 0) {
                        sb += '0';
                    }
                } else {   // if read process not end  - writes bytes only enough quantity of bits,
                    // leave the rest to next write
                    int remainder = basket.length() % quantityBite;  // value of rested bit
                    sb = basket.substring(0, basket.length() - remainder); //string of writes in bytes
                    basket = basket.substring(basket.length() - remainder); //rest bits
                }
                for (int i = 0; i < sb.length(); i = i + quantityBite) {  // translate bits in byte and writes to file
                    countArchByte++;
                    bos.write((byte) Integer.parseInt(sb.substring(i, i + quantityBite), 2));  //writes byte to file
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return dictionary.length + countArchByte + sizeLongInt;
    }

    /**
     * add bytes in hashmap, value key +1 of new unique byte
     */
    private void addMap() {
        for (byte value : byteArray) {
            countInPutByte++;
            if (!mapInt.containsKey(value)) {
                mapInt.put(value, encodingVal++);
            }
        }
    }

    /**
     * creates dictionary sequence unique bytes and new bytes
     */
    private void creatingDictionary() {
        maxEncodingSequence = Integer.toBinaryString(mapInt.size()).length(); // max bit sequence in hashmap
        if (maxEncodingSequence > quantityBite) {
            maxEncodingSequence = quantityBite;
        }
        dictionary = new byte[mapInt.size() * 2]; //sequence of original and encoding bit
        int i = 0;
        for (byte key : mapInt.keySet()) {
            int value = mapInt.get(key);
            dictionary[i] = key;
            i++;
            dictionary[i] = (byte) value;
            i++;
        }
    }


    /**
     * create string sequence of encoding bit used hashmap and original array byte
     *
     * @return encoding sequence in string
     */
    private String writeNewByteSequence() {
        String string = "";

        for (byte b : byteArray) {
            String format = "%0" + maxEncodingSequence + "d";
            if (mapInt.containsKey(b)) {
                String str = Integer.toBinaryString(mapInt.get(b));
                int toInt = Integer.parseInt(str);
                string += String.format(format, toInt);
            }
        }
        return string;
    }

}
