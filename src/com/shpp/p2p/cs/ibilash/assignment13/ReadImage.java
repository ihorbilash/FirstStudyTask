package com.shpp.p2p.cs.ibilash.assignment13;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *  the picture translate in pixel array and detecting background of perimeter
 */
class ReadImage {
    /**
     * pixels array contains color every pixel
     */
    Color[][] pixels;
    /**
     * background RGB color 0-red,1-green,2-blue,3-alpha
     */
    private int[] background = new int[4];

    /**
     *  takes input parameters
     * @param image this images
     * @param w width of images
     * @param h height of images
     */
    ReadImage(BufferedImage image, int w, int h) {
        pixels = new Color[w][h];

        fillArray(image);

    }

    /**
     * writing values every pixels in pixels array
     *
     * @param image input image
     */
    private void fillArray(BufferedImage image) {
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                pixels[x][y] = new Color(image.getRGB(x, y), true);
            }
        }
    }

    /**
     * determinate picture background going around of perimeter pixels
     *
     * @return array with four RGB background values
     */
     int[] determBackground() {
        countingSide(0, 0, pixels.length, 1);//up side
        countingSide(0, pixels[0].length-1, pixels.length, pixels[0].length);//down
        countingSide(0, 0, 1, pixels[0].length);//left
        countingSide(pixels.length-1, 0, pixels.length, pixels[0].length);//right
        int countFontPixel = pixels.length * 2 + pixels[0].length * 2; //count pixels of perimeter
        //average of each colors
        background[0] = background[0] / countFontPixel;
        background[1] = background[1] / countFontPixel;
        background[2] = background[2] / countFontPixel;
        background[3] = background[3] / countFontPixel;
        System.out.println("fond colors : "+"r:"+background[0]+" g:"+background[1]+
                " b:"+background[2]+" a:"+background[3]);

        return background;
    }

    /**
     *  reading pixels in these sides and plus they values in basket
     * @param startX start х
     * @param startY start у
     * @param endX end х
     * @param endY end у
     */
    private void countingSide(int startX, int startY, int endX, int endY) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                background[0] += pixels[x][y].getRed();
                background[1] += pixels[x][y].getBlue();
                background[2] += pixels[x][y].getGreen();
                background[3] += pixels[x][y].getAlpha();
            }
        }
    }
}
