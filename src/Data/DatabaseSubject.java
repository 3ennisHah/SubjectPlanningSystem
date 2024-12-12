package Data;

import java.util.Map;

public class DatabaseSubject {
    private String subjectCode;
    private String subjectName;
    private String grade;
    private String semester;

    // Constructor
    public DatabaseSubject(String subjectCode, String subjectName, String grade) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.grade = grade;
    }

    // Static factory method to create DatabaseSubject from a map
    public static DatabaseSubject fromMap(Map<String, String> subjectMap) {
        return new DatabaseSubject(
                subjectMap.get("subjectCode"),
                subjectMap.get("subjectName"),
                subjectMap.get("grade")
        );
    }

    // Check if the grade is a failing grade
    public boolean isFailingGrade() {
        return "F".equalsIgnoreCase(grade) || "F#".equalsIgnoreCase(grade) || "F*".equalsIgnoreCase(grade);
    }

    // Getters and Setters
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getGrade() {
        return grade;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return subjectName + " (" + subjectCode + ") - Grade: " + grade;
    }
}
