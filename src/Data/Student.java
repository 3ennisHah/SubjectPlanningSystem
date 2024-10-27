package Data;

import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private List<Subject> completedSubjects;
    private List<Subject> enrolledSubjects;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    // Getters and setters
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
}

