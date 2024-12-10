package Utils;

import Data.LineupManager;
import Data.Subject;
import Data.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SemesterHelper {
    public static final int MAX_CREDITS_LONG_SEMESTER = 19;
    public static final int MAX_CREDITS_SHORT_SEMESTER = 10;

    public static boolean canFitInSemester(List<Subject> semester, Subject subject, int semesterIndex, Student student) {
        int maxCredits = getMaxCredits(semesterIndex, student);
        int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

        // Check if the subject can be offered in the current semester
        boolean isOffered = isSemesterValidForSubject(semesterIndex + 1, subject, student);

        if (!isOffered) {
            System.out.println("[DEBUG] Semester " + (semesterIndex + 1) + " is not valid for " + subject.getSubjectCode() +
                    ". Reason: Subject not offered in this semester.");
            return false;
        }

        boolean canFit = currentCredits + subject.getCreditHours() <= maxCredits;
        if (!canFit) {
            System.out.println("[DEBUG] Semester " + (semesterIndex + 1) + " is not valid for " + subject.getSubjectCode() +
                    ". Reason: Credit limit exceeded.");
        }

        return isOffered && canFit;
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
        int offset = semesterIndex % semesterCycle.size();
        int startMonthIndex = semesterCycle.indexOf(student.getIntakeMonth());
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
                plan.get(i).add(subject);
                System.out.println("[DEBUG] Placed subject " + subject.getSubjectCode() + " in semester " + (i + 1));
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

        plan.get(nextSemesterNumber - 1).add(subject);
        System.out.println("[DEBUG] Placed subject " + subject.getSubjectCode() + " in semester " + nextSemesterNumber);

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
        String cohortKey = "BCS" + student.getEnrollmentYear() + student.getIntakeMonth();
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey, student.isInternational());

        if (lineup == null || lineup.isEmpty()) {
            System.out.println("[DEBUG] No lineup available for cohort key: " + cohortKey);
            return 1; // Default to semester 1 if no lineup exists
        }

        for (int i = 0; i < 9; i++) {
            List<Subject> subjectsInSemester = lineup.get("Semester" + (i + 1));
            if (subjectsInSemester != null && subjectsInSemester.contains(subject)) {
                return i + 1;
            }
        }
        return 1; // Default to semester 1 if subject not found
    }

}
