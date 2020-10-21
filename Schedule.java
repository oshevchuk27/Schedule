import java.util.*;
import java.io.*;

public class Schedule {
    
    static int numClasses;
    static int numTimeslots;
    static Classes[] classes;
    static Room[] rooms;
    static Student[] students;
    static Professor[] professors;
    
    public static void main(String[] args) throws FileNotFoundException {
	readConstraints("demo_constraints.txt");
	readPreferences("demo_studentprefs.txt");
	setTimes();
	
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
	int numRooms = constraints.nextInt();
	System.out.println("rooms: " + numRooms);

	
	rooms = new Room[numRooms+1];
	rooms[0] = new Room(0,0);
	int size;
	for(int i = 1; i <=  numRooms; i++) {
	    constraints.nextInt();
	    size = constraints.nextInt();
	    rooms[i] = new Room(i, size);
	    
	}
	
	for(int i = 1; i <= numRooms; i++) {
	    System.out.println(rooms[i]);
	}
	
	Arrays.sort(rooms, Collections.reverseOrder());
	
	for(int i = 1; i <= numRooms; i++) {
	    System.out.println(rooms[i]);
	}

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
	professors[0] = new Professor(0);

	int professor;
	Classes c;
	Professor p;
	for(int i = 1; i <=  numClasses; i++) {
	    constraints.nextInt();
	    professor = constraints.nextInt();
	    c = new Classes(i);
	    p = new Professor(professor);
	    c.setProfessor(p);
	    classes[i] = c;
	    professors[professor] = p;
	}
	
	for(int i = 1; i <= numClasses; i++) {
	    System.out.println(classes[i]);
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
	    for(int j = 0; j < 4; j++) {
		c = classes[preferences.nextInt()];
		//prefList[j] = preferences.nextInt();
		prefList[j] = c;
		//classes[prefList[j]].incPopularity();
		classes[c.getID()].incPopularity();
	    }
	    students[i] = new Student(i, prefList);
	}

	for(int i = 1; i <= numStudents; i++) {
	    System.out.println(students[i]);
	}

	Arrays.sort(classes, Collections.reverseOrder());

	for(int i = 0; i < numClasses; i++) {
	    System.out.println(classes[i]);
	}
    }

    public static void setTimes() {
	int[] timeslots = new int[numTimeslots + 1];
	//need to fix so each professor has their own list
	LinkedList<Integer> ts = new LinkedList<Integer>();
	timeslots[0] = 0;
	for(int i = 1; i <= numTimeslots; i++) {
	    timeslots[i] = i;
	    ts.add(i);
	}
	for(Room room : rooms) {
	    room.setAvailableTimes(timeslots);
	}
	for(Professor professor : professors) {
	    //professor.setAvailableTimes(timeslots);
	    professor.setAvailableTimes(ts);
	}
	for(int i = 1; i < professors.length; i++) {
	    System.out.println(professors[i]);
	}
	System.out.println(professors[4]);
	professors[4].removeTime(2);
	for(int i = 1; i < professors.length; i++) {
	    System.out.println(professors[i]);
	}

    }
}
