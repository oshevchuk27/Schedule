import java.util.*;
import java.io.*;

public class Schedule {

    static int currentLargestRoom = 0;
    
    static int numClasses;
    static int numTimeslots;
    static int numRooms;
    
    static Classes[] classes;
    static Room[] rooms;
    static Student[] students;
    static Professor[] professors;
    
    public static void main(String[] args) throws FileNotFoundException {
	readConstraints("demo_constraints.txt");
	readPreferences("demo_studentprefs.txt");
	setTimes();
	makeSchedule();
	for(int i = 0; i < classes.length; i++) {
	    System.out.println(classes[i]);
	}
	for(int i = 0; i< professors.length;i++) {
	    // professors[i].printAvailableTimes();
	}
	
	
    }
    public static void readConstraints(String filename) throws FileNotFoundException {
	Scanner constraints = new Scanner(new File(filename));
	while(!constraints.hasNextInt()) {
	    constraints.next();
	}
	numTimeslots = constraints.nextInt();
	System.out.println("timeslots: " + numTimeslots);
	
	while(!constraints.hasNextInt()) {
	    constraints.next();
	}
	numRooms = constraints.nextInt();
	System.out.println("rooms: " + numRooms);

	
	rooms = new Room[numRooms+1];
	rooms[0] = new Room(0,0);
	int size;
	for(int i = 1; i <=  numRooms; i++) {
	    constraints.nextInt();
	    size = constraints.nextInt();
	    rooms[i] = new Room(i, size);
	}

	//sort list from largest to smallest room
	Arrays.sort(rooms, Collections.reverseOrder());

	while(!constraints.hasNextInt()) {
	    constraints.next();
	}
	
	numClasses = constraints.nextInt();
	classes = new Classes[numClasses+1];
	classes[0] = new Classes(0);
	System.out.println("classes: " + numClasses);

	while(!constraints.hasNextInt()) {
	    constraints.next();
	}
	int numProfessors = constraints.nextInt();
	if(numProfessors != numClasses/2) {
	    System.out.println("num professors wrong");
	}
	System.out.println("professors: " + numProfessors);
	professors = new Professor[numProfessors + 1];
	professors[0] = new Professor(0,numTimeslots);

	int professor;
	Classes c;
	Professor p;

	//set professors to classes
	for(int i = 1; i <=  numClasses; i++) {
	    constraints.nextInt();
	    professor = constraints.nextInt();
	    c = new Classes(i);
	    //if professor has not yet been processed
	    if(professors[professor] == null) {
		p = new Professor(professor,numTimeslots);
	    } else {
		p = professors[professor];
	    }
	    c.setProfessor(p);
	    classes[i] = c;
	    professors[professor] = p;
	} 
    }

    public static void readPreferences(String filename) throws FileNotFoundException {
	Scanner preferences = new Scanner(new File(filename));
	while(!preferences.hasNextInt()) {
	    preferences.next();
	}
	int numStudents = preferences.nextInt();
	System.out.println("students: " + numStudents);
	students = new Student[numStudents + 1];
	students[0] = new Student(0,null);

	Classes c;
	
	for(int i = 1; i <= numStudents; i++) {
	    Classes[] prefList = new Classes[4];
	    preferences.nextInt();

	    //count popularity of classes
	    for(int j = 0; j < 4; j++) {
		c = classes[preferences.nextInt()];
		prefList[j] = c;
		classes[c.getID()].incPopularity();
	    }
	    //save students' preference lists
	    students[i] = new Student(i, prefList);
	}

	//sort classes most to least popular
	Arrays.sort(classes, Collections.reverseOrder());
    }

    //set rooms' and professors' available timeslots to all timeslots
    public static void setTimes() {

	int[] timeslots;
	
	for(Room room : rooms) {
	    timeslots = new int[numTimeslots + 1];
	    for(int i = 0; i <= numTimeslots; i++) {
		timeslots[i] = i;
	    }
	    room.setAvailableTimes(timeslots);
	}
	
	for(Professor professor : professors) {
	    timeslots = new int[numTimeslots + 1];
	    int[] ts = professor.getAvailableTimes();
	    //System.out.println(ts.length);
	    for(int i = 0; i <= numTimeslots; i++) {
		timeslots[i] = i;
		//ts[i] = i;
	    }
	    professor.setAvailableTimes(timeslots);
	}
	/*for(int i = 1; i < professors.length; i++) {
	    System.out.println(i);
	    professors[i].printAvailableTimes();
	    System.out.println();
	}
	professors[2].removeTime(3);
	professors[1].removeTime(2);
	for(int i = 1; i < professors.length; i++) {
	    System.out.println(i);
	    professors[i].printAvailableTimes();
	    System.out.println();
	    }*/
    }

    public static boolean scheduleClass(Classes c, Room r) {
	
	Professor p = c.getProfessor();
	int[] roomTimeslots = r.getAvailableTimes();
	boolean available;
	int t;
	//for(int t : roomTimeslots) {
	for(int i = 1; i <= numTimeslots; i++) {
	   
	    //find available timeslot for room
	    t = roomTimeslots[i];
	    if(!r.available(i)) {
		continue;
	    }
	    //System.out.println("class1 " + c);
	     //professors[3].printAvailableTimes();
	     //System.out.println(" b " + professors[3].getAvailableTimes());
	    //check if professor is available at this time
	    
	    //System.out.println("c " + c);
	    if(p.getID()==2) {
		//p.printAvailableTimes();
	    }
	    available = p.available(t);
	    //System.out.println(available);
	    //System.out.println("class2 " + c);
	       //professors[3].printAvailableTimes();
	    
	    if(available) {
		//System.out.println(c);
		//p.printAvailableTimes();
		r.removeTime(t);
		p.removeTime(t);
		//System.out.println("p " + p);
		//p.printAvailableTimes();
		c.setTime(t);
		c.setRoom(r);
		//if there are no more available slots, remove room from list
		if(r.getNumRemoved() == numTimeslots) {
		    //rooms[r.getID()] = null;
		    //change index of max instead
		    r.setUnavailable();
		    currentLargestRoom++;
		}
	
		return true;
	    }
	}
	System.out.println("sad");
	return false;
    }
    
    public static void makeSchedule() {
	//for(int i = 0; i < rooms.length; i++) {
	//System.out.print(rooms[i] + ", ");
	//}
	
	boolean success;
	Room r;
	Classes c;
	int roomID;
	int nextRoom;
	
	//for(Classes c : classes) {
	for(int i = 0; i < numClasses; i++) {
	    c = classes[i];
	    r = rooms[0];
	    success = false;
	    //int j = 0;
	    nextRoom = currentLargestRoom;
	    r = rooms[currentLargestRoom];
	    // while(!success && r.getID() <= numRooms) {
	    while(!success && r.getID() != 0) {
		//j = 0;
		
		//r = rooms[j];
		
		//keep track of index of max room instead
		/*while(!r.hasAvailableTimeslots()){
		    j++;
		    r = rooms[j];
		}
		System.out.println("j " + j);
		System.out.println("id " + r.getID());*/
		success = scheduleClass(c, r);
		//System.out.println(success);
		//try next room
		if(!success) {
		    nextRoom++;
		    r = rooms[nextRoom];
		//  roomID = r.getID();
		    //System.out.println(roomID+1);
		//  r = rooms[roomID + 1];
		    //System.out.println("here");
		  }
		    //System.out.println("room " + r);
		    //System.out.println("index " + 
	    }
	}
    }
}
