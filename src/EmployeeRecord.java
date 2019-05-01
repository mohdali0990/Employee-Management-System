import java.util.Calendar;

// Fig. 17.4: AccountRecord.java
// AccountRecord class maintains information for one account.


public class EmployeeRecord
{
   private int ID;
   private String firstName;
   private String lastName;
   private int  yearStarted;
   private double Salary;
   private String teamName;
  
   private final double FIVE_PERCENT = 0.05;
   private final double TEN_PERCENT = 0.10;
   private final double FIFTEEN_PERCENT = 0.15;
   private final double TWENTY_PERCENT = 0.20;
   private final double THIRTY_PERCENT = 0.30;
   private final int FIVE = 5;
   private final int TEN = 10;
   private final int FIFTEEN = 15;
   private final int TWENTY = 20;
   private final int THIRTY = 30;
   
   
   public EmployeeRecord(){
	   
   }
  
   // initialize a record
   public EmployeeRecord( int ID, String first, String last, int year, double salary, String teamName )
   {
	  setID(ID); 
      setFirstName( first );
      setLastName( last );
      setYearStarted(year);
      setSalary(salary);
      setTeamName(teamName);

   } // end four-argument AccountRecord constructor
   
   // set first name   
   public void setFirstName( String first )
   {
      firstName = first;
   } // end method setFirstName

   // get first name   
   public String getFirstName() 
   { 
      return firstName; 
   } // end method getFirstName
   
   // set last name   
   public void setLastName( String last )
   {
      lastName = last;
   } // end method setLastName

   // get last name   
   public String getLastName() 
   {
      return lastName; 
   } // end method getLastName
   

public int getID() {
	return ID;
}

public void setID(int iD) {
	ID = iD;
}

public int  getYearStarted() {
	return yearStarted;
}

public void setYearStarted(int yearStarted) {
	this.yearStarted = yearStarted;
}

public double getSalary() {
	return Salary;
}

public int experience(){
	
	Calendar calendar = Calendar.getInstance();
	
	return calendar.get(Calendar.YEAR) - getYearStarted();
}

public double bonus(){
	
	if (experience() <= FIVE){
		return FIVE_PERCENT;
	} else if (experience() > FIVE && experience() <= TEN){
		return TEN_PERCENT;
	} else if (experience() > TEN && experience() <= FIFTEEN){
		return FIFTEEN_PERCENT;
	} else if (experience() > FIFTEEN && experience() <= TWENTY){
		return TWENTY_PERCENT;
	} else if (experience() > TWENTY){
		return THIRTY_PERCENT;
	} else {
		return 0;
	}
}

public int bonusToInteger(){
	if(bonus() == FIVE_PERCENT){
		return FIVE;
	} else if (bonus() == TEN_PERCENT){
		return TEN;
	} else if (bonus() == FIFTEEN_PERCENT){
		return FIFTEEN;
	} else if (bonus() == TWENTY_PERCENT){
		return TWENTY;
	} else if (bonus() == THIRTY_PERCENT){
		return THIRTY;
	} else {
		return 0;
	}
}
public void setSalary(double salary) {
	Salary = salary;
}

public int bonusAmount(){
	return (int) (getSalary()*bonus());
}
public int totalSalary(){
	return (int) (getSalary()+bonusAmount());
}

public String getTeamName() {
	return teamName;
}

public void setTeamName(String teamName) {
	this.teamName = teamName;
}
public String toString(){
	return String.format("%-12d%-10s%-10s%-8s%-13d%-11d%-14.0f%2d %12d\n",
               getID(), getFirstName().toUpperCase().charAt(0)+getFirstName().substring(1),
               getLastName().toUpperCase().charAt(0)+getLastName().substring(1), getTeamName(), getYearStarted(),experience(),
               getSalary(), bonusToInteger(), totalSalary());
	
}//end of toString
} // end class AccountRecord