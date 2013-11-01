package it.zm.interfaces;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.http.client.HttpClient;

public class EventWindow implements ChangeListener {
	  // http://192.168.69.104/cgi-bin/nph-zms?source=event&mode=jpeg&event=2503467&frame=1&scale=100&rate=100&maxfps=5&replay=single&auth=9ca5b26378b565b34ea1133042b337d0&connkey=13977&rand=1382646636
	  // Try to fill the grid of the superframe
		
	  // Create image view
		//ImageWindow window = new ImageWindow("http://192.168.69.104/cgi-bin/nph-zms?mode=jpeg&monitor="
						//+ IDs.get(i) + "&scale=100&maxfps=5&buffer=1000&"+auth, frame, true);
		
		// Set its size and position
		//window.setPosAndSize((int)i%gridSize, (int)i/gridSize ,playGroundX/gridSize ,playGroundX/gridSize);
	
	JFrame frame;
	
	VideoPanel iw;
		
	String auth;
	
	String eventID;
	
	String baseUrl;
	
	String ID; // Monitor ID
	
	int frames;
	
	public EventWindow(String base, String id, String eID, String a, String f){
		baseUrl = base;
		eventID = eID;
		auth = a;
		ID = id;
		frames = Integer.parseInt(f);
	}
	
	public void show(){
		frame = new JFrame ("Evento");

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked
		
		// Create the slider
		
		JSlider frameSlider = new JSlider(JSlider.HORIZONTAL,
                1, frames, 1);
		
		frame.add(frameSlider, BorderLayout.PAGE_START);
		
		// Create the panel
		
		System.out.println("http://192.168.69.104/cgi-bin/nph-zms?source=event&mode=jpeg&event="+eventID+"&monitor="
				+ ID + "&frame=1&scale=100&maxfps=5&buffer=1000&replay=single&"+auth);
		
		iw = new VideoPanel("http://192.168.69.104/cgi-bin/nph-zms?source=event&mode=jpeg&event="+eventID+"&monitor="
				+ ID + "&frame=1&scale=100&maxfps=5&buffer=1000&replay=single&"+auth, frame, false);
	
		// Add the panel
		
		iw.setSlider(frameSlider);
		frameSlider.addChangeListener(this);
		
		// Showtime
		frame.setVisible(true);
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int fr = (int)source.getValue();
            if (fr != iw.getCurrentFrame()) {
            	iw.chengeStreamPosition(fr);
            }
        }
		
	}
}
