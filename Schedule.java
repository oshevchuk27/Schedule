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
    long startTime = System.nanoTime();
    readConstraints("constraint5.txt");
    readPreferences("pref5.txt");
    setTimes();
    makeSchedule();
    scheduleStudents();
    writeScheduleFile();
    long endTime = System.nanoTime();

    double duration = (endTime - startTime)/ 1e6;
    System.out.println(duration);
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
  }

  public static boolean scheduleClass(Classes c, Room r) {

    Professor p = c.getProfessor();
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
      while(!success && r.getID() != 0) {

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

  public static void writeScheduleFile() throws FileNotFoundException {
    System.out.println("Course	Room	Teacher	Time	Students");
    for(int i = 0; i < classes.length-1; i++) {
      System.out.println(classes[i]);
    }
    PrintWriter writer = new PrintWriter("schedule.txt");
    writer.println("Course	Room	Teacher	Time	Students");
    for(int i = 0; i < classes.length-1; i++) {
      writer.println(classes[i]);
    }
    writer.close();
  }
}
