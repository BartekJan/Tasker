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
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class TaskManTasks implements ActionListener{

	private dbInfo dbinfo = new dbInfo();
	
	private String taskName = "";
	private String memberEmail = "";
	private String memberName = "";
	boolean logoutVal = false;
	private boolean running = false;
	
	private JFrame frmTasks = new JFrame();
	Actions action = new Actions();
	private Tasker tasker;
	boolean cbAutoChange = false;
	
	
	private JComboBox cbStatus = new JComboBox();
	private JButton commentButton = new JButton("Add New Comment");
	private JLabel lblstartdate = new JLabel("startdate");
	private JLabel lblenddate = new JLabel("enddate");
	private JLabel lblStatus = new JLabel("Status");
	private JEditorPane taskContent = new JEditorPane();
	private JEditorPane editorPane = new JEditorPane();
	private JButton btnInfo = new JButton("Add new DB");
	

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public TaskManTasks() {
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void paintWindow(String name) {
		running = true;
		memberName = name;
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
		frmTasks.setResizable(false);
		frmTasks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTasks.getContentPane().setLayout(null);
		
		JLabel userPic = new JLabel("User pic");
		Image defaultUserPic= new ImageIcon(this.getClass().getResource("/DefaultUser2.png")).getImage();
		userPic.setIcon(new ImageIcon(defaultUserPic));
		userPic.setBounds(29, 30, 72, 72);
		frmTasks.getContentPane().add(userPic);
		
		JLabel userName = new JLabel(memberName);
		userName.setBounds(136, 26, 149, 23);
		frmTasks.getContentPane().add(userName);
		
		JList taskList = new JList();
		taskList.setBorder(new LineBorder(new Color(0, 0, 0)));
		taskList.setBounds(136, 338, -109, -192);
		frmTasks.getContentPane().add(taskList);
		
		btnInfo.setBounds(600, 200, 120, 23);
		btnInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dbinfo.paintWindow();
				
			}
		});
		frmTasks.getContentPane().add(btnInfo);
		
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
		
		editorPane.setEditable(false);
		editorPane.setBounds(200, 240, 300, 150);
		frmTasks.getContentPane().add(editorPane);
		
		JLabel lblTaskContent = new JLabel("Task content:");
		lblTaskContent.setBounds(200, 60, 100, 14);
		frmTasks.getContentPane().add(lblTaskContent);
		
		commentButton.setBounds(270, 400, 150, 23);
		commentButton.addActionListener(this);
		frmTasks.getContentPane().add(commentButton);
		
		lblStatus.setBounds(600, 80, 72, 14);
		
		cbStatus.setBounds(600, 80, 75, 16);
		cbStatus.addItem("Allocated");
		cbStatus.addItem("Abandoned");
		cbStatus.addItem("Completed");
		cbStatus.addActionListener(this);
		frmTasks.getContentPane().add(cbStatus);
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
	
	private void setStatus(String s) {
		cbAutoChange = true;
		cbStatus.setSelectedItem(s);
	}
	
	public void setTaskTitles(String[] titles) {
		
		ArrayList<JButton> buttonList = new ArrayList<JButton>();
		int buttonCounter = 0;
		JPanel buttonPanel = new JPanel();
		
		for (String t : titles) {
			if (t == null)
				break;
			buttonList.add(new JButton(t));
			//buttonPanel.add(new JButton(t));
		}
		for (JButton b : buttonList) {
			b.addActionListener(this);
			buttonPanel.add(b);
			buttonCounter++;
		}
		buttonPanel.setLayout( new GridLayout(buttonCounter, 1) );
		
		JScrollPane scrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(29, 130, 170, 280);
		frmTasks.add(scrollPane);
		
		frmTasks.invalidate();
		frmTasks.validate();
		frmTasks.repaint();
	}
	
	private void updateTask(ActionEvent e) {
		taskName = e.getActionCommand();
		action.getTaskInfo(e.getActionCommand());
		
		setStatus(action.getStatus());
		lblstartdate.setText(action.getStartDate());
		lblenddate.setText(action.getEndDate());
		//lblStatus.setText(action.getStatus());
		taskContent.setText(action.getElement());
		editorPane.setText(action.getComments());
	}
	
	private void newComment() {
		
		commentButton.setText("Save Comment");
		editorPane.setEditable(true);
		editorPane.setText("Type here..");
	}
	
	private void saveComment() {
		action.saveComment(taskName, memberEmail, editorPane.getText());
		commentButton.setText("Add New Comment");
		editorPane.setEditable(false);
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
		taskName = "";
	}
	
	public void setMemberEmail(String email) {
		memberEmail = email;
	}
	
	private void changedStatus() {
		action.changeStatus(taskName, cbStatus.getSelectedIndex());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Add New Comment" && taskName != "")
			newComment();
		else if (e.getActionCommand() == "Add New Comment" && taskName == "")
			return;
		else if (e.getActionCommand() == "Save Comment")
			saveComment();
		else if (e.getActionCommand() == "comboBoxChanged" && cbAutoChange) {
			cbAutoChange = false;
			return;
		}
		else if (e.getActionCommand() == "comboBoxChanged" && taskName != "")
			changedStatus();
		else if (e.getActionCommand() == "comboBoxChanged" && taskName == "")
			return;
		else
			updateTask(e);
	}
	
}

