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
	
	public String testGetContent () {
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
	
	public String getAllContent() {
		// NOT DONE
		Statement stmt = null;
		Connection c = connect();
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM tasks");
			ResultSetMetaData rsmd = rs.getMetaData();
			String name = rsmd.getColumnName(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return "";
	}
}