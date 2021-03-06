import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;




public class DatabaseManager {
	
	//Database credentials.
	String URL = "jdbc:postgresql://db.dcs.aber.ac.uk:5432/cs27020_15_16";
	String USER = "nep5";
	String PASS = "groupXYZ";
	int memberID = 0;

	/**
	 * Links to a new database
	 * @param url
	 * @param user
	 * @param password
	 * @param port
	 * @param databaseName
	 */
	public void setNewDatabase(String url, String user, String password, String port, String databaseName) {
		URL = "jdbc:postgresql://" + url + ":" + port + "/" + databaseName;
		USER = user;
		PASS = password;
	}

	/**
	 * Connects to the database
	 * @return the connection (Connection c)
	 */
	private Connection connect() {
		Connection c = null;
		try {
	    	  
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection(URL,USER, PASS);
	         
	      } catch (Exception e) {
	    	  
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	         
	      }
		return c;
	}
	
	private void closeConnection(Connection c, Statement stmt) {
		try{
	         if(stmt!=null)
	            c.close();
	      }catch(SQLException se){
	      }// do nothing
	}
	
	
	public boolean loginDetails(String memberEmail, String memberPassword) {
		
		Connection c = connect();
		Statement stmt = null;
		String tableContent = "";
		
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT password FROM members WHERE email='" + memberEmail + "'");
			
			while (rs.next()) {
				tableContent = rs.getString("password");
			}
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
			return false;
		}
		
		closeConnection(c, stmt);
		
