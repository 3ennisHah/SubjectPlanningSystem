package Data;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private List<Subject> completedSubjects;
    private List<Subject> enrolledSubjects;
    private List<Subject> failedSubjects;
    private boolean directEntry; // True if direct entry, false otherwise
    private boolean mathRequirement; // True if math requirement is met, false otherwise

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.completedSubjects = new ArrayList<>();
        this.enrolledSubjects = new ArrayList<>();
        this.failedSubjects = new ArrayList<>();
        this.directEntry = false; // Default: not direct entry
        this.mathRequirement = true; // Default: math requirement met
    }

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

    public List<Subject> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public void addEnrolledSubject(Subject subject) {
        enrolledSubjects.add(subject);
    }

    public List<Subject> getFailedSubjects() {
        return failedSubjects;
    }

    public void addFailedSubject(Subject subject) {
        failedSubjects.add(subject);
    }

    public boolean hasCompleted(String subjectCode) {
        return completedSubjects.stream().anyMatch(subject -> subject.getSubjectCode().equals(subjectCode));
    }

    public boolean hasFailed(String subjectCode) {
        return failedSubjects.stream().anyMatch(subject -> subject.getSubjectCode().equals(subjectCode));
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

    public String getDirectEntryStatus() {
        return directEntry ? "Y" : "N";
    }

    public String getMathRequirementStatus() {
        return mathRequirement ? "Y" : "N";
    }

    // Examples of students with different circumstances
    public static List<Student> getExampleStudents() {
        List<Student> students = new ArrayList<>();

        // Student 1: Perfect Student
        Student student1 = new Student("S1001", "Alice Perfect");
        student1.addCompletedSubject(Subject.CSC1024);
        student1.addCompletedSubject(Subject.CSC1202);
        student1.addCompletedSubject(Subject.MTH1114);
        student1.addCompletedSubject(Subject.PRG1203);
        student1.addCompletedSubject(Subject.NET1014);
        student1.addCompletedSubject(Subject.SEG1201);
        student1.addCompletedSubject(Subject.CSC2104);
        student1.setDirectEntry(false); // Not a direct entry student
        student1.setMathRequirement(true); // Completed math requirement
        students.add(student1);

        // Student 2: No Math Background
        Student student2 = new Student("S1002", "Bob NoMath");
        student2.addCompletedSubject(Subject.CSC1024);
        student2.addCompletedSubject(Subject.CSC1202);
        student2.addCompletedSubject(Subject.PRG1203);
        student2.addCompletedSubject(Subject.NET1014);
        student2.addFailedSubject(Subject.MTH1114); // Failed math subject
        student2.setDirectEntry(false); // Not a direct entry student
        student2.setMathRequirement(false); // Did not complete math requirement
        students.add(student2);

        // Student 3: Failed a Subject
        Student student3 = new Student("S1003", "Charlie Retry");
        student3.addCompletedSubject(Subject.CSC1024);
        student3.addCompletedSubject(Subject.CSC1202);
        student3.addCompletedSubject(Subject.MTH1114);
        student3.addCompletedSubject(Subject.PRG1203);
        student3.addCompletedSubject(Subject.NET1014);
        student3.addFailedSubject(Subject.CSC2104); // Failed core subject
        student3.setDirectEntry(true); // Direct entry student
        student3.setMathRequirement(true); // Completed math requirement
        students.add(student3);

        return students;
    }
}
