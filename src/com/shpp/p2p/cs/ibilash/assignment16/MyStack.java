package com.shpp.p2p.cs.ibilash.assignment16;

import java.util.EmptyStackException;
import java.util.Iterator;


/**
 * Automatically expandable container, expands execution of all operations push, pop, size, toString,empty,search.
 * @param <T>the type of element
 */
public class MyStack<T> {
    /**
     * array contained this elements
     */
    private Object[] elements;
    /**
     * default size
     */
    private int defaultSize = 10;
    /**
     * count of element in stack
     */
    private int countElements = 0;

    /**
     * default constructor
     */
    MyStack() {
        elements = new Object[defaultSize];
    }

    /**
     * constructor with special size
     * @param insertValueArray this size
     */
    MyStack(int insertValueArray) {
        if (insertValueArray <= 0) {
            throw new NegativeArraySizeException(" " + insertValueArray);
        }
        elements = new Object[insertValueArray];
    }

    /**
     * put an object at the top of the stack
     *
     * @param obj object that we put
     */
    public void push(T obj) {
        if (elements.length - 1 == countElements) {
            Object[] newArray = new Object[elements.length + elements.length / 2];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
        elements[countElements] = obj;
        countElements++;
    }

    /**
     *take an object from the top of the stack and delete it
     *
     * @return the object that removed the top of the stack
     */
    public T pop() {
        if(empty()){
            throw new EmptyStackException();
        }
        Object obj = elements[countElements -1];
        countElements--;
        if (elements.length - 1 > countElements  && elements.length-1 > defaultSize) {
            Object[] newArray = new Object[countElements];
            System.arraycopy(elements, 0, newArray, 0, countElements);
            elements = newArray;
        }

        return (T) obj;
    }

    /**
     * if stack not empty return element from head else return exception
     * @return element from head
     */
    public T peek(){
            if(empty()){
                throw new EmptyStackException();
            }
        return (T)elements[countElements-1];
    }

    /**
     * number of items in the stack
     *
     * @return number of items in the stack
     */
    public int size() {
        return countElements;
    }

    /**
     * returns the data in the stack as a string
     *
     * @return string with data
     */
    public String toString() {
        String str = "";
        for (int i = countElements - 1; i >= 0; i--) {
            str += " " + (T) elements[i];
        }
        return str;
    }

    /**
     * check the queue for data
     *
     * @return if there is data returns false, else - true
     */
    public boolean empty() {
            return countElements==0;
    }

    /**
     *  search for an element and when it finds it returns its number from the vertex,
     *  if element is missing - return -1
     *
     * @param obj the object we want to find
     * @return number of element
     */
    public int search(T obj) {
        for (int i = countElements-1 , j = 0; i >= 0; i--, j++) {
            if (elements[i].equals((T) obj)) {
                return j+1;
            }
        }
        return -1;
    }

}
