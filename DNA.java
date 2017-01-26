import java.util.Scanner;

/**
 * Caroline Oliver
 * March 1st, 2016
 * CSCI 230
 * Homework 3
 * 
 * Create a DNA strand represented by two linked list, right helix and left helix,
 * that are constructed with nucleotides and implement methods to add, alter, and 
 * get information from the DNA strand.
 * 
 * The input varies for each method but will be entered from a file by the user.
 * 
 * The output is the value of the method call return from the input,
 * if there is anything to return.
 * 
 * I certify that this code is entirely my own work. 
 * 
 */

public class DNA {
	
	private Nucleotide leftHelix; //points to beginning of leftHelix list
	private Nucleotide rightHelix; //points to beginning of rightHelix list
	private int numElements; //logical length of list
	private static Scanner scan;
	
	
	class Nucleotide
	{
		public Character base;
		public Nucleotide next;
		public Nucleotide across;
	
		// constructor for Nucleotide class
		public Nucleotide()
		{
			
		}
	
	}
	
	// constructor for DNA class
	public DNA()
	   {
		this.leftHelix = null;
		this.rightHelix = null;
		this.numElements = 0;
		
	   }
	
	/*
     * Purpose: run methods called by user
     * Preconditions: requires user input through file 
     * Postcondition: returns the correct value for whichever method is called from the user input
     */
	public void main(String[] args)
    {
		scan = new Scanner(System.in);
        int numOfInput = scan.nextInt(); //assign number of inputs to variable 
		DNA strand = new DNA(); //initialize DNA strand

        
        for(int i = 0; i < numOfInput; i++)
        {
        	
        int methodCall = scan.nextInt(); //assign method call to variable for use
        
        if(methodCall == 1)
        {
        	int index = scan.nextInt(); //assign integer index input to value
        	String basePair = scan.next(); //assign basePair string to value
        	
        	strand.insert(index, basePair);
        }
        
        if(methodCall == 2)
        {
        	int index = scan.nextInt(); //assign integer index to value
        	
        	strand.remove(index);
        }
        
        if(methodCall == 3)
        {
        	int startIndex = scan.nextInt(); //assign integer startIdex to value
        	int endIndex = scan.nextInt(); //assign integer endIndex to value
        	
        	strand.print(startIndex, endIndex);
        }
        
        if(methodCall == 4)
        {
        	strand.clear();
        }
        
        if(methodCall == 5)
        {
        	System.out.println(strand.isEmpty());
        }
        
        if(methodCall == 6)
        {
        	System.out.println(strand.getLength());
        }
        
        if(methodCall == 7)
        {
        	String basePair = scan.next(); //assign basePair string to value
        	
        	System.out.println(strand.find(basePair));
        }
        
        if(methodCall == 8)
        {
        	strand.printLeft();
        }
        
        if(methodCall == 9)
        {
        	strand.printRight();
        }
        
        if(methodCall == 10)
        {
        	int index = scan.nextInt(); //assign integer index to value
        	int helix = scan.nextInt(); //assign integer helix to value
        	
        	strand.printBasePair(index, helix);
        }
        
        }
    
    }
    
	
	/*
     * Purpose: inserts a series of bases at a given index
     * Preconditions: requires an integer index for starting position and a string of bases
     * Postcondition: updated list with additional bases added at given index
     */
	public void insert(int index, String basePair)
	{
		if(index < 0 || index > numElements) //check if index is in bounds
		{
			throw new IndexOutOfBoundsException("Index given is out of range"); //EXCEPTION
		}
		else //index determined to be in bounds
		{
			//create and populate new left and right nucleotide
			Nucleotide newLeftHelix = new Nucleotide(); //create left helix nucleotide
			Nucleotide newRightHelix = new Nucleotide(); //create right helix nucleotide

			String[] baseLetter = basePair.split(""); //String input to array string for use
			String tempLeftH = baseLetter[0];
			char leftH = tempLeftH.charAt(0); // character for left helix base
			String tempRightH = baseLetter[1];
			char rightH = tempRightH.charAt(0); //character for right helix base

			newLeftHelix.base = leftH; //assign base character to left helix nucleotide
			newRightHelix.base = rightH; //assign base character to right helix nucleotide

			if(index == 0) //insert new nucleotides at beggining of DNA strand
			{
				//insert left nucleotide
				newLeftHelix.next = this.leftHelix;
				this.leftHelix = newLeftHelix;
				this.leftHelix.across = newRightHelix;
				
				//insert right nucleotide
				newRightHelix.next = this.rightHelix;
				this.rightHelix = newRightHelix;
				this.rightHelix.across = newLeftHelix;
			}
			
			else //insert new nucleotide at index that is not 0
			{
				//initialize pointers for left and right helices
				Nucleotide lHPointer = this.leftHelix;
				Nucleotide rHPointer = this.rightHelix;
				
				for(int i = 0; i < index-1; i++) //traverse through DNA strand to nucleotide before point of insertion
				{
					lHPointer = lHPointer.next;
					rHPointer = rHPointer.next;
					//now, pointers point to nucleotide before insertion point
				}
				//insert left nucleotide
				newLeftHelix.next = lHPointer.next;
				lHPointer.next = newLeftHelix;
				lHPointer.across = rHPointer;

				//insert right nucleotide
				newRightHelix.next = rHPointer.next;
				rHPointer.next = newRightHelix;
				rHPointer.across = lHPointer;
			}
		}
		//update the logical size of the DNA strand
		this.numElements = this.numElements + 1;
	}
	
