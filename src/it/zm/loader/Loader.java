package it.zm.loader;

import javax.swing.*; 

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

public class Loader {

	private static ZmHashAuth authenticator;
	
	
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				int x = 1000, y = 500;
				
				authenticator = new ZmHashAuth("http://192.168.69.104/zm/index.php", "admin", "laboratorio55");

				String auth = authenticator.getAuthHash();
				
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(x, y);
				frame.setResizable(false);
				frame.setLayout(new GridLayout(0,2));
				
				try {
					ImageWindow window = new ImageWindow("http://192.168.69.104/cgi-bin/nph-zms?mode=jpeg&monitor=3&scale=100&maxfps=5&buffer=1000&"+auth, frame);
					window.setPosAndSize(0,0,x/2,y/2);
					ImageWindow window2 = new ImageWindow("http://192.168.69.104/cgi-bin/nph-zms?mode=jpeg&monitor=1&scale=100&maxfps=5&buffer=1000&"+auth, frame);
					window2.setPosAndSize(x/2,0,x/2,y/2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				frame.setVisible(true);
				//frame.pack();
			}
		});
    }

}
