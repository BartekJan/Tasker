
public class Actions {

	private DatabaseManager db = new DatabaseManager();
	private TaskManLogin taskerLogin;

	private String startdate = "";
	private String enddate = "";
	private String status = "";
	private String element = "";
	private String comments = "";
	
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
	
	
	public void getTaskInfo(String taskTitle) {
		String[] allInfo = db.getAllTaskInfo(taskTitle);
		
		startdate = allInfo[0];
		enddate = allInfo[1];
		status = allInfo[2];
		element = allInfo[3];
		comments = allInfo[4];
	}
	
	public String getStartDate() {
		return startdate;
	}
	public String getEndDate() {
		return enddate;
	}
	public String getStatus() {
		return status;
	}
	public String getElement() {
		return element;
	}
	public String getComments() {
		return comments;
	}
	
	public String getName(String memberEmail) {
		return db.getName(memberEmail);
	}
	
	public String[] getAllUserTaskTitles() {
		return db.getAllUserTaskTitles();
	}
	
	public void runLogin(Tasker taskObject) {
		taskObject.runLogin();
	}
	
	public void saveComment(String taskName, String memberEmail, String comment) {
		db.saveComment(taskName, memberEmail, comment);
	}
	
	public void changeStatus(String taskName, int status) {
		db.changeStatus(taskName, status+1);
	}
}
