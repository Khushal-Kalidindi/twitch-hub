import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class UserDatabaseIO {
	public static final String filename = "users.txt";
	private int lastInterestID = 0;
	
	private LinkedList<User> userList;
	private ArrayList<User> userArrayList;
	private BST<User> userBST;
	private HashTable<Interest> userInterestHashTable;
	private ArrayList<BST<User>> userInterestsTotalBST;
	
	public ArrayList<BST<User>> getUserInterestsTotalBST() {
		return userInterestsTotalBST;
	}

	public LinkedList<User> getUserList() {
		return userList;
	}

	public ArrayList<User> getUserArrayList() {
		return userArrayList;
	}

	public BST<User> getUserBST() {
		return userBST;
	}
	
	public void updateDataStructures(User newUser) {
		userList.addLast(newUser);
		userArrayList.add(newUser);
		userBST.insert(newUser);		
		
		LinkedList<Interest> interests = newUser.getInterests();
		LinkedList<Interest> actualInterests = new LinkedList<Interest>();
		interests.positionIterator();
		while(!interests.offEnd()) {
			actualInterests.addLast(validateInterest(newUser, interests.getIterator().getName()));
			interests.advanceIterator();
		}

		int len = actualInterests.getLength();
		for (int i = 0 ; i < len/2; i++) {
			actualInterests.removeFirst();
		}
		newUser.setInterests(actualInterests);
		
	}

	public HashTable<Interest> getUserInterestHashTable() {
		return userInterestHashTable;
	}
	
	public UserDatabaseIO() {
		userList = new LinkedList<User>();
		userArrayList = new ArrayList<User>();
		userBST = new BST<User>();
		userInterestHashTable = new HashTable<Interest>(10);
		userInterestsTotalBST = new ArrayList<BST<User>>();
		populateEverything();
	}
	
	public void populateEverything() {
		generateUserLists();
		generateUserBST();
	}
	
	public void generateUserBST() {
		userList.positionIterator();
		while(!userList.offEnd()) {
			userBST.insert(userList.getIterator());
			userList.advanceIterator();
		}
	}
	
	public void writeToFile() throws IOException {
		PrintWriter pw = new PrintWriter(new FileOutputStream("users.txt", false));
		for(User user : userArrayList) {
			pw.println(user.getId());
			pw.println(user.getName());
			pw.println(user.getUsername());
			pw.println(user.getPassword());
			pw.println(user.getFriendCount());
			for (User u : user.getFriends().toArrayList()) {
				pw.println(u.getId());
			}
			pw.println(user.getCity());
			pw.println(user.getInterestCount());
			LinkedList<Interest> l = user.getInterests();
			l.positionIterator();
			while(!l.offEnd()) {
				pw.println(l.getIterator().getName());
				l.advanceIterator();
			}
		}
		pw.close();
		
		
	}
	
	
	
	
	public void generateUserLists() {
		//First pass w/o friends
		try {
            Scanner input = new Scanner(System.in);
            File file = new File(filename);
            
            input = new Scanner(file);
            while (input.hasNextLine()) {
            	User temp = new User();
            	temp.setId(getUserID(input));
            	temp.setName(getUsersName(input));
            	temp.setUsername(getUsername(input));
            	temp.setPassword(getPassword(input));
            	setUserFriendCount(input, temp);
            	//havent set friends yet
            	temp.setCity(getUserCity(input));
            	setInterestStuff(input,temp);
            	userList.addLast(temp);
            	userArrayList.add(temp);
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
		//Second pass w friends
		try {
            Scanner input = new Scanner(System.in);
            File file = new File(filename);
            
            userList.positionIterator();
            input = new Scanner(file);
            while (!userList.offEnd()) {
                input.nextLine(); //skip id
                input.nextLine(); //skip name
                input.nextLine(); //skip username
                input.nextLine(); //skip password
                int count1 = Integer.parseInt(input.nextLine()); //get friends
                User tempUser = userList.getIterator();
                BST<User> friendsBST = new BST<User>();
                while(count1 > 0) {
                	User u = userArrayList.get(Integer.parseInt(input.nextLine())-1);
                	friendsBST.insert(u);
                	count1--;
                }
                userList.getIterator().setFriends(new BST<User>(friendsBST));
                input.nextLine(); //skip city
                int count2 = Integer.parseInt(input.nextLine()); //skip interests
                while(count2 > 0) {
                	input.nextLine();
                	count2--;
                }
                userList.advanceIterator();
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	private int getUserID(Scanner input) {
		String temp = input.nextLine();
		return Integer.parseInt(temp);
	}
	
	private String getUsername(Scanner input) {
		String temp = input.nextLine();
		return temp;
	}
	
	private String getUsersName(Scanner input) { 
		String temp = input.nextLine();
		return temp;
	}
	
	private String getPassword(Scanner input) {
		String temp = input.nextLine();
		return temp;
	}
	
	private String getUserCity(Scanner input) {
		return input.nextLine();
	}
	
	private void setUserFriendCount(Scanner input, User o) {
		int count = Integer.parseInt(input.nextLine());
		o.setFriendCount(count);
		while(count > 0) {
			input.nextLine();
			count--;
		}
	}
	
	private void setInterestStuff(Scanner input, User o) {
		int count = Integer.parseInt(input.nextLine());
		o.setInterestCount(count);
		o.setInterests(new LinkedList<Interest>());
		LinkedList<Interest> list = new LinkedList<Interest>();
		while(count > 0) {
			String inter = input.nextLine();
			Interest i;
			validateInterest(o,inter);
			count--;
		}
	}
	
	public Interest validateInterest(User o, String inter) {
		Interest htSearchResult = (Interest)userInterestHashTable.searchAndSpit(new Interest(inter,0));
		if(htSearchResult != null) {
			if(!userInterestsTotalBST.get(htSearchResult.getId()).search(o)){
				userInterestsTotalBST.get(htSearchResult.getId()).insert(o);
				o.getInterests().addLast(htSearchResult);
			}
			return htSearchResult;
		} else {
			Interest i = new Interest(inter, lastInterestID);
			userInterestsTotalBST.add(new BST<User>());
			userInterestsTotalBST.get(lastInterestID).insert(o);
			lastInterestID++;
			userInterestHashTable.insert(i);
			o.getInterests().addLast(i);		
			return i;
		}
	}
	
	public void createAccount(User u) {
		updateDataStructures(u);
		u.setId(userArrayList.size());
		
	}
	
	public User createUser(String name,String username,String password,String city, int interestCount, LinkedList<Interest> interests) {
		return new User(0, name, username, password, 0, new BST<User>(), city, interestCount,interests);
	}
	
	public void printDB() {
		try {
            Scanner input = new Scanner(System.in);
            File file = new File(filename);

            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
}