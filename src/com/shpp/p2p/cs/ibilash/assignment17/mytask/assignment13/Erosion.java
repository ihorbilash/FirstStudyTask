package com.shpp.p2p.cs.ibilash.assignment17.mytask.assignment13;

import java.awt.*;

/**
 * class repaint pixels around silhouette
 */
 class Erosion extends FindInBreadth {
    /**
     * count erosion going around
     */
    private final int COUNT = 2; //number of count per pixel on each side of the silhouette
    /**
     * image main pixels
     */
    private Color[][] pixel;

    /**
     * array where save redrawn pixels
     */
    private boolean[][] redrawn;

    /**
     *  takes parameter to next processes
     * @param pixel array color of image
     * @param background background of image
     */
    Erosion(Color[][] pixel, int[] background) {
        super(pixel, background);
        this.pixel = pixel;

    }

    /**
     * main method where going along the line x and y, when find silhouette repaint pixel begin and end of it
     *
     * @return redrawn array of pixels
     */
     Color[][] erosion() {
        int count = 0;
        while (count < COUNT) {
            redrawn = new boolean[pixel.length][pixel[0].length];
            for (int x = 1; x < pixel.length - 1; x++) {
                for (int y = 1; y < pixel[0].length - 1; y++) {
                    wayY(x, y);
                }
            }
            redrawn = new boolean[pixel.length][pixel[0].length];
            for (int y = 1; y < pixel[0].length - 1; y++) {
                for (int x = 1; x < pixel.length - 1; x++) {
                    wayX(x, y);
                }
            }
            count++;
        }
        return pixel;
    }

    /** going to Y axis and searching silhouette, then find - repaint color to background
     * @param x coordinate x
     * @param y coordinate y
     */
    private void wayY(int x, int y) {
        if (notComparesColor(pixel[x][y]) && !notComparesColor(pixel[x][y - 1]) && !redrawn[x][y - 1]) {
            Color font = pixel[x][y - 1];
            repaintEdge(x, y, font);
        }
        if (notComparesColor(pixel[x][y]) && !notComparesColor(pixel[x][y + 1])) {
            Color font = pixel[x][y + 1];
            repaintEdge(x, y, font);
        }
    }

    /**
     * going to X axis and searching silhouette, then find - repaint color to background
     * @param x coordinate x
     * @param y coordinate y
     */
    private void wayX(int x, int y) {
        if (notComparesColor(pixel[x][y]) && !notComparesColor(pixel[x - 1][y]) && !redrawn[x - 1][y]) {
            Color background = pixel[x - 1][y];
            repaintEdge(x, y, background);
        }
        if (notComparesColor(pixel[x][y]) && !notComparesColor(pixel[x + 1][y])) {
            Color background = pixel[x + 1][y];
            repaintEdge(x, y, background);
        }
    }

    /**
     * change color pixel and check coordinate in redrawn array
     * @param x coordinate x
     * @param y coordinate y
     * @param font font of image
     */
    private void repaintEdge(int x, int y, Color font) {
        if (!redrawn[x][y]) {
            pixel[x][y] = font;
            redrawn[x][y] = true;
        }
    }


}