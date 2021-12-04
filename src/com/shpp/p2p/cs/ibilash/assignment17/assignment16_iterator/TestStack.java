package com.shpp.p2p.cs.ibilash.assignment17.assignment16_iterator;

import java.util.Stack;

/**
 * Class for testing MyStack, contains method who check MyStack.
 */
public class TestStack {
    MyStack<Integer> sk = new MyStack<>();
    Stack<Integer> stack = new Stack<>();

    private final int ADD_VALUE = 1000;

    public void testingStack() {
        addStack();
        removeElement();
        testSearch();
        for (int i: sk){
            System.out.print(" "+i);
        }
        System.out.println(" ");
        for (int i: stack){
            System.out.print(" "+i);
        }
    }

    /**
     * fill the cells of the stack with data ,  check the size method and check the empty method
     */
    private void addStack() {
        boolean markSize = true;
        boolean markPeek = true;
        System.out.print("Stack empty , check method Empty : ");
        if (sk.empty()) {
            System.out.println("Pass");
        } else System.out.println("Fall");
        for (int i = 0; i < ADD_VALUE; i++) {
            sk.push(i);
            stack.push(i);
            if (sk.size() != stack.size()) {
                markSize = false;
            }
            if (!sk.peek().equals(stack.peek())) {
                markPeek = false;
            }
        }
        System.out.print(" check method Peek: ");
        if (!markPeek) {
            System.out.println("Fall");
        } else System.out.println("Pass");

        System.out.print("Stack not empty , check method empty: ");
        if (sk.empty()) {
            System.out.println("Fall");
        } else System.out.println("Pass");
        System.out.print("Check size: ");
        if (markSize) {
            System.out.println("Pass");
        } else System.out.println("Fall");
    }

    /**
     * read the first element from the head of the stack by the pop method,
     * then read the pack and check the stack size after
     */
    private void removeElement() {
        boolean mark = true;
        System.out.print("Check to remove a stack of items from the head: ");
        for (int i = 0; i < ADD_VALUE / 2; i++) {
            if (!stack.pop().equals(sk.pop())) {
                mark = false;
            }
        }
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fall");
        System.out.print("Size check by size method after using the pop method: ");
        if (sk.size() == stack.size()) {
            System.out.println("Pass");
        } else System.out.println("Fall");

    }

    /**
     * check method Search ,check index of this element
     */
    private void testSearch() {
        System.out.print("check Search if element is empty: ");
        if (sk.search(ADD_VALUE) == -1) {
            System.out.println("Pass");
        } else System.out.println("Fall");
        System.out.print("check Search if element is present: ");
        boolean mark = true;
        int elementInHead = sk.peek();
        for (int i = elementInHead; i > 0; i--) {
            if (sk.search(i) != stack.search(i)) {
                mark = false;
            }
        }
        if (mark) {
            System.out.println("Pass");
        } else System.out.println("Fall");

    }

}
