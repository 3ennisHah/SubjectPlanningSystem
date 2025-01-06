package Data;

import java.util.*;
import java.util.stream.Collectors;

public class Student {
    private int id;
    private String name;
    private String cohort; // Cohort information (e.g., 202404)
    private String status; // Student status (e.g., Active, Withdrawn)
    private String country; // Student's country
    private boolean isInternational; // Whether the student is an international student
    private List<DatabaseSubject> allSubjects; // All subjects the student has taken
    private List<DatabaseSubject> failingSubjects; // Failing subjects from the database
    private List<Subject> completedSubjects; // Subjects the student has completed
    private int currentSemester; // Semester directly from the database
    private int algorithmSemester; // Semester used for the genetic algorithm
    private List<String> requiredElectives; // Electives the student is required to take

    // Constructor
    public Student(int id, String name, String cohort, String status, String country, boolean isInternational,
                   List<DatabaseSubject> allSubjects, int currentSemester) {
        this.id = id;
        this.name = name;
        this.cohort = cohort;
        this.status = status;
        this.country = country;
        this.isInternational = isInternational;
        this.allSubjects = allSubjects;
        this.currentSemester = currentSemester; // Directly from the database
        this.algorithmSemester = currentSemester + 1; // Assume next semester unless overridden
        this.failingSubjects = new ArrayList<>();
        this.completedSubjects = new ArrayList<>();
        this.requiredElectives = new ArrayList<>(); // Initialize required electives

        // Populate failing and completed subjects
        for (DatabaseSubject dbSubject : allSubjects) {
            if (dbSubject.isFailingGrade()) {
                this.failingSubjects.add(dbSubject);
            } else {
                Subject completedSubject = Subject.valueOf(dbSubject.getSubjectCode());
                if (completedSubject != null) {
                    completedSubjects.add(completedSubject);
                    completedSubject.setCompleted(true); // Mark as completed
                } else {
                    System.out.println("[WARN] Subject not found in registry: " + dbSubject.getSubjectCode());
                }
            }
        }

        // Populate required electives (Placeholder logic; replace with actual requirements)
        populateRequiredElectives();
    }

    // ******** New Methods ******** //

    /**
     * Checks if a semester is a short semester for this student based on the semester index.
     */
    public boolean isShortSemester(int semesterIndex) {
        String semesterMonth = getSemesterMonth(semesterIndex);
        return semesterMonth.equalsIgnoreCase("January");
    }

    /**
     * Gets the semester month based on the semester index and the student's cohort.
     */
    public String getSemesterMonth(int semesterIndex) {
        List<String> semesterCycle = List.of("January", "March", "August");
        String intakeMonth = extractIntakeMonthFromCohort();
        int startMonthIndex = semesterCycle.indexOf(intakeMonth);

        if (startMonthIndex == -1) {
            throw new IllegalArgumentException("Invalid cohort or intake month: " + cohort);
        }

        int offset = semesterIndex % semesterCycle.size();
        return semesterCycle.get((startMonthIndex + offset) % semesterCycle.size());
    }

    /**
     * Extracts the intake month from the cohort string.
     */
    private String extractIntakeMonthFromCohort() {
        switch (cohort.substring(4, 6)) {
            case "01":
                return "January";
            case "04":
                return "March";
            case "09":
                return "August";
            default:
                throw new IllegalArgumentException("Unknown intake month in cohort: " + cohort);
        }
    }

    /**
     * Populates the required electives for this student (placeholder logic, replace with actual requirements).
     */
    private void populateRequiredElectives() {
        // Placeholder electives based on semester or program requirements
        this.requiredElectives.add("Elective1");
        this.requiredElectives.add("Elective2");
        this.requiredElectives.add("Elective3");
        this.requiredElectives.add("Elective4");
    }

    /**
     * Gets the required electives for the student.
     */
    public List<String> getRequiredElectives() {
        return this.requiredElectives;
    }

    // ******** Existing Methods ******** //

    // Method to check if the student is on track
    public boolean isOnTrack(Map<String, List<Subject>> baseLineup) {
        // Collect the subject codes of completed subjects
        Set<String> completedSubjectCodes = this.completedSubjects.stream()
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());

        List<String> semesterKeys = new ArrayList<>(baseLineup.keySet());
        Collections.sort(semesterKeys); // Ensure semesters are in the correct order

        // Iterate through all semesters prior to the current semester
        for (int semesterIndex = 0; semesterIndex < currentSemester - 1; semesterIndex++) {
            String semesterKey = semesterKeys.get(semesterIndex);
            List<Subject> semesterSubjects = baseLineup.get(semesterKey);

            for (Subject subject : semesterSubjects) {
                // Ignore electives when checking progression
                if (!subject.isElective() && !completedSubjectCodes.contains(subject.getSubjectCode())) {
                    System.out.println("[DEBUG] Missing Subject from Prior Semesters: " + subject.getSubjectCode());
                    return false; // Student is not on track
                }
            }
        }

        return true; // Student is on track
    }

    // Helper method to map the cohort to the program lineup key
    public String getCohortKey() {
        switch (cohort) {
            case "202401":
                return "BCS2024January";
            case "202404":
                return "BCS2024March";
            case "202409":
                return "BCS2024August";
            default:
                return null;
        }
    }

    // Getter for algorithmSemester
    public int getAlgorithmSemester() {
        return algorithmSemester;
    }

    // Setter for algorithmSemester
    public void setAlgorithmSemester(int algorithmSemester) {
        this.algorithmSemester = algorithmSemester;
    }

    // Getter for cohort
    public String getCohort() {
        return cohort;
    }

    // Getter for current semester
    public String getCurrentSemester() {
        return "Semester " + currentSemester; // Directly return the semester from the database
    }

    public List<Subject> getFailingSubjectsAsSubjects() {
        return failingSubjects.stream()
                .map(dbSubject -> Subject.valueOf(dbSubject.getSubjectCode()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ******** Getters ******** //
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public List<DatabaseSubject> getAllSubjects() {
        return allSubjects;
    }

    public List<DatabaseSubject> getFailingSubjects() {
        return failingSubjects;
    }

    public List<Subject> getCompletedSubjects() {
        return completedSubjects;
    }

    // Setter for completed subjects
    public void setCompletedSubjects(List<Subject> completedSubjects) {
        this.completedSubjects = completedSubjects;
    }

    // ******** String Representation ******** //
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Cohort: ").append(cohort).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Country: ").append(country).append("\n");
        sb.append("International: ").append(isInternational ? "Yes" : "No").append("\n");
        sb.append("Current Semester: ").append(currentSemester).append("\n");
        sb.append("All Subjects:\n");
        for (DatabaseSubject subject : allSubjects) {
            sb.append("  - ").append(subject).append("\n");
        }
        sb.append("Failing Subjects:\n");
        for (DatabaseSubject subject : failingSubjects) {
            sb.append("  - ").append(subject).append("\n");
        }
        sb.append("Completed Subjects:\n");
        for (Subject subject : completedSubjects) {
            sb.append("  - ").append(subject).append("\n");
        }
        return sb.toString();
    }
}
