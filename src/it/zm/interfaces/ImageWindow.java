package it.zm.interfaces;


import it.lilik.capturemjpeg.CaptureMJPEG;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
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


public class ImageWindow extends JPanel {

	public JFrame frame;
	
	private String url;

	private CaptureMJPEG capture;
	
	BufferedImage img; // Img currently shown
	
	/**
	 * Create the application.
	 */
	public ImageWindow(String u, JFrame f) {
		frame = f;
		url = u;
		img = null;
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
	
	public void setPosAndSize(int x,int y,int width,int height){
		this.setBounds(x, y, width, height);
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private void initialize() throws MalformedURLException, IOException {
		  frame.getContentPane().add(this);
		
		  capture = new CaptureMJPEG(this,url);
		  capture.startCapture();
	}
	
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
    
    public void changeImage(BufferedImage i){
    	img = i;
    	this.repaint();
    }

    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(frame.getWidth(), frame.getHeight());
       }
    }
	
	public void captureMJPEGEvent(Image img){
                
        BufferedImage bi = (BufferedImage) img;

        //final Image background = ImageUtils.scaleImage(bi.getHeight(), bi.getWidth(), (BufferedImage)img);
        //final Dimension jpanelDimensions = new Dimension(new ImageIcon(background).getIconWidth(), new ImageIcon(background).getIconHeight());

        changeImage(bi);
	}

	public String getName() {
		return new String("ImageWindow");
	}

}

// Deprecated
class ImgWindow extends JPanel {

    BufferedImage img;

    public ImgWindow() {
        img = null;
     }
    
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
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


