package Utils;

import Data.Subject;
import Data.Student;

import java.util.List;

public class SemesterValidator {

    /**
     * Validates and adjusts credit hours for each semester in the plan.
     * If a semester is empty, it refills it with valid placeholder electives.
     */
    public void validateAndAdjustCreditHours(List<List<Subject>> plan, Student student) {
        for (int i = 0; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);

            // Check for empty semester and refill with placeholder electives if necessary
            if (semester.isEmpty()) {
                System.out.println("[ERROR] Semester " + (i + 1) + " is empty. Refilling it with valid subjects.");
                refillSemester(plan, i, student);
            }

            // Ensure credit hours don't exceed the limit for the semester
            int maxCredits = SemesterHelper.getMaxCredits(i, student);
            int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
            if (currentCredits > maxCredits) {
                System.out.println("[WARN] Semester " + (i + 1) + " exceeds the credit limit. Adjusting...");
                adjustSemesterCredits(plan, i, student, maxCredits);
            }
        }
    }

    /**
     * Refills an empty semester with placeholder electives.
     */
    private void refillSemester(List<List<Subject>> plan, int semesterIndex, Student student) {
        System.out.println("[DEBUG] Refilling semester " + (semesterIndex + 1) + " with placeholder electives.");
        List<Subject> newSubjects = SemesterHelper.getElectivesForSemester(semesterIndex, student);
        plan.get(semesterIndex).addAll(newSubjects);
    }

    /**
     * Adjusts the credit hours of a semester to ensure it meets the maximum credit limit.
     * Subjects that exceed the limit are moved to the next available semester.
     */
    private void adjustSemesterCredits(List<List<Subject>> plan, int semesterIndex, Student student, int maxCredits) {
        List<Subject> semester = plan.get(semesterIndex);
        int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

        while (currentCredits > maxCredits) {
            // Find the subject with the lowest priority (non-core or elective)
            Subject subjectToMove = semester.stream()
                    .filter(subject -> !subject.isCore()) // Non-core subjects have lower priority
                    .findFirst()
                    .orElse(null);

            if (subjectToMove == null) {
                System.out.println("[ERROR] Unable to adjust credits for semester " + (semesterIndex + 1) + ". All subjects are core.");
                break;
            }

            // Remove the subject from the current semester
            semester.remove(subjectToMove);
            SemesterHelper.recalculateCreditHours(plan, semesterIndex);
            System.out.println("[DEBUG] Removed subject " + subjectToMove.getSubjectCode() + " from semester " + (semesterIndex + 1));

            // Find the next valid semester to place the removed subject
            int nextSemesterIndex = SemesterHelper.findNextOfferingSemester(semesterIndex, subjectToMove, student, plan);
            if (nextSemesterIndex == -1) {
                System.out.println("[ERROR] Could not find a valid semester to move subject " + subjectToMove.getSubjectCode());
                break;
            }

            // Place the subject in the next valid semester
            plan.get(nextSemesterIndex).add(subjectToMove);
            SemesterHelper.recalculateCreditHours(plan, nextSemesterIndex);
            System.out.println("[DEBUG] Moved subject " + subjectToMove.getSubjectCode() + " to semester " + (nextSemesterIndex + 1));

            // Update current credits after adjustment
            currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
        }
    }
}
