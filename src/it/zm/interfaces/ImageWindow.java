package it.zm.interfaces;


import it.lilik.capturemjpeg.CaptureMJPEG;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.net.URL;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;


public class ImageWindow {

	public JFrame frame;
	private ImgWindow imgw;
	
	private String url;

	private CaptureMJPEG capture;
	
	/**
	 * Create the application.
	 */
	public ImageWindow(String u) {
		url = u;
		try {
			initialize();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Set visible
	public void show(){
		//frame.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private void initialize() throws MalformedURLException, IOException {
		
		  frame = new JFrame("Telecamera");
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  imgw = new ImgWindow();
		  frame.setContentPane(imgw);
		  frame.setVisible(true);
		
		  capture = new CaptureMJPEG(this,url);
		  capture.startCapture();
		  
		  System.out.println("Initialized");
    }
	
	public void captureMJPEGEvent(Image img){
                
        BufferedImage bi = (BufferedImage) img;

        //final Image background = ImageUtils.scaleImage(bi.getHeight(), bi.getWidth(), (BufferedImage)img);
        //final Dimension jpanelDimensions = new Dimension(new ImageIcon(background).getIconWidth(), new ImageIcon(background).getIconHeight());

        imgw.changeImage(bi);
        frame.setSize(bi.getWidth(null), bi.getHeight(null));
	}

	public String getName() {
		// TODO Auto-generated method stub
		return new String("ImageWindow");
	}

}

class ImgWindow extends JPanel {

    BufferedImage img;

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public ImgWindow() {
       img = null;
    }
    
    public void changeImage(BufferedImage i){
    	img = i;
    	this.repaint();
    }

    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }

}

class ImageUtils {

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

    static Image scaleImage(int width, int height, BufferedImage filename) {
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
