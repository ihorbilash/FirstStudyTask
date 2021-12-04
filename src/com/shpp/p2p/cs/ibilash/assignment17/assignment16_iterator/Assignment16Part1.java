package com.shpp.p2p.cs.ibilash.assignment17.assignment16_iterator;

/**
 * contains class checking MyArrayList, MyLinkedList, MyStack, MyQueue .
 */

public class Assignment16Part1 {

    /**
     *  call classes TestArrayList,TestLinkedList,TestStack,TestQueue who testing work of my collections
     * @param args not used
     */
    public static void main(String[] args) {

        TestArrayList testArrayList = new TestArrayList();
        testArrayList.testing();

        TestLinkedList testLinkedList = new TestLinkedList();
        testLinkedList.testing();

        TestStack testStack = new TestStack();
        testStack.testingStack();

        TestQueue testQueue = new TestQueue();
        testQueue.testingQueue();

    }
}
