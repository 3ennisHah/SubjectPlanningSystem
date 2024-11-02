import Data.Student;
import Data.Subject;
import SubjectPlan.SubjectPlanner;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Sample subjects (code, name, credit hours, prerequisites, isCore)
        Subject math = new Subject("MATH101", "Mathematics", 3, new String[]{}, true);
        Subject physics = new Subject("PHYS101", "Physics", 4, new String[]{"MATH101"}, true);
        Subject chemistry = new Subject("CHEM101", "Chemistry", 3, new String[]{}, true);
        Subject biology = new Subject("BIO101", "Biology", 3, new String[]{"CHEM101"}, false);

        List<Subject> availableSubjects = Arrays.asList(math, physics, chemistry, biology);

        // Sample student
        Student student = new Student("S12345", "John Doe");
        student.addCompletedSubject(math);  // Assume student has completed math

        // Initialize Subject Planner and run genetic algorithm
        SubjectPlanner subjectPlanner = new SubjectPlanner();
        subjectPlanner.planSubjects(student, availableSubjects);
    }
}

