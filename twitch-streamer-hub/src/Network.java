import java.util.ArrayList;

public class Network {
	Graph net;
	UserDatabaseIO uio;
	public Network(UserDatabaseIO uio) {
		this.uio = uio;
		net = new Graph(uio.getUserList().getLength());
		
		LinkedList<User> list = uio.getUserList();
		list.positionIterator();
		while(!list.offEnd()) {
			ArrayList<User> friends = list.getIterator().getFriends().toArrayList();
			for (User friend : friends) {
				net.addDirectedEdge(list.getIterator().getId(), friend.getId());
			}
			list.advanceIterator();
		}
		
	}
	
	
	public Graph getNet() {
		return net;
	}
	public void setNet(Graph net) {
		this.net = net;
	}
	
	public void addNewUser() {
		net.updateVariables();
	}
	
	
	public void addFriends(User user1, User user2) {
		if(user1.getFriends().toArrayList().contains(user2)) {
			return;
		}
		else {
			BST<User> user1bst = user1.getFriends();
			user1bst.insert(user2);
			user1.setFriends(user1bst);
			user1.setFriendCount(user1.getFriendCount()+1);
			
			BST<User> user2bst = user2.getFriends();
			user2bst.insert(user1);
			user2.setFriends(user2bst);
			user2.setFriendCount(user2.getFriendCount()+1);
			
			net.addDirectedEdge(user1.getId(), user2.getId());
			net.addDirectedEdge(user2.getId(), user1.getId());		
		}
		}
		
	
	public void removeFriends(User user1, User user2) {
		BST<User> user1bst = user1.getFriends();
		user1bst.remove(user2);
		user1.setFriends(user1bst);
		
		BST<User> user2bst = user2.getFriends();
		user2bst.remove(user1);
		user2.setFriends(user2bst);
		
		net.removeDirectedEdge(user1.getId(), user2.getId());
		net.removeDirectedEdge(user2.getId(), user1.getId());		
	}
	
	
	public ArrayList<User> sortByName(ArrayList<User> userArrList) {
		ArrayList<User> out = userArrList;
		out.sort((User u1, User u2) -> (((u1.getName().toLowerCase().compareTo(u2.getName().toLowerCase())))));	
		return out;
		
	}
	
	public ArrayList<User> getUsersFriends(User u) {
		LinkedList<User> userList = uio.getUserList();		
		userList.iteratorToIndex(u.getId()-1);
		return sortByName(userList.getIterator().getFriends().toArrayList());		
	}
	
	
	public ArrayList<User> getUsersByName(String name) {
		ArrayList<User> out = new ArrayList<User>();
		LinkedList<User> userList = uio.getUserList();
		
		userList.positionIterator();
		while(!userList.offEnd()) {
			if(userList.getIterator().getName().toLowerCase().contains(name.toLowerCase())) {
				out.add(userList.getIterator());
			}
			userList.advanceIterator();
		}
		return out;
	}
	
	public ArrayList<User> getUsersByInterest(String interest) {
		boolean in_hashTable = uio.getUserInterestHashTable().search(new Interest(interest,0));
		if(!in_hashTable) {
			System.out.println("No such interest exists on record.");
			return null;
		}	
		else {
			Interest htSearchResult = (Interest)uio.getUserInterestHashTable().searchAndSpit(new Interest(interest,0));
			int inter_id = htSearchResult.getId();
			return uio.getUserInterestsTotalBST().get(inter_id).toArrayList();
		}
		
		
	}
	
	public ArrayList<LinkedList<Object>> getFriendRecommendations(Integer source) {
		ArrayList<LinkedList<Object>> out = new ArrayList<LinkedList<Object>>();
		net.BFS(source);
		ArrayList<Integer> distarraycopy = net.getDistanceArrayList();
		int count = 0;
		for(Integer dist : distarraycopy){
			//Compare source interests to target interests
			if(dist != -1 && dist != 0 && dist != 1 ){				
				int common_interests = this.numOfCommonInterests(source, count);
				//Ranking Logic
				dist = (100 - dist*10) + 5*(common_interests);
				LinkedList<Object> temp = new LinkedList<Object>();
				temp.addLast(dist);
				temp.addLast(uio.getUserArrayList().get(count-1));
				out.add(temp);
			} else {
				dist = -1;
			}
			count++;
			
		}
		out.sort((LinkedList<Object> l1, LinkedList<Object> l2) -> (-1*(Integer.compare((int)l1.getFirst(), (int)l2.getFirst()))));
		
		return out;
	}
	
	private int numOfCommonInterests(int id1, int id2){
		int count = 0;
		LinkedList<Interest> interest1 = uio.getUserArrayList().get(id1-1).getInterests();
		LinkedList<Interest> interest2 = uio.getUserArrayList().get(id2-1).getInterests();
		
		
		int max = Math.max(interest1.getLength(), interest2.getLength());		
		LinkedList<Interest> maxList = (max == interest1.getLength()) ? interest1 : interest2;
		LinkedList<Interest> minList = (max == interest1.getLength()) ? interest2 : interest1;
		
		minList.positionIterator();
		while(!minList.offEnd()) {
			if(maxList.linearSearch(minList.getIterator()) != -1) {
				count++;
			}
			minList.advanceIterator();
		}
		return count;
	}
}