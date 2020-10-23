public class Room implements Comparable<Room>{

    private int id;
    private int size;
    private int[] availableTimes;
    private int numRemoved;
    private boolean unavailable;
    
    public Room(int id, int size)  {
	this.id = id;
	this.size = size;
	this.numRemoved = 0;
	this.unavailable = false;
    }
    public int getSize() {
	return size;
    }

    public int getID() {
	return id;
    }
    
    public void setAvailableTimes(int[] availableTimes) {
	this.availableTimes = availableTimes;
    }

    public int[] getAvailableTimes() {
	return availableTimes;
    }
    public void removeTime(int time) {
	availableTimes[time] = -1;
	numRemoved++;
    }

    public int getNumRemoved() {
	return numRemoved;
    }

    public boolean available(int time) {
	return availableTimes[time] != -1;
    }

    public void setUnavailable() {
	this.unavailable = true;
    }

    public boolean hasAvailableTimeslots() {
	return !unavailable;
    }
    
    public String toString() {
	return id + "";
    }
    
    public int compareTo(Room other) {
	if(this.size > other.size) {
	    return 1;
	} else if(this.size == other.size) {
	    return 0;
	} else {
	    return -1;
	}
    }
}
