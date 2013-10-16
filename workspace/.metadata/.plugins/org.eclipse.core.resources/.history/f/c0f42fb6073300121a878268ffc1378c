package Calendar.Year;
import Calendar.Month.*;

public class Year {
	private int number;
	private Month months[] = new Month[12];
	private boolean isLeap; 
	
	//prototype - real calendar should have methods to calculate if the 
	//year is a leap year, etc.
	
	public Year(int number){
		this.number = number;
		//set leap year
		isLeap = false; //prototype code see above
		for(int i = 0; i < months.length; i++){
			months[i] = new Month(i, isLeap);
		}
	}
	
	public Month getMonth(int i){
		return months[i];
	}

	public int getNumber() {
		return number;
	}


	
	
}
