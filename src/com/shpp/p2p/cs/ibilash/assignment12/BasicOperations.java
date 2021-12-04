package com.shpp.p2p.cs.ibilash.assignment12;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class uses ReadImage for reading file and writes it in array of pixels,
 * use method resize
 * detect font and return it in class FindDepth, where finding silhouette.
 */
public class BasicOperations {
    /**
     * new size of image
     */
    private final int NEW_SIZE =200;
    /**
     * count of silhouette
     */
    int silhouette;

    /** read file and use class ReadImage and FindDepth
     * @param str file name
     */
    BasicOperations(String str) {
        try {
            BufferedImage image = ImageIO.read(new File(str)); //read file
            image = resize(image, NEW_SIZE, NEW_SIZE);
            ReadImage ri = new ReadImage(image, image.getWidth(), image.getHeight()); // translate file in array of pixel
            int[] background = ri.determBackground(); // detect background
            Color[][] color = ri.pixels; // get array of pixel

            FindDepth fd = new FindDepth(color, background);
            silhouette = fd.findSilhouette(); //find silhouette


        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * resize this image to new size
     *
     * @param img  input image
     * @param newW new width
     * @param newH new height
     * @return resized image
     */
    private BufferedImage resize(BufferedImage img, int newW, int newH) {

        BufferedImage newImg = new BufferedImage(newW, newH, img.getType());  //image type
        Graphics2D g = newImg.createGraphics();   //start Graphics2D to work with
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //smoothing
        //redrawing the image with the new settings
        g.drawImage(img, 0, 0, newW, newH, 0, 0, img.getWidth(), img.getHeight(), null);
        g.dispose();
        return newImg;
    }
}
