package com.shpp.p2p.cs.ibilash.assignment12;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * class gets color background and array of pixels where performed finding of silhouette  "recursive depth-first"
 */
class FindDepth {
    /**
     *  parameter of change silhouette
     */
    private final int SENSITIVITY = 10;

    /**
     * array of pixel where save colors
     */
    Color[][] pixel;
    /**
     * array of visited pixels
     */
    boolean[][] visited;
    /**
     * font array
     */
    int[] background;
    /**
     * count of pixels in silhouette
     */
    int countPixel = 0;
    /**
     * stored neighbors not visited vertex
     */
    Stack<Vertex> stack = new Stack<>();
    /**
     *  the number means the percentage of the maxSize of the silhouette that will be the silhouette
     */
   final double PERCENT = 95; //%
    /**
     * array save size of all silhouettes
     */
    ArrayList<Integer>arrSilhouette = new ArrayList<>();

    /**
     *  takes parameter to next processes
     * @param pixel array color of image
     * @param background background of image
     */
    FindDepth(Color[][] pixel, int[] background) {
        this.background = background;
        this.pixel = pixel;
        visited = new boolean[pixel.length][pixel[0].length];

    }

    /**
     * main method where find difference in array and find silhouette
     */
    public int findSilhouette() {
        for (int x = 1; x < pixel.length - 1; x++) {
            for (int y = 1; y < pixel[0].length - 1; y++) {
                if (notComparesColor(pixel[x][y]) && !visited[x][y]) { //find difference and start DFS
                    stack.push(new Vertex(x, y));
                    DFS(stack.pop());
                    arrSilhouette.add(countPixel);
                    countPixel = 0;
                }
            }
        }

        return analyzer(arrSilhouette);

    }

    /**
     *  takes array of values and find max value, return the values on percent less of max value
     * @param arr size silhouette
     * @return count of silhouette
     */
    private int analyzer(ArrayList<Integer> arr){
        int valMax=0;
        for(int val1:arr){
            valMax = Math.max(valMax, val1);
        }
        valMax=valMax-(int)(valMax*PERCENT/100);
        int counter =0;
        for (int val1:arr){
            counter+=valMax<val1?1:0;
        }
        return counter;
    }

    /**
     * get coordinate of vertex and note it like visited ,adding all neighbors(not visited) vertex in stack
     * @param vertex not visited vertex(get from the stack)
     */
    private void DFS(Vertex vertex) {
        countPixel++;
        int x = vertex.getX();
        int y = vertex.getY();
        visited[x][y] =true; //note vertex like visited
        addNearVertex(x, y);
        if (!stack.isEmpty()) {
            DFS(stack.pop());
        }
    }

    /**
     * get coordinate of pixel and all neighbors, not visited pixel different to font - adding in stack
     * @param x х coordinate
     * @param y у coordinate
     */
    private void addNearVertex(int x, int y) {
        if (!visited[x+1][y] && notComparesColor(pixel[x + 1][y]) &&
                x + 1 > 1 && x + 1 < pixel.length - 1 && y > 1 && y < pixel[0].length - 1) {
            stack.push(new Vertex(x + 1, y));
        }
        if (!visited[x-1][y] && notComparesColor(pixel[x - 1][y]) &&
                x - 1 > 1 && x - 1 < pixel.length - 1 && y > 1 && y < pixel[0].length - 1) {
            stack.push(new Vertex(x - 1, y));
        }
        if (!visited[x][y+1] && notComparesColor(pixel[x][y + 1]) &&
                x > 1 && x < pixel.length - 1 && y + 1 > 1 && y + 1 < pixel[0].length - 1) {
            stack.push(new Vertex(x, y + 1));
        }
        if (!visited[x][y-1] && notComparesColor(pixel[x][y - 1]) &&
                x > 1 && x < pixel.length - 1 && y - 1 > 1 && y - 1 < pixel[0].length - 1) {
            stack.push(new Vertex(x, y - 1));
        }

    }


    /**
     *  input color compares to font array and if only one color will be different to font - the method return  true
     *
     * @param color input color checking
     * @return  if difference detected - true, else - false
     */
     boolean notComparesColor(Color color) {
        // check colors on difference
        boolean red = color.getRed() > background[0] + SENSITIVITY || color.getRed() < background[0] - SENSITIVITY ;
        boolean green = color.getGreen() > background[1] + SENSITIVITY || color.getGreen() < background[1] - SENSITIVITY;
        boolean blue = color.getBlue() > background[2] + SENSITIVITY || color.getBlue() < background[2] - SENSITIVITY;
        boolean alpha = color.getAlpha() > background[3] + SENSITIVITY || color.getAlpha() < background[3] - SENSITIVITY;

        return red || green || blue ||alpha;
    }
}
