package Calendar.Day;
import java.util.ArrayList;
import java.util.Collections;

import Calendar.Event.*;
import Calendar.Category.*;

public class Day {
	private ArrayList<Event> events;


	public Day(){
		events = new ArrayList<Event>();
	}


	public boolean quickAddEvent(String title, EventTime stime,EventTime etime,String location, Category category){
		//Currently implements quick add
		//will have to be expanded to encompass full add
		if (!(category == null)){
			return events.add(new Event(title, stime,etime,location, category));
		}else{
			return events.add(new Event(title,stime,etime));
		}
	}
	//temp for testing
	
	public ArrayList<Event> getEvent(){
		return events;
	}


	public boolean fullAddEvent(String title, CalendarDate date, EventTime starttime, EventTime endtime,String location,
		Category category,String description, int recurrence, int reminder){
		return events.add(new Event(title,starttime,endtime,location,category,description,recurrence,reminder));
	}

	public Event removeEvent(int id){
		Collections.sort(events);
		return events.remove(id);
	}







}
