package com.shpp.p2p.cs.ibilash.assignment16;


import java.util.LinkedList;

/**
 * the class tests the work of the LinkedList
 */
public class TestLinkedList {

    MyLinkedList<Integer> myList = new MyLinkedList<>();
    LinkedList<Integer> list = new LinkedList<>();

    private final int ADD_VALUE = 100;

    public void testing() {

        checkAddSize();
        checkPollFirst();
        fillListToIndex();
        checkPoolLast();
        checkRemoveFirst();
        ifListEmpty();
        addInEmptyList();
        lastCheck();
        checkAddSize();

    }

    /**
     * fill in cells and check the size
     */
    private void checkAddSize() {
        boolean mark = true;
        System.out.print("  add elements to the list and check the size : ");
        for (int i = 1; i <= ADD_VALUE; i++) {
            myList.addLast(i);
            list.addLast(i);
            if (myList.size() != list.size()) {
                mark = false;
            }
        }
        if (mark) {
            System.out.println(" Pass ");
        } else System.out.println(" Fall ");

    }

    /**
     * read and delete elements, check them and check the size after delete
     */
    private void checkPollFirst() {

        boolean mark = true;
        System.out.print(" check method pollFirst :");
        for (int i = 0; i < list.size(); i++) {
            if (!myList.pollFirst().equals(list.pollFirst())) {
                mark = false;
            }
        }
        if (mark) {
            System.out.print(" Pass");
        } else {
            System.out.print(" Fail");
        }
        System.out.println();

        System.out.print(" check size after poll:");
        if (myList.size() == list.size()) {
            System.out.println(" Pass");
        } else {
            System.out.println(" Fail");
        }
    }

    /**
     * fill in cells to index
     */
    private void fillListToIndex() {
        boolean mark = true;
        System.out.print("  add elements to the list by index and check GET : ");
        myList.add(0, 333);
        list.add(0, 333);
        myList.add(0, 444);
        list.add(0, 444);
        for (int i = 0; i < ADD_VALUE; i++) {
            myList.add(i, i * 10);
            list.add(i, i * 10);
            if (myList.size() != list.size()) {
                mark = false;
            }
            if (!myList.get(i).equals(list.get(i))) {
                mark = false;
            }
        }
        if (mark) {
            System.out.println(" Pass ");
        } else System.out.println(" Fall ");

    }

    /**
     * read and delete elements from the end until there are 10 cells left
     */
    private void checkPoolLast() {
        int firstSize = list.size();
        boolean mark = true;

        System.out.print(" check method pollLast :");
        while (myList.size() > firstSize / 2) {

            if (!myList.pollLast().equals(list.pollLast())) {
                mark = false;
            }
        }
        if (mark) {
            System.out.print(" Pass");
        } else {
            System.out.print(" Fall");
        }
        System.out.println();

    }

    /**
     * check methods peekFirst and remove, we get the zero element and delete the zero element, compare
     */
    private void checkRemoveFirst() {
        System.out.print("check methods peekFirst and peekLast : ");
        boolean markRemove = true;
        boolean markPeek = true;

        while (myList.size() > 0) {
            if (!myList.peekFirst().equals(list.peekFirst()) && !myList.peekLast().equals(list.peekLast())) {
                markPeek = false;
            }
            if (!list.remove(0).equals(myList.remove(0)) && !list.pollLast().equals(myList.pollLast())) {
                markRemove = false;
            }
        }
        if (markPeek) {
            System.out.println("Pass");
        } else {
            System.out.println("Fall");
        }
        System.out.print("check methods remove and pollLast : ");
        if (markRemove) {
            System.out.println("Pass");
        } else {
            System.out.println("Fall");
        }
    }

    /**
     * we perform operations with an empty list, and item out of bound
     */
    private void ifListEmpty() {
        System.out.print("if list empty, size() : ");
        if (myList.size() == list.size()) {
            System.out.println("Pass");
        } else System.out.println("Fall");


        System.out.print("if list empty, peekFirst : ");
        if (myList.peekFirst() == null) {
            System.out.println("Pass");
        } else System.out.println("Fall");

        System.out.print("if list empty, peekLast : ");
        if (myList.peekLast() == null) {
            System.out.println("Pass");
        } else System.out.println("Fall");

        System.out.print("if list empty, poolFirst : ");
        if (myList.pollFirst() == null) {
            System.out.println("Pass");
        } else System.out.println("Fall");

        System.out.print("if list empty, poolLast : ");
        if (myList.pollLast() == null) {
            System.out.println("Pass");
        } else System.out.println("Fall");

        System.out.print("if list empty, get item out of bound : ");
        try {
            myList.get(1);
            System.out.println("Fall");
        } catch (IndexOutOfBoundsException npe) {
            System.out.println("Pass");
        }
        System.out.print("if list empty, set item out of bound: ");
        try {
            myList.set(0, 33);
            System.out.println("Fall");
        } catch (IndexOutOfBoundsException npe) {
            System.out.println("Pass");
        }

    }

    /**
     * fill empty list of method add(index, obj), check size
     */
    private void addInEmptyList() {
        System.out.print("add data and pollLast in list  : ");
        boolean mark = true;
        int i = 0;
        while (myList.size() < ADD_VALUE / 2) {
            myList.add(0, i);
            list.add(0, i);
            i++;
        }

        while (myList.size() < ADD_VALUE) {
            myList.add(myList.size(), i);
            list.add(list.size(), i);
            i++;
        }
        while ((myList.size() > 0)) {
            if (!myList.pollLast().equals(list.pollLast())) {
                mark = false;
            }
        }
        if (mark) {
            System.out.println("Pass");
        } else {
            System.out.println("Fall");
        }

    }

    /**
     * add a null object to the cells and delete this data from the cells
     */
    private void lastCheck() {
        System.out.print("add null to first and second point and poolFirst: ");

        myList.addFirst(null);
        myList.addLast(null);
        myList.set(1, null);
        if (myList.get(0) == null && myList.peekFirst() == myList.pollFirst()) {
            System.out.println("Pass");
        } else {
            System.out.println("Fall");
        }

        System.out.print("check element null in first and last point: ");
        if (myList.pollFirst() == myList.pollLast()) {
            System.out.println("Pass");
        } else {
            System.out.println("Fall");
        }
    }
}
