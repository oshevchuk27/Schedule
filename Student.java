import java.util.*;

public class Student {

    private int id;
    private Classes[] prefList;
    private ArrayList<Classes> enrolledClasses;
    
    public Student(int id, Classes[] prefList) {
	this.id = id;
	this.prefList = prefList;
    }
    
    public Classes[] getPrefList() {
	return prefList;
    }

    public void enroll(Classes c) {
	enrolledClasses.add(c);
    }
    
    public String toString() {
	String s =  id + "";
	//for(int i = 0; i < 4; i++) {
	//    s += prefList[i] + " ";
	//}
	return s;
    }
    
    

}
