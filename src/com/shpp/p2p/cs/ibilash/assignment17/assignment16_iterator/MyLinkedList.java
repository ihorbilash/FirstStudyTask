package com.shpp.p2p.cs.ibilash.assignment17.assignment16_iterator;

import java.util.Iterator;

/**
 * Container,each element has a link to the previous and next elements,
 * expands execution of all operations in container,addFirst,addLast,add(index,obj),pollFirst,pollLast,peekFirst,
 * peekLast,get,set,remove, toString
 *
 * @param <T> the type of element
 */
public class MyLinkedList<T> implements Iterable<T>{

    /**
     * count of element in this array
     */
    private int countElement = 0;
    /**
     * class object to access the first element
     */
    private Node<T> first;
    /**
     * class object to access the last element
     */
    private Node<T> last;


    /**
     * returns the number of objects in the list
     *
     * @return size list
     */
    public int size() {
        return countElement;
    }

    /**
     * add a new element to the beginning of the list
     *
     * @param obj the object that we add to the list
     */
    public void addFirst(T obj) {
        Node<T> second = first;
        Node<T> newElement = new Node<>(obj, null, first);
        first = newElement;
        if (last == null) {
            last = newElement;
        } else {
            second.previous = newElement;
        }
        countElement++;
    }


    /**
     * add a new element to the end of the list
     *
     * @param obj object what we add to the list
     */
    public void addLast(T obj) {
        Node<T> temp = last;
        Node<T> newElement = new Node<>(obj, last, null);
        last = newElement;
        if (first == null) {
            first = newElement;
        } else {
            temp.next = newElement;
        }
        countElement++;
    }

    /**
     * add a new element to the list by index
     *
     * @param index this index
     * @param obj   this add element
     */
    public void add(int index, T obj) {
        if (index == 0) {
            addFirst(obj);
            return;
        }
        if (index == countElement) {
            addLast(obj);
            return;
        }
        if (countElement < index || index < 0) {
            throw new IndexOutOfBoundsException(" Size: " + countElement + " index: " + index);
        }
        Node temp = returnElementFromIndex(index); // return element by index
        Node newNode = new Node(obj, temp.previous, temp);
        temp.previous.next = newNode;
        temp.previous = newNode;
        countElement++;
    }

    /**
     * returns the first item in the list and deletes it
     *
     * @return first element of the list
     */
    public T pollFirst() {
        Node<T> fElem = first;
        if (first != null) {
            Node<T> temp = first.next;
            if (temp != null) {
                temp.previous = null;
            } else {
                last = temp;
            }
            first = temp;
            countElement--;
        } else {
            return null;
        }

        return (T) fElem.obj;
    }

    /**
     * returns the last item in the list and deletes it
     *
     * @return last item of list
     */
    public T pollLast() {
        Node<T> lElem = last;
        if (last != null) {
            Node<T> temp = last.previous;
            if (temp != null) {
                temp.next = null;
            } else {
                first = temp;
            }
            last = temp;
            countElement--;
        } else {
            return null;
        }
        return (T) lElem.obj;
    }

    /**
     * returns the last item in the list
     *
     * @return last item in the list
     */
    public T peekLast() {
        return last == null ? null : (T) last.obj;
    }

    /**
     * returns the first item in the list
     *
     * @return first item in the list
     */
    public T peekFirst() {
        return first == null ? null : (T) first.obj;
    }

    /**
     * returns list element by index
     *
     * @param index index of element
     * @return this element
     */
    public T get(int index) {
        if (index == 0) {
            return peekFirst();
        }
        if (index == countElement - 1) {
            return peekLast();
        }
        checkIndexBounds(index);
        Node temp = returnElementFromIndex(index);
        return (T) temp.obj;
    }

    /**
     * replaces index list item with another item
     *
     * @param index this index
     * @param obj   new item
     */
    public void set(int index, T obj) {
        checkIndexBounds(index);
        if (index == 0) {
            first.obj = obj;
            return;
        }
        if (index == countElement - 1) {
            last.obj = obj;
            return;
        }
        Node<T> temp = returnElementFromIndex(index);

        temp.obj = obj;
    }

    /**
     * remove item by the index
     *
     * @param index the index of the item we are deleting
     * @return removed item;
     */
    public T remove(int index) {
        if (index == 0) {
            return pollFirst();
        }
        if (index == countElement - 1) {
            return pollLast();
        }
        checkIndexBounds(index);

        Node temp = returnElementFromIndex(index);

        Node removedObj = temp;
        Node prev = temp.previous;
        Node nex = temp.next;
        nex.previous = temp.previous;
        prev.next = temp.next;
        countElement--;
        return (T) removedObj.obj;
    }


    /**
     * check index bounds of list
     *
     * @param index this index
     */
    private void checkIndexBounds(int index) {
        if (countElement <= index || 0 > index) {
            throw new IndexOutOfBoundsException(" Size: " + countElement + " index: " + index);
        }
    }


    /**
     * determinate short way from the index and return the element
     *
     * @param index index of the element we need to return
     * @return object by index
     */
    private Node returnElementFromIndex(int index) {
        int middle = countElement / 2;
        Node<T> temp = null;
        if (index <= middle && index > 0) {
            temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        }
        if (index > middle && index < countElement) {
            temp = last;
            for (int i = 1; i < countElement - index; i++) {
                temp = temp.previous;
            }
        }
        return temp;
    }

    /**
     * returns list data as a string
     */
    public String toString() {
        String list = "";
        Node<T> newOb = first;
        for (int i = 0; i < countElement; i++) {
            list += " " + newOb.obj;
            newOb = newOb.next;
        }
        return list;
    }


    @Override
    /**
     * Returns an iterator over the elements in this list
     */
    public Iterator<T> iterator() {
        Iterator<T>iterator = new Iterator<T>() {
           Node<T> itNode = last;
            @Override
            public boolean hasNext() {
                if( itNode!=null){
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                T value = itNode.obj;
                itNode=itNode.previous;
                return value;
            }
        };
        return iterator;
    }

    /**
     * the class contains an object and two instances to create a linked list
     *
     * @param <T> type of object
     */
    private class Node<T> {

        private T obj;
        private Node<T> previous;
        private Node<T> next;

        Node(T obj, Node<T> previous, Node<T> next) {
            this.obj = obj;
            this.previous = previous;
            this.next = next;
        }

    }
}


