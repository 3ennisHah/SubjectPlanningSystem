package Utils;

import Data.Subject;
import Data.Student;

import java.util.ArrayList;
import java.util.List;

public class PlacementHandler {

    public void placeFailedSubjects(List<Subject> failingSubjects, List<List<Subject>> plan, Student student, int startingSemesterIndex) {
        for (Subject failingSubject : failingSubjects) {
            boolean placed = false;
            for (int i = startingSemesterIndex; i < plan.size(); i++) { // Start from the defined algorithm semester
                List<Subject> semester = plan.get(i);

                // Check if the subject can fit into the semester
                if (SemesterHelper.canFitInSemester(semester, failingSubject, i, student)) {
                    semester.add(failingSubject);
                    SemesterHelper.recalculateCreditHours(plan, i);
                    System.out.println("[DEBUG] Placed failing subject " + failingSubject.getSubjectCode() + " in semester " + (i + 1));
                    placed = true;
                    break;
                }

                // Attempt displacement if direct placement fails
                if (SemesterHelper.attemptToDisplace(plan, failingSubject, i, student)) {
                    System.out.println("[DEBUG] Placed failing subject " + failingSubject.getSubjectCode() + " in semester " + (i + 1) + " after displacing another subject.");
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                // Create a new semester if placement and displacement fail
                System.out.println("[DEBUG] Creating a new semester for failing subject: " + failingSubject.getSubjectCode());
                List<Subject> newSemester = new ArrayList<>();
                newSemester.add(failingSubject);
                plan.add(newSemester);
                SemesterHelper.recalculateCreditHours(plan, plan.size() - 1);
            }
        }
    }
}
