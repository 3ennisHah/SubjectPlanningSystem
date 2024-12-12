package Utils;

import Data.Subject;
import Data.Student;

import java.util.*;

public class PlacementHandler {

    public void placeFailedSubjects(List<Subject> failedSubjects, List<List<Subject>> plan, Student student) {
        int currentSemester = student.getAlgorithmSemester();

        for (Subject failedSubject : failedSubjects) {
            System.out.println("[DEBUG] Attempting to place failed subject: " + failedSubject.getSubjectCode());
            boolean placed = placeFailedSubject(failedSubject, plan, currentSemester, student);
            if (!placed) {
                System.out.println("[ERROR] Failed to place subject " + failedSubject.getSubjectCode() + " in any semester.");
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

            if (SemesterHelper.canFitInSemester(semester, failedSubject, i, student)) {
                semester.add(failedSubject);
                System.out.println("[DEBUG] Placed " + failedSubject.getSubjectCode() + " in semester " + (i + 1));
                return true;
            }

            // Log why placement failed
            System.out.println("[DEBUG] Placement of " + failedSubject.getSubjectCode() + " in semester " + (i + 1) + " failed.");

            // Attempt displacement if placement fails
            if (tryDisplacingSubject(failedSubject, plan, i, currentSemester, student)) {
                return true;
            }
        }

        System.out.println("[WARN] Failed to find a valid semester for " + failedSubject.getSubjectCode());
        return false;
    }

    private boolean tryDisplacingSubject(Subject subjectToPlace, List<List<Subject>> plan, int currentSemesterIndex, int currentSemester, Student student) {
        List<Subject> currentSemesterSubjects = plan.get(currentSemesterIndex);
        System.out.println("[DEBUG] Attempting to displace a subject in semester " + (currentSemesterIndex + 1) + " to fit " + subjectToPlace.getSubjectCode());

        Optional<Subject> subjectToDisplace = currentSemesterSubjects.stream()
                .filter(subject -> !subject.isYear1()) // Prefer displacing Year 2/3 subjects
                .min(Comparator.comparingInt(Subject::getCreditHours));

        if (subjectToDisplace.isPresent()) {
            Subject displacedSubject = subjectToDisplace.get();
            System.out.println("[DEBUG] Selected subject to displace: " + displacedSubject.getSubjectCode());

            int targetSemesterIndex = SemesterHelper.findNextOfferingSemester(currentSemesterIndex, displacedSubject, student, plan);

            if (targetSemesterIndex != -1) {
                plan.get(targetSemesterIndex).add(displacedSubject);
                currentSemesterSubjects.remove(displacedSubject);
                currentSemesterSubjects.add(subjectToPlace);
                System.out.println("[DEBUG] Successfully displaced " + displacedSubject.getSubjectCode() + " to fit " + subjectToPlace.getSubjectCode());
                return true;
            } else {
                System.out.println("[ERROR] Could not find a valid semester to move displaced subject " + displacedSubject.getSubjectCode());
            }
        } else {
            System.out.println("[WARN] No suitable subject found for displacement in semester " + (currentSemesterIndex + 1));
        }

        return false;
    }

}
