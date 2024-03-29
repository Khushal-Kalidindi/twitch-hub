
public class User implements Comparable<User> {
	private int id;
	private String name;
	private String username;
	private String password;
	private int friendCount;
	private BST<User> friends;
	private String city;
	private int interestCount;
	private LinkedList<Interest> interests;
	
	public User(int id,String name,String username,String password,int friendCount,BST<User> friends,String city, int interestCount, LinkedList<Interest> interests) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.friendCount = friendCount;
		this.friends = friends;
		this.city = city;
		this.interestCount = interestCount;
		this.interests = interests;
	}
	
	public User(String username,String password) {
		this.username = username;
		this.password = password;
	}
	
	public User() {
		
	}
	
	
	public int getFriendCount() {
		return friendCount;
	}

	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public String getPassword() {
		return password;
	}

	public String getCity() {
		return city;
	}

	public int getInterestCount() {
		return interestCount;
	}

	public void setName(String name) {
		this.name = name;
	}




	public BST<User> getFriends() {
		return friends;
	}




	public void setFriends(BST<User> friends) {
		this.friends = friends;
	}




	public LinkedList<Interest> getInterests() {
		return interests;
	}




	public void setInterests(LinkedList<Interest> interests) {
		this.interests = interests;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}




	public void setPassword(String password) {
		this.password = password;
	}

	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public void setInterestCount(int interestCount) {
		this.interestCount = interestCount;
	}

	
	
	/**
	 * Returns a consistent hash code for each User by summing the Unicode
	 * values of each character in the key key = username + password
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		String key = username.toLowerCase() + password; // this is the key. The element is the User Object
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
	/**
	 * Returns whether two objects are Users
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof User)) {
			return false;
		} else {
			if (this.compareTo(((User) o)) == 0) {
				return true;
			}
			return false;
		}
	}
	
	public void printSelfProfile() {
		System.out.println("Name: " + name);
		System.out.println("Username:" + username);
		System.out.println("Password: " + password);
		System.out.println("You have " + friendCount + " friends:");
		for (User friend : friends.toArrayList()) {
			System.out.println("\t"+friend.getName());
		}
		System.out.println("You have "+ interestCount + " interests: ");
		LinkedList<Interest> temp = interests;
		temp.positionIterator();
		while(!temp.offEnd()) {
			System.out.println("\t"+temp.getIterator().getName());
			temp.advanceIterator();
		}		
	}
	
	public void printUserProfile() {
		System.out.println("----");
		System.out.println("Name: " + name);
		System.out.println("Username:" + username);
		System.out.println(name + " has " + friendCount + " friends:");
		for (User friend : friends.toArrayList()) {
			System.out.println("\t"+friend.getName());
		}
		System.out.println(name + " has " + interestCount + " interests: ");
		LinkedList<Interest> temp = interests;
		temp.positionIterator();
		while(!temp.offEnd()) {
			System.out.println("\t"+temp.getIterator().getName());
			temp.advanceIterator();
		}
		System.out.println("----");

	}
	
	
	public String toString() {
		return "id : " +  id + "\n" +
				"name : " +  name + "\n" +
				"username : " +  username + "\n" +
				"password : " +  password + "\n" +
				"friendCount : " +  friendCount + "\n" +
				"friends : \n" +  friends +
				"city : " +  city + "\n" +
				"interestCount : " +  interestCount + "\n"+
				"interests : " +  interests + "\n";
		
	}

	@Override
	public int compareTo(User o) {
		if (this.name.compareTo(o.name) != 0) {
			return this.name.compareTo(o.name);
		} else {
			return ((Integer)this.id).compareTo((Integer)o.id);
		}
	}
	
	
}