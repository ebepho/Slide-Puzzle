/*
 * Slider Puzzle Game
 * 
 * Phoebe Owusu
 * Thu Sep 23, 2021
 */

import java.util.Scanner;

public class Core{
	
	public static void main(String[] args){
		
		//scanner setup(we will use this to get keyboard input)
		Scanner scan = new Scanner(System.in);
		
		String strKeyboardInput; //will be either "w/a/s/d" or "stop"
		boolean booSwitch = true; //use to loop the game
		
		//setup the puzzle w/h and the gameplay class
		byte bytGridSize = 3;
		Gameplay gameplay = new Gameplay(bytGridSize);
		 

		while(booSwitch) {
			//print updated grid
			System.out.print("w/a/s/d or stop: ");
			strKeyboardInput = scan.nextLine(); 
			System.out.print("\n"); // make a new line so the console doesn't looked clogged
			
			//the game will only update if the user puts a valid value
			if(strKeyboardInput.equalsIgnoreCase("w") || strKeyboardInput.equalsIgnoreCase("a") || 
					strKeyboardInput.equalsIgnoreCase("s") || strKeyboardInput.equalsIgnoreCase("d") ) 
			{
				booSwitch = gameplay.move(strKeyboardInput); // if you win the game, gameplay.move will reutrn false
			}
			else if(strKeyboardInput.equalsIgnoreCase("STOP"))
				booSwitch = false; //stop the game loop
			else
				System.out.println("Only input w/a/s/d or stop");//error message
		}
		
		//end message
		System.out.println("Thank you for playing!");
		scan.close();
		
		
	}
	
}