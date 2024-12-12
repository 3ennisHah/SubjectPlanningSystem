package Utils;

import Data.Subject;
import Data.Student;

import java.util.List;

public class SemesterValidator {
    public void validateAndAdjustCreditHours(List<List<Subject>> plan, Student student) {
        int currentSemester = student.getAlgorithmSemester();

        for (int i = currentSemester - 1; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);
            int maxCredits = SemesterHelper.getMaxCredits(i, student);
            int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

            System.out.println("[DEBUG] Semester " + (i + 1) + " has " + currentCredits + " credit hours. Max allowed: " + maxCredits);

            while (currentCredits > maxCredits) {
                Subject subjectToMove = semester.remove(semester.size() - 1);
                System.out.println("[DEBUG] Moving subject " + subjectToMove.getSubjectCode() + " to reduce credit hours in semester " + (i + 1));

                boolean moved = moveSubjectToNextSemester(subjectToMove, i + 1, plan, student);

                if (!moved) {
                    SemesterHelper.createNewSemesterAndPlace(plan, subjectToMove);
                    break;
                }

                currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
                System.out.println("[DEBUG] Semester " + (i + 1) + " now has " + currentCredits + " credit hours.");
            }
        }
    }

    private boolean moveSubjectToNextSemester(Subject subject, int startSemester, List<List<Subject>> plan, Student student) {
        for (int i = startSemester; i < plan.size(); i++) {
            if (SemesterHelper.canFitInSemester(plan.get(i), subject, i, student)) {
                plan.get(i).add(subject);
                return true;
            }
        }
        return false;
    }

    private int extractSemesterNumber(String currentSemester) {
        try {
            return Integer.parseInt(currentSemester.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 1; // Default to semester 1
        }
    }
}
