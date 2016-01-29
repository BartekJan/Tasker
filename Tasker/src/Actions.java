
public class Actions {

	DatabaseManager db = new DatabaseManager();
	
	public String loginTest(String email, String password) {
	
		if (checkEmail(email)) {
			if (db.loginDetails(email, password)) {
				// Valid email and password. User is now logged in
//				memberEmail = loginWindow.getEmail();
//				memberPass = loginWindow.getPassword();
				return "Valid email and password";
			}
			else {
				return "Wrong email or password";
			}
		}
		else {
			// print error
			return "Please enter a valid email address";
		}
//		loginWindow.setEmail("");
//		loginWindow.setPassword("");
	}
	
	private boolean checkEmail(String email) {
		
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
		
	}
	
	
}
