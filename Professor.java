import java.util.*;

public class Professor {

    private String id;
    private int[] availableTimes;

    public Professor(String id, int numTimes) {
	this.id = id;
	this.availableTimes = new int[numTimes+1];
    }
    
    public void setAvailableTimes(int[] availableTimes) {
	this.availableTimes = availableTimes;
    }	

    public int[] getAvailableTimes() {
	return availableTimes;
    }

    public String getID() {
	return id;
    }
    
    public void printAvailableTimes() {
	System.out.println("HERE");
	for(int i = 1; i < availableTimes.length; i++) {
	    System.out.print(availableTimes[i] + ", ");
	}
	System.out.println();
    }

    public void removeTime(int time) {
	availableTimes[time] = -1;
    }

    public boolean available(int time) {
	return availableTimes[time] != -1;
    }
    
    public String toString() {
	String s = id + "";
	return s;
    }
}
