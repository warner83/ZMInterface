package it.zm.interfaces;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class EventsTimeline{
	static JLabel lblEvents;
	static JButton btnPrev, btnNext;
	static JTable tblEvents;
	static JFrame frmMain;
	static Container pane;
	static JScrollPane stblEvents; //The scrollpane
	static JPanel pnlEvents;

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

		//Make frame visible
		frmMain.setResizable(false);
		frmMain.setVisible(true);

	}


	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			
		}
	}
	
}