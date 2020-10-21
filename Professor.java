import java.util.*;

public class Professor {
    //private int class1;
    //private int class2;
    private int id;
    //private int[] availableTimes;
    private LinkedList<Integer> availableTimes;

    public Professor(int id) {
	this.id = id;
	availableTimes = new LinkedList<Integer>();
    }
    public void setAvailableTimes(LinkedList<Integer> availableTimes) {
	this.availableTimes = availableTimes;
    }

    public LinkedList<Integer> getAvailableTimes() {
	return availableTimes;
    }

    public void removeTime(int time) {
	availableTimes.remove(time);
    }
    
    public String toString() {
	String s = id + ": ";
	s += availableTimes;
	//for(int i = 1; i < availableTimes.length; i++) {
	//    s += availableTimes[i] + " ";
	//}
	return s;
    }
}
