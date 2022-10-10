public class Authentication {
	private HashTable<User> authTable;	
	private UserDatabaseIO uio;
	
	public HashTable<User> getAuthTable() {
		return authTable;
	}

	public Authentication(UserDatabaseIO uio) {
		this.uio = uio;
		this.authTable = new HashTable<User>(50);
		populateAuthTable();
	}
	
	public void populateAuthTable() {
		LinkedList<User> list = uio.getUserList();
		list.positionIterator();
		while(!list.offEnd()) {
			authTable.insert(list.getIterator());
			list.advanceIterator();
		}
	}
	
	public void addUserToAuthTable(User u) {
		authTable.insert(u);
	}
	
	public User authenticate(String username, String password) {
		User temp = new User(username, password);
		LinkedList<User> users = authTable.getBucket(temp);
		if(users != null) {
			users.positionIterator();
			while(!users.offEnd()) {
				User user = users.getIterator();
				if(username.toLowerCase().equals(user.getUsername().toLowerCase())) {
					return user;
				}
				users.advanceIterator();
			}
		}
		return null;
	}
	
	public boolean validateUsername(String username) {
		LinkedList<User> userList = uio.getUserList();
		userList.positionIterator();
		while(!userList.offEnd()) {
			if(userList.getIterator().getUsername().toLowerCase().equals(username.toLowerCase()))
				return true;
			userList.advanceIterator();
		}
		return false;
	}
	
}