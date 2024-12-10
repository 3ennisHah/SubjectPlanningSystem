package Utils;

import Data.Subject;
import Data.Student;

import java.util.List;

public class SemesterValidator {
    public void validateAndAdjustCreditHours(List<List<Subject>> plan, Student student) {
        for (int i = student.getCurrentSemester() - 1; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);
            int maxCredits = SemesterHelper.getMaxCredits(i, student);
            int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

            while (currentCredits > maxCredits) {
                Subject subjectToMove = semester.remove(semester.size() - 1);
                boolean moved = moveSubjectToNextSemester(subjectToMove, i + 1, plan, student);

                if (!moved) {
                    SemesterHelper.createNewSemesterAndPlace(plan, subjectToMove);
                    break;
                }

                currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
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
}