		if (tableContent.equals(memberPassword)) {
			return true;
		}
		else
			return false;
	}
	
	
	
	
	public boolean createNewMember(String fName, String sName, String email, String password) {
		Connection c = connect();
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO members " + "VALUES ( 2, '" + email + "','" + fName + "','" + sName + "','" + password + "')";
			stmt.executeUpdate(sql);
			closeConnection(c, stmt);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			closeConnection(c, stmt);
			return false;
		}
	}
	
	public String getName(String memberEmail) {
		Connection c = connect();
		Statement stmt = null;
		String tableContent = "";		
		
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, firstname, surname FROM members WHERE email='" + memberEmail + "'");
			
			while (rs.next()) {
				memberID = rs.getInt("id");
				tableContent = rs.getString("firstname");
				tableContent += " ";
				tableContent += rs.getString("surname");
			}
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
			return "Could not get name";
		}
		
		closeConnection(c, stmt);
		
		return tableContent;
	}
	
	private int[] getAllTasksID(Connection c) {
		int[] taskIDs = new int[500];
		Statement stmt = null;
		String tableContent = "";
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT task_id FROM taskmembers WHERE member_id=" + memberID);
			
			int idCounter = 0;
			while (rs.next()) {
				
				taskIDs[idCounter] = rs.getInt("task_id");
				
				idCounter++;
	         }
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
		}
		
		
        return taskIDs;
	}
	/**
	 * Returns a string array
	 * of all the titles of the tasks
	 * @return string array
	 */
	public String[] getAllUserTaskTitles() {
		String[] taskTitles = new String[500];
		Connection c = connect();
		Statement stmt = null;
		
		int[] taskIDs = getAllTasksID(c);
		int idCounter = 0;
		for (int i : taskIDs) {
			
			if (i == 0)
				break;
			
			try {
				
				stmt = c.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT title FROM tasks WHERE id=" + i);
				
				while (rs.next()) {
					
					taskTitles[idCounter] = rs.getString("title");
					
					idCounter++;
		         }
				
			} catch (SQLException e) {
				closeConnection(c, stmt);
				e.printStackTrace();
			}
			
		}
		
		closeConnection(c, stmt);
		
		return taskTitles;
	}
	
	/**
	 * Returns an array of Strings
	 * with 5 elements containing the information of the task
	 * @param taskTitle
	 * @return String array (5 elements)
	 */
	public String[] getAllTaskInfo(String taskTitle) {
		
		String[] allInfo = new String[5];
		String startdate = "";
		String enddate = "";
		int status = 0;
		int id = 0;
		String statusString = "";
		String element = "";
		
		Connection c = connect();
		Statement stmt = null;
		String tableContent = "";
		String comments = "";
		int elementID = 0;
		int elementMemberID = 0;
		String memberName = "";
		
		try {
			stmt = c.createStatement();
			
			// Get startdate, enddate and the id of the status
			ResultSet rs = stmt.executeQuery("SELECT id, startdate, enddate, status FROM tasks WHERE title='" + taskTitle + "'");
			
			while (rs.next()) {
				id = rs.getInt("id");
				startdate = rs.getString("startdate");
				enddate = rs.getString("enddate");
				status = rs.getInt("status");
	         }
			
			// Get status from id
			rs = stmt.executeQuery("SELECT text FROM taskstatuses WHERE id=" + status );
			while (rs.next()) {
				statusString = rs.getString("text");
			}
			
			// Get element from id
			rs = stmt.executeQuery("SELECT id, text FROM taskelements WHERE task_id=" + id );
			while (rs.next()) {
				elementID = rs.getInt("id");
				element = rs.getString("text");
			}
			
			// Get element from id
			comments = "";
			rs = stmt.executeQuery("SELECT member_id, text FROM taskelementcomments WHERE taskelement_id=" + elementID );
			while (rs.next()) {
				elementMemberID = rs.getInt("member_id");
				comments += getUsername(c, elementMemberID);
				comments += ": ";
				comments += rs.getString("text");
				comments += "\n";
			}
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
		}
		allInfo[0] = startdate;
		allInfo[1] = enddate;
		allInfo[2] = statusString;
		allInfo[3] = element;
		allInfo[4] = comments;
		
		closeConnection(c, stmt);
        return allInfo;
		
	}
	/**
	 * Returns a name based on the id
	 * @param c
	 * @param elementMemberID
	 * @return String full name
	 */
	private String getUsername(Connection c, int elementMemberID) {
		String memberName = "";
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT firstname, surname FROM members WHERE id=" + elementMemberID );
			while (rs.next()) {
				memberName = rs.getString("firstname");
				memberName += " ";
				memberName += rs.getString("surname");
			}
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
		}

		return memberName;
	}
	
	/**
	 * Finds the correct id and
	 * saves the comment
	 * @param taskName
	 * @param memberEmail
	 * @param comment
	 */
	public void saveComment(String taskName, String memberEmail, String comment) {
		Connection c = connect();
		// Find member ID
		
		int memberID = 0;
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM members WHERE email='" + memberEmail + "'");
			while (rs.next()) {
				memberID = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
		}
		
		
		// Find task ID
		
		int taskID = 0;
		stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM tasks WHERE title='" + taskName + "'");
			while (rs.next()) {
				taskID = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
		}
		
		// Find task ID
		
		int taskElementID = 0;
		stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM taskelements WHERE task_id='" + taskID + "'");
			while (rs.next()) {
				taskElementID = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
		}
		
		int commentID = 0;
		stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM taskelementcomments");
			while (rs.next()) {
				commentID = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
		}
		commentID++;
		// Insert comment
		stmt = null;
		
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO taskelementcomments " + "VALUES ( "+ commentID +", " + memberID + "," + taskElementID + ",'" + comment + "')";
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
			closeConnection(c, stmt);
		}
		
		closeConnection(c, stmt);
	}
	/**
	 * Changes the status of a task in the database
	 * @param taskName
	 * @param status
	 */
	public void changeStatus(String taskName, int status) {
		Connection c = connect();
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			String sql = "UPDATE tasks SET status="+ status +" WHERE title='"+ taskName +"'";
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
			closeConnection(c, stmt);
		}
		
		closeConnection(c, stmt);
	}
}