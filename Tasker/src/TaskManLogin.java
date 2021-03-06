import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.PublicKey;


public class TaskManLogin{

	private TaskManTasks mainWindow = new TaskManTasks();
	private Actions action = new Actions();
	private JFrame frmTaskmanLogin;
	private final JButton btnLogIn = new JButton("Login");
	private JPasswordField txtPassword;
	private JTextField txtEmail;
	private JLabel lblPassword;
	private JLabel lblEmail;
	private JLabel lblMessage;
	private JLabel UserPicture;
	
	private String memberEmail = "";
	private String memberPass = "";
	private boolean rememberMe = false;
	private boolean running = false;

	/**
	 * Launch the application.
	 */


	public TaskManLogin(Tasker taskerObject) {
		mainWindow.setTaskerObject(taskerObject);
	}
	
	/**
	 * Create the application.
	 */
	public void paintWindow() {
		running = true;
		initialize();
		this.frmTaskmanLogin.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTaskmanLogin = new JFrame();
		frmTaskmanLogin.setTitle("TaskMAN - Login");
		frmTaskmanLogin.setBounds(100, 100, 508, 263);
		frmTaskmanLogin.setResizable(false);
		frmTaskmanLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTaskmanLogin.getContentPane().setLayout(null);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				memberEmail = txtEmail.getText();
				memberPass = txtPassword.getText();
				lblMessage.setText(action.loginTest(memberEmail, memberPass));
				txtEmail.setText("");
				txtPassword.setText("");
				if(lblMessage.getText() == "Valid email and password") {
					runMainWindow();
				}
			}
		});
		btnLogIn.setBounds(322, 158, 118, 39);
		frmTaskmanLogin.getContentPane().add(btnLogIn);
		
		txtPassword = new JPasswordField();
		txtPassword.setText("Password");
		txtPassword.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				txtPassword.setText("");
			}
		});
		txtPassword.setBounds(330, 124, 118, 23);
		frmTaskmanLogin.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setText("E-mail");
		txtEmail.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				txtEmail.setText("");
				
			}
		});
		txtEmail.setBounds(106, 124, 118, 23);
		frmTaskmanLogin.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		JCheckBox chckbxRememberMe = new JCheckBox("Remember me.");
		chckbxRememberMe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxRememberMe.isSelected())
					rememberMe = true;
				else if (!chckbxRememberMe.isSelected())
					rememberMe = false;
				
			}
		});
		chckbxRememberMe.setBounds(106, 154, 130, 23);
		frmTaskmanLogin.getContentPane().add(chckbxRememberMe);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(254, 126, 90, 19);
		frmTaskmanLogin.getContentPane().add(lblPassword);
		
		lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(59, 128, 60, 14);
		frmTaskmanLogin.getContentPane().add(lblEmail);
		
		lblMessage = new JLabel("");
		lblMessage.setBounds(110, 190, 200, 19);
		frmTaskmanLogin.getContentPane().add(lblMessage);
		
		UserPicture = new JLabel("");
		Image defaultUserPic= new ImageIcon(this.getClass().getResource("/DefaultUser2.png")).getImage();
		UserPicture.setIcon(new ImageIcon(defaultUserPic));
		UserPicture.setBounds(217, 25, 72, 72);
		frmTaskmanLogin.getContentPane().add(UserPicture);
	}
	
	public String getEmail() {
		return memberEmail;
	}
	
	public String getPassword() {
		return memberPass;
	}
	
	public void setEmail(String email) {
		memberEmail = email;
	}
	
	public void setPassword(String pass) {
		memberPass = pass;
	}
	
	public boolean getRememberMe() {
		return rememberMe;
	}
	
	public void popupWindiw(String message) {
		JOptionPane.showMessageDialog(null, message);
		return;
	}
	
	public void setMessage(String message) {
		lblMessage.setText(message);
	}
	
	public boolean getRunning() {
		return running;
	}
	
	public void exitWindow() {
		running = false;
		frmTaskmanLogin.dispose();
	}
	
	public void runMainWindow () {
		
		mainWindow.setMemberEmail(memberEmail);
		mainWindow.paintWindow(action.getName(memberEmail));
		mainWindow.setTaskTitles(action.getAllUserTaskTitles());
		
		exitWindow();
	}
	
	public boolean mainRunning() {
		return mainWindow.getRunning();
	}
}
