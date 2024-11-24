package Data;

import java.util.HashMap;
import java.util.Map;

public class StudentRepository {
    private Map<String, Student> studentDatabase = new HashMap<>();

    public void saveStudent(Student student) {
        studentDatabase.put(student.getStudentId(), student);
    }

    public Student getStudent(String studentId) {
        return studentDatabase.get(studentId);
    }

    public void deleteStudent(String studentId) {
        studentDatabase.remove(studentId);
    }

    public void displayAllStudents() {
        if (studentDatabase.isEmpty()) {
            System.out.println("No students found.");
        } else {
            studentDatabase.values().forEach(System.out::println);
        }
    }
}
