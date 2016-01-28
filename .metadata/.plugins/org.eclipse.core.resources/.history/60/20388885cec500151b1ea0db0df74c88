import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class TaskManTasks {

	
	private String memberEmail = "";
	boolean logoutVal = false;
	
	private JFrame frmTasks;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TaskManTasks window = new TaskManTasks();
//					window.frmTasks.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public TaskManTasks() {
		//initialize();
	}
	
	public void paintWindow(String email) {
		memberEmail = email;
		initialize();
		this.frmTasks.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTasks = new JFrame();
		frmTasks.setTitle("Tasks");
		frmTasks.setBounds(100, 100, 740, 500);
		frmTasks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTasks.getContentPane().setLayout(null);
		
		JLabel userPic = new JLabel("User pic");
		Image defaultUserPic= new ImageIcon(this.getClass().getResource("/DefaultUser2.png")).getImage();
		userPic.setIcon(new ImageIcon(defaultUserPic));
		userPic.setBounds(29, 30, 72, 72);
		frmTasks.getContentPane().add(userPic);
		
		JLabel userName = new JLabel(memberEmail);
		userName.setBounds(136, 26, 149, 23);
		frmTasks.getContentPane().add(userName);
		
		JList taskList = new JList();
		taskList.setBorder(new LineBorder(new Color(0, 0, 0)));
		taskList.setBounds(136, 338, -109, -192);
		frmTasks.getContentPane().add(taskList);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(500, 15, 89, 23);
		frmTasks.getContentPane().add(updateButton);
		
		JButton logoutButton = new JButton("Log out");
		logoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logoutVal = true;
			}
		});
		logoutButton.setBounds(600, 15, 89, 23);
		frmTasks.getContentPane().add(logoutButton);
		
//		JLabel progressBarTitle = new JLabel("Progress Bar:");
//		progressBarTitle.setHorizontalAlignment(SwingConstants.CENTER);
//		progressBarTitle.setBounds(250, 420, 149, 23);
//		frmTasks.getContentPane().add(progressBarTitle);
		
//		JProgressBar progressBar = new JProgressBar();
//		progressBar.setBounds(250, 440, 149, 23);
//		frmTasks.getContentPane().add(progressBar);
		
		JTextArea taskContent = new JTextArea();
		taskContent.setBounds(200, 240, 300, 150);
		frmTasks.getContentPane().add(taskContent);
		
		JLabel lblTaskContent = new JLabel("Task content:");
		lblTaskContent.setBounds(210, 60, 100, 14);
		frmTasks.getContentPane().add(lblTaskContent);
		
		/* Can set this to take text from a URL, was thinking this was a way to 
		display the task details?*/
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setBounds(200, 80, 300, 150);
		//Enable scroll bar.
		JScrollPane editorScrollPane = new JScrollPane(editorPane);
		editorScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frmTasks.getContentPane().add(editorPane);
		
		JButton commentButton = new JButton("Add comment");
		commentButton.setBounds(270, 400, 150, 23);
		frmTasks.getContentPane().add(commentButton);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * If true, the user has pressed 
	 * the logout button
	 * @return true or false
	 */
	public boolean getLogOut() {
		return logoutVal;
	}
	
	/**
	 * Closes the window
	 */
	public void exitWindow() {
		frmTasks.dispose();
	}
	
}

