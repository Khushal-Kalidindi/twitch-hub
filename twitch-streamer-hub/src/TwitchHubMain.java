import java.util.ArrayList;

public class TwitchHubMain {
	public static void main(String args[]) {
		System.out.println("Hello World");
		UserDatabaseIO uio = new UserDatabaseIO();
		
		LinkedList<User> userList = uio.getUserList();
		userList.print();
		ArrayList<Interest> userInterests = uio.getUserInterestArrList();
		for(Interest x : userInterests) {
			System.out.println(x);
		}
//		BST<String> test = new BST<String>(new String[]{"a","b","c"});
//		System.out.print(test.toString());

	}
}
