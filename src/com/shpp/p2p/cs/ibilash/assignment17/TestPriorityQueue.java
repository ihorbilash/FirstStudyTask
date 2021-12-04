package com.shpp.p2p.cs.ibilash.assignment17;

import acm.util.RandomGenerator;

import java.util.PriorityQueue;

/**
 * Class for testing PriorityQueue, contains method add, peek, poll, size, toString
 */
public class TestPriorityQueue {

    MyPriorityQueue<Integer> myPriorityQueue = new MyPriorityQueue<>();
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

    private final int MAX_SIZE = 100;


    public void test() {

        addPeekPoll();
        addSize();
        removeElement();
        for(int i: myPriorityQueue){
            System.out.print(" * "+i);
        }


    }

    /**
     *  check method add, Peek,Poll.
     */
    private void addPeekPoll(){
        RandomGenerator rg = new RandomGenerator();
        while (myPriorityQueue.size() < MAX_SIZE) {
            int i = rg.nextInt(-MAX_SIZE, MAX_SIZE);
            myPriorityQueue.add(i);
            priorityQueue.add(i);

        }
        System.out.print("Check method Peek: ");
        boolean markPoll = true;
        boolean markPeek = true;
        while (myPriorityQueue.size() > 0) {
            if(!myPriorityQueue.peek().equals(priorityQueue.peek())){
                markPeek = false;
            }
            if(!myPriorityQueue.poll().equals(priorityQueue.poll())){
                markPoll = false;
            }
        }
        if (markPeek) {
            System.out.println("Pass");
        } else System.out.println("Fail");
        System.out.print("Check method Poll: ");
        if (markPoll) {
            System.out.println("Pass");
        } else System.out.println("Fail");
    }


    /**
     * filling the queue with elements and checking the size along the way after adding each element
     */
    private void addSize() {
        boolean mark = true;
        for (int i = 0; i < MAX_SIZE; i++) {
            myPriorityQueue.add(i);
            priorityQueue.add(i);
            if (myPriorityQueue.size() != priorityQueue.size()) {
                mark = false;
            }
        }
        System.out.print("Check method Add, Size : ");
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fail");

    }

    /**
     * check method peek and pool
     */
    private void removeElement() {

        boolean mark = true;
        int firstSize = myPriorityQueue.size();
        System.out.print("Проверка method peek and pool : ");
        while (myPriorityQueue.size() == firstSize / 2) {
            if (!priorityQueue.poll().equals(myPriorityQueue.poll())) {
                mark = false;
            }
        }
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fail");


    }

}
