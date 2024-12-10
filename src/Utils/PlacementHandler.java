package Utils;

import Data.Subject;
import Data.Student;

import java.util.*;

public class PlacementHandler {
    public void placeFailedSubjects(List<Subject> failedSubjects, List<List<Subject>> plan, Student student) {
        int currentSemester = student.getCurrentSemester();

        for (Subject failedSubject : failedSubjects) {
            System.out.println("[DEBUG] Attempting to place failed subject: " + failedSubject.getSubjectCode());
            boolean placed = placeFailedSubject(failedSubject, plan, currentSemester, student);
            if (!placed) {
                System.out.println("[DEBUG] Failed to place subject " + failedSubject.getSubjectCode() + " due to constraints.");
            }
        }
    }

    private boolean placeFailedSubject(Subject failedSubject, List<List<Subject>> plan, int currentSemester, Student student) {
        for (int i = currentSemester - 1; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);

            System.out.println("[DEBUG] Checking placement for " + failedSubject.getSubjectCode() + " in semester " + (i + 1));

            if (SemesterHelper.isFixedSemester(semester)) {
                System.out.println("[DEBUG] Semester " + (i + 1) + " is fixed (e.g., internship). Skipping.");
                continue;
            }

            if (failedSubject.isYear1() && i >= 6) {
                System.out.println("[DEBUG] Skipping placement of Y1 subject in semester " + (i + 1) + " as it exceeds semester 6.");
                continue;
            }

            if (SemesterHelper.canFitInSemester(semester, failedSubject, i, student)) {
                semester.add(failedSubject);
                System.out.println("[DEBUG] Placed " + failedSubject.getSubjectCode() + " in semester " + (i + 1));
                return true;
            }

            if (failedSubject.isYear1() && i < 6) {
                // Corrected method call with all required arguments
                if (tryDisplacingSubject(failedSubject, plan, i, currentSemester, student)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tryDisplacingSubject(Subject subjectToPlace, List<List<Subject>> plan, int currentSemesterIndex, int currentSemester, Student student) {
        List<Subject> currentSemesterSubjects = plan.get(currentSemesterIndex);
        System.out.println("[DEBUG] Attempting to displace a subject in semester " + (currentSemesterIndex + 1) + " to fit " + subjectToPlace.getSubjectCode());

        // Find a subject to displace
        Optional<Subject> subjectToDisplace = currentSemesterSubjects.stream()
                .filter(subject -> !subject.isYear1()) // Displace Y2 or higher subjects first
                .min(Comparator.comparingInt(Subject::getCreditHours));

        if (subjectToDisplace.isPresent()) {
            Subject displacedSubject = subjectToDisplace.get();
            System.out.println("[DEBUG] Selected subject to displace: " + displacedSubject.getSubjectCode() + " (Credits: " + displacedSubject.getCreditHours() + ")");

            // Check if the subject already exists in the plan
            if (SemesterHelper.isSubjectAlreadyInPlan(plan, displacedSubject)) {
                System.out.println("[DEBUG] Subject " + displacedSubject.getSubjectCode() + " already exists in the plan. Avoiding duplicate placement.");
            }

            // Attempt to find a valid semester for the displaced subject
            int targetSemesterIndex = SemesterHelper.findNextOfferingSemester(currentSemesterIndex + 1, displacedSubject, student, plan);

            if (targetSemesterIndex != -1) {
                // Place the displaced subject in the found semester
                System.out.println("[DEBUG] Placing displaced subject " + displacedSubject.getSubjectCode() + " in Semester " + (targetSemesterIndex + 1) + ". Reason: Found valid semester.");
                plan.get(targetSemesterIndex).add(displacedSubject);
            } else {
                // If no valid semester is found, create a new one
                System.out.println("[DEBUG] No valid semester found for displaced subject " + displacedSubject.getSubjectCode() + ". Creating a new semester...");
                SemesterHelper.createNewSemesterAndPlace(plan, displacedSubject);
                System.out.println("[DEBUG] Created new semester for displaced subject " + displacedSubject.getSubjectCode());
            }

            // Place the original subject in the current semester
            currentSemesterSubjects.remove(displacedSubject);
            currentSemesterSubjects.add(subjectToPlace);
            System.out.println("[DEBUG] Successfully displaced subject " + displacedSubject.getSubjectCode() + " to fit " + subjectToPlace.getSubjectCode() +
                    " in Semester " + (currentSemesterIndex + 1) + ".");
            return true;
        } else {
            System.out.println("[DEBUG] No suitable subject found for displacement in semester " + (currentSemesterIndex + 1));
        }

        return false;
    }
    private void validateFinalPlan(List<List<Subject>> plan) {
        for (int i = 0; i < plan.size(); i++) {
            List<Subject> semesterSubjects = plan.get(i);
            Set<String> seenSubjects = new HashSet<>();
            List<Subject> cleanedSubjects = new ArrayList<>();

            for (Subject subject : semesterSubjects) {
                if (!seenSubjects.contains(subject.getSubjectCode())) {
                    cleanedSubjects.add(subject);
                    seenSubjects.add(subject.getSubjectCode());
                } else {
                    System.out.println("[DEBUG] Removing duplicate subject: " + subject.getSubjectCode() + " from Semester " + (i + 1));
                }
            }
            plan.set(i, cleanedSubjects);
        }
    }

}
