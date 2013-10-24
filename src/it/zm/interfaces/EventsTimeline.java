package it.zm.interfaces;

import it.zm.util.ImageUtils;
import it.zm.data.MonitorEvent;
import it.zm.xml.DataEvents;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.apache.http.client.HttpClient;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;


public class EventsTimeline{
	static JLabel lblEvents;
	static JButton btnPrev, btnNext;
	static JTable tblEvents;
	static DefaultTableModel mtblEvents; //Table model
	static JFrame frmMain;
	static Container pane;
	static JScrollPane stblEvents; //The scrollpane
	static JPanel pnlEvents;
	
	static HttpClient client;
	static String auth;
	static String baseUrl;
	static int page; // Actual page to be shown
	static String ID; // Monitor ID
	static DataEvents de;
	
	public EventsTimeline(String base, HttpClient cl, String id, String a){
		client = cl;
		baseUrl = base;
		page = 0;
		ID = id;
		auth = a;
	}

	public void show(){
		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
		frmMain = new JFrame ("Eventi"); //Create frame
		frmMain.setSize(800, 600); 
		pane = frmMain.getContentPane(); //Get content pane
		pane.setLayout(null); //Apply null layout
		frmMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked

		//Create controls
		lblEvents = new JLabel ("1");
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		mtblEvents= new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex){return false;}
			  
				@Override
		        public Class getColumnClass(int column)
		        {
					// Show icon for image or video
		            if (column == 3 || column == 4)
		            {

		                return ImageIcon.class;
		            }
		            return Object.class;
		            // other code; default to Object.class
		        }
		};
		tblEvents = new JTable(mtblEvents);
		stblEvents = new JScrollPane(tblEvents);
		pnlEvents = new JPanel(null);

		//Set border
		pnlEvents.setBorder(BorderFactory.createTitledBorder("Eventi"));

		//Register action listeners
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());

		//Add controls to pane
		pane.add(pnlEvents);
		pnlEvents.add(lblEvents);
		pnlEvents.add(btnPrev);
		pnlEvents.add(btnNext);
		pnlEvents.add(stblEvents);

		//Set bounds
		pnlEvents.setBounds(0, 0, 800, 580);
		lblEvents.setBounds(400, 25, 100, 25);
		btnPrev.setBounds(10, 25, 50, 25);
		btnNext.setBounds(740, 25, 50, 25);
		stblEvents.setBounds(10, 50, 780, 520);
		
		// Set table properties
		
		// Add mouse listener
		tblEvents.addMouseListener(new clickCell_Action());
		
		//No resize/reorder
		tblEvents.getTableHeader().setResizingAllowed(false);
		tblEvents.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tblEvents.setColumnSelectionAllowed(true);
		tblEvents.setRowSelectionAllowed(true);
		tblEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		mtblEvents.addColumn("ID");
		mtblEvents.addColumn("Data");
		mtblEvents.addColumn("Durata");
		mtblEvents.addColumn("Visualizza immagine");
		mtblEvents.addColumn("Visualizza filmato");
		mtblEvents.addColumn("Maxframeid");
		
		// Fetch events
		de = new DataEvents(baseUrl, client, ID);
		de.setPage(new String(Integer.toString(page)));
		de.fetchData();
		List li = de.getAllEvents();
		
		mtblEvents.setRowCount(li.size());
		for(int i =0; i < li.size(); ++i ){
			MonitorEvent e = (MonitorEvent) li.get(i);
									
			mtblEvents.setValueAt(e.id, i, 0);
			mtblEvents.setValueAt(e.time, i, 1);
			mtblEvents.setValueAt(e.duration, i, 2);
			mtblEvents.setValueAt(e.maxframeid, i, 5); // Hide this field
			tblEvents.setRowHeight(i, 50);
			
			URL imgURL = ClassLoader.getSystemResource("resources/image.png");
			ImageIcon imgIcon = new ImageIcon(imgURL); // Get icon
			imgIcon = ImageUtils.createImage(50, 50, imgIcon); // Resize

			URL videoURL = ClassLoader.getSystemResource("resources/play.png");
			ImageIcon videoIcon = new ImageIcon(videoURL); // Get icon
			videoIcon = ImageUtils.createImage(50, 50, videoIcon); // Resize
			
			mtblEvents.setValueAt(imgIcon, i, 3); // TODO set icon
			mtblEvents.setValueAt(videoIcon, i, 4); // TODO set icon
		}
		
		//Make frame visible
		frmMain.setResizable(false);
		frmMain.setVisible(true);

	}
	
	private static BufferedImage getUnscaledImageForEvent(String evID, String maxframeid){
		BufferedImage bi = null;
		// Get the id of the frame
		Integer numFrame = Integer.parseInt(maxframeid);
		String frame = String.format("%03d", numFrame);;
		
		// Get the string of the image in this format "http://192.168.69.104/zm/events/3/2494859/011-analyse.jpg"
		String base = new String(baseUrl.replaceAll("index.php", ""));
		String u =	base+"events/"+ID+"/"+evID+"/"+frame+"-analyse.jpg";
				
		try {
			bi = ImageUtils.createImage(new URL(u));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bi;
	}


	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if(page == 0)
				return;
			page--;
			
			de.setPage(new String(Integer.toString(page)));
			de.fetchData();
			List li = de.getAllEvents();
			
			mtblEvents.setRowCount(li.size());
			for(int i =0; i < li.size(); ++i ){
				MonitorEvent ev = (MonitorEvent) li.get(i);
											
				mtblEvents.setValueAt(ev.id, i, 0);
				mtblEvents.setValueAt(ev.time, i, 1);
				mtblEvents.setValueAt(ev.duration, i, 2);
			}
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			page++; // Hey there is an issue here, I have to detect the last page and stop incrementing
			
			de.setPage(new String(Integer.toString(page)));
			de.fetchData();
			List li = de.getAllEvents();
			
			mtblEvents.setRowCount(li.size());
			for(int i =0; i < li.size(); ++i ){
				MonitorEvent ev = (MonitorEvent) li.get(i);
											
				mtblEvents.setValueAt(ev.id, i, 0);
				mtblEvents.setValueAt(ev.time, i, 1);
				mtblEvents.setValueAt(ev.duration, i, 2);
			}
		}
	}
	
	static class clickCell_Action extends MouseAdapter{
		  public void mouseClicked(MouseEvent e) {
		    if (e.getClickCount() == 2) {
		      JTable target = (JTable)e.getSource();
		      int row = target.getSelectedRow();
		      int column = target.getSelectedColumn();
		      if(column == 3 || column == 4){
		    	  String event = (String) target.getValueAt(row, 0); // Get event id
		    	  String maxframeid = (String) target.getValueAt(row, 5);
		    	  
		    	  if(column == 3){
		    		  // Show the image with the alarm
		    		  BufferedImage ic = getUnscaledImageForEvent(event, maxframeid);
		    		  
		    		  // Get the image
		    		  SingleImageWindow iw = new SingleImageWindow(ic);
		    		  
		    		  iw.show();
		    	  } else if(column == 4){
		    		  // Show the event
		    		  
		    		  EventWindow ew = new EventWindow(baseUrl, ID, event, auth);
		    		  
		    		  ew.show();
		    	  }
		      }
		    }
		  }
	}
	
}
