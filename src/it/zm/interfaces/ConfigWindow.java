package it.zm.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class ConfigWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblHost;
	private JTextField textField_1;
	private JLabel lblUsername;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public ConfigWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(147, 29, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblHost = new JLabel("Host");
		lblHost.setBounds(23, 35, 61, 16);
		contentPane.add(lblHost);
		
		textField_1 = new JTextField();
		textField_1.setBounds(147, 66, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		lblUsername = new JLabel("Username");
		lblUsername.setBounds(23, 72, 96, 16);
		contentPane.add(lblUsername);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(147, 106, 134, 28);
		contentPane.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(23, 112, 61, 16);
		contentPane.add(lblPassword);
		
		JCheckBox chckbxAttivaApplicazioneFull = new JCheckBox("Attiva applicazione full screen");
		chckbxAttivaApplicazioneFull.setBounds(20, 164, 324, 23);
		contentPane.add(chckbxAttivaApplicazioneFull);
		
		JButton btnSalva = new JButton("Salva");
		btnSalva.setBounds(57, 222, 117, 29);
		contentPane.add(btnSalva);
		
		JButton btnCancel = new JButton("Annulla");
		btnCancel.setBounds(262, 222, 117, 29);
		contentPane.add(btnCancel);
	}
}
