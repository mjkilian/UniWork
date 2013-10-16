package Calendar.Month;
import Calendar.Day.*;

public class Month {
	/* Prototype so month lengths will be set manually*/

	static final int  monthLength[] = {31,28,31,30,31,30,31,31,30,31,30,31};

	private int monthNumber;
	private int length;
	private Day day[];

	Month(int number){ //default constructor
		this(number,false);
	}
	
	public Month(int number, boolean isLeap){
		monthNumber = number;
		if(number == 2 && isLeap){
			length = 29;
		}
		else
			length = monthLength[number];
		day = new Day[length];
		for(int i = 0; i < length; i++){
			day[i] = new Day();
		}		
	}

	public Day getDay(int i) {
		return day[i];
	}

	public int getMonthNumber() {
		return monthNumber;
	}

	public int getLength() {
		return length;
	}

	


}
