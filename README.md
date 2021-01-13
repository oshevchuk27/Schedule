README.txt

Group Project: The Registrar's Problem
Collaborators: Olga Shevchuk - Sarah Coufal - Nitisha Bhandari


Description and Analysis:
________________

We all know that getting into classes is extremely stressful and time consuming process. The Registrar's Office has to work hard to create a semester schedule that would fit the availability of professors and classrooms? But what if they actually took student preferences into consideration before assigning classes? We explore this question by creating an algorithm that enrolls students into classes based on student preferences after it had already assigned classes to rooms and professors. We consider a popularity-driven approach that assigns the most popular classes (those chosen by the bigger number of students as evident from their class preference lists) to the largest available rooms to maximize the class capacity. Our aim is to enable as many students as possible to get into the classes they want. The algorithm is adjusted to fit the real Bryn Mawr College semester registration data and considers several real-world registration scenarios. However, some of the cases are not considered such as assigning certain types of classes to designated academic buildings which would complicate the process of getting more students satisfied. The optimality of the algorithm is measured by the value of the student preferences which is calculated by awarding 1 point for each class on the student preference list that the student ends up enrolled in. This value is compared to the best case student value which is the count of all the classes on the student preference lists (the situation when every student gets all the classes on their preference lists). The measure is the percentage the algorithms's value of the student preferences is of the best case student value. Despite all the limitations of our algorithm, it manages to achieve the student preference value in the range of 81-92% when run on different Bryn Mawr College semester files. At the same time, when given a large input, the algorithm runs nearly instantenously achieving an almost perfect quadratic runtime.

__________________


Main src files:
________________
- Classes.java
- Professor.java
- Room.java
- Schedule.java
- Student.java
- Timeslot.java
________________

How to compile:

$javac *.java

________________

How to run:

$java Schedule <constraints file> <prefs file> <schedule file>
  
________________  
 
