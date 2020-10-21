import java.util.*;

public class Classes implements Comparable<Classes>{

    private int id;
    private int popularity;
    private Professor professor;
    private ArrayList<Student> enrolledStudents;
    private int timeslot;
    private Room room;
    
    public Classes(int id) {
	this.id = id;
	this.popularity = 0;
	enrolledStudents = new ArrayList<>();
    }

    public int getID() {
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

    public void enrollStudent(Student s) {
	enrolledStudents.add(s);
    }

    public ArrayList<Student> getEnrolledStudents() {
	return enrolledStudents;
    }
    
    public String toString() {
	return id + ": pop: " + popularity + ", prof: " + professor;
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
