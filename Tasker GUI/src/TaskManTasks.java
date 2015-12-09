import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;


public class TaskManTasks {

	private JFrame frmTasks;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskManTasks window = new TaskManTasks();
					window.frmTasks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TaskManTasks() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTasks = new JFrame();
		frmTasks.setTitle("Tasks");
		frmTasks.setBounds(100, 100, 612, 456);
		frmTasks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTasks.getContentPane().setLayout(null);
		
		JLabel userPic = new JLabel("User pic");
		Image defaultUserPic= new ImageIcon(this.getClass().getResource("/DefaultUser2.png")).getImage();
		userPic.setIcon(new ImageIcon(defaultUserPic));
		userPic.setBounds(29, 30, 72, 72);
		frmTasks.getContentPane().add(userPic);
		
		JLabel userName = new JLabel("User name");
		userName.setBounds(136, 26, 149, 23);
		frmTasks.getContentPane().add(userName);
		
		JList taskList = new JList();
		taskList.setBorder(new LineBorder(new Color(0, 0, 0)));
		taskList.setBounds(136, 338, -109, -192);
		frmTasks.getContentPane().add(taskList);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(341, 26, 89, 23);
		frmTasks.getContentPane().add(updateButton);
		
		JButton logoutButton = new JButton("Log out");
		logoutButton.setBounds(473, 26, 89, 23);
		frmTasks.getContentPane().add(logoutButton);
		
		JLabel progressBarTitle = new JLabel("Progress Bar");
		progressBarTitle.setHorizontalAlignment(SwingConstants.CENTER);
		progressBarTitle.setBounds(241, 269, 149, 23);
		frmTasks.getContentPane().add(progressBarTitle);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(241, 294, 149, 23);
		frmTasks.getContentPane().add(progressBar);
		
		JTextArea taskContent = new JTextArea();
		taskContent.setBounds(216, 145, 200, 113);
		frmTasks.getContentPane().add(taskContent);
		
		JLabel lblTaskContent = new JLabel("Task content:");
		lblTaskContent.setBounds(279, 120, 83, 14);
		frmTasks.getContentPane().add(lblTaskContent);
		
		JButton commentButton = new JButton("Add comment");
		commentButton.setBounds(251, 338, 125, 23);
		frmTasks.getContentPane().add(commentButton);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}


/*import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TaskManTasks extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskManTasks frame = new TaskManTasks();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
/*	public TaskManTasks() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel userPic = new JLabel("User pic");
		Image defaultUserPic= new ImageIcon(this.getClass().getResource("/DefaultUser2.png")).getImage();
		userPic.setIcon(new ImageIcon(defaultUserPic));
		userPic.setBounds(29, 30, 72, 72);
		contentPane.getRootPane().add(userPic);
		
		JLabel userName = new JLabel("User name");
		userName.setBounds(136, 26, 149, 23);
		contentPane.getRootPane().add(userName);
		
		JList taskList = new JList();
		taskList.setBorder(new LineBorder(new Color(0, 0, 0)));
		taskList.setBounds(136, 338, -109, -192);
		contentPane.getRootPane().add(taskList);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(341, 26, 89, 23);
		contentPane.getRootPane().add(updateButton);
		
		JButton logoutButton = new JButton("Log out");
		logoutButton.setBounds(473, 26, 89, 23);
		contentPane.getRootPane().add(logoutButton);
		
		JLabel progressBarTitle = new JLabel("Progress Bar");
		progressBarTitle.setHorizontalAlignment(SwingConstants.CENTER);
		progressBarTitle.setBounds(241, 269, 149, 23);
		contentPane.getRootPane().add(progressBarTitle);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(241, 294, 149, 23);
		contentPane.getRootPane().add(progressBar);
		
		JTextArea taskContent = new JTextArea();
		taskContent.setBounds(216, 145, 200, 113);
		contentPane.getRootPane().add(taskContent);
		
		JLabel lblTaskContent = new JLabel("Task content:");
		lblTaskContent.setBounds(279, 120, 83, 14);
		contentPane.getRootPane().add(lblTaskContent);
		
		JButton commentButton = new JButton("Add comment");
		commentButton.setBounds(251, 338, 125, 23);
		contentPane.getRootPane().add(commentButton);
	}
}
*/