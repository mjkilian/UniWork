package Calendar;
import java.awt.Color;
import java.io.*;
import java.util.Scanner;

import Calendar.Year.*;
import Calendar.Category.*;
import Calendar.Day.*;
import Calendar.Event.*;

public class Calendar { //prototype
	private static final int NO_OF_CATEGORIES = 10;
	private static Calendar cal = null;

	private Year years[] = {new Year(2012), new Year (2013)}; //for prototype
	private CategorySet categories = new CategorySet(NO_OF_CATEGORIES);

	private Calendar(){
		super();
	}


	public static Calendar returnCalendar(){ //singleton 
		if(cal == null){
			cal = new Calendar();
		}
		return cal;
	}

	public boolean quickAdd(String name, CalendarDate date, EventTime start, EventTime end,String location, Category category){
		int i = 0;
		if (date.getYear() == 2013){
			i = 1;
		}
		Day targetDay = years[i].getMonth(date.getMonth()).getDay(date.getDay());
		return targetDay.quickAddEvent(name,start,end,location,category);
	}
	public boolean fullAdd(String name, CalendarDate date, EventTime start, EventTime end,String location, Category category,String description,int recurrence, int reminder){
		int i = 0;
		if (date.getYear() == 2013){
			i = 1;
		}
		Day targetDay = years[i].getMonth(date.getMonth()).getDay(date.getDay());
		return targetDay.fullAddEvent(name,date,start,end,location,category,description,recurrence,reminder);
	}
	public Event removeEvent(CalendarDate date,int id){
		Day targetday = years[date.getYear()].getMonth(date.getMonth()).getDay(date.getDay());
		return targetday.removeEvent(id);
	}

	public boolean addCategory(String name, Color color){
		return categories.add(new Category(name,color));
	}
	
	public boolean buildFromFile(String fileName){
		
		Scanner read = null;
		
		try {
			read = new Scanner(new FileInputStream(fileName));
			
		} catch(FileNotFoundException e){
			System.out.println(e.getMessage());
			return false;
		}
		
		Scanner inputFeed;
		Scanner dateScanner;
		
		String line = "";
		
	
		
		while (read.hasNextLine()){
			line = read.nextLine();
			System.out.println(line);
			inputFeed = new Scanner(line);
			inputFeed.useDelimiter(",");
			
			String title = inputFeed.next();
			String date = inputFeed.next();
			dateScanner = new Scanner(date);
			dateScanner.useDelimiter("/");
			int day = dateScanner.nextInt();
			int month = dateScanner.nextInt();
			int year = dateScanner.nextInt();
			dateScanner.close();
			int startHours = inputFeed.nextInt();
			int startMins = inputFeed.nextInt();
			int endHours = inputFeed.nextInt();
			int endMins = inputFeed.nextInt();
			String location = inputFeed.next();
			int category = inputFeed.nextInt();
			String description = inputFeed.next();
			int recurrence = inputFeed.nextInt();
			int reminder = inputFeed.nextInt();
			try{
				fullAdd(title,new CalendarDate(day,month,year), new EventTime(startHours,startMins), new EventTime(endHours, endMins),location, categories.get(category),description, recurrence,reminder);
			}catch(Exception e){}
			
		
		}
	
		
		
		return true;
		
	}


	public static void main(String args[]){

		Calendar cal = returnCalendar();
		cal.buildFromFile(args[0]);
	}


}
