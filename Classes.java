import java.util.*;

public class Classes implements Comparable<Classes>{

    private String id;
    private int popularity;
    private Professor professor;
    private ArrayList<Student> enrolledStudents;
    private int timeslot;
    private Room room;
    
    public Classes(String id) {
	this.id = id;
	this.popularity = 0;
	this.enrolledStudents = new ArrayList<Student>();
    }

    public String getID() {
	return id;
    }
    
    public void setProfessor(Professor professor) {
	this.professor = professor;
    }

    public Professor getProfessor() {
	return professor;
    }
	
    public void incPopularity() {
	popularity += 1;
    }
    
    public int getPopularity() {
	return popularity;
    }

    public void setTime(int timeslot) {
	this.timeslot = timeslot;
    }

    public void setRoom(Room room) {
	this.room = room;
    }

    public int getTime() {
	return timeslot;
    }

    public Room getRoom() {
	return room;
    }
    
    public void enrollStudent(Student s) {
	enrolledStudents.add(s);
    }

    public boolean isFull() {
	int roomSize = this.getRoom().getSize();
	int numStudentsEnrolled = enrolledStudents.size();
	return roomSize == numStudentsEnrolled;
    }

    public ArrayList<Student> getEnrolledStudents() {
	return enrolledStudents;
    }
    
    public String toString() {
	String s = id + "\t" + room + "\t" + professor + "\t" + timeslot + "\t";
	for(int i = 0; i < enrolledStudents.size(); i++) {
	    s = s +  enrolledStudents.get(i) + " ";
	}
	return s;
    }
    
    public int compareTo(Classes other) {
	if(this.popularity > other.popularity) {
	    return 1;
	} else if(this.popularity == other.popularity) {
	    return 0;
	} else {
	    return -1;
	}
    }
}
