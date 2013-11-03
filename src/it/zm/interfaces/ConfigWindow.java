package it.zm.interfaces;

import it.zm.data.ConfigData;
import it.zm.data.MonitorEvent;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;

import javax.swing.JCheckBox;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class ConfigWindow extends JDialog {

	private static JPanel contentPane;
	private static JTextField textField;
	private static JLabel lblHost;
	private static JTextField textField_1;
	private static JLabel lblUsername;
	private static JPasswordField passwordField;
	private static JCheckBox chckbxAttivaApplicazioneFull;
	
	private static ConfigData config;
	
	/**
	 * Create the frame with default data
	 * @param lock 
	 */
	public ConfigWindow(ConfigData cd) {
		config = cd;
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setText(config.baseUrl);
		textField.setBounds(147, 29, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblHost = new JLabel("Host");
		lblHost.setBounds(23, 35, 61, 16);
		contentPane.add(lblHost);
		
		textField_1 = new JTextField();
		textField_1.setText(config.username);
		textField_1.setBounds(147, 66, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		lblUsername = new JLabel("Username");
		lblUsername.setBounds(23, 72, 96, 16);
		contentPane.add(lblUsername);
		
		passwordField = new JPasswordField();
		passwordField.setText(config.password);
		passwordField.setBounds(147, 106, 134, 28);
		contentPane.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(23, 112, 61, 16);
		contentPane.add(lblPassword);
		
		chckbxAttivaApplicazioneFull = new JCheckBox("Attiva applicazione full screen");
		chckbxAttivaApplicazioneFull.setBounds(20, 164, 324, 23);
		chckbxAttivaApplicazioneFull.setSelected(config.fullOnActive);
		contentPane.add(chckbxAttivaApplicazioneFull);
		
		JButton btnSalva = new JButton("Salva");
		btnSalva.setBounds(57, 222, 117, 29);
		btnSalva.addActionListener(new btnSave_Action(this));
		contentPane.add(btnSalva);
		
		JButton btnCancel = new JButton("Annulla");
		btnCancel.setBounds(262, 222, 117, 29);
		btnCancel.addActionListener(new btnCancel_Action(this));
		contentPane.add(btnCancel);
		
	}

	static class btnSave_Action implements ActionListener{
		ConfigWindow frame;
		
		public btnSave_Action(ConfigWindow f){
			frame =f;
		}
		
		public void actionPerformed (ActionEvent e){
			
			// Save data
			config.baseUrl = textField.getText();
			config.username = textField_1.getText();
			config.password = new String(passwordField.getPassword());
			config.fullOnActive = chckbxAttivaApplicazioneFull.isSelected();
			
			// And close
			frame.setVisible(false); 
			frame.dispose();
		}
	}
	
	static class btnCancel_Action implements ActionListener{
		ConfigWindow frame;
		
		public btnCancel_Action(ConfigWindow f){
			frame =f;
		}
		
		public void actionPerformed (ActionEvent e){
			// Do nothing and close
			
			frame.setVisible(false); 
			frame.dispose();
		}
	}

}