	/*
     * Purpose: remove nucleotide base pair at given index
     * Preconditions: requires integer index of nucleotide base pair to be removed
     * Postcondition: DNA strand is updated with nucleotide base pair at given index removed
     */
	public String remove(int index)
	{
		String result = null; //initialize result
		
		if(index < 0 || index > numElements-1) //check if index is within bounds
		{
			throw new IllegalArgumentException("Index given is out of range"); //exception
		}
		
		else //index determined to be within bounds
		{
			if(index == 0) //remove nucleotide from beginning of the DNA strand
			{
				//remove left nucleotide from left helix
				Nucleotide tempLH = this.leftHelix;
				this.leftHelix = tempLH.next;
				Character lHBase = tempLH.base; //remember left helix base
				String lResult = lHBase.toString(); //left helix base converted to string for return
				tempLH.next = null;
				
				//remove right nucleotide from right helix
				Nucleotide tempRH = this.rightHelix;
				this.rightHelix = tempRH.next;
				Character rHBase = tempRH.base; //remember right helix base 
				String rResult = rHBase.toString(); //right helix base converted to string for return
				tempRH.next = null;
				
				result = lResult + rResult; //concatenate helix base strings for return result
			}
			
			else //remove nucleotide from index greater than 0
			{
				//initialize left and right helix pointers for DNA strand traversal
				Nucleotide lHPointer = this.leftHelix;
				Nucleotide rHPointer = this.rightHelix;
				
				for(int i = 0; i < index-1; i++) //traverse through list to nucleotide before removal index
				{
					lHPointer = lHPointer.next;
					rHPointer = rHPointer.next;
					//now, left and right helix pointers point to nucleotide before removal index
				}
				
				//remove left nucleotide from left helix
				Nucleotide tempLH = lHPointer.next;
				lHPointer.next = tempLH.next;
				Character lHBase = tempLH.base; //remember left helix base
				String lResult = lHBase.toString(); //left helix base to string for return
				tempLH.next = null;
				
				//remove right nucleotide from right helix
				Nucleotide tempRH = rHPointer.next;
				rHPointer.next = tempRH.next;
				Character rHBase = tempRH.base; //remember right helix base
				String rResult = rHBase.toString(); //right helix base to string for return
				tempRH.next = null;
				
				result = lResult + rResult; //concatenate helix base strings for return result 
			}
			this.numElements = this.numElements - 1; //update logical length of list after removal
		}
		return result; //return base pair removed as string
	}
	
	/*
     * Purpose: print all elements in given DNA sequence
     * Preconditions: DNA sequence to be printed
     * Postcondition: returns a string with all elements of given DNA sequence if any
     */
	public void print(int startIndex, int endIndex)
	{
		if(startIndex < 0 || endIndex > numElements+1) //check if startIndex and endIndex are within range
		{
			throw new IndexOutOfBoundsException("One of the indexes given is out of range"); //EXCEPTION
		}
		
		else //startIndex and endIndex are within range
		{
			//points to first Nucleotide in left helix of DNA strand
			Nucleotide lH;
			lH = this.leftHelix;
			
			//points to first nucleotide in right helix of DNA strand
			Nucleotide rH;
			rH = this.rightHelix;
			
				for(int i = startIndex; i < endIndex; i++)
				{
					//prints out current item
					System.out.print(lH.base);
					System.out.print(rH.base);
					//increments pointer to next nucleotide
					lH = lH.next;
					rH = rH.next;
				}
		}
	}
	
	/*
     * Purpose: clear all elements from given DNA sequence
     * Preconditions: DNA sequence to be cleared
     * Postcondition: all elements have been cleared from the DNA sequence
     */
	public void clear()
	{
		this.leftHelix = null; //set left helix DNA strand to null
		this.rightHelix = null; //set right helix DNA strand to null
		this.numElements = 0; //set logical length of list to 0
	}
	
	/*
     * Purpose: checks if given DNA sequence contains elements
     * Preconditions: DNA sequence to be checked
     * Postcondition: returns true if list is empty and false if list contains elements
     */
	public boolean isEmpty()
	{
		return this.numElements == 0; //return true if DNA strand is empty and false if there are elements present
	}
	
	/*
     * Purpose: determine the length of a given DNA sequence
     * Preconditions: DNA sequence to be measured
     * Postcondition: returns the length of the given DNA sequence
     */
	public int getLength()
	{
		return this.numElements; //returns the logical length of the DNA strand
		
	}

