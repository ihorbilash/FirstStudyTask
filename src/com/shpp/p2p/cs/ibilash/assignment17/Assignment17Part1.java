package com.shpp.p2p.cs.ibilash.assignment17;

/**
 * contains class checking MyHashMap, MyPriorityQueue .
 */
public class Assignment17Part1 {
    /**
     *  call classes TestHashMap and TestPriorityQueue who testing they work
     * @param args not used
     */
    public static void main(String[] args) {

        TestPriorityQueue testPriorityQueue = new TestPriorityQueue();
        testPriorityQueue.test();

        TestHashMap testHashMap = new TestHashMap();
        testHashMap.test();

    }
}
