import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TaskerLocalStorage {
	DatabaseManager db = new DatabaseManager();
	TaskManLogin loginWindow;
	
	public void main(String[] args) {
		// Create the scanner to write data
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter("local.txt"));
	            for (int i = 0; i < 4; i++) {
	                out.write("test " + "\n");
	            }
	            out.close();
	        } catch (IOException e) {}
		
		savePassword();
	}
	
	public void savePassword(){
		getLoginData();
	}
	
	public String getLoginData(){
		String temppw = loginWindow.getPassword();
		System.out.println(temppw);
		return temppw;
	}
}

// TO BE PASTED INTO OTHER CLASSES
// 	TaskerLocalStorage local = new TaskerLocalStorage();
// 