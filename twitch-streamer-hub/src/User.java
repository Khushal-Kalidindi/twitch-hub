
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
	
	public User(int id,String name,String username,String password,int friendCount,BST<User> friends,String city, int interestCount) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.friendCount = friendCount;
		this.friends = friends;
		this.city = city;
		this.interestCount = interestCount;
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
	 * Returns a consistent hash code for each Butterfly by summing the Unicode
	 * values of each character in the key key = family + species
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		String key = username + password; // this is the key. The element is the Butterfly Object
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
	/**
	 * Returns whether two objects are Butterflies, and have the same family and
	 * species (only)
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
	
	public String toString() {
		return "id : " +  id + "\n" +
				"name : " +  name + "\n" +
				"username : " +  username + "\n" +
				"password : " +  password + "\n" +
				"friendCount : " +  friendCount + "\n" +
				"friends : \n" +  friends +
				"city : " +  city + "\n" +
				"interestCount : " +  interestCount + "\n";
		
	}

	//TODO add HashTable for Interests
	@Override
	public int compareTo(User o) {
		if (this.name.compareTo(o.name) != 0) {
			return this.name.compareTo(o.name);
		} else {
			return ((Integer)this.id).compareTo((Integer)o.id);
		}
	}
	
	
}
