import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import org.junit.Test;

/*
 * Test whether an incorrect email address is able to log in.
 */
public class SE_F_029 {

	@Test
	public void test() {
		String testEmail = "test@gmail.com";
		String testPass = "123";
		
		Tasker task = new Tasker();
		TaskManLogin login = new TaskManLogin(task);
		
		task.runLogin();
		login.setEmail(testEmail);
		login.setPassword(testPass);
		JOptionPane.showMessageDialog(null, login.emailTest());
	}

}