	/*
     * Purpose: find the first index of given base pair if found
     * Preconditions: DNA sequence
     * Postcondition: returns index of given base pair of found or -1 if not found
     */
	public int find(String basePair)
	{
		Nucleotide lResult = this.leftHelix; //point to nucleotide of left helix
		Nucleotide rResult = this.rightHelix; //point to nucleotide of right helix
		boolean found; //true if item found, false if item is not found
		found = false; //initialize found to false
		int index = 0; //initialize index counter to 0
		int result; //initialize result variable for return
		
		String[] baseLetter = basePair.split(""); //convert basePair input to string array
		String tempLeftH = baseLetter[0];
		char leftH = tempLeftH.charAt(0); //assign left helix base value to variable for comparison
		String tempRightH = baseLetter[1];
		char rightH = tempRightH.charAt(0); //assign right helix base value to variable for comparison
		
		int i = 0; //instantiation of i variable for count
		
		while(i < numElements && found != true) //traverse through list looking for basePair value
		{
			if(lResult.base.equals(leftH) && rResult.base.equals(rightH)) //compares given value to value in DNA strand
			{
				found = true; //values are a match 
			}
			else //current value is not given input value
			{
				//increments left and right helix values to next in DNA strand
				lResult = lResult.next;
				rResult = rResult.next;
				index = index + 1; //increments index value to current index
				i++; //increments i counter variable
			}
		}
		
		if(index == numElements) //basePair value has not been found
		{
			result = -1; //assign not found value to result
		}
		
		else //basePair value has been found
		{
			result = index; //assign index value to result
		}
		
		return result; 
	}
	
	/*
     * Purpose: prints the left helix of the DNA sequence
     * Preconditions: DNA sequence to be printed
     * Postcondition: returns the elements in the left helix of the DNA sequence 
     */
	public void printLeft()
	{
		if(numElements == 0) //checks if the DNA strand is empty
		{
			throw new IndexOutOfBoundsException("There is nothing to print"); //there is nothing to print exception thrown
		}
		else //there are nucleotide values to be printed
		{
			//initialize pointer to first nucleotide value for left helix
			Nucleotide lH;
			lH = this.leftHelix;
			
			while(lH != null)
			{
				System.out.print(lH.base); //print current base value
				lH = lH.next; //increment to next base value
			}
		}
	}
	
	/*
     * Purpose: prints the right helix of the DNA sequence
     * Preconditions: DNA sequence to be printed
     * Postcondition: returns the elements in the right helix of the DNA sequence
     */
	public void printRight()
	{
		if(numElements == 0) //checks if the DNA stand is empty
		{
			throw new IndexOutOfBoundsException("There is nothing to print"); //there is nothing to print exception thrown
		}
		else //there are nucleotide values to be printed
		{
			//initialize pointer to first nucleotide value of right helix
			Nucleotide rH;
			rH = this.rightHelix;
			
			while(rH != null)
			{
				System.out.print(rH.base); //print current base value
				rH = rH.next; //increment to next base value
			}	
		}
	}
	
	/*
     * Purpose: prints a base pair at given index starting with the helix indicated
     * Preconditions: DNA sequence to be printed, index to start, and helix to begin with
     * Postcondition: returns the base pair at a given index starting with helix given
     */
	public void printBasePair(int index, int helix)
	{
		if(helix < 0 || helix > 1) //check is helix value is valid
		{
			throw new IndexOutOfBoundsException("Helix index out of bounds"); //helix out of bounds exception thrown
		}
		else //helix value is in correct range
		{
			if(helix == 0) //helix value represents left helix
			{
				if(index < 0 || index > numElements-1) //check is index value is valid
				{
					throw new IndexOutOfBoundsException("Index out of bounds"); //index out of bounds exception thrown
				}
				else //index value is in correct range
				{
					//initialize pointer to beginning of left helix DNA strand
					Nucleotide lH;
					lH = this.leftHelix;
					
					if(index == 0) //if index is beginning of DNA strand
					{
						System.out.print(lH.base); //print left helix base
						System.out.print(lH.across.base); //print right helix base
					}
					
					else if (index > 0) //index is greater than 0
					{
						for(int i = 0; i < index; i++)
						{
							lH = lH.next; 
							//now, lH represents the nucleotide basePair to be printed
						}
						
						System.out.print(lH.base); //print left helix base
						System.out.print(lH.across.base); //print right helix base
					}
				}
			}	
			
			else if(helix == 1) //helix value represents right helix
			{
				if(index < 0 || index > numElements-1) //check if index is valid
				{
					throw new IndexOutOfBoundsException("Index out of bounds"); //index out of bounds exception thrown
				}
				else //index value is in correct range
				{
					//initialize pointer to beginning of right helix DNA strand
					Nucleotide rH;
					rH = this.rightHelix;
					
					if(index == 0) //if index is beginning of list
					{
						System.out.print(rH.base); //print right helix base 
						System.out.print(rH.across.base); //print left helix base
					}
					else //index is greater than 0
					{
						for(int i = 0; i < index; i++)
						{
							rH = rH.next;
							//now, rH represents the nucleotide basePair to be printed
						}
						
						System.out.print(rH.base); //print right helix base
						System.out.print(rH.across.base); //print left helix base
					}
					
				}
				
			}
		}
		
	}
}
