
public class Authentication {
	HashTable<User> authTable;
	
	UserDatabaseIO uio;
	
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
				return false;
			userList.advanceIterator();
		}
		return true;
	}
	
	//public void createAccount(String )
}
