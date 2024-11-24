package Data;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private List<Subject> enrolledSubjects;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.enrolledSubjects = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public void enrollSubject(Subject subject) {
        if (!enrolledSubjects.contains(subject)) {
            enrolledSubjects.add(subject);
            System.out.println(name + " successfully enrolled in " + subject.getSubjectName());
        } else {
            System.out.println("Already enrolled in " + subject.getSubjectName());
        }
    }

    public void dropSubject(Subject subject) {
        if (enrolledSubjects.remove(subject)) {
            System.out.println(name + " successfully dropped " + subject.getSubjectName());
        } else {
            System.out.println(name + " is not enrolled in " + subject.getSubjectName());
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID='" + studentId + '\'' +
                ", Name='" + name + '\'' +
                ", EnrolledSubjects=" + enrolledSubjects +
                '}';
    }
}
