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
    private boolean directEntry;
    private boolean mathRequirement;
    private String enrollmentYear;
    private String enrollmentIntake;
    private int currentSemester; // New Field: Current Semester

    public Student(String studentId, String name, String enrollmentYear, String enrollmentIntake, int currentSemester) {
        this.studentId = studentId;
        this.name = name;
        this.completedSubjects = new ArrayList<>();
        this.failedSubjects = new ArrayList<>();
        this.directEntry = false;
        this.mathRequirement = false;
        this.enrollmentYear = enrollmentYear;
        this.enrollmentIntake = enrollmentIntake;
        this.currentSemester = currentSemester; // Initialize current semester
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
        if (subject.getSubjectCode().equals("MTH1114")) {
            this.mathRequirement = true; // Math requirement fulfilled
        }
    }

    public List<Subject> getFailedSubjects() {
        return failedSubjects;
    }



    public boolean hasMathRequirement() {
        return mathRequirement;
    }

    public String getEnrollmentYear() {
        return enrollmentYear;
    }

    public String getEnrollmentIntake() {
        return enrollmentIntake;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public int determineCurrentSemester(Map<String, List<Subject>> baseLineup) {
        Set<String> completedSubjectCodes = this.getCompletedSubjectCodes();
        int semesterIndex = 0;

        for (Map.Entry<String, List<Subject>> entry : baseLineup.entrySet()) {
            List<Subject> semesterSubjects = entry.getValue();
            boolean allSubjectsCompleted = semesterSubjects.stream()
                    .allMatch(subject -> completedSubjectCodes.contains(subject.getSubjectCode()));

            // If all subjects in a semester are completed, move to the next semester
            if (allSubjectsCompleted) {
                semesterIndex++;
            } else {
                // If not all subjects are completed, the current semester is found
                break;
            }
        }

        return semesterIndex + 1; // Return the current semester as a 1-based index
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public boolean isOnTrack(Map<String, List<Subject>> baseLineup) {
        Set<String> completedSubjectCodes = this.getCompletedSubjectCodes();
        List<String> semesterKeys = new ArrayList<>(baseLineup.keySet());

        // Check all semesters prior to the current semester
        for (int semesterIndex = 0; semesterIndex < currentSemester - 1; semesterIndex++) {
            String semesterKey = semesterKeys.get(semesterIndex);
            List<Subject> semesterSubjects = baseLineup.get(semesterKey);

            for (Subject subject : semesterSubjects) {
                if (!completedSubjectCodes.contains(subject.getSubjectCode())) {
                    System.out.println("Missing Subject from Prior Semesters: " + subject.getSubjectCode());
                    return false; // Not on track if a required subject from prior semesters is missing
                }
            }
        }

        System.out.println("Current semester is Semester " + currentSemester + ". Skipping strict validation.");
        return true; // On track if all prior semesters are completed
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
                ", currentSemester=" + currentSemester +
                '}';
    }

    public static Student getStudentByChoice(int choice, Map<String, List<Subject>> baseLineup) {
        Student student = null;

        switch (choice) {
            case 1: // Alice Perfect
                student = new Student("S1001", "Alice Perfect", "2024", "January", 5); // Current semester is 5
                student.addCompletedSubject(Subject.CSC1024);
                student.addCompletedSubject(Subject.MPU3193);
                student.addCompletedSubject(Subject.MPU3183);
                student.addCompletedSubject(Subject.MAT1013);
                student.addCompletedSubject(Subject.ENG1044);
                student.addCompletedSubject(Subject.CSC1202);
                student.addCompletedSubject(Subject.MTH1114);
                student.addCompletedSubject(Subject.PRG1203);
                student.addCompletedSubject(Subject.SEG1201);
                student.addCompletedSubject(Subject.NET1014);
                student.addCompletedSubject(Subject.CSC2104);
                student.addCompletedSubject(Subject.WEB1201);
                student.addCompletedSubject(Subject.MPU3222);
                student.addCompletedSubject(Subject.MPU3412);
                student.addCompletedSubject(Subject.SEG2202);
                student.addCompletedSubject(Subject.KIAR);
                break;

            // Add other student cases here
        }

        return student;
    }
}
