import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	public void updateDataStuctures(User newUser) {
		userList.addLast(newUser);
		userArrayList.add(newUser);
		userBST.insert(newUser);
		LinkedList<Interest> interests = newUser.getInterests();
		interests.positionIterator();
		while(!interests.offEnd()) {
			validateInterest(newUser, interests.getIterator().getName());
			interests.advanceIterator();
		}
		
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
		//System.out.println("DONE W LISTS");
		generateUserBST();
	}
	
	public void generateUserBST() {
		userList.positionIterator();
		while(!userList.offEnd()) {
			userBST.insert(userList.getIterator());
			userList.advanceIterator();
		}
	}
	
	public void generateUserLists() {
		//First pass w/0 friends
		try {
            Scanner input = new Scanner(System.in);
            File file = new File(filename);
            
            input = new Scanner(file);
            while (input.hasNextLine()) {
                //String line = input.nextLine();
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
            	//System.out.println("\nUSER: " + userList.getIterator().getName());
                input.nextLine(); //skip id
                input.nextLine(); //skip name
                input.nextLine(); //skip username
                input.nextLine(); //skip password
                int count1 = Integer.parseInt(input.nextLine()); //get friends
                User tempUser = userList.getIterator();
                BST<User> friendsBST = new BST<User>();
                while(count1 > 0) {
                	User u = userArrayList.get(Integer.parseInt(input.nextLine()));
                	//System.out.println("\n----");
					//System.out.println(u);
					//System.out.println("----\n");
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
		System.out.println("getting ID: " + temp);
		return Integer.parseInt(temp);
	}
	
	private String getUsername(Scanner input) {
		String temp = input.nextLine();
		System.out.println("getting username: " + temp);
		return temp;
	}
	
	private String getUsersName(Scanner input) {  //couldnt think of a better method name im tired
		String temp = input.nextLine();
		System.out.println("getting users name: " + temp);
		return temp;
	}
	
	private String getPassword(Scanner input) {
		String temp = input.nextLine();
		System.out.println("getting Password: " + temp);
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
		LinkedList<Interest> list = new LinkedList<Interest>();
		while(count > 0) {
			String inter = input.nextLine();
			Interest i;
			//Search Interest HashTable for Interest i (by matching name)
			//If interest already exists in HashTable, dont have to add it to HT
			//If interest does not exist in HashTable, add it 
			//Keeping track of last interest ID, and then ++ at the end
//			boolean in_hashTable = userInterestHashTable.search(new Interest(inter,0));
//			Interest htSearchResult = (Interest)userInterestHashTable.searchAndSpit(new Interest(inter,0));
//			if(htSearchResult != null) {
//				list.addLast(htSearchResult);
//				userInterestsTotalBST.get(htSearchResult.getId()).insert(o);
//			} else {
//				i = new Interest(inter, lastInterestID);
//				userInterestsTotalBST.add(new BST<User>());
//				userInterestsTotalBST.get(lastInterestID).insert(o);
//				lastInterestID++;
//				userInterestHashTable.insert(i);
//								
//				list.addLast(i);
//			}
			list.addLast(validateInterest(o,inter));
			count--;
		}
		o.setInterests(new LinkedList<Interest>(list));
	}
	
	public Interest validateInterest(User o, String inter) {
		boolean in_hashTable = userInterestHashTable.search(new Interest(inter,0));
		Interest htSearchResult = (Interest)userInterestHashTable.searchAndSpit(new Interest(inter,0));
		if(htSearchResult != null) {
			userInterestsTotalBST.get(htSearchResult.getId()).insert(o);
			return htSearchResult;
		} else {
			Interest i = new Interest(inter, lastInterestID);
			userInterestsTotalBST.add(new BST<User>());
			userInterestsTotalBST.get(lastInterestID).insert(o);
			lastInterestID++;
			userInterestHashTable.insert(i);
							
			return i;
		}
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
