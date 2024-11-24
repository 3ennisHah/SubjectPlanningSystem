package Data;

import java.util.HashMap;
import java.util.Map;

public class SubjectRepository {
    private Map<String, Subject> subjectDatabase = new HashMap<>();

    // Create or Update Subject
    public void saveSubject(Subject subject) {
        subjectDatabase.put(subject.getSubjectCode(), subject);
    }

    // Retrieve Subject by Code
    public Subject getSubject(String subjectCode) {
        return subjectDatabase.get(subjectCode);
    }

    // Delete Subject by Code
    public void deleteSubject(String subjectCode) {
        subjectDatabase.remove(subjectCode);
    }

    // Display All Subjects
    public void displayAllSubjects() {
        if (subjectDatabase.isEmpty()) {
            System.out.println("No subjects found.");
        } else {
            subjectDatabase.values().forEach(System.out::println);
        }
    }
}
