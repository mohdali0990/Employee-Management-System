// This class is responsible to display the main menu and take user inputs 
// This could have been main, but I wanted my main to be clean!. 

import java.io.IOException;
import java.util.Scanner;


public class MainMenu {

	Scanner input = new Scanner(System.in);
	ReadTextFile readApplication = new ReadTextFile();
	WriteTextFile writeApplication = new WriteTextFile();
	int userEntry;
	public void start() throws IOException{
		readApplication.loadRecord();
		try {
			while(true){
				displayMainScreen();
				userEntry = input.nextInt();
				if (userEntry == 1){
					while(true){
						readApplication.showRecordsByID();
						System.out.println("Press 1 to see the record again or Press 0 to go back to main menu");
						userEntry = input.nextInt();
						if (userEntry == 1){
							continue;
						} else if (userEntry == 0){
							break;
						} else{
							System.out.println("Invalid Entry. Going back to Main menu");
							break;
						}
					}
				}
				else if (userEntry == 2){
					while(true){
						readApplication.showRecordsByName();
						System.out.println("Press 1 to see the record again or Press 0 to go back to main menu");
						userEntry = input.nextInt();
						if (userEntry == 1){
							continue;
						} else if (userEntry == 0){
							break;
						} else{
							System.out.println("Invalid Entry. Going back to Main menu");
							break;
						}
					}
				} else if (userEntry == 3){
					readApplication.showExpenseReport();
				} else if (userEntry ==  4){
					if(!readApplication.getList().isEmpty()){
					System.out.println("Please enter the employee id you would like to remove or enter 0 to exit:");
					userEntry = input.nextInt();
					while(true){
						if(userEntry != 0){
							if(readApplication.checkIDinTree(userEntry)){
								readApplication.delete(userEntry);
								writeApplication.delete(readApplication.getList());
								System.out.println("Employee ID " + userEntry + " Removed.");
								break;
							} else {
								System.out.println("Employee id not found\nPlease enter the employee id you would like to remove or enter 0 to exit:");
								userEntry = input.nextInt();
								if(userEntry == 0){
									break;
								} else {
									continue;
								}
							}

						} else{
							System.out.println("I WANT NOTHING WRITTEN HERE");
							break;
						}
					}
					} else{
						System.out.println("There is no Record to delete. Going back to Main Menu");
						continue;
					}
				} else if (userEntry == 5){
					System.out.println("Please enter employee id you would like to add or enter 0 to exit:");
					userEntry = input.nextInt();
					while(true){
						if(userEntry == 0){
							break;
						} else{
							if(readApplication.checkIDinTree(userEntry)){
								System.out.println("Employee ID already exists.\nPlease enter"
										+ " employee id you would like to add or enter 0 to exit: ");
								userEntry = input.nextInt();
								if(userEntry == 0)
									break;
							} else{
								System.out.println("Please Enter Full Name");
								/*
								 * Here I had to make another object of Scanner
								 * Otherwise old scanner instance "input" was showing
								 * weird behavior. It would skip taking input for full name
								 * and go on to take input for Employment Date. I just took a shortcut
								 * and created new Scanner instead of trying to fix it or check where the problem lies. 
								 */
								Scanner i = new Scanner (System.in);
								String fullName = i.nextLine();
								System.out.println("Please Enter Employment start date:");
								int yearStarted = i.nextInt();
								System.out.println("Please enter annual salary:");
								int salary = i.nextInt();
								System.out.println("Please enter team name");
								
								/*
								 * Same issue as above. Created new Scanner to avoid it. 
								 */
								
								Scanner in = new Scanner (System.in);
								String teamName = in.nextLine();
								readApplication.add(userEntry, fullName, yearStarted, salary, teamName);							
								writeApplication.add(userEntry, fullName, yearStarted, salary, teamName);
								System.out.println("Employee ID: " + userEntry + " added.\nPlease enter"
										+ " employee id you would like to add or enter 0 to exit: ");
								
								userEntry = input.nextInt();
								
								if(userEntry == 0){
									break;
								} else{
									continue;
								}
								
								
							}
						}
					}
				} else if (userEntry == 6){
					System.out.println("“Thank you for using Employee Management Portal”");
					System.exit(0);
				} else {
					
					System.out.println("Invalid Entry. Try again");
					continue;
				}
			}//end of main while
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			input.close();
			
		}
		
	}	
	
	public void displayMainScreen(){
		System.out.println("************************************\n"
				+ "Welcome To Employee Management Portal\n"
				+ "************************************\n"
				+ "Main menu\n"
				+ "1. Display all employee records sorted by Employee Id\n"
				+ "2. Display all employee records sorted by Employee Name\n"
				+ "3. Generate and display expense report\n"
				+ "4. Remove an employee\n"
				+ "5. Add an employee\n"
				+ "6. Exit Portal\n");
	}
}
