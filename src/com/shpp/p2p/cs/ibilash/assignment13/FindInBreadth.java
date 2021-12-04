package com.shpp.p2p.cs.ibilash.assignment13;


import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * class gets color background and array of pixels where perform finds of silhouette
 */
 class FindInBreadth {
    /**
     * parameter of change silhouette
     */
    private final int SENSITIVITY = 40;
    /**
     * the number means the percentage of the maxSize of the silhouette that will be the silhouette
     */
    final double  PARAMETER = 0.95; // P/100%
    /**
     * array save size of all silhouettes
     */
    ArrayList<Integer> arrSilhouette = new ArrayList<>();
    /**
     * array of pixel where save colors
     */
    private Color[][] pixel;
    /**
     * array of visited pixels
     */
    private boolean[][] visited;
    /**
     * background array
     */
    private int[] background;
    /**
     * count of pixels in silhouette
     */
    private int countPixel = 0;
    /**
     * stored neighbors not visited vertex
     */
    LinkedList<Vertex> list = new LinkedList<>();
    /**
     *  takes parameter to next processes
     * @param pixel array color of image
     * @param background background of image
     */
    FindInBreadth(Color[][] pixel, int[] background) {
        this.background = background;
        this.pixel = pixel;
        visited = new boolean[pixel.length][pixel[0].length];

    }

    /**
     * main method where finds difference in array and counts silhouette
     */
     int findSilhouette() {

        for (int x = 1; x < pixel.length - 1; x++) {
            for (int y = 1; y < pixel[0].length - 1; y++) {
                if (notComparesColor(pixel[x][y]) && !visited[x][y]) { //find difference and start bfs
                    BFS(new Vertex(x, y));
                    arrSilhouette.add(countPixel);
                    countPixel = 0;
                }
            }
        }
        return  analyzer(arrSilhouette);

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
        valMax=valMax-(int)(valMax*PARAMETER);
        int counter = 0;
        for (int val1:arr){
            counter+=valMax<val1?1:0;
        }
        return counter;
    }

    /**
     * get coordinate of vertex and note it like visited ,adding all neighbors(not visited) vertex in first basket LinkedList
     *
     * @param vertex not visited vertex( get last from LinkedList)
     */
    private void BFS(Vertex vertex) {

        list.addFirst(vertex);
        while (list.size() != 0) {
            vertex = list.pollLast();
            countPixel++;
            int x = vertex.getX();
            int y = vertex.getY();
            addNearVertex(x, y);
        }

    }

    /**
     * get coordinate of pixel and all neighbors, not visited pixel different to background - adding first in LinkedList
     *
     * @param x х coordinate
     * @param y у coordinate
     */
    private void addNearVertex(int x, int y) {
        if (!visited[x + 1][y] && notComparesColor(pixel[x + 1][y]) &&
                x + 1 > 1 && x + 1 < pixel.length - 1 && y > 1 && y < pixel[0].length - 1) {
            visited[x + 1][y] = true;
            list.addFirst(new Vertex(x + 1, y));
        }
        if (!visited[x - 1][y] && notComparesColor(pixel[x - 1][y]) &&
                x - 1 > 1 && x - 1 < pixel.length - 1 && y > 1 && y < pixel[0].length - 1) {
            visited[x - 1][y] = true;
            list.addFirst(new Vertex(x - 1, y));
        }
        if (!visited[x][y + 1] && notComparesColor(pixel[x][y + 1]) &&
                x > 1 && x < pixel.length - 1 && y + 1 > 1 && y + 1 < pixel[0].length - 1) {
            visited[x][y + 1] = true;
            list.addFirst(new Vertex(x, y + 1));
        }
        if (!visited[x][y - 1] && notComparesColor(pixel[x][y - 1]) &&
                x > 1 && x < pixel.length - 1 && y - 1 > 1 && y - 1 < pixel[0].length - 1) {
            visited[x][y - 1] = true;
            list.addFirst(new Vertex(x, y - 1));
        }

    }

    /**
     * input color compares to background array and if only one color will be different to background - the method return  true
     *
     * @param color input color checking
     * @return if difference detected - true, else - false
     */
     boolean notComparesColor(Color color) {
        // check colors on difference
        boolean red = color.getRed() > background[0] + SENSITIVITY || color.getRed() < background[0] - SENSITIVITY;
        boolean green = color.getGreen() > background[1] + SENSITIVITY || color.getGreen() < background[1] - SENSITIVITY;
        boolean blue = color.getBlue() > background[2] + SENSITIVITY || color.getBlue() < background[2] - SENSITIVITY ;
        boolean alpha = color.getAlpha() > background[3] + SENSITIVITY || color.getAlpha() < background[3] - SENSITIVITY ;

        return red || green || blue || alpha;
    }
}
