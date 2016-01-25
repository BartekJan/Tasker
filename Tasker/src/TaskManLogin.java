import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TaskManLogin{

	private JFrame frmTaskmanLogin;
	private final JButton btnLogIn = new JButton("Login");
	private JTextField txtPassword;
	private JTextField txtEmail;
	private JLabel lblPassword;
	private JLabel lblEmail;
	private JLabel UserPicture;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskManLogin window = new TaskManLogin();
					window.frmTaskmanLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TaskManLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTaskmanLogin = new JFrame();
		frmTaskmanLogin.setTitle("TaskMAN - Login");
		frmTaskmanLogin.setBounds(100, 100, 508, 263);
		frmTaskmanLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTaskmanLogin.getContentPane().setLayout(null);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*try{
					// Connectiong to database to check login  and password
				}
				if(//Username and password is correct){
					JOptionPane.showMessageDialog(null, "Username and password is correct");
					
				}*/
				JOptionPane.showMessageDialog(null, "Username and password is correct");
				frmTaskmanLogin.dispose();
				TaskManTasks tasks= new TaskManTasks();
				tasks.setVisible(true);
			}
		});
		btnLogIn.setBounds(322, 158, 118, 39);
		frmTaskmanLogin.getContentPane().add(btnLogIn);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(330, 124, 118, 23);
		frmTaskmanLogin.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setText("E-mail");
		txtEmail.setBounds(106, 124, 118, 23);
		frmTaskmanLogin.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		JCheckBox chckbxRememberMe = new JCheckBox("Remember me.");
		chckbxRememberMe.setBounds(106, 154, 130, 23);
		frmTaskmanLogin.getContentPane().add(chckbxRememberMe);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(254, 126, 90, 19);
		frmTaskmanLogin.getContentPane().add(lblPassword);
		
		lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(59, 128, 60, 14);
		frmTaskmanLogin.getContentPane().add(lblEmail);
		
		UserPicture = new JLabel("");
		Image defaultUserPic= new ImageIcon(this.getClass().getResource("/DefaultUser2.png")).getImage();
		UserPicture.setIcon(new ImageIcon(defaultUserPic));
		UserPicture.setBounds(217, 25, 72, 72);
		frmTaskmanLogin.getContentPane().add(UserPicture);
	}
}
