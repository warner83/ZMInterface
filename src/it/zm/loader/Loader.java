package it.zm.loader;

import javax.swing.*; 

import java.awt.EventQueue;
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
				
				authenticator = new ZmHashAuth("http://192.168.69.104/zm/index.php", "admin", "laboratorio55");

				String auth = authenticator.getAuthHash();
				
				try {
					ImageWindow window = new ImageWindow("http://192.168.69.104/cgi-bin/nph-zms?mode=jpeg&monitor=3&scale=100&maxfps=5&buffer=1000&"+auth);
					window.show();
					ImageWindow window2 = new ImageWindow("http://192.168.69.104/cgi-bin/nph-zms?mode=jpeg&monitor=1&scale=100&maxfps=5&buffer=1000&"+auth);
					window2.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

}
