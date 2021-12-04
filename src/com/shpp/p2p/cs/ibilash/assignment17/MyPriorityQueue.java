package com.shpp.p2p.cs.ibilash.assignment17;

import java.util.Iterator;

/**
 * Automatically expandable and sorting container, expands execution of all operations add, poll,peek, size, toString.
 * @param <T> the type of element
 */
public class MyPriorityQueue<T> implements Iterable<T> {
    /**
     * array for filling the elements
     */
    Comparable[] elements;
    /**
     *  default array size
     */
    private int defaultSize = 10;
    /**
     * count of elements in PriorityQueue
     */
    private int size = 0;

    /**
     * count of element in this array
     */
    public MyPriorityQueue() {
        elements = new Comparable[defaultSize];

    }
    /**
     * constructor initialize any original size in priorityQueue
     *
     * @param insertValueArray number of size
     */
    MyPriorityQueue(int insertValueArray) {
        if (insertValueArray <= 0) {
            throw new NegativeArraySizeException(" " + insertValueArray);
        }
        elements = new Comparable[insertValueArray];

    }


    /**
     * adding an element to the end of queue and sorted by the binary heap
     *
     * @param obj added object
     * @return if added - true, else  //IllegalStateException
     */
    public boolean add(T obj) {
        if (elements[elements.length - 1] != null) {
            throw new IllegalStateException("Queue is full");
        }
        if (obj == null) {
            throw new NullPointerException();
        }
        if (elements.length - 1 == size) {
            Comparable[] newArray = new Comparable[elements.length + elements.length / 2];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
        elements[size] = (Comparable) obj;
        checkParent(size);
        size++;
        return true;
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return element from head
     */
    public T peek() {
        if (elements[0] != null) {
            return (T) elements[0];
        } else
            return null;
    }

    /**
     * get element by head or returns null if this queue is empty.
     *
     * @return removed element
     */
    public T poll() {
        Object ob = elements[0];
        if (ob == null) {
            return null;
        }
        elements[0] = elements[size - 1];
        elements[size - 1] = null;
        size--;
        checkChild(0);
        return (T) ob;
    }

    /**
     * return data in string
     *
     * @return string with data
     */
    public String toString() {
        String str = "";
        for (int i = 0; i < size; i++) {
            str += " " + (T) elements[i].toString();
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
     * check if to added element smaller the element in parent - exchange element
     *
     * @param index index of elements
     */
    private void checkParent(int index) {
        int parent = (index -1) / 2;

        if (elements[index].compareTo(elements[parent]) < 0 ) { //if this object smaller the object in parameter
            Comparable temp = elements[index];
            elements[index] = elements[parent];
            elements[parent] = temp;
            checkParent(parent);
        } else
            return;
    }

    /**
     * check element by index and his child, if parent higher child  exchange elements
     *
     * @param index this index
     */
    private void checkChild(int index) {
        int minChild = selectMinChild(index);
        if (minChild < 0) {
            return;
        }
        if (elements[index].compareTo(elements[minChild]) > 0) {//if this object higher the object in parameter
            Comparable temp = elements[index];
            elements[index] = elements[minChild];
            elements[minChild] = temp;
            checkChild(minChild);
        }
    }

    /**
     * find the smaller value in the index of element and return the index
     * @param index  index of parent element
     * @return  index of element smaller element
     */
    private int selectMinChild(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;
        if (rightChild >= size) {
            if (leftChild < size) {
                return leftChild;
            }
        } else if (elements[rightChild].compareTo(elements[leftChild]) > 0) { //if this object higher the object in parameter
            return leftChild;
        } else {
            return rightChild;
        }
        return -1;
    }

    @Override
    /**
     * Returns an iterator over the elements in this queue
     */
    public Iterator<T> iterator() {

        Iterator<T> iterator = new Iterator<T>() {
            Object[] iteratorArr = elements;
            int index = 0;

            @Override
            public boolean hasNext() {

                return iteratorArr[index] != null;
            }

            @Override
            public T next() {

                return (T) iteratorArr[index++];
            }
        };
        return iterator;
    }

}


