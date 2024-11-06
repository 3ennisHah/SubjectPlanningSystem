import Data.Student;
import Data.Subject;
import SubjectPlan.SubjectPlanner;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Sample subjects with subject code, name, credit hours, prerequisites, and core status
        Subject math = new Subject("MATH101", "Mathematics", 3, new String[]{}, true);
        Subject physics = new Subject("PHYS101", "Physics", 4, new String[]{"MATH101"}, true);
        Subject chemistry = new Subject("CHEM101", "Chemistry", 3, new String[]{}, true);
        Subject biology = new Subject("BIO101", "Biology", 3, new String[]{"CHEM101"}, false);
        Subject programming = new Subject("CS101", "Programming", 4, new String[]{}, true);
        Subject dataStructures = new Subject("CS102", "Data Structures", 4, new String[]{"CS101"}, true);

        // Create a list of all available subjects
        List<Subject> availableSubjects = Arrays.asList(math, physics, chemistry, biology, programming, dataStructures);

        // Initialize a sample student
        Student student = new Student("S12345", "John Doe");
        student.addCompletedSubject(math);  // Assume student has completed math
        student.addCompletedSubject(programming);  // Assume student has completed programming

        // Print student's academic record for reference
        System.out.println("Student: " + student.getName());
        System.out.println("Completed Subjects: " + student.getCompletedSubjects());

        // Initialize the SubjectPlanner and run the genetic algorithm
        SubjectPlanner subjectPlanner = new SubjectPlanner();
        System.out.println("Running genetic algorithm to plan subjects...");
        subjectPlanner.planSubjects(student, availableSubjects);

        // Print the result
        System.out.println("Subject planning completed.");
    }
}

