import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import org.junit.Test;


public class JUnitCLI {

	/*
	 * Test whether an incorrect email address is able to log in.
	 */
	@Test
	public void test_29() {
		String testEmail = "test_email@gmail.com";
		String testPass = "123";
		
		Actions action = new Actions();
		
		JOptionPane.showMessageDialog(null, action.loginTest(testEmail, testPass));
		
		}

	/*
	 * Test whether the email-text field is empty
	 */
	@Test
	public void test_30() {
		String testEmail = "";
		String testPass = "123";
		
		Actions action = new Actions();
		
		JOptionPane.showMessageDialog(null, action.loginTest(testEmail, testPass));
	}

	/*
	 * Test whether the password-text field is empty
	 */
	@Test
	public void test_31() {
		String testEmail = "einar.dogger@gmail.com";
		String testPass = "";
		
		Actions action = new Actions();
		
		JOptionPane.showMessageDialog(null, action.loginTest(testEmail, testPass));
	}
	
	/*
	 * Test whether an incorrect password is entered
	 */
	@Test
	public void test_32() {
		String testEmail = "einar.dogger@gmail.com";
		String testPass = "asdsa";
		
		Actions action = new Actions();
		
		JOptionPane.showMessageDialog(null, action.loginTest(testEmail, testPass));
	}
	
	/*
	 * Check to see whether incorrect input is acknowledged.
	 */
	@Test
	public void test_33() {
		String testEmail = "WSF!#$2sds#$%";
		String testPass = "sdf3445&^";
		
		Actions action = new Actions();
		
		JOptionPane.showMessageDialog(null, action.loginTest(testEmail, testPass));
	}

}
