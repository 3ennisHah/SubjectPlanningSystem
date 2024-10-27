import Data.Student;
import Data.Subject;
import SubjectPlan.SubjectPlanner;

public class Main {
    public static void main(String[] args) {
        // Create sample subjects (in practice, you might load these from a database)
        Subject math = new Subject("MATH101", "Mathematics", 3, new String[]{});
        Subject physics = new Subject("PHYS101", "Physics", 4, new String[]{"MATH101"});
        Subject chemistry = new Subject("CHEM101", "Chemistry", 3, new String[]{});
        Subject biology = new Subject("BIO101", "Biology", 3, new String[]{"CHEM101"});

        // Create a student
        Student student = new Student("S12345", "John Doe");
        student.addCompletedSubject(math); // Assume the student has completed math

        // Create and run the subject planner using the Genetic Algorithm
        SubjectPlanner subjectPlanner = new SubjectPlanner();
        subjectPlanner.planSubjects(student);

        // The output will display the best subject plan found
    }
}
