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
	
	public String getAllMembers() {
		
		String table = "members";
		Connection c = connect();
		Statement stmt = null;
		String tableContent = "";
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
			
			while (rs.next()) {
				tableContent += rs.getInt("id");
	            tableContent += "\t\t";
	            tableContent += rs.getString("email");
	            tableContent += "\t\t";
	            tableContent += rs.getString("firstname");
	            tableContent += "\t\t";
	            tableContent += rs.getString("surname");
	            tableContent += "\t\t";
	            tableContent += rs.getString("password");
	            tableContent += "\n";
	         }
			
		} catch (SQLException e) {
			closeConnection(c, stmt);
			e.printStackTrace();
			return "Could not get content from " + table;
		}
		
		closeConnection(c, stmt);
        return tableContent;
	}
	
	public String testGetContent() {
		String table = "test";
		Connection c = connect();
		
		Statement stmt;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
			
			while (rs.next()) {
	            int id = rs.getInt("id");
	            String testtext = rs.getString("testtext");
	            return(id + "\t" +testtext);
	         }
			
		} catch (SQLException e) {
			e.printStackTrace();
			return "Could not get content from " + table;
		}
		
        return "Could not get content from " + table;
	}
	
	public String testConnection() {
		Connection c = null;
		try {
	    	  
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection(URL,USER, PASS);
	         
	      } catch (Exception e) {
	    	  
	         //e.printStackTrace();
	         return (e.getClass().getName()+": "+e.getMessage());
	         
	      }
		return "Connection successfull";
	}
}