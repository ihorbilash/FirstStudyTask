package com.shpp.p2p.cs.ibilash.assignment17.assignment16_iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Automatically expandable container, expands execution of all operations add, poll,peek, size, toString.
 *
 * @param <T> the type of element
 */
public class MyQueue<T>implements Iterable<T> {
    /**
     * array contained this elements
     */
    private Object[] elements;
    /**
     * default size
     */
    private int defaultSize = 10;
    /**
     * count of element in queue
     */
    private int size = 0;

    /**
     * default constructor
     */
    public MyQueue() {
        elements = new Object[defaultSize];
    }

    /**
     * constructor with special size
     *
     * @param insertValueArray this size
     */
    MyQueue(int insertValueArray) {
        if (insertValueArray <= 0) {
            throw new NegativeArraySizeException(" " + insertValueArray);
        }
        elements = new Object[insertValueArray];
    }

    /**
     * add element to the end of the queue
     *
     * @param obj the item to be added
     * @return if added - true
     */
    public boolean add(T obj) {

        if (elements.length - 1 == size) {
            Object[] newArray = new Object[elements.length + elements.length / 2];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
        elements[size] = obj;
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

        T ob = (T) elements[0];
        size--;
        Object[] newArray = new Object[elements.length];
        if (size > defaultSize) {
            newArray = new Object[size];
        }
        System.arraycopy(elements, 1, newArray, 0, size);
        elements = newArray;
        return  ob;
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of this queue
     */
    public T peek() {
        checkEmptyQueue();
        return elements[0] != null ? (T) elements[0] : null;
    }

    /**
     * returns queued data as a string
     *
     * @return data as a string
     */
    public String toString() {
        String str = "";
        for (int i = 0; i < size; i++) {
            str += " " + (T) elements[i];
        }
        return str;
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

    @Override
    /**
     * Returns an iterator over the elements in this queue
     */
    public Iterator<T> iterator() {

        Iterator<T> iterator = new Iterator<T>() {
            Object []iteratorArr = elements;
            int index=0;
            @Override
            public boolean hasNext() {

                return  iteratorArr[index] != null;
            }

            @Override
            public T next() {

                return (T)iteratorArr[index++];
            }
        };
        return iterator;
    }
}
