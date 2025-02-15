package FM;
import java.util.*;
import java.util.Map.Entry;

public class FinanceManager {

		private static int monthlyIncome, savingsGoal, expense, updatedTotal, constantTotal, value, count, difference;
		private static String category, key, changes, confirm;
		private static Map <String, Integer> expenses;
		private static Scanner input;
		private static String[] commands;
		
		public static void main (String[] args)
		{
			 input = new Scanner(System.in);
			 expenses = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); //created tree map so that we can use the case insensitive order function so that when user is searching for an expense, the casing won't matter.
			 commands = new String[5];
			 commands[0] = "/quit";
			 commands[1] = "/changes";
			 commands[2] = "/delete";
			 commands[3] = "/back";
			 commands[4] = "/clear";
			 category = "";
			 
			 UI();
			
				
		}
		
		public static void display()
		{
			 count = 0; //counter variable that will increment in a for loop that automates the numbering of the expenses.
			
			System.out.println("----------------------------");
			System.out.println("Average Monthly Income: $" + monthlyIncome);
			System.out.println("Savings Goal: $" + savingsGoal);
			System.out.println("Monthly Total: $" + updatedTotal);
			System.out.println("----------------------------");
			System.out.println();
			System.out.println("--------Expenses--------");
			System.out.println();
			
			
			
			//In order to sort the hash map based on its integer values from greatest to least, we did this: 
			
			//Created an array list of the map entries and used a comparator to compare the integer values (this was the sorting part of the algorithm):
			List<Map.Entry<String, Integer>> list = new ArrayList<>(expenses.entrySet()); 
			list.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
			
			//Then we created a linked hash map to allow the keys to stay with their paired values as we put the sorted values into the map:
			LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
			
			//Then we put everything from our array list into a new map entry stored the ordered keys and the newly sorted values into map:
			for(Map.Entry<String, Integer> entry : list)
			{
				map.put(entry.getKey(), entry.getValue());
				
			}
			
			//Finally, we took a new entry set from the new linked hash map we created, got the values and keys, then printed our user display:
			for(Map.Entry<String,Integer> entry2 : map.entrySet())
			{
				
				 key = entry2.getKey();
				 value = entry2.getValue();
				 count++;
				 
				System.out.println(count + ". " + key + " $" + value);
			}
			
			
			System.out.println("--------------------");
			System.out.println("($" + updatedTotal + " of $" + constantTotal + " remaining.)"); 
			System.out.println();
			
			if(updatedTotal < 0)
			{
				
				System.out.println("WARNING: You have now spent more than you made this month!");
			}
			
			System.out.println();
			System.out.println("To make changes to any category, use - /changes -");
			System.out.println("To remove an expense, use - /delete -");
			System.out.println("To clear all expenses, use - /clear -");
			
			
			
			
			
			
		}
		
		 public static void inputExpenses()
		    {
		         input = new Scanner(System.in);
		        System.out.println("Name of expense:");
			 category = input.nextLine();
			 
			 if(category.equals("/quit"))
			 {
				 if(updatedTotal < 0)
				 {
					 System.out.println("This month, you went: $" + updatedTotal + " over your monthly budget.");
					 System.out.println("Thanks for using our Finance Manager!");
					 input.close();
					 
				 }
				 
				 if(updatedTotal > 0)
				 {
					 System.out.println("This month, you were left with: $" + updatedTotal);
					 int increase = updatedTotal + savingsGoal;
					 System.out.println("This allows your savings rate to potentially increase by: $" + updatedTotal + "\n" + "Making your monthly savings goal: $" + increase);
					 System.out.println();
					 System.out.println("Thanks for using our Finance Manager!");
					 input.close();
				 }
				 
				 if(updatedTotal == 0)
				 {
					 System.out.println("Based off your current expenses, you are living pay check to pay check, consider making some changes!");
					 System.out.println("Thanks for using our Finance Manager!");
					 input.close();
					 
				 }
				 
				 input.close();
				 System.exit(0);
				 
				
			 }
			 
			 if(category.equalsIgnoreCase("/changes"))
			 {
				 changes();
				 
			 }
			 
			 if(category.equals("/delete"))
			 {
				 
				 delete();
			 }
			 if(category.equals("/back"))
			 {
				//WHOLE purpose of this feature and the delete feature coexisting, is so that if you only have a small amount of expenses entered and you just want to remove one that you just added without having to go through 
				 //the delete command, you can do so. if you have a longer list, then using the delete command that consists of a search algorithm would be more efficient for the user. 
				 
				if(!expenses.isEmpty()) //if our map is NOT empty:
				{
					Iterator<Entry<String, Integer>> iterator = expenses.entrySet().iterator(); //create iterator object to traverse our map 
		            Entry<String, Integer> lastEntry = null; //create map entry variable to hold our answer
					
		            while(iterator.hasNext()) //while there is something next to iterate, continue to update our last entry to that next thing until it gets to last element 
		            {
		            	lastEntry = iterator.next();
		            	
		            }
		            
		            updatedTotal =  updatedTotal + lastEntry.getValue(); //update total to add back the expense that is removed
		            
		            if(lastEntry != null) //if once we get there, its not null, meaning it exists, then remove it. 
					{
						
						expenses.remove(lastEntry.getKey());
					}
				}
				
				
				
				display();
				inputExpenses();
			 }
			 
			 if(category.equals("/clear"))
			 {
					 expenses.clear();
					 updatedTotal = constantTotal;
					 System.out.println("Expenses Successfully Cleared.");
					 UI();
			 }
		         
			 
			 if(category.startsWith("/")) //if they started input with back slash, we can assume they wanted to use a command so we will search our array of commands, and if the command they input is not found, then it does'nt exist.
			 {
				 for(int i = 0; i < commands.length; i++)
				 {
					 if(!category.equals(commands[i]))
					 {
						 System.out.println("Unknown Command.");
						 inputExpenses();
						 
					 }
					 
					 
				 }
				 
				 
				 
				
			 }
			 
			 
			 
		        System.out.println("Amount:");
		        
		        while(true)
				 {
					 try {
						  
						 expense = input.nextInt();
						 
							break; //if successful, break out of loop
						 }
						 catch(InputMismatchException e)
						 {
							 
							 System.out.println("Invalid Entry.");
							 input.next(); //clear the invalid entry
							 inputExpenses();
						 }
					 
				 }
		        
		         expenses.put(category, expense);
		         
		         
		         
		         
		        
		       
		        
		                                					
		    }
		 
		 public static void UI()
		 {
			 System.out.println("Enter average monthly income:");
			 
			 while(true)
			 {
				 try {
					  
						monthlyIncome = input.nextInt();
						break; //if successful, break out of loop
					 }
					 catch(InputMismatchException e)
					 {
						 
						 System.out.println("Invalid Entry.");
						 input.next(); //clear the invalid entry
						 UI();
					 }
				 
			 }
				
				
				
				System.out.println("What is your monthly goal in savings?");
				while(true)
				 {
					 try {
						  
						 savingsGoal = input.nextInt();
							break; //if successful, break out of loop
						 }
						 catch(InputMismatchException e)
						 {
							 
							 System.out.println("Invalid Entry.");
							 input.next(); //clear the invalid entry
							 UI();
						 }
					 
				 }
				
				
				if(monthlyIncome < savingsGoal)
				{
					 
					
					
					double low = 10.0;
					double high = 20.0;
					//calculating percentages with their current monthly income
					long lowEnd = Math.round((low / 100) * monthlyIncome);
					long highEnd = Math.round((high / 100) * monthlyIncome);
					
					System.out.println("Based on your monthly income, this savings goal is not currently possible.");
					System.out.println("Our financial experts suggest that on average, we should set aside about 10-20% of our monthly income.");
					System.out.println("Your savings goals based off our calculations:" + "\n" + "\n" + "1. Low end: $" + lowEnd + "\n" + "2. High end: $" + highEnd);
					System.out.println();
					System.out.println("To default one of these goals, enter their respective number. Otherwise, enter 00 to start with a new monthly income.");
					int response = input.nextInt();
					
					if(response == 1)
					{
						savingsGoal = (int)lowEnd;
						
					}
					else if(response == 2)
					{
						savingsGoal = (int)highEnd;
						
					}
					else if(response == 00)
					{
						UI(); //use of recursion to start method from the top.
						
					}
					else
					{
						
						System.out.println("Invaild Input.");
						UI();
					}
					
				}
				
				updatedTotal = monthlyIncome - savingsGoal; //this is the ever changing total that is being adjusted with every user input.
				constantTotal = monthlyIncome - savingsGoal; //this is the initial total that shows up in the UI and does not change. 
			
				System.out.println("When done, enter - /quit - to exit.");
				System.out.println();
				System.out.println("----------------------------");
				System.out.println("Average Monthly Income: $" + monthlyIncome);
				System.out.println("Savings Goal: $" + savingsGoal);
				System.out.println("Monthly Total: $" + constantTotal);
				System.out.println("----------------------------");
				System.out.println();
				
				while(!category.equals("/quit"))
				{
					inputExpenses();
					updatedTotal = updatedTotal - expenses.get(category);
					display();
					
					System.out.println();
				}
			 
		 }
		 
		 public static void changes()
		 {
			
			 
				 System.out.println("----------CHANGES----------");
				 System.out.println();
				 System.out.println("Search name of category you wish to make changes to:" + "\n" + "To go back, enter - /back -");
				 changes = input.nextLine();
				 
				 
				
				 
				 
				 if(expenses.containsKey(changes))
				 {
					
					 int price = expenses.get(changes);
					 System.out.println("Current Expense for: " + changes.toUpperCase() + "  $" + price);
					 System.out.println("New Amount:");
					 int newAmount = input.nextInt();
					 
					 expenses.put(changes, newAmount);
					 //adjustments to calculations based on user changes.
					 if(newAmount > price)
					 {
						 difference = newAmount - price; 
						 updatedTotal = updatedTotal - difference;
						 
					 }
					 else
					 {
						 difference = price - newAmount;
						 updatedTotal = updatedTotal + difference;
						 
					 }
					 
					 System.out.println("Changes Saved.");
					 System.out.println();
					 
					 display();
					 inputExpenses(); //used recursion to start the method over again without moving straight to the "amount:" prompt.
					 
					 
					 
				 }
				 else if(changes.equalsIgnoreCase("/back"))
				 {
					 back();
					 
				 }
				 else if(!expenses.containsKey(changes))
				 {
					 System.out.println("The category: " + changes + " does not exist.");
					 changes();
					 
				 }
				  
				 
			 
			 
		 }
		 
		 public static void delete()
		 {
			 System.out.println("Search name of category you wish to remove:" + "\n" + "To go back, enter - /back -");
			 String delete = input.nextLine();
			 
			 if(delete.equalsIgnoreCase("/back"))
				{
					
					back(); 
				}
			 
			 if(expenses.containsKey(delete))
			 {
				int price = expenses.get(delete);
				
				System.out.println("Remove " + delete.toUpperCase() + " $" + price + "? (Y/N)");
				 confirm = input.nextLine();
				
				
				 if(confirm.equalsIgnoreCase("Y"))
				{
					expenses.remove(delete); 
					 System.out.println(delete.toUpperCase() + " has been removed.");
					 display();
					 back();
				}
				else if(confirm.equalsIgnoreCase("N"))
				{
					delete(); //this WORKS.
					
				}	
				
				else
				{
						System.out.println("Invalid Entry."); //this WORKS.
						delete();
					
				}
				 
			 }
			 else
			 {
				 
				 System.out.println(delete.toUpperCase() + " was not found in your expenses.");
				 delete();
			 }
		 }
		 
		 public static void back()
		 {
			 inputExpenses();
			 
		 }
		
		
			 
			 
			 
		 }
		 
		 
		 
		 
		 
		 
		
		 
	

