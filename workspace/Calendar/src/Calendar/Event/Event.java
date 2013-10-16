package Calendar.Event;
import Calendar.Category.*;


public class Event implements Comparable<Event> {
	private static int idCount = 0;
	public enum Recurrence{NONE, DAILY, WEEKLY, TWO_WEEKLY, FOUR_WEEKLY};
	
	
	private int id;
	private String title;
	private EventTime starttime, endtime;
	public String location;
	private Category category = null ;
	private String description;
	public Recurrence recur;   // How often it recurs... see enum Recurrence
	public int reminder;

	
	public Event(String title, EventTime stime, EventTime etime) {
		super();
		this.id = idCount++;
		this.title = title;
		this.starttime = stime;
		this.endtime = etime;
	}
	
	
	public Event(String title, EventTime stime,EventTime etime,String location, Category category) {
		super();
		this.id = idCount++;
		this.title = title;
		this.starttime = stime;
		this.endtime = etime;
		this.location = location;
		this.category = category;
	}
	
	
	public Event(String title, EventTime starttime, EventTime endtime,String location,
			Category category,String description, int recurrence, int reminder) {
		super();
		this.id = idCount++;
		this.title = title;
		this.starttime = starttime;
		this.endtime = endtime;
		this.location = location;
		this.category = category;
		this.description = description;
		switch(recurrence){
		case 0:
			this.recur = Recurrence.NONE;
			break;
		case 1: 
			this.recur = Recurrence.DAILY;
			break;
		case 2:
			this.recur = Recurrence.WEEKLY;
			break;
		case 3:
			this.recur = Recurrence.TWO_WEEKLY;
			break;
		case 4:
			this.recur = Recurrence.FOUR_WEEKLY;
		}
		this.reminder = reminder;
	}


	public int compareTo(Event e){
		if(this.id < e.getId())
			return -1;
		else if(this.id > e.getId())
			return 1;
		else
			return 0;
	}
	
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    

	public EventTime getStarttime() {
		return starttime;
	}

	public void setStarttime(EventTime starttime) {
		this.starttime = starttime;
	}

	public EventTime getEndtime() {
		return endtime;
	}

	public void setEndtime(EventTime endtime) {
		this.endtime = endtime;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Recurrence getRecur() {
		return recur;
	}

	public void setRecur(Recurrence recur) {
		this.recur = recur;
	}

	public int getReminder() {
		return reminder;
	}

	public void setReminder(int reminder) {
		this.reminder = reminder;
	}
	
	
	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public void outString(){
		System.out.println("");
		System.out.println("ID: " + this.id);
		System.out.println("Title " + this.title);
		System.out.println("Start " + this.starttime);
		System.out.println("End " + this.endtime);
		System.out.println("location " + this.location);
		System.out.println("cat " + this.category.getTitle());
		System.out.println("catcolor " + this.category.getColor());
		System.out.println("recur " + this.recur.toString());
		System.out.println("reminder " + this.reminder + "mins before");
	
	}
	
	
}
