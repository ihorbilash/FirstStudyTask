package com.shpp.p2p.cs.ibilash.assignment16;

import acm.util.RandomGenerator;
import java.util.ArrayList;

/**
 * Class for testing MyArrayList, contains method who check methods add,size,set,get,remove,indexOf.
 */
public class TestArrayList {

    MyArrayList<Integer> myArrayList = new MyArrayList();
    ArrayList<Integer> arrayList = new ArrayList<>();

    private final int ADD_VALUE = 1000;
    private final int NOT_CONTAIN_THIS_ELEMENT = 9999999;

    /**
     * method testing collection
     */
    public void testing() {
        addElementToIndexAndComparisonWithOriginCollection();
        addElement();
        addToIndexAndGet();
        checkContainAndSize();
        setListSizeThousand();
        addElementAndDel();
        checkGetSetRemove();
        AbroadArray();
        addNull();

    }

    /**
     * fill the cells of ArrayList by index, check the size method
     */
    private void addElementToIndexAndComparisonWithOriginCollection() {
        boolean mark = true;

        for (int i = 0; i < ADD_VALUE; i++) {
            myArrayList.add(0, i);
            arrayList.add(0, i);
            if (myArrayList.size() != arrayList.size()) {
                mark = false;
            }
        }
        System.out.print("Add element in ArrayList to index, check size : ");
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fail");

        for (int i = 0; i < ADD_VALUE; i++) {
            if (!arrayList.get(i).equals(myArrayList.get(i))) {
                mark = false;
            }
        }
        System.out.print("Check my element in ArrayList with original Collection : ");
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fail");

        System.out.print("Check Clear : ");
        myArrayList.clear();
        arrayList.clear();
        if (myArrayList.size()==arrayList.size()) {
            System.out.println("Pass");
        } else System.out.println("Fail");


    }

    /**
     * fill the cells of ArrayList, check the size method
     */
    private void addElement() {
        boolean mark = true;
        for (int i = 1; i <= ADD_VALUE; i++) {
            myArrayList.add(i);
            arrayList.add(i);
            if (myArrayList.size() != arrayList.size()) {
                mark = false;
            }
        }
        System.out.print("Add element in ArrayList, check size : ");
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fail");
    }

    /**
     * add element into index , check right adding
     */
    private void addToIndexAndGet() {

        int newElem = 45678;
        boolean mark = false;
        System.out.print(" Check add into index and Get : ");
        for (int i = 10; i < ADD_VALUE; i = i + 5) {
            myArrayList.add(i, newElem);
            arrayList.add(i, newElem);
            if (!myArrayList.get(i).equals(arrayList.get(i)) ||
                    !myArrayList.get(i + 1).equals(arrayList.get(i + 1)) ||
                    !arrayList.get(i - 1).equals(myArrayList.get(i - 1)) ||
                    !myArrayList.get(i + 2).equals(arrayList.get(i + 2))) {
                mark = true;
            }
        }
        if (!mark) {
            System.out.println(" Pass");
        } else System.out.println(" Fail");
    }

    /**
     * add two element - check size, and check method contains
     */
    public void checkContainAndSize() {

        boolean markContain = true;
        System.out.print(" Check method contains : ");
        for (int i = 0; i < arrayList.size(); i++) {
            if (!myArrayList.contains(arrayList.get(i))) {
                markContain = false;
            }
        }
        if (markContain) {
            System.out.println(" Pass");
        } else System.out.println(" Fail");

        System.out.print(" check contains, element dont exist : ");
        if (myArrayList.contains(NOT_CONTAIN_THIS_ELEMENT)) {
            System.out.println("Fall");
        } else System.out.println("Pass");
    }

