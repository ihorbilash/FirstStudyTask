package com.shpp.p2p.cs.ibilash.assignment17;

import java.util.HashMap;

/**
 * Class for testing myHashMap, contains method who check methods put, get, remove, containKey .
 */
public class TestHashMap {

    MyHashMap<String, Integer> myHM = new MyHashMap<>();
    HashMap HM = new HashMap();

    private final int MAX_SIZE = 280000;
    private final int FIRST_ELEMENT = 10000;


    public void test() {


        addAndCheckSize();
        getAndRemove();
        checkResize();
        operationsNull();
        // System.out.println(myHM.returnKey());

    }

    /**
     * add the value by key and check the size
     */
    private void addAndCheckSize() {
        boolean mark = true;
        for (int i = 0, j = FIRST_ELEMENT; i < MAX_SIZE; i++, j++) {
            myHM.put("" + i, j);
            HM.put("" + i, j);
            if (myHM.size() != HM.size()) {
                mark = false;
            }
        }
        System.out.print("Fill the collections and check the size:  ");
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fail");
    }

    /**
     * 1.get elements by key.
     * 2.With the same keys, we delete the elements and compare the values.
     * Check Get, Remove and Size
     */
    private void getAndRemove() {

        boolean markGet = true;
        boolean markRemove = true;
        boolean markSize = true;

        for (int i = 0; i < MAX_SIZE; i++) {
            if (!myHM.get("" + i).equals(HM.get("" + i))) {
                markGet = false;
            }
        }
        for (int i = 0; i < MAX_SIZE; i = i + 50) {
            if (!HM.remove("" + i).equals(myHM.remove("" + i))) {
                markRemove = false;
            }
            if (HM.size() != myHM.size()) {
                markSize = false;
            }

        }
        System.out.print("Check method GET : ");
        if (markGet) {
            System.out.println("Pass");
        } else System.out.println("Fail");

        System.out.print("Check method REMOVE : ");
        if (markRemove) {
            System.out.println("Pass");
        } else System.out.println("Fail");

        System.out.print("Check method SIZE : ");
        if (markSize) {
            System.out.println("Pass");
        } else System.out.println("Fail");
    }


    /**
     * overwrite the value by key
     */
    private void checkResize() {
        boolean markPut = true;
        boolean markContains = true;
        for (int i = -1, j = 0; i > -MAX_SIZE; i--, j++) {
            if (myHM.containsKey("" + i) && HM.containsKey("" + i)) {
                markContains = false;
            }
            myHM.put("" + i, j);
            HM.put("" + i, j);
            if (!myHM.containsKey("" + i) && !HM.containsKey("" + i)) {
                markPut = false;
            }
        }
        for (int i = -1; i > -MAX_SIZE; i--) {
            if (!myHM.get("" + i).equals(HM.get("" + i))) {
                markPut = false;
            }
        }
        System.out.print("Check method ContainsKey : ");
        if (markContains) {
            System.out.println("Pass");
        } else System.out.println("Fail");

        System.out.print("Check method Put , Get : ");
        if (markPut) {
            System.out.println("Pass");
        } else System.out.println("Fail");
    }

    /**
     * We write an element with a key null, rewrite, remove.
     */
    private void operationsNull() {
        int value = 565;
        myHM.put(null, value);
        HM.put(null, value);
        System.out.print("Insert value by key Null : ");
        if (myHM.get(null).equals(HM.get(null))) {
            System.out.println("Pass");
        } else System.out.println("Fail");

        System.out.print("Replacement value by key null Null : ");
        int value2 = 777;
        myHM.put(null, value2);
        HM.put(null, value2);
        if (myHM.get(null).equals(HM.get(null))) {
            System.out.println("Pass");
        } else System.out.println("Fail");

        System.out.print("remove value by key null : ");
        if (myHM.remove(null).equals(HM.remove(null))) {
            System.out.println("Pass");
        } else System.out.println("Fail");
    }


}
