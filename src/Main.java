package Main;

import Data.*;
import SubjectPlan.SubjectPlanner;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Get the example students
        Student student = Student.getExampleStudents().get(0);

        // Generate the subject plan
        SubjectPlanner planner = new SubjectPlanner();
        List<List<Subject>> subjectPlan = planner.planSubjects(student);

        // Display student information
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Intake: " + student.getEnrollmentIntake() + " " + student.getEnrollmentYear());
        System.out.println("Perfect fitness score detected. Therefore, printing out the base lineup.\n");

        // Display the subject plan
        System.out.println("Complete Subject Plan:");
        for (int semester = 1; semester <= subjectPlan.size(); semester++) {
            System.out.println("Semester " + semester + ": " + subjectPlan.get(semester - 1));
        }
    }
}