    /**
     * add element and check size
     */
    public void setListSizeThousand() {
        int first = myArrayList.size();
        boolean markSize = true;
        for (int i = 1; i <= ADD_VALUE*10; i++) {
            myArrayList.add(i*-1);
            arrayList.add(i*-1);
            if (myArrayList.size() != arrayList.size()) {
                markSize = false;
            }
        }
        System.out.print("add element and check size : ");
        if (markSize) {
            System.out.println("Pass");
        } else System.out.println("Fail");
    }

    /**
     * remove element from array, testing method remove and get
     */
    private void addElementAndDel() {
        int firstSize = myArrayList.size();
        boolean markGet = true;
        boolean markRemove = true;
        RandomGenerator rg = new RandomGenerator();
        int index= rg.nextInt(0,firstSize/2);
        System.out.print(" remove element from RANDOM basket,check method Get and Remove : ");
        while (firstSize / 2 < arrayList.size()) {
            if (!myArrayList.remove(index).equals(arrayList.remove(index) )) {

                markRemove = false;
            }
            if (!myArrayList.get(index).equals(arrayList.get(index))) {
                markGet = false;
            }
        }
        if (markGet && markRemove) {
            System.out.println("  Pass");
        } else {
            System.out.println("  Fall");
        }
    }

    /**
     * check method set,get,remove,add element In some position, make a set, get and
     * delete by method remove
     */
    public void checkGetSetRemove() {
        int value = 33;

        boolean markGet = true;
        boolean markRemove = true;
        boolean markSet = true;
        System.out.println("In some position, make a set, get and remove : -> ");
        for (int i = 0; i < ADD_VALUE; i ++) {
            if (!arrayList.set(i,value).equals(myArrayList.set(i, value))) {
                markSet = false;
            }
            if (!myArrayList.get(i).equals(arrayList.get(i))) {
                markGet = false;
            }
            if (!arrayList.remove(i).equals(myArrayList.remove(i))) {
                markRemove = false;
            }
        }
        if (markSet) {
            System.out.println("Check Method Set: Pass  ");
        } else {
            System.out.println("Check Method Set: Fall  ");
        }
        if (markGet) {
            System.out.println("Check Method Get: Pass  ");
        } else {
            System.out.println("Check Method Get: Fall  ");
        }
        if (markRemove) {
            System.out.println("Method Remove: Pass  ");
        } else {
            System.out.println("Method Remove: Fall  ");
        }
    }

    /**
     * check methods for non-existing positions in the array
     */
    private void AbroadArray() {
        try {
            myArrayList.set(-9, 1110);
            System.out.println("IndexOutOfBoundsException set:  Fall ");
        } catch (IndexOutOfBoundsException npe) {
            System.out.println("IndexOutOfBoundsException set:  Pass ");
        }
        try {
            myArrayList.get(myArrayList.size());
            System.out.println("IndexOutOfBoundsException get:  Fall ");
        } catch (IndexOutOfBoundsException npe) {
            System.out.println("IndexOutOfBoundsException get:  Pass ");
        }
        try {
            myArrayList.remove(-2);
            System.out.println("IndexOutOfBoundsException remove:  Fall ");
        } catch (IndexOutOfBoundsException npe) {
            System.out.println("IndexOutOfBoundsException remove:  Pass ");
        }
        try {
            myArrayList.add(myArrayList.size() + 1, 999);
            System.out.println("IndexOutOfBoundsException add:  Fall ");
        } catch (IndexOutOfBoundsException npe) {
            System.out.println("IndexOutOfBoundsException add:  Pass ");
        }
    }

    /**
     * add null in basket of array  , determine the availability and position with the array, check methods add,contains,indexOf
     */
    private void addNull() {
        int index = 398;
        myArrayList.add(index, null);
        System.out.print(" add null ass object and determinate available : ");
        if (myArrayList.contains(null)) {
            System.out.println("Pass");
        } else {
            System.out.println("Fall");
        }
        System.out.print("Check basket where null was put : ");
        if (myArrayList.indexOf(null) == index) {
            System.out.println("Pass");
        } else {
            System.out.println("Fall");
        }

    }

}
