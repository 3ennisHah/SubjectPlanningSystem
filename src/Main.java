package Main;

import Data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Get the example students
        List<Student> students = Student.getExampleStudents();

        // Get Student1 (Alice Perfect)
        Student student1 = students.get(0);

        // Generate a subject plan for Student1
        System.out.println("Generating subject plan for " + student1.getName() + " (" + student1.getEnrollmentIntake() + " " + student1.getEnrollmentYear() + " Intake)");
        List<List<Subject>> subjectPlan = generateSubjectPlanForStudent(student1);

        // Output the subject plan
        System.out.println("Complete Subject Plan:");
        for (int semester = 1; semester <= subjectPlan.size(); semester++) {
            System.out.println("Semester " + semester + ": " + subjectPlan.get(semester - 1));
        }
    }

    public static List<List<Subject>> generateSubjectPlanForStudent(Student student) {
        // Identify the cohort and intake
        String cohortYear = student.getEnrollmentYear();
        String intake = student.getEnrollmentIntake();

        // Map intake to short key
        String intakeKey = mapIntakeToKey(intake);

        if (intakeKey == null) {
            System.err.println("Invalid intake: " + intake);
            return new ArrayList<>();
        }

        // Construct cohort key
        String cohort = cohortYear + intakeKey;

        // Fetch the full subject lineup for the cohort and intake
        Map<String, List<Subject>> subjectLineup = LineupManager.getLineupForCohort(cohort);

        if (subjectLineup.isEmpty()) {
            System.out.println("No subject lineup found for cohort: " + cohort);
            return new ArrayList<>();
        }

        // Debug: Print lineup to verify
        System.out.println("Lineup for " + cohort + ":");
        subjectLineup.forEach((semester, subjects) -> {
            System.out.println("  Semester: " + semester);
            subjects.forEach(System.out::println);
        });

        // Filter out completed subjects and structure by semesters
        List<List<Subject>> subjectPlan = new ArrayList<>();
        subjectLineup.forEach((semester, subjects) -> {
            List<Subject> semesterSubjects = new ArrayList<>();
            for (Subject subject : subjects) {
                if (!student.hasCompleted(subject.getSubjectCode())) {
                    semesterSubjects.add(subject);
                }
            }
            if (!semesterSubjects.isEmpty()) {
                subjectPlan.add(semesterSubjects);
            }
        });

        // Ensure plan has exactly 9 semesters
        while (subjectPlan.size() > 9) {
            subjectPlan.remove(subjectPlan.size() - 1);
        }

        return subjectPlan;
    }

    // Helper method to map intake names to keys
    private static String mapIntakeToKey(String intake) {
        switch (intake.toLowerCase()) {
            case "january":
                return "01";
            case "march":
                return "03";
            case "august":
                return "08";
            default:
                return null; // Invalid intake
        }
    }
}
