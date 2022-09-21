import java.lang.Math;

public class Gameplay {
	
	//DECLARATIONS---------
	//create 2 puzzles, one with the actual numbers and one with the emojis
	int arrNumbers[][]; 
	String puzzle[][];
	
	//Declare the grid size variable and the x and y position
	byte bytGridSize;
	int xPos = 0, yPos = 0; // we will use the x and y to keep track of the empty spot or 0
							// this is a global area so that we can access this from multiple methods
							// and we can keep changing it everytime it moves
	
	//import emojis and their emoji code
	String emojis[];
	int code[] = new int [2]; 
	
	//SETUP-------------------------
	Gameplay(byte size){
		bytGridSize = size; //get the puzzle w/h
		
		emojiSetup(); //set up the emojis
		fillGrid(shuffle(size)); // fill the puzzle
		printInfo(); //and then print the start
		
	}
	
	//help received from: https://stackoverflow.com/questions/53194987/how-do-i-insert-an-emoji-in-a-java-string
	private void emojiSetup() {
		emojis = new String[10];
		//0
		emojis[0] = "||";
		
		//1  U+0031 U+20E3
	    code[0] = 0x0031; code[1] = 0x20E3;
		emojis[1] = new String(code, 0, code.length);	
		
		//2  U+0032 U+20E3
		code[0] = 0x0032; code[1] = 0x20E3;
		emojis[2] = new String(code, 0, code.length);
		
		//3  U+0033 U+20E3
		code[0] = 0x0033; code[1] = 0x20E3;
		emojis[3] = new String(code, 0, code.length);	
		
		//4 U+0034 U+20E3
	    code[0] = 0x0034; code[1] = 0x20E3;
		emojis[4] = new String(code, 0, code.length);	
		
		//5  U+0035 U+20E3
		code[0] = 0x0035; code[1] = 0x20E3;
		emojis[5] = new String(code, 0, code.length);
		
		//6  U+0036 U+20E3
		code[0] = 0x0036; code[1] = 0x20E3;
		emojis[6] = new String(code, 0, code.length);
		
		//7 U+0037 U+20E3
	    code[0] = 0x0037; code[1] = 0x20E3;
		emojis[7] = new String(code, 0, code.length);	
		
		//8  U+0038 U+20E3
		code[0] = 0x0038; code[1] = 0x20E3;
		emojis[8] = new String(code, 0, code.length);
		
		//9  U+0039 U+20E3
		code[0] = 0x0039; code[1] = 0x20E3;
		emojis[9] = new String(code, 0, code.length);
	}
	
	
	
	
	
	
	
	
	private int[] shuffle(byte size) {
		int [] numShuffle = new int[(size * size)]; // have to make a 1D array instead because I realized this algorithm will not work with a 2D array
		int fillCount = 0;
		boolean booSwitch = true;
		
		//fill the temporary array
		for(int i = 1; i < (size * size); i ++) {	
			numShuffle[i-1] = i;
		}
		 
		while(booSwitch) {
			//shuffle it - logic from here: https://developerslogblog.wordpress.com/2020/04/01/how-to-shuffle-an-slide-puzzle/
			for (int i = numShuffle.length - 1; i > 0; i--)
			{
				 int randomIndex = (int)(Math.random() * (i + 1));
				 
				 int temp = numShuffle[i];
				 numShuffle[i] = numShuffle[randomIndex];
				 numShuffle[randomIndex] = temp;
			}
			
			//solvable?
			byte emptyTile = 0;
			byte numberInverions = 0;
			for (int i = 0; i < numShuffle.length -1; i++)
			{
			    if (numShuffle[i] == emptyTile)
			        continue;
			 
			    for(int j = i + 1; j <  numShuffle[i];j ++) {
			    	if(numShuffle[j] != emptyTile || j > i)
			    		numberInverions++;	
			    }
			    
			    //If the puzzle´s grid is odd the puzzle is solvable when the number of inversions is even
			    if(size%2 == 1) {
			    	if(numberInverions % 2 == 0)
			    		booSwitch = false;
			    		return numShuffle; 		
			    }
			}
		}
		return numShuffle; 
	}
	
	
	
	
	
	
	
	
    //fill in the puzzle with emojis and numbers after we recieve the size
	private void fillGrid(int shuffledNumbers[]){
		//initialize the variables
		byte count = 0; //will use this to travers through the numbers in the shuffled array
		arrNumbers = new int[bytGridSize][bytGridSize];
		puzzle = new String[bytGridSize][bytGridSize];
				
		//fill the arrays with numbers to keep track
		for(int y = 0; y < bytGridSize; y ++) {
			for(int x = 0; x < bytGridSize; x ++) {
				arrNumbers[x][y] = shuffledNumbers[count];
				puzzle[x][y] = emojis[arrNumbers[x][y]]; // fill the puzzle with the emojis (the emoji used 
				                                        // will depend on what number is at the same position	
				if(shuffledNumbers[count] == 0) { //find the zero position
					xPos = x; 
					yPos = y;
				}
					count++;
			}
		}
		
	}
	
	
	
	
	
	
	//print the information (only the puzzle with emojis)
	void printInfo() {
		for (int y = 0; y < bytGridSize; y ++) {
			for (int x = 0; x < bytGridSize; x ++) {
				System.out.print(puzzle[x][y] + " ");
			}
			System.out.print("\n");
		}
	}
	
	
	//MOVEMENT-----------------
	public boolean move(String direction) {
		String temp = ""; //temporary storage to swap positions. Im declaring this here so that i dont have to make multiple ones
		byte count;

		if(direction.equalsIgnoreCase("w")) 
			moveUp(temp);
		else if (direction.equalsIgnoreCase("s"))
			moveDown(temp);
		else if (direction.equalsIgnoreCase("a"))
			moveRight(temp);
		else if (direction.equalsIgnoreCase("d"))
			moveLeft(temp);
		
		if(arrNumbers[bytGridSize - 1][bytGridSize - 1] == 0 || arrNumbers[0][0] == 0) {
			if(arrNumbers[bytGridSize - 1][bytGridSize - 1] == 0) {
				count = 1;
				for (int y = 0; y < bytGridSize; y ++) {
					for (int x = 0; x < bytGridSize; x ++) {
						if(arrNumbers[x][y] != count) {
							if(x!= bytGridSize - 1 && y != bytGridSize) // make sure it isnt the last index
								return true;
						}
						count ++;
					}	
				}
			}
			else {
				count = 0;
				for (int y = 0; y < bytGridSize; y ++) {
					for (int x = 0; x < bytGridSize; x ++) {
						if(arrNumbers[x][y] != count)
							return true;
						count ++;
					}	
				}
				
			}
			
			System.out.println("You win!");
			return false;
		}
		
		return true;
	}
	
	
	
	
	
