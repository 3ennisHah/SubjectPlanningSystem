package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Student {
    private String studentId;
    private String name;
    private List<Subject> completedSubjects;
    private List<Subject> failedSubjects;
    private boolean directEntry; // Direct entry flag
    private boolean mathRequirement; // Math requirement flag
    private String enrollmentYear; // Year of enrollment
    private String enrollmentIntake; // Intake (e.g., January, March, August)

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.completedSubjects = new ArrayList<>();
        this.failedSubjects = new ArrayList<>();
        this.directEntry = false;
        this.mathRequirement = false;
        this.enrollmentYear = "";
        this.enrollmentIntake = "";
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getCompletedSubjects() {
        return completedSubjects;
    }

    public void addCompletedSubject(Subject subject) {
        completedSubjects.add(subject);
    }

    public List<Subject> getFailedSubjects() {
        return failedSubjects;
    }

    public void addFailedSubject(Subject subject) {
        failedSubjects.add(subject);
    }

    public boolean isDirectEntry() {
        return directEntry;
    }

    public void setDirectEntry(boolean directEntry) {
        this.directEntry = directEntry;
    }

    public boolean hasMathRequirement() {
        return mathRequirement;
    }

    public void setMathRequirement(boolean mathRequirement) {
        this.mathRequirement = mathRequirement;
    }

    public String getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(String enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public String getEnrollmentIntake() {
        return enrollmentIntake;
    }

    public void setEnrollmentIntake(String enrollmentIntake) {
        this.enrollmentIntake = enrollmentIntake;
    }

    // Check if a subject is already completed
    public boolean hasCompleted(String subjectCode) {
        return completedSubjects.stream()
                .anyMatch(subject -> subject.getSubjectCode().equals(subjectCode));
    }

    public boolean hasPerfectRecord(Map<String, List<Subject>> baseLineup) {
        for (List<Subject> semester : baseLineup.values()) {
            for (Subject subject : semester) {
                if (!completedSubjects.contains(subject)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Set<String> getCompletedSubjectCodes() {
        return completedSubjects.stream()
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());
    }


    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", completedSubjects=" + completedSubjects +
                ", failedSubjects=" + failedSubjects +
                ", directEntry=" + directEntry +
                ", mathRequirement=" + mathRequirement +
                ", enrollmentYear='" + enrollmentYear + '\'' +
                ", enrollmentIntake='" + enrollmentIntake + '\'' +
                '}';
    }

    // Example Students
    public static Student getStudentByChoice(int choice) {
        Student student = null;

        switch (choice) {
            case 1:
                student = new Student("S1001", "Alice Perfect");
                student.setEnrollmentYear("2024");
                student.setEnrollmentIntake("January");
                student.addCompletedSubject(Subject.CSC1024);
                student.addCompletedSubject(Subject.CSC1202);
                student.addCompletedSubject(Subject.MTH1114);
                student.addCompletedSubject(Subject.PRG1203);
                student.addCompletedSubject(Subject.NET1014);
                student.addCompletedSubject(Subject.SEG1201);
                student.addCompletedSubject(Subject.CSC2104);
                break;
            case 2:
                student = new Student("S1002", "Bob NoMath");
                student.setEnrollmentYear("2024");
                student.setEnrollmentIntake("MathMarch");
                student.addCompletedSubject(Subject.CSC1024);
                student.addCompletedSubject(Subject.CSC1202);
                student.addFailedSubject(Subject.MTH1114);
                break;
            case 3:
                student = new Student("S1003", "Charlie Retry");
                student.setEnrollmentYear("2024");
                student.setEnrollmentIntake("August");
                student.addCompletedSubject(Subject.CSC1024);
                student.addCompletedSubject(Subject.CSC1202);
                student.addCompletedSubject(Subject.MTH1114);
                student.addCompletedSubject(Subject.PRG1203);
                student.addFailedSubject(Subject.CSC2104);
                break;
            default:
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                System.exit(1);
        }

        return student;
    }
}
