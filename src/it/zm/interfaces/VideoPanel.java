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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;


public class VideoPanel extends JPanel {

	public JFrame frame;
	
	private String url;

	private CaptureMJPEG capture;
	
	private boolean resize;
	
	BufferedImage img; // Img currently shown
	
	/**
	 * Create the application.
	 */
	public VideoPanel(String u, JFrame f, boolean res) {
		frame = f;
		url = u;
		img = null;
		resize = res;
		
		if(res){
			// The image window has to be resized -> it is part of a mosaic, let's handle mouse click
			this.addMouseListener(new MouseHander(url));
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
		frame.setSize(width , height);
		frame.setLocationRelativeTo(null);
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
		  
		  capture.setResizable(resize);
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
        
        changeImage(bi);
   	}

	public Boolean isFocused(){
		return frame.isFocused();
	}
	
	public String getName() {
		return new String("ImageWindow");
	}

}

// Manage click on a frame
class MouseHander implements MouseListener{
	String url;
	
	public MouseHander(String u){
		url = u;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Create the superframe
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 300);
		
		// Create image view
		url = url.replace("scale=", "scole=");
		url += "scale=100"; // Worse thing ever done, replace with regexpr
		System.out.print(url);
		VideoPanel window = new VideoPanel(url, frame, false);
		
		// Set visible
		frame.setVisible(true);
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