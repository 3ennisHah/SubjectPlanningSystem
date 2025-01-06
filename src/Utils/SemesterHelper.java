package Utils;

import Data.LineupManager;
import Data.Subject;
import Data.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SemesterHelper {
    public static final int MAX_CREDITS_LONG_SEMESTER = 19; // Maximum credit hours for long semesters
    public static final int MAX_CREDITS_SHORT_SEMESTER = 10; // Maximum credit hours for short semesters

    /**
     * Deep copy of the plan to prevent modifications to the original.
     */
    public static List<List<Subject>> deepCopyPlan(List<List<Subject>> originalPlan) {
        return originalPlan.stream()
                .map(semester -> new ArrayList<>(semester))
                .collect(Collectors.toList());
    }

    public static void displayPlan(String title, List<List<Subject>> plan) {
        System.out.println("========== " + title + " ==========");
        for (int i = 0; i < plan.size(); i++) {
            System.out.println("Semester " + (i + 1) + ":");
            for (Subject subject : plan.get(i)) {
                System.out.println(" - " + subject.getSubjectName() + " (" + subject.getSubjectCode() + ")");
            }
        }
        System.out.println("===================================");
    }


    /**
     * Checks if a semester is fixed and should not be modified (e.g., internship semester).
     */
    public static boolean isFixedSemester(List<Subject> semester) {
        return isInternshipSemester(semester);
    }

    /**
     * Finds the next semester where a subject can be offered starting from the given semester.
     */
    public static int findNextOfferingSemester(int currentSemesterIndex, Subject subject, Student student, List<List<Subject>> plan) {
        int nextOffering = -1;

        // Year 1 subjects: offered in March and August (long semesters only).
        if (subject.isYear1()) {
            for (int i = currentSemesterIndex + 1; i < plan.size(); i++) {
                String semesterMonth = getSemesterMonth(i, student);
                if (!semesterMonth.equals("January")) { // Skip short semesters
                    nextOffering = i;
                    break;
                }
            }
        }
        // Year 2/3 subjects: follow a 3-semester offering rule.
        else if (subject.isYear2() || subject.isYear3()) {
            int initialOfferingSemester = getInitialOfferingSemester(subject, student);
            for (int i = currentSemesterIndex + 1; i < plan.size(); i++) {
                if ((i + 1 - initialOfferingSemester) % 3 == 0) { // Check 3-semester increment
                    nextOffering = i;
                    break;
                }
            }
        }
        return nextOffering;
    }

    /**
     * Returns the maximum allowed credit hours for a given semester.
     */
    public static int getMaxCredits(int semesterIndex, Student student) {
        return isShortSemester(semesterIndex, student) ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER;
    }

    /**
     * Determines if a given semester is a short semester.
     */
    public static boolean isShortSemester(int semesterIndex, Student student) {
        String semesterMonth = getSemesterMonth(semesterIndex, student);
        return semesterMonth.equals("January");
    }

    /**
     * Checks if a subject can fit into a given semester based on credit limits and semester offerings.
     */
    public static boolean canFitInSemester(List<Subject> semester, Subject subject, int semesterIndex, Student student) {
        if (isInternshipSemester(semester)) {
            System.out.println("[DEBUG] Semester " + (semesterIndex + 1) + " is an internship semester. Cannot place other subjects.");
            return false;
        }

        int maxCredits = getMaxCredits(semesterIndex, student);
        int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
        boolean isOffered = isSemesterValidForSubject(semesterIndex + 1, subject, student);

        if (!isOffered) {
            System.out.println("[DEBUG] Semester " + (semesterIndex + 1) + " is not valid for " + subject.getSubjectCode() + ". Subject not offered in this semester.");
            return false;
        }

        if (currentCredits + subject.getCreditHours() > maxCredits) {
            System.out.println("[DEBUG] Semester " + (semesterIndex + 1) + " cannot fit " + subject.getSubjectCode() + ". Credit limit exceeded.");
            return false;
        }

        System.out.println("[DEBUG] Semester " + (semesterIndex + 1) + " can fit " + subject.getSubjectCode());
        return true;
    }

    /**
     * Determines if a semester is valid for a specific subject based on the subject's year and offerings.
     */
    public static boolean isSemesterValidForSubject(int currentSemester, Subject subject, Student student) {
        if (subject.isYear1()) {
            String semesterMonth = getSemesterMonth(currentSemester - 1, student);
            boolean isValid = !semesterMonth.equals("January"); // Valid only for March and August
            System.out.println("[DEBUG] Semester " + currentSemester + " is " + (isValid ? "valid" : "invalid") + " for Year 1 subject " + subject.getSubjectCode());
            return isValid;
        }

        if (subject.isYear2() || subject.isYear3()) {
            int initialOfferingSemester = getInitialOfferingSemester(subject, student);
            boolean isValid = currentSemester >= initialOfferingSemester &&
                    (currentSemester - initialOfferingSemester) % 3 == 0;
            System.out.println("[DEBUG] Semester " + currentSemester + " is " + (isValid ? "valid" : "invalid") +
                    " for subject " + subject.getSubjectCode() + " (Initial Offering: " + initialOfferingSemester + ")");
            return isValid;
        }

        return true; // Default case
    }

    /**
     * Retrieves the initial offering semester of a subject based on the student's cohort lineup.
     */
    private static int getInitialOfferingSemester(Subject subject, Student student) {
        // Resolve the cohort key dynamically
        String cohortKey = resolveCohortKey("BCS" + student.getCohort());

        // Get the lineup for the resolved cohort key
        Map<String, List<Subject>> lineup = LineupManager.PROGRAMME_LINEUPS.get(cohortKey);
        if (lineup == null) {
            System.out.println("[DEBUG] No lineup found for cohort: " + cohortKey);
            throw new IllegalArgumentException("No lineup available for cohort: " + cohortKey);
        }

        // Iterate through the semesters to find the first occurrence of the subject
        int semesterNumber = 1;
        for (Map.Entry<String, List<Subject>> entry : lineup.entrySet()) {
            List<Subject> subjects = entry.getValue();
            if (subjects.contains(subject)) {
                System.out.println("[DEBUG] Initial offering of subject " + subject.getSubjectCode() +
                        " found in Semester " + semesterNumber);
                return semesterNumber; // Return the first semester where the subject appears
            }
            semesterNumber++;
        }

        // If the subject is not found, throw an exception or handle as needed
        System.out.println("[DEBUG] Subject " + subject.getSubjectCode() + " not found in any semester for cohort: " + cohortKey);
        throw new IllegalArgumentException("Subject not found in cohort lineup: " + subject.getSubjectCode());
    }

    /**
     * Resolves the cohort key to match available keys in the lineup.
     */
    private static String resolveCohortKey(String cohort) {
        // Define mappings for cohorts based on prefixes or patterns
        if (cohort.equalsIgnoreCase("BCS202401")) {
            return "BCS2024January";
        } else if (cohort.equalsIgnoreCase("BCS202403")) {
            return "BCS2024March";
        } else if (cohort.equalsIgnoreCase("BCS202408")) {
            return "BCS2024August";
        }

        // Default case: return the input cohort
        return cohort;
    }



    /**
     * Attempts to displace a subject in the target semester to fit the given subject, ensuring all constraints are met.
     */
    public static boolean attemptToDisplace(List<List<Subject>> plan, Subject subjectToPlace, int targetSemesterIndex, Student student) {
        List<Subject> targetSemester = plan.get(targetSemesterIndex);

        if (isShortSemester(targetSemesterIndex, student) || isInternshipSemester(targetSemester)) {
            System.out.println("[DEBUG] Cannot displace subjects in semester " + (targetSemesterIndex + 1) + ". Invalid for displacement.");
            return false;
        }

        Subject subjectToDisplace = findSubjectToDisplace(targetSemester, subjectToPlace);
        if (subjectToDisplace == null) {
            System.out.println("[DEBUG] No suitable subject to displace in semester " + (targetSemesterIndex + 1));
            return false;
        }

        System.out.println("[DEBUG] Selected subject to displace: " + subjectToDisplace.getSubjectCode() + " from semester " + (targetSemesterIndex + 1));
        targetSemester.remove(subjectToDisplace);
        recalculateCreditHours(plan, targetSemesterIndex);

        // Find the next valid semester for the displaced subject
        int newSemesterIndex = findNextValidSemester(plan, subjectToDisplace, targetSemesterIndex, student);

        if (newSemesterIndex == -1) {
            System.out.println("[ERROR] Could not find a valid semester to move displaced subject " + subjectToDisplace.getSubjectCode());
            targetSemester.add(subjectToDisplace);
            recalculateCreditHours(plan, targetSemesterIndex);
            return false;
        }

        plan.get(newSemesterIndex).add(subjectToDisplace);
        recalculateCreditHours(plan, newSemesterIndex);
        System.out.println("[DEBUG] Successfully moved displaced subject " + subjectToDisplace.getSubjectCode() + " to semester " + (newSemesterIndex + 1));

        targetSemester.add(subjectToPlace);
        recalculateCreditHours(plan, targetSemesterIndex);
        System.out.println("[DEBUG] Successfully placed subject " + subjectToPlace.getSubjectCode() + " in semester " + (targetSemesterIndex + 1));

        return true;
    }

    /**
     * Finds a subject to displace based on priority.
     */
    private static Subject findSubjectToDisplace(List<Subject> semester, Subject subjectToPlace) {
        // Prefer to displace non-core subjects with lower priority
        return semester.stream()
                .filter(subject -> !subject.isCore()) // Avoid displacing core subjects
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns a list of electives for a given semester. This logic can be adjusted as needed.
     */
    public static List<Subject> getElectivesForSemester(int semesterIndex, Student student) {
        List<Subject> electives = new ArrayList<>();

        // Example electives based on semester (can be replaced with dynamic logic)
        if (semesterIndex % 3 == 0) { // Semester divisible by 3
            electives.add(new Subject("Elective1", "Elective 1", 3, new String[]{}, false, 2, false));
        } else if (semesterIndex % 2 == 0) { // Semester divisible by 2
            electives.add(new Subject("FreeElective1", "Free Elective 1", 3, new String[]{}, false, 2, false));
        } else { // Default case
            electives.add(new Subject("Elective2", "Elective 2", 3, new String[]{}, false, 3, false));
        }

        System.out.println("[DEBUG] Fetched electives for semester " + (semesterIndex + 1) + ": " + electives);
        return electives;
    }



    /**
     * Finds the next valid semester to place a displaced subject.
     */
    private static int findNextValidSemester(List<List<Subject>> plan, Subject subject, int currentSemesterIndex, Student student) {
        for (int i = currentSemesterIndex + 1; i < plan.size(); i++) {
            if (canFitInSemester(plan.get(i), subject, i, student)) {
                System.out.println("[DEBUG] Found next valid semester for " + subject.getSubjectCode() + ": Semester " + (i + 1));
                return i;
            }

            if (isInternshipSemester(plan.get(i))) {
                System.out.println("[DEBUG] Skipping internship semester " + (i + 1) + " for " + subject.getSubjectCode());
            }
        }

        System.out.println("[DEBUG] No valid semester found for " + subject.getSubjectCode());
        return -1;
    }



    /**
     * Determines if a semester is an internship semester.
     */
    public static boolean isInternshipSemester(List<Subject> semester) {
        return semester.size() == 1 && semester.get(0).getSubjectCode().equals("SEG3203");
    }

    /**
     * Gets the month of a semester based on the student's cohort and semester index.
     */
    public static String getSemesterMonth(int semesterIndex, Student student) {
        List<String> semesterCycle = List.of("January", "March", "August");
        String intakeMonth = extractIntakeMonthFromCohort(student.getCohort());
        int startMonthIndex = semesterCycle.indexOf(intakeMonth);

        if (startMonthIndex == -1) {
            throw new IllegalArgumentException("Invalid cohort or intake month: " + student.getCohort());
        }

        int offset = semesterIndex % semesterCycle.size();
        return semesterCycle.get((startMonthIndex + offset) % semesterCycle.size());
    }

    /**
     * Extracts the intake month from the cohort string.
     */
    private static String extractIntakeMonthFromCohort(String cohort) {
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
     * Recalculates credit hours for a given semester.
     */
    public static void recalculateCreditHours(List<List<Subject>> plan, int semesterIndex) {
        List<Subject> semester = plan.get(semesterIndex);
        int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
        System.out.println("[DEBUG] Semester " + (semesterIndex + 1) + " has " + currentCredits + " credit hours.");
    }
}
