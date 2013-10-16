package it.zm.loader;

import javax.swing.*; 

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.awt.EventQueue;
import java.awt.GridLayout;
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
	
	
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				String baseUrl = new String("http://192.168.69.104/zm/index.php");
				
				// Create Http connection for everyone
				HttpClient client = new DefaultHttpClient();
				
				int playGroundX = 2000, playGroundY = 1000; // TODO evaluate the playground 
				
				// Authenticate
				authenticator = new ZmHashAuth(baseUrl, "admin", "laboratorio55", client);

				String auth = authenticator.getAuthHash();
				
				// Get number of cameras
				DataCameras dc = new DataCameras(baseUrl, client);
				dc.fetchData();
				int numCameras = dc.getNumCameras();

				// Create the superframe
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(playGroundX, playGroundY);
				frame.setResizable(false);
				
				// Evaluate the size of the grid
				int gridSize = (int) Math.ceil(Math.sqrt(numCameras));
				System.out.println(gridSize);
				
				frame.setLayout(new GridLayout(gridSize,gridSize));
				
				try {
					for(int i=0; i < numCameras; ++i){
						// Try to fill the grid of the superframe
						
						// Create image view
						ImageWindow window = new ImageWindow("http://192.168.69.104/cgi-bin/nph-zms?mode=jpeg&monitor="
										+ i + "&scale=100&maxfps=5&buffer=1000&"+auth, frame);
						
						// Set its size and position
						window.setPosAndSize((int)i%gridSize, (int)i/gridSize ,playGroundX/gridSize ,playGroundX/gridSize);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				frame.setVisible(true);
			}
		});
    }

}
