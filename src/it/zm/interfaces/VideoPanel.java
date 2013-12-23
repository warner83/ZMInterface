package it.zm.interfaces;


import it.lilik.capturemjpeg.CaptureMJPEG;
import it.zm.util.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.BorderLayout;
import java.awt.Image;
import java.net.URL;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import it.lilik.capturemjpeg.TextImage;


public class VideoPanel extends JPanel {

	public JFrame frame;
	
	private String url;

	private CaptureMJPEG capture;
	
	private boolean resize;
	
	private boolean bwSaver;
	
	BufferedImage img; // Img currently shown
	
	JSlider slider; 
	
	int position; // Position in the slider
	
	/**
	 * Create the application.
	 */
	public VideoPanel(String u, JFrame f, boolean res) {
		frame = f;
		url = u;
		img = null;
		resize = res;
		slider=null; // If this remain null there is no slider in the panel
		position = 1;
		bwSaver = false;
		
		if(res){
			// The image window has to be resized -> it is part of a mosaic, let's handle mouse click
			this.addMouseListener(new MouseHander(url, f));
		} else {
			// This image is not part of a mosaic, let it be always on top
			frame.setAlwaysOnTop(true);
		}
		
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
	
	public void setFrameSize(int width, int height){
		if(!(frame.getWidth() == width && frame.getHeight() == height)){ // Update size only if needed
			frame.setSize(width , height);
			frame.setLocationRelativeTo(null);
		}
	}
	
	public void setBwSaver(boolean bws){
		bwSaver = bws;
	}
	
	public int getCurrentFrame(){
		return position;
	}

	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private void initialize() throws MalformedURLException, IOException {
		
		  // Initialize frame
		  frame.getContentPane().add(this);
		
		  // Display text image for loading action
		  img = new TextImage("Caricamento...");
		  frame.setLocationRelativeTo(null);
		  frame.repaint();
		  
		  // Start capture
		  capture = new CaptureMJPEG(this,url);
		  capture.startCapture();
		  
		  capture.setResizable(resize);
		 
	}
	
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
    
    public void changeImage(BufferedImage i){
    	img = i;
    	
    	position++;
    	
    	// Update the slider, if one
    	if(slider != null){
    		slider.setValue(position);
    	}
    	
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
        
        changeImage(bi);
   	}

	public Boolean isFocused(){
		return frame.isFocused();
	}
	
	public Boolean bwSaverActive(){
		return bwSaver;
	}
	
	public String getName() {
		return new String("ImageWindow");
	}

	public void setSlider(JSlider frameSlider) {
		slider = frameSlider;
	}

	public void chengeStreamPosition(int fr) {
		// Replace stream position in the url
		url = url.replaceAll("frame=[^&]+","frame=" + fr);
		
		// Update URL
		capture.setURL(url);
		
		// Update position and slider, if any
		position = fr;
    	if(slider != null){
    		slider.setValue(position);
    	}
	}

}

// Manage click on a frame
class MouseHander implements MouseListener{
	String url;
	
	JFrame frame; // JFrame that will be created
	
	JFrame parent; // Parent JFrame
	
	public MouseHander(String u, JFrame p){
		url = u;
		frame = null;
		parent = p;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		// Create a new window only this is the first one or the old one has been closed
		if( frame == null || !frame.isShowing() ){ 
			// Create the superframe
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(400, 300);
			
			// Create image view
			url = url.replaceAll("scale=[^&]+","scale=100");
			System.out.print(url);
			VideoPanel window = new VideoPanel(url, frame, false);
			
			// Set visible
			frame.setVisible(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}