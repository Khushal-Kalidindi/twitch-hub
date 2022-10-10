import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

	
public class Prompt { 
	private Scanner in;
	
	public Prompt(Scanner s){
		in = s;
	}
	
	public int getIntInput(Printer p,ArrayList<Integer> possibleValues){
		int input;
		do{
			p.prompt();
			input = in.nextInt();
			if(!possibleValues.contains(input))
				p.fail();
		}while(!possibleValues.contains(input));
		return input;
	}
	
	public String getStringInput(Printer p, Validator v){
		String input;
		do{
			p.prompt();
			input = in.nextLine();
			if(!v.isStringValid(input))
				p.fail();
		}while(!v.isStringValid(input));
		return input;
	}

}
