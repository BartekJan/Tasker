import javax.security.auth.login.LoginContext;

import org.postgresql.ssl.DbKeyStoreSocketFactory.DbKeyStoreSocketException;


public class Tasker {
	
	DatabaseManager db = new DatabaseManager();
	TaskManLogin loginWindow;
	TaskManTasks mainWindow;
	
	private String memberEmail = "";
	private String memberPass = "";
	private String memberName = "";

	public static void main(String[] args) {
		
		Tasker tasker = new Tasker();
		tasker.run();
		
	}
	
	private void run() {
		testConnection();
		
		while(true) {
			runLogin();
			memberName = db.getName(memberEmail);
			runMainWindow();
		}
	}
	
	public void runLogin() {
		
		loginWindow = new TaskManLogin();
		
		loginWindow.paintWindow();
		
		boolean loingCorrect = false;
		String test = "";
		
		 // Wait for login
		while (!loingCorrect) {
			if (!loginWindow.getEmail().equals("")) {
			
				if (checkEmail(loginWindow.getEmail())) {
					if (db.loginDetails(loginWindow.getEmail(), loginWindow.getPassword())) {
						// Valid email and password. User is now logged in
						loingCorrect = true;
						memberEmail = loginWindow.getEmail();
						memberPass = loginWindow.getPassword();
						loginWindow.setMessage("Valid email and password");
						loginWindow.exitWindow();
					}
					else {
						loginWindow.setMessage("Wrong email or password");
					}
				}
				else {
					// print error
					loginWindow.setMessage("Please enter a valid email address");
				}
				loginWindow.setEmail("");
				loginWindow.setPassword("");
			
			}
		}
	}

	
	public void runMainWindow () {
		mainWindow = new TaskManTasks();
		
		mainWindow.paintWindow(memberName);
		
		boolean done = false;
		
		while(!done) {
			if (mainWindow.getLogOut()) {
				mainWindow.exitWindow();
				done = true;
			}
		}
	}
	
	
	public boolean checkEmail(String email) {
		
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
		
	}
	
	public void testConnection() {
//		if (db.createNewMember("Tom", "Christoffersen", "tom@gmail.com", "tomsPassword") == false) {
//			System.out.println("Could not insert into database");
//		}
		//System.out.println(db.getAllMembers());
		//System.out.println(db.testGetContent());
		//System.out.println(db.testConnection());
		//System.out.println(db.getAllContent());	// NOT DONE
		//System.out.println(db.loginDetails("even@gmail.com", "evensPassword"));
	}

}
