import org.postgresql.ssl.DbKeyStoreSocketFactory.DbKeyStoreSocketException;


public class Tasker {
	
	DatabaseManager db = new DatabaseManager();

	public static void main(String[] args) {
		
		Tasker tasker = new Tasker();

		tasker.testConnection();
	}
	
	public void testConnection() {
		System.out.println(db.testGetContent());
		//System.out.println(db.testConnection());
		//System.out.println(db.getAllContent());	// NOT DONE
	}

}
