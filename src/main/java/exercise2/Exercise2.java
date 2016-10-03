package exercise2;

import java.util.*;

public class Exercise2{	
	private ProcessFile fileOperations;
	private ProcessMap mapOperations;
	private Scanner input = new Scanner(System.in);

	Exercise2(String filePath){
		mapOperations = new ProcessMap();
		fileOperations = new ProcessFile(filePath, mapOperations);
		
	}	
	
	public static void main(String[] args){
		boolean readSuccess = false;		
		String filePath = "";
		if (args.length == 0) {
			filePath = "/home/ecc/Documents/programs/exercise2/testFile1.txt";
		}		
		else{
				filePath = args[0];	
		}	
		try{	
			Exercise2 mainFunctions = new Exercise2(filePath);		
			readSuccess = mainFunctions.readFile(filePath);				
				
			if(readSuccess){
				Scanner input = new Scanner(System.in);	
				char choice = 'X';
				do
				{
					System.out.print("Menu"+ 
						"\nA. Search " +
						"\nB. Edit " +
						"\nC. Print " +
						"\nD. Reset " +
						"\nE. Add Row " +	
						"\nF. Sort " +	
						"\nG. Exit " +	
						"\nEnter Choice: " );

					choice = input.next().charAt(0);
					switch(Character.toUpperCase(choice))
					{
						case 'A': 
							mainFunctions.search();
							break;
						
						case 'B': 
							mainFunctions.edit();
							break;
	
						case 'C': 
							mainFunctions.print();
							break;
						
						case 'D': 
							mainFunctions.reset();
							break;
						
						case 'E': 
							mainFunctions.add();
							break;
	
						case 'F': 
							mainFunctions.sort();
							break;
	
						case 'G':	
							 break;

						default: 
							System.out.println("\nInvalid Choice!");
							 break;	
					}		
	
				}while(Character.toUpperCase(choice) != 'G');		
			}		
		}	
		catch(StringIndexOutOfBoundsException e){
			System.out.println("\nPlease provide full file path. Program will end");
		}		
	}

	protected boolean readFile(String filePath){
		return fileOperations.readFile();
	}

	protected void search(){
		String searchString = "";
		System.out.print("\nEnter character(s) to search: ");
		searchString = input.next();
		System.out.println("\n" + mapOperations.search(searchString));	
	}

	protected void edit(){
		boolean repeatBlock = false;
		do{
				System.out.print("\nEnter index (00,01,02..):");
				String index = input.next();
				if(index.length()!=2 || !mapOperations.checkIndex(index))
				{
					System.out.println("\nInput Error. Kindly try again");
					repeatBlock = true;
				}
				else
				{ 	
					repeatBlock=false;
					int editChoice = 0;
					do{										
						try{
							System.out.println("\nEdit:\t (1)Key (2)Value (3)Both");
							editChoice = input.nextInt();
							switch(editChoice){
								case 1: editKey(index);
									   fileOperations.rewriteData();
									   break;
								case 2: editValue(index);
									   fileOperations.rewriteData();
									   break;
								case 3: editKey(index);
									   editValue(index);
									   fileOperations.rewriteData();
									   break;		
								default:  System.out.println("\nEnter 1, 2 or 3 only");
										editChoice=0;
										break;
							}
						}
						catch(InputMismatchException ime){
							System.out.println("\nEnter 1,2 or 3 only");
							editChoice = 0;
							input.next();
			   			}
					}while(editChoice==0);			
				}
		}while(repeatBlock);	
	}

	protected void editKey(String index){
		boolean repeatBlock = false;
		do{	
			System.out.print("\nEnter new key:");
			String newKey = input.next();
			if(mapOperations.checkKey(newKey)){
				System.out.println("\nKey already exists!");
				repeatBlock = true;		
			}
			else{
				mapOperations.editKey(index, newKey);
				repeatBlock = false;
			}	
		}while(repeatBlock);		
	}

	protected void editValue(String index){
		System.out.print("\nEnter new value:");
		String newValue = input.next();
		mapOperations.editValue(index, newValue);		
	}


	protected void print(){
		System.out.println("\n" + mapOperations.printMap());	
	}

	protected void reset(){
		fileOperations.writeOriginalData();
	}

	protected void add(){
		boolean repeatBlock = false;
		String key = "", value = "";
		int row = fileOperations.getRow();

		do{
			try{				
					System.out.print("\nInput number of key-value pair: ");
					int newEntriesCount = input.nextInt();
					if (newEntriesCount>0){
						repeatBlock = false;
						row++;
						for(int i=0;i<newEntriesCount;i++){
							do{															
								System.out.print("\nEnter key for index["+row+""+i+"]: ");
								key = input.next();
								boolean keyExists = mapOperations.checkKey(key);
								if(keyExists){
									System.out.println("\nKey already exists. Enter a different key");
									repeatBlock = true;	
								}
								else{
									repeatBlock = false;
									System.out.print("\nEnter value for index["+row+""+i+"]: ");
									value = input.next();
									mapOperations.addRow(key, value, row+""+i);
								}									
							}while(repeatBlock);
						}
						fileOperations.setRow(row);
						fileOperations.rewriteData();
					}
					else{
						System.out.println("\nOnly positive numbers are allowed");
						repeatBlock = true;			
					}
				}
				catch(InputMismatchException ime){
					System.out.println("\nOnly positive numbers are allowed");
					input.next();
					repeatBlock = true;
				}
		}while(repeatBlock);	
	}

	protected void sort(){
		mapOperations.sort();
		fileOperations.rewriteData();
	}
}