	public void moveUp(String temp) {
		if(yPos < bytGridSize - 1) {
			//swap the number grid
			arrNumbers[xPos][yPos] = arrNumbers[xPos][yPos + 1];
			arrNumbers[xPos][yPos + 1] = 0;
			
			
			//swap the puzzle
			temp = puzzle[xPos][yPos + 1];
			puzzle[xPos][yPos + 1] = puzzle[xPos][yPos];
			puzzle[xPos][yPos] = temp;	
			
			yPos ++;//update the 0 y position
			printInfo();
		}
		else {
			System.out.println("Out of bounds"); //error message
		}
	}
	
	public void moveDown(String temp) {
		if(yPos > 0) {
			//swap the number grid
			arrNumbers[xPos][yPos] = arrNumbers[xPos][yPos - 1];
			arrNumbers[xPos][yPos - 1] = 0;
			
			//swap the puzzle
			temp = puzzle[xPos][yPos - 1];
			puzzle[xPos][yPos - 1] = puzzle[xPos][yPos];
			puzzle[xPos][yPos] = temp;	
			
			yPos --;//update the 0 y position
			printInfo(); // update the grid
		}
		else {
			System.out.println("Out of bounds"); //error message
		}
		
	}
	
	public void moveRight(String temp) {
		if(xPos < bytGridSize - 1) {
			//swap the number grid
			arrNumbers[xPos][yPos] = arrNumbers[xPos + 1][yPos];
			arrNumbers[xPos + 1][yPos] = 0;
			
			//swap the puzzle
			temp = puzzle[xPos + 1][yPos];
			puzzle[xPos + 1][yPos] = puzzle[xPos][yPos];
			puzzle[xPos][yPos] = temp;	
			
			xPos ++;//update the 0 x position
			printInfo();
		}
		else {
			System.out.println("Out of bounds"); //error message
		}
	}
	
	public void moveLeft(String temp) {
		if(xPos > 0) {
			//swap the number grid
			arrNumbers[xPos][yPos] = arrNumbers[xPos - 1][yPos];
			arrNumbers[xPos - 1][yPos] = 0;
			
			//swap the puzzle
			temp = puzzle[xPos - 1][yPos];
			puzzle[xPos - 1][yPos] = puzzle[xPos][yPos];
			puzzle[xPos][yPos] = temp;	
			
			xPos --;//update the 0 x position
			printInfo();
		}
		else {
			System.out.println("Out of bounds"); //error message
		}
	}

}

