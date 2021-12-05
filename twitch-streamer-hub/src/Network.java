public class Network {
	Graph net;
	UserDatabaseIO uio;
	public Network(UserDatabaseIO uio) {
		this.uio = uio;
		net = new Graph(uio.getUserList().getLength());
		
		LinkedList<User> list = uio.getUserList();
		list.positionIterator();
		while(!list.offEnd()) {
			int count = list.getIterator().getFriends().getSize();
			String friends = list.getIterator().getFriends().toString();
			while(count > 0) {
				//net.addUndirectedEdge(list.getIterator().getId(), );
				count--;
			}
			list.advanceIterator();
		}
		
	}
	public Network() {
		// TODO Auto-generated constructor stub
	}
	
//	public getFriendRecommendations() {
//		
//	}
}
