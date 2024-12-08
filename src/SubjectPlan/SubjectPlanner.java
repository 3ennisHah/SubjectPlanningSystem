package SubjectPlan;

import Data.*;
import Operators.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPlanner {
    private final GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

    public Map<String, List<Subject>> initializeBaseLineup(String cohortKey, boolean isInternational) {
        System.out.println("[DEBUG] Initializing base lineup for cohort key: " + cohortKey + ", International: " + isInternational);
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey, isInternational);
        if (lineup == null) {
            System.out.println("[ERROR] No lineup found for cohort key: " + cohortKey);
        }
        return lineup;
    }


    public List<List<Subject>> convertToPlanList(Map<String, List<Subject>> lineupMap) {
        List<List<Subject>> planList = new ArrayList<>();
        for (Map.Entry<String, List<Subject>> entry : lineupMap.entrySet()) {
            planList.add(entry.getValue());
        }
        return planList;
    }

    public Chromosome runGeneticAlgorithm(Student student, List<List<Subject>> basePlan) {
        // Step 1: Filter and lock prior semesters
        List<List<Subject>> adjustedPlan = filterAndFixSemesters(basePlan, student);

        // Step 2: Run optimization logic
        return geneticAlgorithm.optimizePlan(adjustedPlan, student, student.getFailedSubjects());
    }

    private List<List<Subject>> filterAndFixSemesters(List<List<Subject>> basePlan, Student student) {
        List<List<Subject>> adjustedPlan = new ArrayList<>();
        int currentSemester = student.getCurrentSemester();

        for (int i = 0; i < basePlan.size(); i++) {
            if (i < currentSemester - 1) {
                // Fix semesters prior to the current semester with completed subjects
                List<Subject> completedSubjects = new ArrayList<>();
                for (Subject subject : basePlan.get(i)) {
                    if (student.getCompletedSubjectCodes().contains(subject.getSubjectCode())) {
                        completedSubjects.add(subject);
                    } else {
                        System.out.println("[DEBUG] Removing incomplete subject " + subject.getSubjectCode() + " from Semester " + (i + 1));
                    }
                }
                adjustedPlan.add(completedSubjects);
                System.out.println("[DEBUG] Fixed Semester " + (i + 1) + ": " + completedSubjects);
            } else {
                // Include the remaining semesters as-is
                adjustedPlan.add(new ArrayList<>(basePlan.get(i)));
                System.out.println("[DEBUG] Keeping Semester " + (i + 1) + " as-is: " + basePlan.get(i));
            }
        }
        return adjustedPlan;
    }

}
