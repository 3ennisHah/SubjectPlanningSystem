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
    private String currentSemester; // Current semester of the student (e.g., Semester 3)
    private int algorithmSemester;

    // Constructor
    public Student(int id, String name, String cohort, String status, String country, boolean isInternational, List<DatabaseSubject> allSubjects) {
        this.id = id;
        this.name = name;
        this.cohort = cohort;
        this.status = status;
        this.country = country;
        this.isInternational = isInternational;
        this.allSubjects = allSubjects;
        this.failingSubjects = new ArrayList<>();
        this.completedSubjects = new ArrayList<>();
        this.currentSemester = "Unknown"; // Default to "Unknown"

        // Identify failing subjects
        for (DatabaseSubject dbSubject : allSubjects) {
            if (dbSubject.isFailingGrade()) {
                this.failingSubjects.add(dbSubject);
            }
        }
    }

    // Method to check if the student is on track
    public boolean isOnTrack(Map<String, List<Subject>> baseLineup) {
        Set<String> completedSubjectCodes = this.completedSubjects.stream()
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());
        List<String> semesterKeys = new ArrayList<>(baseLineup.keySet());

        // Iterate through all semesters prior to the current semester
        for (int semesterIndex = 0; semesterIndex < getNumericCurrentSemester() - 1; semesterIndex++) {
            String semesterKey = semesterKeys.get(semesterIndex);
            List<Subject> semesterSubjects = baseLineup.get(semesterKey);

            for (Subject subject : semesterSubjects) {
                // Ignore electives when checking progression
                if (!subject.isElective() && !completedSubjectCodes.contains(subject.getSubjectCode())) {
                    System.out.println("[DEBUG] Missing Subject from Prior Semesters: " + subject.getSubjectCode());
                    return false;
                }
            }
        }
        return true; // Student is on track
    }

    // Method to convert the current semester string to a numeric value for comparison
    private int getNumericCurrentSemester() {
        if (currentSemester == null || currentSemester.equalsIgnoreCase("Unknown")) {
            return 1; // Default to semester 1 if unknown
        }

        try {
            return Integer.parseInt(currentSemester.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Failed to parse current semester: " + currentSemester + ". Defaulting to semester 1.");
            return 1;
        }
    }

    // Helper method to map the cohort to the programme lineup key
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
        Map<String, List<Subject>> cohortPlan = LineupManager.getLineupForCohort(getCohortKey(), isInternational());
        if (cohortPlan == null || cohortPlan.isEmpty()) {
            return "Unknown";
        }

        int semesterIndex = 1; // Default semester 1
        for (Map.Entry<String, List<Subject>> entry : cohortPlan.entrySet()) {
            List<Subject> subjects = entry.getValue();

            // Check if all subjects in the semester are completed
            boolean completedAll = subjects.stream()
                    .allMatch(subject -> completedSubjects.stream()
                            .anyMatch(completed -> completed.getSubjectCode().equals(subject.getSubjectCode())));
            if (completedAll) {
                semesterIndex++;
            } else {
                break;
            }
        }

        // Clamp semesterIndex to the valid range of semesters
        int maxSemesters = cohortPlan.size();
        semesterIndex = Math.min(semesterIndex, maxSemesters);

        return "Semester " + semesterIndex;
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

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
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
