import java.io.*;
import java.util.Scanner;

public class UserDatabaseIO {
	public static final String filename = "users.txt";
	
	
	public UserDatabaseIO() {
		
	}
	
	public User generateUser(Scanner input) {
//		while (input.hasNextLine()) {
//            String line = input.nextLine();
//            System.out.println(line);
//        }
		return null;
	}
	
	public void printDB() {
		try {
            Scanner input = new Scanner(System.in);
            File file = new File(filename);

            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
}
