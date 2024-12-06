package SubjectPlan;

import Data.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubjectPlanUtils {
    public static void printPlan(List<List<Subject>> plan) {
        for (int i = 0; i < plan.size(); i++) {
            System.out.println("Semester " + (i + 1) + ": " + plan.get(i));
        }
    }

    public static void comparePlans(List<List<Subject>> basePlan, List<List<Subject>> finalPlan) {
        for (int i = 0; i < Math.min(basePlan.size(), finalPlan.size()); i++) {
            List<Subject> baseSemester = basePlan.get(i);
            List<Subject> finalSemester = finalPlan.get(i);

            if (!baseSemester.equals(finalSemester)) {
                System.out.println("Changes in Semester " + (i + 1) + ":");
                System.out.println(" - Added: " + getDifference(finalSemester, baseSemester));
                System.out.println(" - Removed: " + getDifference(baseSemester, finalSemester));
            }
        }
    }

    private static List<Subject> getDifference(List<Subject> list1, List<Subject> list2) {
        List<Subject> difference = new ArrayList<>(list1);
        difference.removeAll(list2);
        return difference;
    }

    // New method: Flatten a nested list of subjects into a set of subject codes
    public static Set<String> flattenPlan(List<List<Subject>> plan) {
        Set<String> subjectCodes = new HashSet<>();
        for (List<Subject> semester : plan) {
            for (Subject subject : semester) {
                subjectCodes.add(subject.getSubjectCode());
            }
        }
        return subjectCodes;
    }
}
