import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TwitchHubMain {
	public static void main(String args[]) {
		UserDatabaseIO uio = new UserDatabaseIO();		
		LinkedList<User> userList = uio.getUserList();
		HashTable<Interest> userInterests = uio.getUserInterestHashTable();
		ArrayList<BST<User>> uITB = uio.getUserInterestsTotalBST();
		Authentication auth = new Authentication(uio);		
		Network n = new Network(uio);
		Prompt p = new Prompt(new Scanner(System.in));
		Scanner in = new Scanner(System.in);
		
		boolean running = true;
		
		while(running){
		
			boolean logged_in = false;
			User logged_user = null;
			System.out.println("Welcome to the Twitch Hub!");
			System.out.println("Please select from the following options (1-3): ");
			while (!logged_in) {
				System.out.println("\t1. Sign-in");
				System.out.println("\t2. Create an Account");
				System.out.println("\t3. Quit\n");
				
				Integer input = in.nextInt();
				if (input == 1) {
					System.out.println("Let's log-in!\n");
					boolean username_acquired = false;
					while(!username_acquired) {					
						System.out.println("Enter your username:\n");
						String username = in.next();
						
						if (auth.validateUsername(username)) {
							System.out.println("Username Accepted.");
							boolean password_acquired = false;
							while(!password_acquired) {
								System.out.println("Enter your password:");
								String password = in.next();
								logged_user = auth.authenticate(username, password);
								if(logged_user != null) {
									System.out.println("Logged in Successfully!");
									password_acquired = true;
								} else {
									System.out.println("Incorrect password, please try again!");
								}	
							}
							username_acquired = true;
							logged_in = true;
						} else {
							System.out.println("Username not found. Please try again.\n");
						}
					}	
				}
				else if (input == 2) {
					ArrayList<String> arrinterests = new ArrayList<String>();
					String full_name = p.getStringInput(new Printer(){void prompt(){ 
						System.out.println("Enter your full name: ");
					} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String full_name){return true;} });
	
	
					String username = p.getStringInput(new Printer(){void prompt(){ 
						System.out.println("Enter your username: ");
					} void fail(){ System.out.println("Sorry, this username is already taken.");} }, new Validator(){ boolean isStringValid(String username){return !auth.validateUsername(username);} });
	
					
					String password = p.getStringInput(new Printer(){void prompt(){ 
						System.out.println("Enter your password: ");
					} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String password){return true;} });
	
					
					String city = p.getStringInput(new Printer(){void prompt(){ 
						System.out.println("Enter your city: ");
					} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String city){return true;} });
	
					
					
					LinkedList<Interest> interests = new LinkedList<Interest>();
					int interest_count = 0;
					
					String interest_1 = p.getStringInput(new Printer(){void prompt(){ 
						System.out.println("Enter an interest: ");
					} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String interest_1){return true;} });
					interests.addLast(new Interest(interest_1.toLowerCase(), 0));
					interest_count++;
					
					String interest_2 = p.getStringInput(new Printer(){void prompt(){ 
						System.out.println("Enter another interest: ");
					} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String interest_2){return true;} });
					interests.addLast(new Interest(interest_2.toLowerCase(), 0));
					interest_count++;
					
					String yes_or_no = "y";
					while(yes_or_no.equals("y")) {
						yes_or_no = p.getStringInput(new Printer(){void prompt(){ 
							System.out.println("Do you have any other interests? (Y/N)");
						} void fail(){ System.out.println("Not a valid entry.");} }, new Validator(){ boolean isStringValid(String s){return s.toLowerCase().equals("y") || s.toLowerCase().equals("n");} });
						
						if(yes_or_no.equals("y")) {
							String new_interest = p.getStringInput(new Printer(){void prompt(){ 
								System.out.println("Enter another interest: ");
							} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String interest_2){return true;} });
							interests.addLast(new Interest(new_interest.toLowerCase(), 0));
							interest_count++;
	
						} else {
							yes_or_no = "n";
						}
					}				
					
					User new_user = uio.createUser(full_name, username, password, city, interest_count, interests);
					uio.createAccount(new_user);
					auth.addUserToAuthTable(new_user);					
					n.addNewUser();
					
					System.out.println("Account Successfully Created!");
					System.out.println("Welcome to Twitch Hub!");
					
					logged_user = auth.authenticate(username, password);
					logged_in = true;
	
				} 
				else if(input == 3) {	
					System.out.println("Leaving TwitchHub...");
					running = false;
					break;
				} else {
					System.out.println("Invalid option! Please select from one of the following options: ");
				}
				
				boolean inMainMenu = true;
				
				while(inMainMenu) {
					System.out.println();
					System.out.println("----");
					logged_user.printSelfProfile();
					System.out.println("----");
					int userinput;
					userinput = p.getIntInput(new Printer(){ void prompt(){ 
						System.out.println("Please select from the following options: (1-3)");
						System.out.println("1. View my Friends");
						System.out.println("2. Make new Friends");
						System.out.println("3. Quit to Main Menu");
						} void fail(){ 
							System.out.println("Not an option.");
						}}, new ArrayList<>(Arrays.asList(1, 2, 3)));
					
					if(userinput==1) {
						userinput = p.getIntInput(new Printer(){ void prompt(){ 
							System.out.println("Please select from the following options: (1-3)");
							System.out.println("1. View my Friends (Sorted By Name)");
							System.out.println("2. Search by Friends Name");
							System.out.println("3. Go back");
							} void fail(){ 
								System.out.println("Not an option.");
							}}, new ArrayList<>(Arrays.asList(1, 2, 3)));
						
						if(userinput==1) {
							ArrayList<User> temp = n.getUsersFriends(logged_user);
							if(temp.size()==0) {
								System.out.println("It seems you don't have any friends..Going back...");
							}
							else {
								for (User u : temp) {
									u.printUserProfile();
								}
							}
							
						} else if(userinput==2) {
							System.out.println();
							String name = p.getStringInput(new Printer(){void prompt(){ 
								System.out.print("");
							} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String name){return true;} });
							name = p.getStringInput(new Printer(){void prompt(){ 
								System.out.println("Enter your friends name: ");
							} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String name){return true;} });
							User u = new User();
							u.setName(name);
							ArrayList<User> a = logged_user.getFriends().searchAll(u);
							int count = 1;
							for(User k : a){
								System.out.println("FRIEND " + count);
								k.printUserProfile();
								count++;
							}
							String yes_or_no;
							yes_or_no = p.getStringInput(new Printer(){void prompt(){ 
							System.out.println("Do you want to remove a friend (Y/N)");
							} void fail(){ System.out.println("Not a valid entry.");} }, new Validator(){ boolean isStringValid(String s){return s.toLowerCase().equals("y") || s.toLowerCase().equals("n");} });
							
							if(yes_or_no.toLowerCase().equals("y")){
								String userStringInput;
								userStringInput = p.getStringInput(new Printer(){ void prompt(){ 
									System.out.println("Please type the FRIEND # you want to remove");
									} void fail(){ 
										System.out.println("Not an option.");
									}}, new Validator(){ boolean isStringValid(String s){int q = Integer.parseInt(s); return q > 0 && q <= a.size();} });
								
								int friendNum = Integer.parseInt(userStringInput);
								n.removeFriends(logged_user, a.get(friendNum-1));
							}
							
							
						} else if(userinput==3) {
							System.out.println("Going back..");
						}
	
					} else if (userinput==2) {
						userinput = p.getIntInput(new Printer(){ void prompt(){ 
							System.out.println("Please select from the following options: (1-4)");
							System.out.println("1. Search by Name");
							System.out.println("2. Search by Interest");
							System.out.println("3. Get Friend Recommendations");
							System.out.println("4. Go back");
							} void fail(){ 
								System.out.println("Not an option.");
							}}, new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
						
						if(userinput==1) {
							String name = p.getStringInput(new Printer(){void prompt(){ 
								System.out.print("");
							} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String name){return true;} });
							name = p.getStringInput(new Printer(){void prompt(){ 
								System.out.println("Enter a name to search: ");
							} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String name){return true;} });

							System.out.println("Generating Results...");
							
							ArrayList<User> a = n.getUsersByName(name);
							int count = 1;
							for(User k : a){
								System.out.println("USER " + count);
								k.printUserProfile();
								System.out.println();
								count++;
							}

							String yes_or_no;
							yes_or_no = p.getStringInput(new Printer(){void prompt(){ 
							System.out.println("Do you want to add a friend (Y/N)");
							} void fail(){ System.out.println("Not a valid entry.");} }, new Validator(){ boolean isStringValid(String s){return s.toLowerCase().equals("y") || s.toLowerCase().equals("n");} });
							
							if(yes_or_no.toLowerCase().equals("y")){
								String userStringInput;
								userStringInput = p.getStringInput(new Printer(){ void prompt(){ 
									System.out.println("Please type the USER # you want to add");
									} void fail(){ 
										System.out.println("Not an option.");
									}}, new Validator(){ boolean isStringValid(String s){int q = Integer.parseInt(s); return q > 0 && q <= a.size();} });
								
								int friendNum = Integer.parseInt(userStringInput);
								n.addFriends(logged_user, a.get(friendNum-1));
							}
							
			
						} else if(userinput==2) {
							String interest = p.getStringInput(new Printer(){void prompt(){ 
								System.out.print("");
							} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String name){return true;} });
							interest = p.getStringInput(new Printer(){void prompt(){ 
								System.out.println("Enter an interest to search: ");
							} void fail(){ System.out.println("Invalid");} }, new Validator(){ boolean isStringValid(String name){return true;} });

							System.out.println("Generating Results...");
							
							ArrayList<User> a = n.getUsersByInterest(interest);
							if(!(a==null)) { 
								int count = 1;
								for(User k : a){
									System.out.println("USER " + count);
									k.printUserProfile();
									System.out.println();
									count++;
								}

								String yes_or_no;
								yes_or_no = p.getStringInput(new Printer(){void prompt(){ 
								System.out.println("Do you want to add a friend (Y/N)");
								} void fail(){ System.out.println("Not a valid entry.");} }, new Validator(){ boolean isStringValid(String s){return s.toLowerCase().equals("y") || s.toLowerCase().equals("n");} });
								
								if(yes_or_no.toLowerCase().equals("y")){
									String userStringInput;
									userStringInput = p.getStringInput(new Printer(){ void prompt(){ 
										System.out.println("Please type the USER # you want to add");
										} void fail(){ 
											System.out.println("Not an option.");
										}}, new Validator(){ boolean isStringValid(String s){int q = Integer.parseInt(s); return q > 0 && q <= a.size();} });
									
									int friendNum = Integer.parseInt(userStringInput);
									n.addFriends(logged_user, a.get(friendNum-1));
								}
							} else {
								continue;
							}
							
							
							
							
						} else if(userinput==3) {
							System.out.println("Searching Network for Friend Recommendations...");
							System.out.println("Generating Results...");
							
							ArrayList<LinkedList<Object>> a = n.getFriendRecommendations(logged_user.getId());
							if (a.size()==0) {
								System.out.println("No results found. Going back..");
							}
							else {
								int count = 1;
								for(LinkedList<Object> k : a){
									System.out.println("USER " + count);
									((User)k.getLast()).printUserProfile();
									System.out.println();
									count++;
									
								}
								String empty_prompt = p.getStringInput(new Printer(){void prompt(){ 
									System.out.print("");
									} void fail(){ System.out.print("");} }, new Validator(){ boolean isStringValid(String s){return true;} });
									
								
								String yes_or_no;
								yes_or_no = p.getStringInput(new Printer(){void prompt(){ 
								System.out.println("Do you want to add a friend (Y/N)");
								} void fail(){ System.out.println("Not a valid entry.");} }, new Validator(){ boolean isStringValid(String s){return s.toLowerCase().equals("y") || s.toLowerCase().equals("n");} });
								
								if(yes_or_no.toLowerCase().equals("y")){
									String userStringInput;
									userStringInput = p.getStringInput(new Printer(){ void prompt(){ 
										System.out.println("Please type the USER # you want to add");
										} void fail(){ 
											System.out.println("Not an option.");
										}}, new Validator(){ boolean isStringValid(String s){int q = Integer.parseInt(s); return q > 0 && q <= a.size();} });
									
									int friendNum = Integer.parseInt(userStringInput);
									n.addFriends(logged_user, (User)a.get(friendNum-1).getLast());
								}	
							}
							
							

						} else if(userinput==4) {
							System.out.println("Going back..");
						}
						
						
						
						
					} else if (userinput==3) {
						try {
							uio.writeToFile();
						}
						catch (IOException e) {
							System.out.println(e.getStackTrace());
						}
						System.out.println("Quitting Twitch Hub Menu..");
						inMainMenu = false;
					} 
				}
			}
			System.out.println("BYE BYE");
		}
	}
}