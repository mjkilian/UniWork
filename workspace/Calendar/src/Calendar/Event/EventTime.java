package Calendar.Event;


/**
 * @author 1003819k
 *
 */
public class EventTime implements Comparable<EventTime>{
	private int hour;
	private int minutes;

	public EventTime(int h, int m) throws EventException {
		if(h < 0 || h > 23){
			throw new EventException("Invalid Hour Value");
		}
		hour = h;
		if(m< 0 || m > 59){
			throw new EventException("Invalid Minute Value");
		}
		minutes = m;
	}

	public static EventTime getDuration(EventTime start_time, EventTime end_time){
		// given the start and end of the meeting, get the duration.
		
		int start_mins = 60 * start_time.getHour() + start_time.getMinutes();
		int end_mins = 60 * end_time.getHour() + end_time.getMinutes();
		
		int diff = end_mins - start_mins;
		
		EventTime et = null;
		 try{
			 et = new EventTime(diff%60, diff / 60);
		 }catch(EventException e){
		 	System.out.println(e.getMessage());
		 }
		 return et;
	}

	public String toString() {
		// turn the time into a string
		
		return "" + hour + ":" + minutes;
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	@Override
	public int compareTo(EventTime o) {
		if(hour < o.getHour()){
			return -1;
		}else if(hour > o.getHour()){
			return 1;
		}else{
			if(minutes < o.getMinutes())
				return -1;
			else if(minutes > o.getMinutes())
				return 1;
			else
				return 0;
		}
	}
	
	
	
	
	
	

}
