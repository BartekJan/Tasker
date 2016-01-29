import javax.security.auth.login.LoginContext;

import org.postgresql.ssl.DbKeyStoreSocketFactory.DbKeyStoreSocketException;


public class Tasker {
	TaskManLogin loginWindow = new TaskManLogin(this);

	public static void main(String[] args) {
		
		Tasker tasker = new Tasker();
		tasker.run();
		
	}
	
	/**
	 * This is the method that starts
	 * the first login window
	 */
	private void run() {
		
		while(true) {
			if(!loginWindow.getRunning() && !loginWindow.mainRunning())
				runLogin();
		}
	}
	
	/**
	 * This method will call the function
	 * that draws the
	 * first login window
	 */
	public void runLogin() {
		
		loginWindow.paintWindow();
		
	}

}
