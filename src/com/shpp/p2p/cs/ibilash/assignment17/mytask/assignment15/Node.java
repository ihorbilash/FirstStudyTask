package com.shpp.p2p.cs.ibilash.assignment17.mytask.assignment15;


/**
 * The node can have data, left and right descendants. Used to create a data tree
 */
public class Node implements Comparable<Node> {

    /** weight node  */
    int weight;
    /**value in node */
    Byte value;
    /** left descendant*/
    Node left;
    /** right descendant */
    Node right;
    /** structure for tree recovery  */
    String strStructure = "";

    /** write bytes in a line in the form of a bit sequence  */
    StringBuilder putByteToString = new StringBuilder();

    Node() { } //empty constructor

    /**
     * create node to adding value and weight
     * @param value unique byte
     * @param weight quantity of repetitions
     */
    Node(byte value, int weight) {
        this.weight = weight;
        this.value = value;
    }

    /**
     * create node with weight and left right relatives
     * @param weight general weight of nodes
     * @param left left node
     * @param right right node
     */
    Node(int weight, Node left, Node right) {
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    /**
     * deep tree traversal, construction of coded sequence
     *
     * @param tree  data tree
     * @param bytes byte for which build a bit sequence
     * @param sb    bit code of byte
     * @return bit sequence in the string
     */
     String findStringValue(Node tree, byte bytes, String sb) {

        if (tree.left != null) {
            String s = findStringValue(tree.left, bytes, sb + 0);
            if (!s.equals("")) {
                return s;
            }
        }
        if (tree.right != null) {
            String s = findStringValue(tree.right, bytes, sb + 1);
            if (!s.equals("")) {
                return s;
            }
        }
        if (tree.value != null && tree.value == bytes) {
            return sb;
        }
        return "";
    }

    /**
     * pass the tree using a sequence of bits to find the desired byte
     *
     * @param tree tree data
     * @param sb   sequence bite for byte
     * @return byte corresponding to the bit sequence
     */
     Byte restoreBytes(Node tree, StringBuilder sb) {

        if (tree.value != null) {
            return tree.value;
        } else {
            if (sb.length() != 0 && sb.charAt(0) == '0') {
                Byte b = restoreBytes(tree.left, sb.deleteCharAt(0));
                if (b != null) {
                    return b;
                }
            }
            if (sb.length() != 0 && sb.charAt(0) == '1') {
                Byte b = restoreBytes(tree.right,sb.deleteCharAt(0));
                if (b != null) {
                    return b;
                }
            }
        }
        return value;
    }

    /**
     * pass the data tree and write the sequence of bits depending on the node
     *
     * @param tree tree data
     * @param str  sequence of bite
     */
     void buildStructure(Node tree, String str) {
        if (tree.value != null) {
            StringBuilder tempStrValue = new StringBuilder(Integer.toBinaryString(tree.value.byteValue()));
            putByteToString.append(addZero(tempStrValue));        // recorded string with unique bytes in the desired sequence
            str = "0";
        } else {
            str = "1";
        }
        strStructure += str;
        if (tree.left != null) {
            buildStructure(tree.left, str);
        }
        if (tree.right != null) {
            buildStructure(tree.right, str);
        }
    }

    /**
     * add or cut bits for the desired length
     *
     * @param tempStrValue sequence of bite
     * @return sequence of bits to write to bytes
     */
    private StringBuilder addZero(StringBuilder tempStrValue) {
        StringBuilder resultS = new StringBuilder();
        if (tempStrValue.length() < Byte.SIZE) {
            while (tempStrValue.length() < Byte.SIZE) {               //adds or removes zeros from the sequence
                tempStrValue.insert(0, '0');
            }
            resultS.append(tempStrValue);
        } else {
            if (tempStrValue.length() > Byte.SIZE) {
                resultS.append(tempStrValue.substring(tempStrValue.length() - Byte.SIZE));
            }
        }
        return resultS;
    }


    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.weight,o.weight);
    }
}
