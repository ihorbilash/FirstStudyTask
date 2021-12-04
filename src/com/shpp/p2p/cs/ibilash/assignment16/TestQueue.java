package com.shpp.p2p.cs.ibilash.assignment16;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Class for testing MyQueue, contains method who check MyQueue.
 */
public class TestQueue {

    MyQueue<Integer> myQueue = new MyQueue<>();
    Queue<Integer> queue = new LinkedList<>();
    private final int ADD_VALUE = 1000;

    public void testingQueue() {

        addQueue();
        removeElement();
        removeToNull();
        addQueue();
    }

    /**
     * filling the queue with elements and checking the size along the way after adding each element
     */
    private void addQueue() {
        boolean mark = true;
        for (int i = 0; i < ADD_VALUE; i++) {
            myQueue.add(i);
            queue.add(i);
            if (myQueue.size() != queue.size()) {
                mark = false;
            }
        }
        System.out.print("Checking the queue size : ");
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fail");
    }

    /**
     * checking methods peek and poll, get element of peek and delete element of poll, check after each iteration
     */
    private void removeElement() {
        boolean markPoll = true;
        boolean markPeek = true;

        System.out.print("Check method peek  : ");
        while (myQueue.size() > 0) {
            if (!queue.peek().equals(myQueue.peek())) {
                markPeek = false;
            }
            if (!queue.poll().equals(myQueue.poll())) {
                markPoll = false;
            }
        }
        if (markPeek) {
            System.out.println("Pass");
        } else System.out.println("Fail");
        System.out.print("Check method poll  : ");
        if (markPoll) {
            System.out.println("Pass");
        } else System.out.println("Fail");

    }

    /**
     * read items from the queue until the queue is empty, read an element from an empty queue and see the reaction,
     * add null as element of queue
     */
    private void removeToNull() {
        System.out.print("Queue empty, check method peek : ");
        try {
            myQueue.peek();

            System.out.println("Fail");
        } catch (NoSuchElementException nsee) {
            System.out.println("Pass");
        }
        System.out.print("Queue empty, check method size : ");
        if (myQueue.size() == 0) {
            System.out.println("Pass");
        } else System.out.println("Fail");

        System.out.print("Check method poll if queue is empty : ");
        try {
            myQueue.poll();
            System.out.println("Fail");
        } catch (NoSuchElementException nsee) {
            System.out.println("Pass");
        }

        System.out.print("Add null to the queue and then check the size : ");
        int someSize = 10;
        while (myQueue.size() < someSize) {
            myQueue.add(null);
        }
        if (myQueue.size() == someSize) {
            System.out.println("Pass");
        } else {
            System.out.println("Fall");
        }
        myQueue = new MyQueue<>();
    }


}


