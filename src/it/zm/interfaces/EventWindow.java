package it.zm.interfaces;

import javax.swing.JFrame;

import org.apache.http.client.HttpClient;

public class EventWindow {
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
	
	public EventWindow(String base, String id, String eID, String a){
		baseUrl = base;
		eventID = eID;
		auth = a;
		ID = id;
	}
	
	public void show(){
		frame = new JFrame ("Evento");
		//frame.setSize(100, 100); 
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked
		
		System.out.println("http://192.168.69.104/cgi-bin/nph-zms?source=event&mode=jpeg&event="+eventID+"&monitor="
				+ ID + "&scale=100&maxfps=5&buffer=1000&replay=single&"+auth);
		
		iw = new VideoPanel("http://192.168.69.104/cgi-bin/nph-zms?source=event&mode=jpeg&event="+eventID+"&monitor="
				+ ID + "&scale=100&maxfps=5&buffer=1000&replay=single&"+auth, frame, false);
	
		// Showtime
		frame.setVisible(true);
		
	}
}
