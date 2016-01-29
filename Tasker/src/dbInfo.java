import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class dbInfo {
	
	private Actions action = new Actions();
	
	private JFrame frmInfo;
	private JButton btnOK;
	private JTextField txtURL;
	private JTextField txtUser;
	private JTextField txtPassword;
	private JTextField txtPort;
	private JTextField txtDBname;
	private JLabel lblURL;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JLabel lblPort;
	private JLabel lblDBname;
	
	
	public void paintWindow() {
		frmInfo = new JFrame();
		frmInfo.setTitle("TaskMAN - Login");
		frmInfo.setBounds(100, 100, 308, 463);
		frmInfo.setResizable(true);
		frmInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInfo.getContentPane().setLayout(null);
		
		lblURL = new JLabel("URL: ");
		lblURL.setBounds(54, 22, 100, 23);
		frmInfo.getContentPane().add(lblURL);
		
		txtURL = new JTextField();
		txtURL.setBounds(54, 45, 200, 23);
		frmInfo.getContentPane().add(txtURL);
		
		lblPort = new JLabel("Port: ");
		lblPort.setBounds(54, 72, 100, 23);
		frmInfo.getContentPane().add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setBounds(54, 95, 200, 23);
		frmInfo.getContentPane().add(txtPort);
		
		lblUser = new JLabel("Username: ");
		lblUser.setBounds(54, 122, 100, 23);
		frmInfo.getContentPane().add(lblUser);
		
		txtUser = new JTextField();
		txtUser.setBounds(54, 145, 200, 23);
		frmInfo.getContentPane().add(txtUser);
		
		lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(54, 172, 100, 23);
		frmInfo.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(54, 195, 200, 23);
		frmInfo.getContentPane().add(txtPassword);
		
		lblDBname = new JLabel("Database Name");
		lblDBname.setBounds(54, 222, 100, 23);
		frmInfo.getContentPane().add(lblDBname);
		
		txtDBname = new JTextField();
		txtDBname.setBounds(54, 245, 200, 23);
		frmInfo.getContentPane().add(txtDBname);
		
		btnOK = new JButton("ok");
		btnOK.setBounds(94, 275, 120, 23);
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				action.setNewDatabase(txtURL.getText(), txtUser.getText(), txtPassword.getText(), txtPort.getText(), txtDBname.getText());
			}
		});
		frmInfo.getContentPane().add(btnOK);
		

		this.frmInfo.setVisible(true);
		frmInfo.invalidate();
		frmInfo.validate();
		frmInfo.repaint();
	}

}
