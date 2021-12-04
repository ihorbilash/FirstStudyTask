package com.shpp.p2p.cs.ibilash.assignment17;

import com.shpp.p2p.cs.ibilash.assignment17.assignment16_iterator.MyArrayList;

/**
 * Data container with get to the address. Contain method put, get, remove, containKey, returnKey.
 *
 * @param <K> address or key data
 * @param <V> value element
 */
public class MyHashMap<K, V> {

    /**
     * value of remove element
     */
    private V value;
    /**
     * array of node data
     */
    private Node[] dataArray;

    /**
     * default size of array
     */
    private final int DEFAULT_CAPACITY = 16;

    /**
     * count of added elements
     */
    private int size = 0;

    /**
     * coefficient of overload factor
     */
    private final double LOAD_FACTOR = 0.75;
    /**
     * we use during the search for the key to delete , if code key not found - switch to true
     */
    private boolean keyAbsent = false;


    public MyHashMap() {
        dataArray = new Node[DEFAULT_CAPACITY];
    }


    /**
     * put the data in node by key
     *
     * @param key   this key
     * @param value this value
     */
    public void put(K key, V value) {

        if (size > (dataArray.length - 1) * LOAD_FACTOR) {
            size = 0;
            reassignBasket();
        }
        int index = getHash(key);
        if (containsKey(key)) {
            remove(key);
        }
        addElement(index, key, value);

    }

    /**
     * get the value by key
     *
     * @param key key we use to get value
     * @return value by key or null
     */
    public V get(K key) {

        int index = getHash(key);
        Node node = dataArray[index];

        findRightNode(node, key, -1); // if index -1 means read operation
        V result = value;
        value = null;
        return result;

    }

    /**
     * delete element to arrayData by key
     *
     * @param key the key by which we delete the value
     * @return the value to remove by key
     */
    public V remove(K key) {

        int index = getHash(key);
        Node basketFromDataArray = dataArray[index];
        dataArray[index] = null;
        findRightNode(basketFromDataArray, key, index);
        V returnValue = value;
        if (!keyAbsent) {
            size--;
        }
        value = null;
        return returnValue;

    }

    /**
     * check for the presence of a key in the hashmap
     *
     * @param key this key we looking
     * @return if key present - true, absent - false.
     */
    public boolean containsKey(K key) {
        boolean mark = false;
        int index = getHash(key);
        for (Node node = dataArray[index]; node != null; node = node.next) {
            if (key == null) {
                mark = node.key == null;
            } else {
                if (node.key == key || node.key.hashCode() == key.hashCode() || node.key.equals(key)) {
                    mark = true;
                }
            }
        }
        return mark;

    }

    /**
     * return the array list contained the key
     *
     * @return key of ArrayList
     */
    public MyArrayList<K> returnKey() {
        MyArrayList<K> arrayList = new MyArrayList<>();
        for (int i = 0; i < dataArray.length; i++) {
            for (Node temp = dataArray[i]; temp != null; temp = temp.next)
                arrayList.add((K) temp.key);
        }
        return arrayList;
    }

    /**
     * calculates the index of basket dataArray to put the node . basket number 0 = for null key
     *
     * @param key this key we put the element
     * @return index of dataArray
     */
    private int getHash(K key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode() & dataArray.length - 1;
    }

    /**
     * add element in data array
     *
     * @param index this index
     * @param key   this key
     * @param value this value by key
     */
    private void addElement(int index, K key, V value) {
        Node node = new Node(null, key, value);
        if (dataArray[index] == null) {
            dataArray[index] = node;
        } else {
            findFreePlaceList(node, index);
        }
        size++;
    }

    /**
     * to the increase in the size of the array, we reassign the address of all filled cells.
     */
    private void reassignBasket() {
        Node[] tempArr = dataArray;
        dataArray = new Node[(int) (dataArray.length * 2)];
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] != null) {
                Node<K, V> temp = tempArr[i];
                put(temp.key, temp.value);
                while (temp.next != null) {
                    temp = temp.next;
                    put(temp.key, temp.value);
                }
            }
        }

    }

    /**
     * equal value in node - return value, if not equals -
     * go to the next node, if the key is not found returns: null and keyAbsent = true;
     *
     * @param node  this node,where looking right key, if not hind  - go to next node
     * @param key   key by witch we want to get value
     * @param index if index >=0  - the index in dataArray where we delete the , index <0 - read node
     * @return value in basket of arrayData by key.
     */
    private V findRightNode(Node node, K key, int index) {
        if (node != null) {
            Node element = new Node(null, node.key, node.value); // overwrite to
            if (key == null) {
                if (node.key == null)
                    value = (V) node.value;
            } else if (node.key.hashCode() == key.hashCode() || node.key == key || node.key.equals(key)) {
                value = (V) node.value;
                fillBasketOrNode(index, node.next);
                return value;
            } else
                fillBasketOrNode(index, element);
            findRightNode(node.next, key, index);
        } else {
            keyAbsent = true;
        }
        return value;
    }

    /**
     * fill the basket with elements if the index >= 0
     *
     * @param index the index to which add element
     * @param node  this  node to fill in basket
     */
    private void fillBasketOrNode(int index, Node node) {
        if (index >= 0) {
            if (dataArray[index] == null) {
                dataArray[index] = node;
            } else {
                findFreePlaceList(node, index);
            }
        }
    }

    /**
     * look for an empty next node and put it there this Node
     *
     * @param node  this node
     * @param index index by which get in array
     */
    private void findFreePlaceList(Node node, int index) {
        Node temp = dataArray[index];
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;

    }


    /**
     * return count of added elements
     *
     * @return count of added elements
     */
    public int size() {
        return size;
    }


    /**
     * node of save data key, value, hashcode и next node
     *
     * @param <K> type of key
     * @param <V> type of value
     */
    private static class Node<K, V> {

        private K key;
        private V value;
        private Node<K, V> next;

        Node(Node<K, V> next, K key, V value) {
            this.next = next;
            this.key = key;
            this.value = value;


        }

    }
}
