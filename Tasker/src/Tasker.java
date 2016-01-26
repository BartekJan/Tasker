import org.postgresql.ssl.DbKeyStoreSocketFactory.DbKeyStoreSocketException;


public class Tasker {
	
	DatabaseManager db = new DatabaseManager();

	public static void main(String[] args) {
		
		Tasker tasker = new Tasker();

		tasker.testConnection();
	}
	
	public void testConnection() {
//		if (db.createNewMember("Tom", "Christoffersen", "tom@gmail.com", "tomsPassword") == false) {
//			System.out.println("Could not insert into database");
//		}
		//System.out.println(db.getAllMembers());
		//System.out.println(db.testGetContent());
		//System.out.println(db.testConnection());
		//System.out.println(db.getAllContent());	// NOT DONE
		System.out.println(db.loginDetails("even@gmail.com", "evensPassword"));
	}

}
