package it.zm.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {

    public static BufferedImage scaleImage(int width, int height, String filename) {
        BufferedImage bi;
        try {
            ImageIcon ii = new ImageIcon(filename);
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, width, height, null);
        } catch (Exception e) {
            return null;
        }
        return bi;
    }

    public static BufferedImage createImage(URL ur) {
        BufferedImage bi;
        try {
        	bi = ImageIO.read(ur);
        } catch (Exception e) {
            return null;
        }
        return bi;
    }
    
    public static BufferedImage createImage(ImageIcon ii) {
        BufferedImage bi;
        try {
            bi = new BufferedImage(ii.getIconWidth(), ii.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, ii.getIconWidth(), ii.getIconHeight(), null);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return null;
        }
        return bi;
    }
    
    public static ImageIcon createImage(int width, int heigh, ImageIcon ii) {
        BufferedImage bi;
        try {
            bi = new BufferedImage(width, heigh, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, width, heigh, null);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return null;
        }
        
        ImageIcon i = new ImageIcon(bi);
        return i;
    }
    
    public static BufferedImage scaleImage(int width, int height, URL ur) {
        BufferedImage bi;
        try {
            ImageIcon ii = new ImageIcon(ur);
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, width, height, null);
        } catch (Exception e) {
            return null;
        }
        return bi;
    }
    
    public static Image scaleImage(int width, int height, BufferedImage filename) {
        BufferedImage bi;
        try {
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(filename, 0, 0, width, height, null);
        } catch (Exception e) {
            return null;
        }
        return bi;
    }
}
