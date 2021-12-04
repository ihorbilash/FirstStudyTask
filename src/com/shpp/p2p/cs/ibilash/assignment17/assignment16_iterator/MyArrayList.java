package com.shpp.p2p.cs.ibilash.assignment17.assignment16_iterator;

import java.util.Iterator;

/**
 * Automatically expandable container, array, expands execution of all operations
 * in container.
 *
 * @param <T> the type of element
 */
public class MyArrayList<T>implements Iterable<T> {
    /**
     * default size
     */
    private int defaultSize = 10;
    /**
     * array contained this elements
     */
    private Object[] elements;
    /**
     * count of element in this array
     */
    private int countElement = 0;

    /**
     * constructor initialize any original size in array
     *
     * @param insertValueArray number of size
     */
    MyArrayList(int insertValueArray) {
        if (insertValueArray <= 0) {
            throw new NegativeArraySizeException(" " + insertValueArray);
        }
        this.elements = new Object[insertValueArray];

    }

    /**
     * constructor initialize default size
     */
    public MyArrayList() {
        elements = new Object[defaultSize];
    }

    /**
     * add object in array
     *
     * @param odj this object
     */
    public void add(T odj) {
        if (elements[countElement] == null) {
            expandArray();
            elements[countElement] = odj;
            countElement++;
        }
    }

    /**
     * add object in array by index
     *
     * @param index index in array
     * @param obj   object what we need to insert
     */
    public void add(int index, T obj) {
        if (countElement < index || 0 > index) {
            throw new IndexOutOfBoundsException(" Size: " + countElement + " index: " + index);
        }
        expandArray();
        Object[] tempArr = new Object[elements.length - index];
        System.arraycopy(elements, index, tempArr, 0, tempArr.length);
        elements[index] = obj;
        System.arraycopy(tempArr, 0, elements, index + 1, tempArr.length - 1);
        countElement++;
    }

    /**
     * get element in array by index
     *
     * @param index element by index
     * @return element in array by index
     */
    public T get(int index) {
        checkIndexBounds(index);
        return (T) elements[index];

    }

    /**
     * search similar element in array, if found return this index if not found return -1
     *
     * @param obj object of search
     * @return index of search object
     */
    public int indexOf(T obj) {
        for (int i = 0; i < countElement ; i++) {
            if (obj != null && elements[i].equals(obj)) {
                return i;
            } else {
                if (obj == null && elements[i] == null) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * search the object in array , when found return true, else - false.
     *
     * @param obj searching object
     * @return find object - true, not found - false.
     */
    public boolean contains(T obj) {
        return indexOf(obj) >= 0;
    }

    /**
     * remove object in array by index
     *
     * @param index number of element
     * @return removed object
     */
    public T remove(int index) {
        checkIndexBounds(index);
        Object obj = elements[index];
        deleteElementFromArray(index);
        return (T) obj;

    }

    /**
     * remove object in array if its present
     *
     * @param object this object
     * @return if remove object - true, else - false
     */
    public boolean remove(T object) {

        for (int index = 0; index < elements.length; index++) {
            if (object.equals(elements[index])) {
                deleteElementFromArray(index);
                return true;
            }
        }
        return false;
    }

    /**
     * returns data as a string
     *
     * @return data in string
     */
    public String toString() {
        String str = "";
        for (int i = 0; i < countElement; i++) {
            str += " " + (T) elements[i];
        }
        return str;
    }

    /**
     * return counts of element in array
     *
     * @return array size
     */
    public int size() {

        return countElement;
    }

    /**
     * remove all data in array and insert default size
     */
    public void clear() {
        countElement = 0;
        elements = new Object[defaultSize];
    }

    /**
     * replace old element to new by index, return old element
     *
     * @param index index of element in array
     * @param obj   new object
     * @return old object element
     */
    public T set(int index, T obj) {
        checkIndexBounds(index);
        Object temp = elements[index];
        elements[index] = obj;

        return (T) temp;
    }

    /**
     * delete element from array by index
     * @param index this index
     */
    private void deleteElementFromArray(int index) {
        Object[] newArray = new Object[elements.length - 1];
        System.arraycopy(elements, 0, newArray, 0, index);
        System.arraycopy(elements, index + 1, newArray, index, elements.length - index - 1);
        countElement--;
        elements = newArray;
    }

    /**
     * check index bounds of array
     * @param index this index
     */
    private void checkIndexBounds(int index) {
        if (countElement <= index || 0 > index) {
            throw new IndexOutOfBoundsException(" Size: " + countElement + " index: " + index);
        }
    }

    /**
     * check numbers of element and expand array if it necessary
     */
    private void expandArray() {
        if (countElement == elements.length - 1) {
            Object[] newArray = new Object[elements.length + elements.length / 2];//increase the size of array
            System.arraycopy(elements, 0, newArray, 0, elements.length); // rewrite array
            elements = newArray;
        }
    }


    @Override
    /**
     * Returns an iterator over the elements in this arrayList
     */
    public Iterator<T> iterator() {

        Iterator<T> iterator = new Iterator<T>() {
            Object []iteratorArr = elements;
            int index=0;
            @Override
            public boolean hasNext() {

                return iteratorArr[index]!=null;
            }

            @Override
            public T next() {

                return (T)iteratorArr[index++];
            }
        };
        return iterator;
    }
}

