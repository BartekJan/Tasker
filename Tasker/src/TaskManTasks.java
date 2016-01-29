import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class TaskManTasks implements ActionListener{

	
	private String memberEmail = "";
	boolean logoutVal = false;
	private boolean running = false;
	
	private JFrame frmTasks = new JFrame();
	Actions action = new Actions();
	private Tasker tasker;
	
	
	JLabel lblstartdate = new JLabel("startdate");
	JLabel lblenddate = new JLabel("enddate");
	JLabel lblStatus = new JLabel("Status");
	JEditorPane taskContent = new JEditorPane();
	JEditorPane editorPane = new JEditorPane();
	

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public TaskManTasks() {
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void paintWindow(String email) {
		running = true;
		memberEmail = email;
		initialize();
		this.frmTasks.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
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
				runLogin();
				clearWindow();
				exitWindow();
			}
		});
		logoutButton.setBounds(600, 15, 89, 23);
		frmTasks.getContentPane().add(logoutButton);
		
		lblstartdate.setBounds(590, 400, 100, 14);
		frmTasks.getContentPane().add(lblstartdate);
		
		lblenddate.setBounds(590, 420, 100, 14);
		frmTasks.getContentPane().add(lblenddate);
		
		taskContent.setEditable(false);
		taskContent.setBounds(200, 80, 300, 150);
		frmTasks.getContentPane().add(taskContent);
		//frmTasks.getContentPane().add(taskContent);
//		JScrollPane taskScrollPane = new JScrollPane(taskContent);
//		taskScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		frmTasks.getContentPane().add(taskScrollPane);
		
		editorPane.setEditable(false);
		editorPane.setBounds(200, 240, 300, 150);
		frmTasks.getContentPane().add(editorPane);
		//Enable scroll bar.
//		JScrollPane editorScrollPane = new JScrollPane(editorPane);
//		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		frmTasks.getContentPane().add(editorScrollPane);
		
		JLabel lblTaskContent = new JLabel("Task content:");
		lblTaskContent.setBounds(200, 60, 100, 14);
		frmTasks.getContentPane().add(lblTaskContent);
		
		JButton commentButton = new JButton("Add comment");
		commentButton.setBounds(270, 400, 150, 23);
		commentButton.addActionListener(this);
		frmTasks.getContentPane().add(commentButton);
		
		lblStatus.setBounds(600, 80, 72, 14);
		frmTasks.getContentPane().add(lblStatus);
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * If true, the user has pressed 
	 * the logout button
	 * @return true or false
	 */
//	public boolean getLogOut() {
//		return logoutVal;
//	}
	
	/**
	 * Closes the window
	 */
	public void exitWindow() {
		running = false;
		frmTasks.dispose();
	}
	
	public boolean getRunning() {
		return running;
	}
	
	public void setTaskTitles(String[] titles) {
		
		ArrayList<JButton> buttonList = new ArrayList<JButton>();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new GridLayout(3,1) );
		
		for (String t : titles) {
			if (t == null)
				break;
			buttonList.add(new JButton(t));
			//buttonPanel.add(new JButton(t));
		}
		for (JButton b : buttonList) {
			b.addActionListener(this);
			buttonPanel.add(b);
		}
		
		//Box buttonPanel = Box.createVerticalBox();
		/*
		JButton testButton1 = new JButton("Test1");
		//testButton1.setBounds(500, 15, 89, 23);
		buttonPanel.add(testButton1);
		
		JButton testButton2 = new JButton("Test2");
		//testButton2.setBounds(500, 15, 89, 23);
		buttonPanel.add(testButton2);
		
		JButton testButton3 = new JButton("Test3");
		//testButton2.setBounds(500, 15, 89, 23);
		buttonPanel.add(testButton3);*/
		
		JScrollPane scrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(29, 130, 170, 280);
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.add(buttonPanel);
		frmTasks.add(scrollPane);
		
		frmTasks.invalidate();
		frmTasks.validate();
		frmTasks.repaint();
	}
	
	private void updateTask(ActionEvent e) {
		action.getTaskInfo(e.getActionCommand());
		
		lblstartdate.setText(action.getStartDate());
		lblenddate.setText(action.getEndDate());
		lblStatus.setText(action.getStatus());
		taskContent.setText(action.getElement());
		editorPane.setText(action.getComments());
	}
	
	private void newComment() {
		
		editorPane.setText("");
		editorPane.setEditable(true);
	}
	
	private void runLogin() {
		action.runLogin(tasker);
	}
	
	public void setTaskerObject(Tasker taskerObject) {
		tasker = taskerObject;
	}
	
	private void clearWindow() {
		lblstartdate.setText("start date");
		lblenddate.setText("end date");
		lblStatus.setText("status");
		taskContent.setText("");
		editorPane.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Add comment") 
			newComment();
		else
			updateTask(e);
	}
	
}

