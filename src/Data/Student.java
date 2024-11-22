package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public static List<Student> getExampleStudents() {
        List<Student> students = new ArrayList<>();

        // Student 1: Perfect Student
        Student student1 = new Student("21345678", "Ah Meng");
        student1.addCompletedSubject(Subject.CSC1024);
        student1.addCompletedSubject(Subject.CSC1202);
        student1.addCompletedSubject(Subject.MTH1114);
        student1.addCompletedSubject(Subject.PRG1203);
        student1.addCompletedSubject(Subject.NET1014);
        student1.addCompletedSubject(Subject.SEG1201);
        student1.addCompletedSubject(Subject.CSC2104);
        student1.setDirectEntry(false);
        student1.setMathRequirement(true);
        student1.setEnrollmentYear("2024");
        student1.setEnrollmentIntake("January");
        students.add(student1);

        // Student 2: No Math Background
        Student student2 = new Student("S1002", "Bob NoMath");
        student2.addCompletedSubject(Subject.CSC1024);
        student2.addCompletedSubject(Subject.CSC1202);
        student2.addCompletedSubject(Subject.PRG1203);
        student2.addCompletedSubject(Subject.NET1014);
        student2.addFailedSubject(Subject.MTH1114);
        student2.setDirectEntry(false);
        student2.setMathRequirement(false);
        student2.setEnrollmentYear("2024");
        student2.setEnrollmentIntake("March");
        students.add(student2);

        // Student 3: Failed a Subject
        Student student3 = new Student("S1003", "Charlie Retry");
        student3.addCompletedSubject(Subject.CSC1024);
        student3.addCompletedSubject(Subject.CSC1202);
        student3.addCompletedSubject(Subject.MTH1114);
        student3.addCompletedSubject(Subject.PRG1203);
        student3.addCompletedSubject(Subject.NET1014);
        student3.addFailedSubject(Subject.CSC2104);
        student3.setDirectEntry(true);
        student3.setMathRequirement(true);
        student3.setEnrollmentYear("2024");
        student3.setEnrollmentIntake("August");
        students.add(student3);

        return students;
    }
}
