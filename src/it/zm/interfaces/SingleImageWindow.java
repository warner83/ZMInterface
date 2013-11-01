package it.zm.interfaces;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SingleImageWindow extends JPanel{
	
	private String url;
		
	private BufferedImage img; // Img to be shown
	
	private JFrame frame;
		
	public SingleImageWindow(BufferedImage ic) {
		img = ic;
	}
	
	public void show(){
		frame = new JFrame ("Immagine evento");
		frame.setSize(img.getWidth(), img.getHeight()); 
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked
		frame.setLocationRelativeTo(null);
		
		// Add the image to the frame I auto-generated
		frame.getContentPane().add(this);
		
		// Showtime
		frame.setVisible(true);
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

}
