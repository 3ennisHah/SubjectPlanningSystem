package Utils;

import Data.LineupManager;
import Data.Subject;
import Data.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SemesterHelper {
    public static final int MAX_CREDITS_LONG_SEMESTER = 19;
    public static final int MAX_CREDITS_SHORT_SEMESTER = 10;

    public static boolean canFitInSemester(List<Subject> semester, Subject subject, int semesterIndex, Student student) {
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

    public static int getMaxCredits(int semesterIndex, Student student) {
        return isShortSemester(semesterIndex, student) ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER;
    }

    public static boolean isShortSemester(int semesterIndex, Student student) {
        String semesterMonth = getSemesterMonth(semesterIndex, student);
        return semesterMonth.equals("January");
    }

    public static String getSemesterMonth(int semesterIndex, Student student) {
        List<String> semesterCycle = List.of("January", "March", "August");

        // Get the intake month from the cohort
        String intakeMonth = extractIntakeMonthFromCohort(student.getCohort());
        int startMonthIndex = semesterCycle.indexOf(intakeMonth);

        if (startMonthIndex == -1) {
            throw new IllegalArgumentException("Invalid cohort or intake month: " + student.getCohort());
        }

        int offset = semesterIndex % semesterCycle.size();
        return semesterCycle.get((startMonthIndex + offset) % semesterCycle.size());
    }

    public static void createNewSemesterAndPlace(List<List<Subject>> plan, Subject subject) {
        // Prevent redundant relocation of existing subjects
        if (isSubjectAlreadyInPlan(plan, subject)) {
            System.out.println("[DEBUG] Subject " + subject.getSubjectCode() + " already exists in the plan. Skipping creation of a new semester.");
            return;
        }

        // Create a new semester and add the subject
        List<Subject> newSemester = new ArrayList<>();
        newSemester.add(subject);
        plan.add(newSemester);

        System.out.println("[DEBUG] Created new semester and placed subject: " + subject.getSubjectCode());
    }

    public static boolean isFixedSemester(List<Subject> semester) {
        return semester.stream().anyMatch(subject -> subject.getSubjectCode().equals("SEG3203"));
    }

    public static int findNextOfferingSemester(int currentSemesterIndex, Subject subject, Student student, List<List<Subject>> plan) {
        int initialOfferingSemester = getInitialOfferingSemester(subject, student);
        System.out.println("[DEBUG] Looking for next valid semester for subject " + subject.getSubjectCode() +
                " starting from semester " + (currentSemesterIndex + 1) + ".");

        for (int i = currentSemesterIndex + 1; i < plan.size(); i++) {
            if (canFitInSemester(plan.get(i), subject, i, student)) {
                System.out.println("[DEBUG] Found valid semester for " + subject.getSubjectCode() + " at semester " + (i + 1));
                return i;
            }
        }

        int nextSemesterNumber = plan.size() + 1;
        while ((nextSemesterNumber - initialOfferingSemester) % 3 != 0) {
            nextSemesterNumber++;
        }

        System.out.println("[DEBUG] Creating new semester " + nextSemesterNumber + " for subject " + subject.getSubjectCode());
        while (plan.size() < nextSemesterNumber) {
            plan.add(new ArrayList<>());
        }

        return nextSemesterNumber - 1;
    }

    public static List<List<Subject>> deepCopyPlan(List<List<Subject>> original) {
        List<List<Subject>> copy = new ArrayList<>();
        for (List<Subject> semester : original) {
            copy.add(new ArrayList<>(semester));
        }
        return copy;
    }

    public static boolean isSubjectAlreadyInPlan(List<List<Subject>> plan, Subject subject) {
        return plan.stream().flatMap(List::stream).anyMatch(s -> s.getSubjectCode().equals(subject.getSubjectCode()));
    }

    private static boolean isSemesterValidForSubject(int currentSemester, Subject subject, Student student) {
        if (subject.isYear2() || subject.isYear3()) {
            int initialOfferingSemester = getInitialOfferingSemester(subject, student);
            return (currentSemester - initialOfferingSemester) % 3 == 0 && currentSemester >= initialOfferingSemester;
        }
        return true;
    }

    private static int getInitialOfferingSemester(Subject subject, Student student) {
        String cohortKey = "BCS" + extractEnrollmentYearFromCohort(student.getCohort()) + extractIntakeMonthFromCohort(student.getCohort());
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey, student.isInternational());

        if (lineup == null || lineup.isEmpty()) {
            return 1; // Default to semester 1
        }

        for (int i = 0; i < 9; i++) {
            List<Subject> subjectsInSemester = lineup.get("Semester" + (i + 1));
            if (subjectsInSemester != null && subjectsInSemester.contains(subject)) {
                return i + 1;
            }
        }
        return 1; // Default to semester 1 if subject not found
    }


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

    private static String extractEnrollmentYearFromCohort(String cohort) {
        return cohort.substring(0, 4);
    }
}
