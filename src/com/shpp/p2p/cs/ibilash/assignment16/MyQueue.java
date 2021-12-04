package com.shpp.p2p.cs.ibilash.assignment16;

import java.util.NoSuchElementException;

/**
 * Automatically expandable container, expands execution of all operations add, poll,peek, size, toString.
 *
 * @param <T> the type of element
 */
public class MyQueue<T> extends MyLinkedList{

    /**
     * count of element in queue
     */
    private int size = 0;



    /**
     * add element to the end of the queue
     *
     * @param obj the item to be added
     * @return if added - true
     */
    public boolean add(T obj) {
        addLast(obj);
        size++;
        return true;
    }

    /**
     * receives and deletes the queue header or returns null if this queue is empty.
     *
     * @return deleted item
     */
    public T poll() {
        checkEmptyQueue();
        size--;
        return (T) pollFirst();
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of this queue
     */
    public T peek() {
        checkEmptyQueue();
        return size!=0 ? (T) peekFirst() : null;
    }


    /**
     * count of element in queue
     */
    public int size() {
        return size;
    }

    /**
     * if queue is empty throws an exception
     */
    private void checkEmptyQueue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
    }

}
