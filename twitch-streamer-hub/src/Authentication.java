
public class Authentication {
	HashTable<User> authTable;
	
	UserDatabaseIO uio;
	
	public Authentication(UserDatabaseIO uio) {
	}
	
	private void populateAuthTable() {
		LinkedList<User> list = uio.getUserList();
		list.positionIterator();
		while(!list.offEnd()) {
			authTable.insert();
			list.advanceIterator();
		}
	}
}
