
public class User implements Comparable{
	int id;
	String name;
	String username;
	String password;
	int friendCount;
	BST friendList;
	String city;
	int interestCount;
	//TODO add HashTable for Interests
	@Override
	public int compareTo(Object o) {
		// TODO
		return 0;
	}
	
	
}
