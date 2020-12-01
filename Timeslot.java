import java.util.*;

public class Timeslot {

    private Date startTime;
    private Date endTime;
    private String days;
    
    public Timeslot(Date startTime, Date endTime, String days) {
	this.startTime = startTime;
	this.endTime = endTime;
	this.days = days;
    }

    public Date getStartTime() {
	return startTime;
    }

    public Date getEndTime() {
	return endTime;
    }

    public String getDays() {
	return days;
    }
    
    public boolean isOverlapping(Timeslot other) {
	String otherDays = other.getDays();
	char day;

	//check if there are days in common
	for(int i = 0; i < days.length(); i++) {
	    
	    day = days.charAt(i);
	    
	    //if there is a day in common
	    if(otherDays.indexOf(day) != -1) {
		
		//check if the times overlap
		return startTime.before(other.getEndTime()) && other.getStartTime().before(endTime);
	    }
	}
	return false;
    }
    
    public String toString() {
	String s = "Start: " + startTime + "\t" + "End: " + endTime + "\t" + "Days: " + days;
	return s;
    }

}
