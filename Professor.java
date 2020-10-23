import java.util.*;

public class Professor {
    //private int class1;
    //private int class2;
    private int id;
    private int[] availableTimes;

    public Professor(int id, int numTimes) {
	this.id = id;
	//System.out.println(numTimes);
	this.availableTimes = new int[numTimes+1];
	//availableTimes = new LinkedList<Integer>();
    }
    public void setAvailableTimes(int[] availableTimes) {
	this.availableTimes = availableTimes;
    }	

    public int[] getAvailableTimes() {
	return availableTimes;
    }

    public int getID() {
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
	//System.out.println(time);
	
	//System.out.println("id " +id);
	//for(int i = 1; i < availableTimes.length; i++) {
	//System.out.println(availableTimes[i]);
	//}
	if(id==2) {
	    //System.out.println("remove");
	    //printAvailableTimes();
	}
	availableTimes[time] = -1;
    }

    public boolean available(int time) {
	//System.out.println("id " + id + " time " + time);
	//System.out.println("s " + availableTimes);
	//System.out.println("id " + id);
	//System.out.println("time " + time);
	//System.out.println(availableTimes[time]);
	if(id==2) {
	    //printAvailableTimes();
	}
	return availableTimes[time] != -1;
    }
    
    public String toString() {
	String s = id + ": ";
	//s += availableTimes;
	//for(int i = 1; i < availableTimes.length; i++) {
	//    s += availableTimes[i] + " ";
	//}
	return s;
    }
}
