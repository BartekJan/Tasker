
public class Actions {

	private DatabaseManager db = new DatabaseManager();
	private TaskManLogin taskerLogin;

	private String startdate = "";
	private String enddate = "";
	private String status = "";
	private String element = "";
	private String comments = "";
	
	/**
	 * This method will test if the 
	 * email is a valid email address
	 * and check if the email and password 
	 * is in the database
	 * @param email
	 * @param password
	 * @return A message that says if if was succeeded or not
	 */
	public String loginTest(String email, String password) {
	
		if (checkEmail(email)) {
			if (db.loginDetails(email, password)) {
				// Valid email and password. User is now logged in
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
	}
	
	/**
	 * This method will check if the email
	 * is built up as an email
	 * 
	 * This function was not written by any of us.
	 * The function was found at:
	 * http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
	 * 28.01.2016
	 * Post written by Pujan Srivastava (17.04.13)
	 * 
	 * @param email
	 * @return true or false
	 */
	private boolean checkEmail(String email) {
		
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
		
	}
	
	/**
	 * Calls the database manager to get
	 * all the info about one specific task
	 * Puts all the information in variables
	 * @param taskTitle
	 */
	public void getTaskInfo(String taskTitle) {
		String[] allInfo = db.getAllTaskInfo(taskTitle);
		
		startdate = allInfo[0];
		enddate = allInfo[1];
		status = allInfo[2];
		element = allInfo[3];
		comments = allInfo[4];
	}
	
	/**
	 * Startdate of task
	 * @return startdate
	 */
	public String getStartDate() {
		return startdate;
	}
	/**
	 * End date of task
	 * @return enddate
	 */
	public String getEndDate() {
		return enddate;
	}
	/**
	 * Status of task
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Element of task
	 * @return element
	 */
	public String getElement() {
		return element;
	}
	/**
	 * Comments of task
	 * @return comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * Returns the name of the member
	 * when email is provided
	 * @param memberEmail
	 * @return Name of member
	 */
	public String getName(String memberEmail) {
		return db.getName(memberEmail);
	}
	/**
	 * Gives all the titles of all the tasks
	 * allocated to that user
	 * @return array of titles
	 */
	public String[] getAllUserTaskTitles() {
		return db.getAllUserTaskTitles();
	}
	/**
	 * runs the login screen 
	 * @param taskObject
	 */
	public void runLogin(Tasker taskObject) {
		taskObject.runLogin();
	}
	/**
	 * Saves a comment in the database
	 * @param taskName
	 * @param memberEmail
	 * @param comment
	 */
	public void saveComment(String taskName, String memberEmail, String comment) {
		db.saveComment(taskName, memberEmail, comment);
	}
	/**
	 * Changes a status in the database
	 * @param taskName
	 * @param status
	 */
	public void changeStatus(String taskName, int status) {
		db.changeStatus(taskName, status+1);
	}
	/**
	 * Liks the databasemanager to a new database
	 * @param url
	 * @param user
	 * @param password
	 * @param port
	 * @param databaseName
	 */
	public void setNewDatabase(String url, String user, String password, String port, String databaseName) {
		db.setNewDatabase(url, user, password, port, databaseName);
	}
}
