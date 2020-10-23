import java.util.*;

public class Student {

    private int id;
    private Classes[] prefList;
    private ArrayList<Classes> enrolledClasses;
    
    public Student(int id, Classes[] prefList) {
	this.id = id;
	this.prefList = prefList;
	this.enrolledClasses = new ArrayList<Classes>();
    }
    
    public Classes[] getPrefList() {
	return prefList;
    }

    public void enroll(Classes c) {
	enrolledClasses.add(c);
    }

    public ArrayList<Classes> getEnrolledClasses() {
	return enrolledClasses;
    }

    public boolean available(int time) {
	Classes c;
	for(int i = 0; i < enrolledClasses.size(); i++) {
	    c = enrolledClasses.get(i);
	    if(c.getTime() == time) {
		return false;
	    }
	}
	return true;
    }
    
    
    public String toString() {
	String s =  id + "";
	return s;
    }
    
    

}
