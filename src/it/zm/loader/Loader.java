package it.zm.loader;

import javax.swing.*; 

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import it.zm.auth.ZmHashAuth;
import it.zm.interfaces.*;
import it.zm.xml.DataCameras;

public class Loader {

	private static ZmHashAuth authenticator;
	
	private static JFrame frame;
	
	private static JMenuBar eventMenuBar;
	
	private static JMenu eventMenu;
	
	private static String baseUrl;
	
	private static String auth;
	
	private static HttpClient client;
		
	public static void initMenu(DataCameras dc){
		eventMenuBar = new JMenuBar();
		
		List<String> IDs = dc.getIDs();
		List<String> names = dc.getNames();
		
		// For each monitor add an entry
		
		JMenu eventMenu = new JMenu();
	    
		eventMenuBar.add(eventMenu);
    
		eventMenu.setText("Eventi");
		
		for(int i=0;i<names.size(); ++i){
			
			JMenuItem text = new JMenuItem();
			
			text.setText(names.get(i));
			
			text.addActionListener(new MenuListener(baseUrl, auth, IDs.get(i), client));
			
			eventMenu.add(text);
		}
		
		frame.setJMenuBar(eventMenuBar);
	}
	
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				baseUrl = new String("http://192.168.69.104/zm/index.php");
				
				// Create Http connection for everyone
				client = new DefaultHttpClient();
				
				// Get screen resolution
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double width = screenSize.getWidth();
				double height = screenSize.getHeight();
				
				int playGroundX = (int)width, playGroundY = (int) height; 
				
				// Authenticate
				authenticator = new ZmHashAuth(baseUrl, "admin", "laboratorio55", client);

				auth = authenticator.getAuthHash();
				
				// Get number of cameras
				DataCameras dc = new DataCameras(baseUrl, client);
				dc.fetchData();
				int numCameras = dc.getNumCameras();

				// Create the superframe
				frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(playGroundX, playGroundY);
				frame.setResizable(false);
				
				// Evaluate the size of the grid
				int gridSize = (int) Math.ceil(Math.sqrt(numCameras));
				System.out.println(gridSize);
				
				frame.setLayout(new GridLayout(gridSize,gridSize));
				
				List<String> IDs = dc.getIDs();
				
				try {
					for(int i=0; i < numCameras; ++i){
						// Try to fill the grid of the superframe
						
						// Create image view
						//VideoPanel window = new VideoPanel("http://192.168.69.104/cgi-bin/nph-zms?mode=jpeg&monitor="
										//+ IDs.get(i) + "&scale=100&maxfps=5&buffer=1000&"+auth, frame, true);
						
						// Set its size and position
						//window.setPosAndSize((int)i%gridSize, (int)i/gridSize ,playGroundX/gridSize ,playGroundX/gridSize);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Initialize menu
				initMenu(dc);
				
				// Showtime
				frame.setVisible(true);
			}
		});
    }

}

class MenuListener implements ActionListener{

	String baseUrl;
	String auth;
	String ID;
	HttpClient client;
	
	public MenuListener(String b, String a, String i, HttpClient h){
		baseUrl = b;
		auth = a;
		ID = i;
		client = h;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		EventsTimeline c = new EventsTimeline(baseUrl,client,ID, auth);
		c.show();
	}
	
}
