package com.shpp.p2p.cs.ibilash.assignment15;

import java.io.*;
import java.util.*;

/**
 * reads the encoded file, creates a tree based on the data
 * and a new coded sequence of bits in the string ... the string is cut into pieces and written to a file
 */
public class Archive {
    /**
     * array incoming data
     */
    private byte[] byteArray;

    /**
     * for saves numbers of repeat data
     */
    private HashMap<Byte, Integer> hashMap = new HashMap<>();

    /**
     * name of out file
     */
    private String outName;

    /**
     * length of structure
     */
    private int lengthStructure;
    /**
     * two bytes of structure and one byte length of end bit and count of encoding data
     */
    int outSize = 3;

    /**
     * gets name of oncoming file and name of out file
     *
     * @param str       name of incoming file
     * @param outPutStr name of outgoing file
     */
    Archive(String str, String outPutStr) throws IOException {
        this.outName = outPutStr;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(str));
        byteArray = bis.readAllBytes();

    }

    /**
     * we create a hashmap of original bytes, we create a tree and on a tree we encode original bytes
     * writes in new file
     */
    int compressData() {
        fillMapRepeatValues();
        StringBuilder sb = buildFinishString(hashMap);
        writeFile(sb);
        return byteArray.length;
    }

    /**
     * writes a map with byte keys to values - number of repetitions
     */
    private void fillMapRepeatValues() {
        for (byte b : byteArray) {
            if (hashMap.containsKey(b)) {
                hashMap.put(b, hashMap.get(b) + 1); //counter of same values
            } else
                hashMap.put(b, 0);
        }
        if (hashMap.size() < 2) {
            System.err.println(" For Huffman Archive file must contain minimum two different bytes");
            System.exit(0);
        }
    }

    /**
     * create a tree based on the hashmap of the original bytes, create a new sequence of bits and write it to the string
     *
     * @param map sequence of original bytes
     * @return string for write in file
     */
    private StringBuilder buildFinishString(HashMap<Byte, Integer> map) {

        Node tree = buildTree(map).peek();
        HashMap<Byte, String> bs = createNewCodeMap(tree, map);//encoded bytes, sequence
        tree.buildStructure(tree, ""); //data in the global variable
        StringBuilder valueOfStructure = tree.putByteToString; //byte sequence
        lengthStructure = tree.strStructure.length();
        StringBuilder sb = encodingString(bs); //real sequence of byte to encoding string
        StringBuilder finishString = new StringBuilder();  //string for final bit sequence recording
        finishString.append(tree.strStructure);  //write structure
        finishString.append(valueOfStructure);  //encoding elements
        finishString.append(sb); // coded data
        return finishString;
    }

    /**
     * takes a string of bits and writes them to a byte file
     *
     * @param byteSequence string with bit sequence
     */
    private void writeFile(StringBuilder byteSequence) {
        int addZeros = 0; //how many zeros did we write
        while (byteSequence.length() % Byte.SIZE != 0) {
            addZeros++;
            byteSequence.append('0');
        }
        try (FileOutputStream fos = new FileOutputStream(outName);
             DataOutputStream dos = new DataOutputStream(fos);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            dos.writeShort(lengthStructure);  // structure
            dos.writeByte(addZeros); //length end byte

            for (int i = 0; i < byteSequence.length(); i = i + Byte.SIZE) {
                bos.write((byte) Integer.parseInt(byteSequence.substring(i, i + Byte.SIZE), 2));  //a sequence of encoded bits
                outSize++;
            }
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }

    }

    /**
     * build a tree on the basis of original bytes and frequency of their repetitions
     *
     * @param map HashMap original bytes
     * @return an array of nodes with data and descendants
     */
    private PriorityQueue<Node> buildTree(HashMap<Byte, Integer> map) {
        PriorityQueue<Node> tree = new PriorityQueue<>();
        for (byte b : map.keySet()) {
            tree.add(new Node(b, map.get(b)));  //we fill in the tree on an example the value lies in a cell on quantity of repetitions
        }
        //sorted the nodes by weight (number of repetitions) and build a tree until there is 1 element left
        while (tree.size() != 1) {
            Node l = tree.poll();
            Node r = tree.poll();
            Node newLeaf = new Node(l.weight + r.weight, l, r);
            tree.add(newLeaf);
        }
        return tree;
    }

    /**
     * create a string of encoded data using an array of bytes and hashmaps
     *
     * @param newCodeMap hash maps with bytes and a new sequence of bits
     * @return a sequence of encoded bytes in StringBuilder
     */
    private StringBuilder encodingString(HashMap<Byte, String> newCodeMap) {
        StringBuilder codedStr = new StringBuilder();
        for (byte b : byteArray) {
            if (newCodeMap.containsKey(b)) {
                codedStr.append(newCodeMap.get(b));
            }
        }
        return codedStr;
    }

    /**
     * create a hashmap of the original bytes and a new bit sequence
     *
     * @param forest data tree
     * @param map    hashmap of original bytes
     * @return map with the original bytes and a new bit sequence
     */
    private HashMap<Byte, String> createNewCodeMap(Node forest, HashMap<Byte, Integer> map) {
        HashMap<Byte, String> newCodesForBytes = new HashMap<>();
        for (byte b : map.keySet()) {
            newCodesForBytes.put(b, forest.findStringValue(forest, b, ""));//find needed sequence with recursion help
        }
        return newCodesForBytes;
    }

}

