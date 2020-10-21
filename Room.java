public class Room implements Comparable<Room>{

    private int id;
    private int size;
    private int[] availableTimes;
    
    public Room(int id, int size)  {
	this.id = id;
	this.size = size;
    }
    public int getSize() {
	return size;
    }

    public void setAvailableTimes(int[] availableTimes) {
	this.availableTimes = availableTimes;
    }

    public int[] getAvailableTimes() {
	return availableTimes;
    }
    
    public String toString() {
	return id + ": " + size; 
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
