import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class ReadTextFile{
	private Scanner input;
	private EmployeeRecord record;
	private List<EmployeeRecord> list = new ArrayList<EmployeeRecord>();
	
	public List<EmployeeRecord> getList(){
		return list;
	}
	
	public void openFile()
  {
     try
     {
        input = new Scanner( new File( Application.path ) );
     } // end try
     catch ( FileNotFoundException fileNotFoundException )
     {
        System.err.println( "Error opening file." );
        System.exit( 1 );
     } // end catch
  } // end method openFile
	
	public void readRecords()
  {	 
	   String line, token[], Name, FullName[], FirstName, LastName, TeamName;
	   int ID, YearStartedWorking;
	   double Salary;
	   
     try // read records from file using Scanner object
     {

   	 while (input.hasNext()) {

		 record = new EmployeeRecord();
         line = input.nextLine();
         token = line.split(",");
         ID = Integer.parseInt(token[0]);
         Name = token[1];
         FullName = Name.split(" ");
         FirstName = FullName[0];
         LastName = FullName[1];
         YearStartedWorking = Integer.parseInt(token[2]);
         Salary = Double.parseDouble(token[3]);
         TeamName = token[4];
         
         record.setID(ID);
         record.setFirstName(FirstName);
         record.setLastName(LastName);
         record.setYearStarted(YearStartedWorking);
         record.setSalary(Salary);
         record.setTeamName(TeamName);              
         
        getList().add(record);    
         }//end of while loop
   } // end try
     catch ( NoSuchElementException elementException )
     {
        System.out.println( "File improperly formed." );
        input.close();
        System.exit( 1 );
     } // end catch
     catch ( IllegalStateException stateException )
     {
        System.err.println( "Error reading from file." );
        System.exit( 1 );
     } // end catch
  } //end of readRecordsForIDs
	
	public void showRecordsByID(){
		Collections.sort(getList(), new CompareByID());
		System.out.println("Employee ID Full Name           Team    Started Year Experience Annual Salary Bonus %  Total Salary" );
		for(EmployeeRecord record : getList()){
			System.out.print(record.toString());
		}
	}
	
	public void showRecordsByName(){
		Collections.sort(getList(), new CompareByName());
		System.out.println("Employee ID Full Name           Team    Started Year Experience Annual Salary Bonus %  Total Salary" );
		for(EmployeeRecord record : getList()){
			System.out.print(record.toString());
		}
	}
	
	public void showExpenseReport(){
		Set<EmployeeRecord> setList = new TreeSet<EmployeeRecord>(new CompareByTeams());
		setList.addAll(getList());             //Easy way to save all the team names without duplicates
		String[] allTeamNames = new String[setList.size()];        //All the team names are in this array
		
		int i = 0;
		for (EmployeeRecord record : setList) {
			allTeamNames[i] = record.getTeamName();
			i++;
		}
			
		double[] teamSalaries = new double[allTeamNames.length];  //Salaries of employees from each team
		double[] bonus = new double[allTeamNames.length];		  //bonus ammount saved here
		for(int j = 0; j < allTeamNames.length; j++){
			for (EmployeeRecord record : getList()){
				if(record.getTeamName().equals(allTeamNames[j])){
					teamSalaries[j] += record.getSalary();
					bonus[j] +=record.bonusAmount();
				}
			}
		}
		
		System.out.println("Team\tSalaries\tBonuses\t\tTotal Expenses");
		
		for(int x = 0; x < allTeamNames.length; x++){
			System.out.println(allTeamNames[x] +" \t"+teamSalaries[x]+" \t"+bonus[x]+"   \t"+(teamSalaries[x]+bonus[x]));
		}
		
		System.out.print("Total" +"\t" + allSalaries(teamSalaries)+"\t"+ allBonuses(bonus)+"\t" + (allSalaries(teamSalaries)+ allBonuses(bonus))+"\n" );
	
	}
	
	private double allBonuses(double[] bonus) {
		double total = 0;
		for (int i = 0; i < bonus.length; i++) {
			total += bonus[i];
		}
		return total;
	}

	private double allSalaries(double[] teamSalaries) {
		double total = 0;
		for (int i = 0; i < teamSalaries.length; i++) {
			total += teamSalaries[i];
		}
		return total;
	}

	public void closeFile()
  {
     if ( input != null )
        input.close(); // close file
  } // end method closeFile

	public boolean checkIDinTree(int userEntry) {
		for(EmployeeRecord record : getList()){
			   if(record.getID() == userEntry)
				   return true;
		   }
		return false;
	}

	public void delete(int userEntry) {
		 Iterator<EmployeeRecord> it = getList().iterator();
		 while (it.hasNext()){
			 EmployeeRecord record = it.next();
			 if(record.getID() == userEntry){
				 it.remove();
			 }
		 }
	}

	public void add(int ID, String fullName, int yearStarted,
			int salary, String teamName) {
		String[] tokens = fullName.split(" ");
		record = new EmployeeRecord(ID,tokens[0], tokens[1], yearStarted, salary, teamName);
             getList().add(record);
		
	}
	
	public void loadRecord(){
		this.openFile();
		this.readRecords();
		this.closeFile();
	}
	
	
}//end of class

