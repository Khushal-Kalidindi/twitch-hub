import java.util.ArrayList;

public class TwitchHubMain {
	public static void main(String args[]) {
		System.out.println("Hello World");
		UserDatabaseIO uio = new UserDatabaseIO();
		
		LinkedList<User> userList = uio.getUserList();
		userList.print();
		
//		HashTable<Interest> userInterests = uio.getUserInterestHashTable();
//		System.out.println(userInterests);
//		
//		ArrayList<BST<User>> uITB = uio.getUserInterestsTotalBST();
//		System.out.println(uITB);
//
//		Authentication auth = new Authentication(uio);
//		System.out.println(auth.authenticate("Ludwig", "iluvqt"));
//		System.out.println(auth.validateUsername("ludwigasd"));
		
		Network n = new Network(uio);
		
		


//		BST<String> test = new BST<String>(new String[]{"a","b","c"});
//		System.out.print(test.toString());

	}
}
