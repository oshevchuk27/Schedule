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

    //hash maps with key ID value index of array
    static HashMap<String, Integer> roomHash = new HashMap<>();
    static HashMap<String, Integer> professorHash = new HashMap<>();
    static HashMap<String, Integer> classHash = new HashMap<>();
    static HashMap<String, Integer> studentHash = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
	try {
	    long startTime = System.nanoTime();
	    
	    //read file names from command line
	    String constraintsFile = args[0];
	    String prefsFile = args[1];
	    String scheduleFile = args[2];
	    
	    readConstraints(constraintsFile);
	    readPreferences(prefsFile);
	    setTimes();
	    makeSchedule();
	    scheduleStudents();
	    writeScheduleFile(scheduleFile);
	    
	    long endTime = System.nanoTime();
	    double duration = (endTime - startTime)/ 1e6;
	    
	    System.out.println(duration);
	}
	catch(IndexOutOfBoundsException n) {
	    System.out.println("Usage:");
	    System.out.println("<constraints file> <prefs file> <schedule file>");
	}
	catch(NoSuchElementException n) {
	    System.out.println("Usage:");
	    System.out.println("<constraints file> <prefs file> <schedule file>");
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
	int roomIndex = 1;
	rooms = new Room[numRooms+1];
	rooms[0] = new Room("",0);
	int size;
	String roomID;
	for(int i = 1; i <=  numRooms; i++) {
	    roomID = constraints.next();
	    size = constraints.nextInt();
	    roomHash.put(roomID, roomIndex);
	    rooms[roomIndex] = new Room(roomID, size);
	    roomIndex++;
	}

	//sort list from largest to smallest room
	Arrays.sort(rooms, Collections.reverseOrder());

	while(!constraints.hasNextInt()) {
	    constraints.next();
	}

	numClasses = constraints.nextInt();
	classes = new Classes[numClasses+1];
	classes[0] = new Classes("");
	System.out.println("classes: " + numClasses);

	while(!constraints.hasNextInt()) {
	    constraints.next();
	}
	int numProfessors = constraints.nextInt();

	System.out.println("professors: " + numProfessors);
	professors = new Professor[numProfessors + 1];
	professors[0] = new Professor("",numTimeslots);

	Classes c;
	Professor p;
	String profID;
	String classID;
	int profIndex = 1;
	int classIndex = 1;
	int pIndex;
   

	//set professors to classes
	for(int i = 1; i <=  numClasses; i++) {
	    classID = constraints.next();
	    profID = constraints.next();
	    c = new Classes(classID);
	    //if professor has not yet been processed
	    if(!professorHash.containsKey(profID)) {

		p = new Professor(profID,numTimeslots);
		professorHash.put(profID, profIndex);
		professors[profIndex] = p;
		profIndex++;
	  
	    } else {
		pIndex = professorHash.get(profID);
		p = professors[pIndex];
	    }
	    c.setProfessor(p);
	    classHash.put(classID, classIndex);
	    classes[classIndex] = c;
	    classIndex++;
	}
	
	constraints.close();
    }

    public static void readPreferences(String filename) throws FileNotFoundException {
	Scanner preferences = new Scanner(new File(filename));
	while(!preferences.hasNextInt()) {
	    preferences.next();
	}
	int numStudents = preferences.nextInt();
	System.out.println("students: " + numStudents);
	students = new Student[numStudents + 1];
	students[0] = new Student("",null);

	Classes c;
	String studentID;
	String classID;
	int classIndex;
	int studentIndex = 1;
	String line;
	preferences.nextLine();
	for(int i = 1; i <= numStudents; i++) {

	    line = preferences.nextLine();
	    
	    //splits line by spaces
	    String[] stringArray = line.split("\\s+");
	    Classes[] prefList = new Classes[stringArray.length-1];
	    
	    studentID = stringArray[0];
	    
	    for(int j = 1; j < stringArray.length; j++) {
		
		classID = stringArray[j];

		//count popularity of classes
		if(classHash.containsKey(classID)) {
		    classIndex = classHash.get(classID);
		    c = classes[classIndex];
		    prefList[j-1] = c;
		    c.incPopularity();
		}
	    }

	    //save students' preference lists
	    studentHash.put(studentID, studentIndex);
	    students[studentIndex] = new Student(studentID, prefList);
	    studentIndex++;
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
	
	for(int j = 1; j<professors.length;j++) {
	    
	    Professor professor = professors[j];
	    
	    if(professor != null) {
		
		timeslots = new int[numTimeslots + 1];
		int[] ts = professor.getAvailableTimes();
		for(int i = 0; i <= numTimeslots; i++) {
		    timeslots[i] = i;
		}
		professor.setAvailableTimes(timeslots);
	    }
	}
    }

    public static boolean scheduleClass(Classes c, Room r) {

	Professor p = c.getProfessor();
	if(p == null) {
	    return false;
	}
	int[] roomTimeslots = r.getAvailableTimes();
	boolean available;
	int t;

	for(int i = 1; i <= numTimeslots; i++) {

	    //find available timeslot for room
	    t = roomTimeslots[i];
	    if(!r.available(i)) {
		continue;
	    }

	    
	    //check if professor is available during this time
	    available = p.available(t);

	    if(available) {

		//remove time from professor and room's available times
		r.removeTime(t);
		p.removeTime(t);

		//set time and room for class
		c.setTime(t);
		c.setRoom(r);

		//if there are no more available slots, remove room from list
		if(r.getNumRemoved() == numTimeslots) {
		    currentLargestRoom++;
		}
		return true;
	    }
	}
	return false;
    }

    public static void makeSchedule() {

	boolean success;
	Room r;
	Classes c;
	int roomID;
	int nextRoom;

	for(int i = 0; i < numClasses; i++) {

	    c = classes[i];
	    success = false;
	    nextRoom = currentLargestRoom;
	    r = rooms[currentLargestRoom];

	    //while class is not schedule or we reach the end of the list
	    while(!success && !r.getID().equals("")) {

		success = scheduleClass(c, r);

		//try next room
		if(!success) {
		    nextRoom++;
		    r = rooms[nextRoom];
		}
	    }
	}
    }

    public static void scheduleStudents() {

	Student s;
	Classes[] prefList;
	Classes c;

	for(int i = 1; i < students.length; i++) {

	    s = students[i];
	    prefList = s.getPrefList();

	    //traverse student's preference list
	    for(int j = 0; j<prefList.length; j++) {

		c = prefList[j];
		if(c != null) {
		    //enroll them in class if it is not full and it doesn't conflict with their other classes
		    if(!c.isFull()) {
			if(s.available(c.getTime())) {
			    s.enroll(c);
			    c.enrollStudent(s);
			}
		    }
		}
	    }
	}
    }

    public static void writeScheduleFile(String scheduleFile) throws FileNotFoundException {
	PrintWriter writer = new PrintWriter(scheduleFile);
	writer.println("Course	Room	Teacher	Time	Students");
	for(int i = 0; i < classes.length-1; i++) {
	    writer.println(classes[i]);
	}
	writer.close();
    }
}
