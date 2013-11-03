package it.zm.loader;

import javax.swing.*; 

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
import it.zm.data.ConfigData;
import it.zm.interfaces.*;
import it.zm.xml.DataCameras;

public class Loader {

	private static ZmHashAuth authenticator;
	
	private static JFrame frame;
	
	private static JMenuBar menuBar;
		
	private static String baseUrl;
	
	private static String auth;
	
	private static HttpClient client;
	
	private static ConfigData confData;
		
	public static void initMenu(DataCameras dc){
		menuBar = new JMenuBar();
		
		// Event menu
		
		List<String> IDs = dc.getIDs();
		List<String> names = dc.getNames();
		
		// For each monitor add an entry
		
		JMenu eventMenu = new JMenu();
	    
		menuBar.add(eventMenu);
    
		eventMenu.setText("Registrazioni");
		
		for(int i=0;i<names.size(); ++i){
			
			JMenuItem text = new JMenuItem();
			
			text.setText(names.get(i));
			
			text.addActionListener(new EventMenuListener(baseUrl, auth, IDs.get(i), client));
			
			eventMenu.add(text);
		}
		
		// App menu
		
		JMenu appMenu = new JMenu();
		
		menuBar.add(appMenu);
		
		appMenu.setText("Menu applicazione");
		
		JMenuItem fullScreen = new JMenuItem();
		
		fullScreen.setText("Attiva full screen");
		
		fullScreen.addActionListener(new FullScreenMenuListener(frame, false, fullScreen));
		
		appMenu.add(fullScreen);
		
		JMenuItem openConfig = new JMenuItem();
		
		openConfig.setText("Configura applicazione");
		
		openConfig.addActionListener(new ConfigMenuListener(confData));
		
		appMenu.add(openConfig);

				
		frame.setJMenuBar(menuBar);		
	}
	
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// Create the superframe
				frame = new JFrame();
				
				// Create configuration data structure
				confData = new ConfigData();
				
				if(!confData.checkConfigFile()){
					// We don't have a configuration file
					System.out.println("No conf file found");
					
					ConfigWindow cw = new ConfigWindow(confData);
					
					// This will wait for the dialog
					cw.setVisible(true);
					
					// Got configuration data -> save them
					confData.save();
				} else {
					// We have a configuration file -> load data
					confData.load();
				}
				
				baseUrl = new String("http://"+confData.baseUrl+"/zm/index.php");
				
				// Create Http connection for everyone
				client = new DefaultHttpClient();
				
				// Authenticate
				authenticator = new ZmHashAuth(baseUrl, confData.username, confData.password, client);

				auth = authenticator.getAuthHash();
				
				// Get number of cameras
				DataCameras dc = new DataCameras(baseUrl, client);
				dc.fetchData();
				int numCameras = dc.getNumCameras();
				
				// Initialize menu
				initMenu(dc);
				
				// Get screen resolution
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double width = screenSize.getWidth();
				double height = screenSize.getHeight() - 20; // 20 seems to be the magic number for menu height
				
				int playGroundX = (int)width, playGroundY = (int) height; 
				
				// Evaluate the size of the grid
				int gridSize = (int) Math.ceil(Math.sqrt(numCameras));
				
				frame.setLayout(new GridLayout(gridSize,gridSize));
				
				List<String> IDs = dc.getIDs();
				
				try {
					for(int i=0; i < numCameras; ++i){
						// Try to fill the grid of the superframe
												
						// Create image view
						VideoPanel window = new VideoPanel("http://"+confData.baseUrl+"/cgi-bin/nph-zms?mode=jpeg&monitor="
										+ IDs.get(i) + "&scale="+dc.getPerc(IDs.get(i), playGroundX/gridSize, playGroundY/gridSize)+"&maxfps=5&buffer=1000&"+auth, frame, true);
						
						// Set its size and position
						window.setPosAndSize((int)i%gridSize, (int)i/gridSize ,playGroundX/gridSize ,playGroundY/gridSize);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Full screen exclusive mode
		        /*GraphicsEnvironment env =
		                GraphicsEnvironment.getLocalGraphicsEnvironment();
		            GraphicsDevice dev = env.getDefaultScreenDevice();
		            frame.setBackground(Color.darkGray);
		            frame.setResizable(false);
		            frame.setUndecorated(true);
		            frame.pack();
		            dev.setFullScreenWindow(frame);*/
				
				// Set properties
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(playGroundX, playGroundY);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				
				// Full size
	            //frame.setResizable(false);
	            //frame.setUndecorated(true);
	            
	            // Showtime
				frame.setVisible(true);
			}
		});
    }

}

class EventMenuListener implements ActionListener{

	String baseUrl;
	String auth;
	String ID;
	HttpClient client;
	
	public EventMenuListener(String b, String a, String i, HttpClient h){
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

class FullScreenMenuListener implements ActionListener{

	JFrame frame;
	
	Boolean fullScreen;
	
	JMenuItem menu;
	
	public FullScreenMenuListener(JFrame f, Boolean fs, JMenuItem m){
		frame = f;
		fullScreen = fs;
		menu = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(fullScreen){
			// App is full screen -> exit
			frame.setVisible(false);
			frame.dispose();
            frame.setResizable(true);
            frame.setUndecorated(false);
            frame.setVisible(true);
			
            menu.setText("Attiva full screen");
            
			fullScreen = false;
		} else {
			// App is not full screen -> go full screen
			frame.setVisible(false);
			frame.dispose();
			frame.setResizable(false);
            frame.setUndecorated(true);
            frame.setVisible(true);
            
            menu.setText("Disattiva full screen");
            
			fullScreen = true;
		}
	}
	
}

class ConfigMenuListener implements ActionListener{
	ConfigData config;

	public ConfigMenuListener(ConfigData cd){
		config = cd;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ConfigWindow frame = new ConfigWindow(config);
		frame.setVisible(true);
		
		// Save
		config.save();
	}
	
}