class CompareByID implements Comparator<EmployeeRecord> {

	@Override
	public int compare(EmployeeRecord e1, EmployeeRecord e2) {
		
		return e1.getID() - e2.getID();
	}
	
}
class CompareByName implements Comparator<EmployeeRecord>{
@Override
public int compare(EmployeeRecord e1, EmployeeRecord e2){
	return e1.getFirstName().compareTo(e2.getFirstName());
}
}
class CompareByTeams implements Comparator<EmployeeRecord>{
@Override
public int compare(EmployeeRecord e1, EmployeeRecord e2){
	return e1.getTeamName().compareTo(e2.getTeamName());
}
}

/*
 * Exception in thread "main" java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
	at java.util.ArrayList$Itr.next(Unknown Source)
	at ReadTextFile.delete(ReadTextFile.java:133)
	at MainMenu.start(MainMenu.java:57)
	at Application.main(Application.java:10)
 */

//// This program reads a text file and displays each record.
//// And also calculates other stuff which 'could' have been in EmployeeRecord.java
//// It has 5 classes. one to read file and 4 for comparators. 
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.lang.IllegalStateException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
//import java.util.Set;
//import java.util.TreeMap;
//import java.util.TreeSet;
//
//
//public class ReadTextFile
//{
//   private Scanner input;
//   EmployeeRecord record;
//   
//   TreeSet<EmployeeRecord> treeWithIDsSorted;    //need three treeSets. Tried to work with one
//   TreeSet<EmployeeRecord> treeWithNamesSorted;  //but as soon as used it to sort it one way,
//   TreeSet<EmployeeRecord> treeWithTeamsSorted;  //it would mess up the whole list. 
//   
//   String line;
//   String[] token;
//   int ID;
//   String Name;
//   String[] FullName;
//   String FirstName;
//   String LastName;
//   int YearStartedWorking;
//   double Salary;
//   String TeamName;
//   
//   public TreeSet<EmployeeRecord> getTreeWithIDsSorted() {
//		return treeWithIDsSorted;
//	}
//	
//	public boolean checkIDinTree(int i){
//		for(EmployeeRecord record : treeWithIDsSorted){
//			   if(record.getID() == i)
//				   return true;
//		   }
//		return false;
//	}
//	
//	public void add(int ID, String name, int year, double salary, String team){
//		String[] tokens = name.split(" ");
//		record = new EmployeeRecord(ID,tokens[0], tokens[1], year, salary, team);
//              treeWithIDsSorted.add(record);
//	}
//	
//	public void delete(int i){
//		
//		/*for(EmployeeRecord record : treeWithIDsSorted){
//			if(record.getID() == i){		
//		treeWithIDsSorted.remove(record);
//	}
//}*/         
//		// Interesting behavior while using the above way. 
//		// It would throw a connecurrent something error. lal
//		// Instead used the iterator below and it works like charm. 
//
//				
//				 Iterator<EmployeeRecord> it = treeWithIDsSorted.iterator();
//				 while (it.hasNext()){
//					 EmployeeRecord value = it.next();
//					 if(value.getID() == i){
//						 it.remove();
//					 }
//				 }
//
//	} // end of DeleteAnEntry
// 
//   public void openFile()
//   {
//      try
//      {
//         input = new Scanner( new File( "C:/Users/noname/Desktop/employee_data.txt" ) );
//      } // end try
//      catch ( FileNotFoundException fileNotFoundException )
//      {
//         System.err.println( "Error opening file." );
//         System.exit( 1 );
//      } // end catch
//   } // end method openFile
//   
//   public void readRecordsForIDs()
//   {
//	   treeWithIDsSorted = new TreeSet<EmployeeRecord>(new CompareByID());
//	 
//      try // read records from file using Scanner object
//      {
//
//    	 while (input.hasNextLine()) {
//    		  record = new EmployeeRecord();
//              line = input.nextLine();
//              token = line.split(",");
//              ID = Integer.parseInt(token[0]);
//              Name = token[1];
//              FullName = Name.split(" ");
//              FirstName = FullName[0];
//              LastName = FullName[1];
//              YearStartedWorking = Integer.parseInt(token[2]);
//              Salary = Double.parseDouble(token[3]);
//              String TeamName = token[4];
//              
//              record.setID(ID);
//              record.setFirstName(FirstName);
//              record.setLastName(LastName);
//              record.setYearStarted(YearStartedWorking);
//              record.setSalary(Salary);
//              record.setTeamName(TeamName);              
//              
//              treeWithIDsSorted.add(record);              
//          }
//    } // end try
//      catch ( NoSuchElementException elementException )
//      {
//         System.err.println( "File improperly formed." );
//         input.close();
//         System.exit( 1 );
//      } // end catch
//      catch ( IllegalStateException stateException )
//      {
//         System.err.println( "Error reading from file." );
//         System.exit( 1 );
//      } // end catch
//   } //end of readRecordsForIDs
//   
//   public void readRecordsForNames()
//   {
//	   treeWithNamesSorted = new TreeSet<EmployeeRecord>(new CompareByName());
//	 
//      try // read records from file using Scanner object
//      {
//
//    	 while (input.hasNextLine()) {
//    		  record = new EmployeeRecord();
//              line = input.nextLine();
//              token = line.split(",");
//              ID = Integer.parseInt(token[0]);
//              Name = token[1];
//              FullName = Name.split(" ");
//              FirstName = FullName[0];
//              LastName = FullName[1];
//              YearStartedWorking = Integer.parseInt(token[2]);
//              Salary = Double.parseDouble(token[3]);
//              String TeamName = token[4];
//              
//              record.setID(ID);
//              record.setFirstName(FirstName);
//              record.setLastName(LastName);
//              record.setYearStarted(YearStartedWorking);
//              record.setSalary(Salary);
//              record.setTeamName(TeamName);              
//              
//              treeWithNamesSorted.add(record);
//                            
//          }
//    } // end try
//      catch ( NoSuchElementException elementException )
//      {
//         System.err.println( "File improperly formed." );
//         input.close();
//         System.exit( 1 );
//      } // end catch
//      catch ( IllegalStateException stateException )
//      {
//         System.err.println( "Error reading from file." );
//         System.exit( 1 );
//      } // end catch
//   } //end of readRecordsForNames
//   
//   public void readRecordsForTeams()
//   {
//	   treeWithTeamsSorted = new TreeSet<EmployeeRecord>(new CompareByTeams());
//	   
//      try // read records from file using Scanner object
//      {
//
//    	 while (input.hasNextLine()) {
//    		
//    		  record = new EmployeeRecord();
//              line = input.nextLine();
//              token = line.split(",");
//              ID = Integer.parseInt(token[0]);
//              Name = token[1];
//              FullName = Name.split(" ");
//              FirstName = FullName[0];
//              LastName = FullName[1];
//              YearStartedWorking = Integer.parseInt(token[2]);
//              Salary = Double.parseDouble(token[3]);
//              String TeamName = token[4];
//              
//              record.setID(ID);
//              record.setFirstName(FirstName);
//              record.setLastName(LastName);
//              record.setYearStarted(YearStartedWorking);
//              record.setSalary(Salary);
//              record.setTeamName(TeamName);              
//              
//              treeWithTeamsSorted.add(record);
//                            
//          }
//    } // end try
//      catch ( NoSuchElementException elementException )
//      {
//         System.err.println( "File improperly formed." );
//         input.close();
//         System.exit( 1 );
//      } // end catch
//      catch ( IllegalStateException stateException )
//      {
//         System.err.println( "Error reading from file." );
//         System.exit( 1 );
//      } // end catch
//   } //end of readRecordsForTeams
// 
//   public void showRecordsByIDs(){
//	   openFile();
//	   readRecordsForIDs();
//	   System.out.println("Employee ID Full Name           Team    Started Year Experience Annual Salary Bonus %  Total Salary" );
//	   if(treeWithIDsSorted.size() != 0){
//	   for(EmployeeRecord record : treeWithIDsSorted){
//		   System.out.print(record);
//	   }
//	   } else{
//		   System.out.println("Record is Empty");
//	   }
//   }
//   
//   public void showRecordsByName(){
//	   openFile();
//	   readRecordsForNames();
//	   System.out.println("Employee ID Full Name           Team    Started Year Experience Annual Salary Bonus %  Total Salary" );
//	   if(treeWithNamesSorted.size() != 0){
//	   for(EmployeeRecord record : treeWithNamesSorted){
//		   System.out.print(record);
//	   }
//	   } else{
//		   System.out.println("Record is Empty");
//	   }
//	   
//   }
//
//   public void showExpenseReport(){
//	   
//	   openFile();
//	   readRecordsForIDs();
//	   List<EmployeeRecord> AL = new ArrayList<EmployeeRecord>(treeWithIDsSorted);
//	   Collections.sort(AL, new EmployeeComparator());
//	   String BA = "BA";
//	   String Dev = "Dev";
//	   String OC = "OC";
//	   String PM = "PM";
//	   String QA = "QA";
//	   long BASalary = 0;
//	   long BABonus = 0;
//	   long DevSalary = 0;
//	   long DevBonus = 0;
//	   long OCSalary = 0;
//	   long OCBonus = 0;
//	   long PMSalary = 0;
//	   long PMBonus = 0;
//	   long QASalary = 0;
//	   long QABonus = 0;
//	   
//	   System.out.println("Team        Salaries       Bonuses          Total Expenses");
//	   
//	   for(EmployeeRecord record : AL){
//		  
//		   if(record.getTeamName().equals(BA)){
//			 
//			   BASalary = (long) (BASalary + record.getSalary());
//			   BABonus = BABonus + record.bonusAmount();
//			  
//		   } else if(record.getTeamName().equals(Dev)){
//			   DevSalary = (long) (DevSalary + record.getSalary());
//			   DevBonus = DevBonus + record.bonusAmount();
//		   } else if(record.getTeamName().equals(OC)){
//			   OCSalary = (long) (OCSalary + record.getSalary());
//			   OCBonus += record.bonusAmount();
//		   } else if (record.getTeamName().equals(PM)){
//			   PMSalary =  (long) (PMSalary + record.getSalary());
//			   PMBonus += record.bonusAmount();
//		   } else if (record.getTeamName().equals(QA)){
//			   QASalary = (long) (QASalary + record.getSalary());
//			   QABonus += record.bonusAmount();
//		   } else{
//			   System.err.print("Cannot read salaries or bonuses. Did you "
//			   		+ "enter the team name in one of the five ways? Terminating program");
//			   System.exit(-1);
//		   }
//		   
//	   }
//	   
//	   openFile();
//	   readRecordsForTeams();
//	   for(EmployeeRecord record: treeWithTeamsSorted){
//		   
//		   if(record.getTeamName().equals(BA)){
//			   System.out.println(record.getTeamName() + "          " + BASalary + "         " + BABonus + "            " + (BASalary+BABonus)); 
//		   } else if(record.getTeamName().equals(Dev)){
//			   System.out.println(record.getTeamName() + "         " + DevSalary + "         " + DevBonus + "           " + (DevSalary+DevBonus));
//			   
//		   } else if(record.getTeamName().equals(OC)){
//			   System.out.println(record.getTeamName() + "          " + OCSalary + "         " + OCBonus + "            " + (OCSalary+OCBonus));
//			   
//		   } else if (record.getTeamName().equals(PM)){
//			   System.out.println(record.getTeamName() + "          " + PMSalary + "         " + PMBonus + "            " + (PMSalary+PMBonus));
//			   
//		   } else if (record.getTeamName().equals(QA)){
//			   System.out.println(record.getTeamName() + "          " + QASalary + "         " + QABonus + "            " + (QASalary+QABonus));
//			   
//		   } else{
//			   System.err.print("Cannot read salaries and bonuses of all the employees");
//			   System.exit(-1);
//		   }
//		   
//	   }
//	   if(!treeWithTeamsSorted.isEmpty()){
//	   System.out.println("Total       " + (BASalary + DevSalary + OCSalary + PMSalary + QASalary) + "        " + (BABonus + DevBonus+OCBonus+PMBonus+QABonus)
//			   + "           "+(BASalary + DevSalary + OCSalary + PMSalary + QASalary + BABonus + DevBonus+OCBonus+PMBonus+QABonus));
//	   } else{
//		   System.out.println("Record is Empty");
//	   }
//   }
//   
//   // close file and terminate application
//   public void closeFile()
//   {
//      if ( input != null )
//         input.close(); // close file
//   } // end method closeFile
//} // end class ReadTextFile
//
//class CompareByID implements Comparator<EmployeeRecord> {
//
//	@Override
//	public int compare(EmployeeRecord e1, EmployeeRecord e2){
//		return e1.getID() - e2.getID() ;
//	}
//}
//
//class CompareByName implements Comparator<EmployeeRecord>{
//	@Override
//	public int compare(EmployeeRecord e1, EmployeeRecord e2){
//		return e1.getFirstName().compareTo(e2.getFirstName());
//	}
//}
//
//class CompareByTeams implements Comparator<EmployeeRecord>{
//	@Override
//	public int compare(EmployeeRecord e1, EmployeeRecord e2){
//		return e1.getTeamName().compareTo(e2.getTeamName());
//	}
//}
//
//class EmployeeComparator implements Comparator<EmployeeRecord> {
//
//	@Override
//	public int compare(EmployeeRecord e1, EmployeeRecord e2) {
//	
//		return e1.getTeamName().compareTo(e2.getTeamName());
//		}
//
//}