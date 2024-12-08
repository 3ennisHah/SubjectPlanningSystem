package SubjectPlan;

import Data.*;
import Operators.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPlanner {
    private final GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

    public Map<String, List<Subject>> initializeBaseLineup(String cohortKey, boolean isInternational) {
        // Pass both the cohort key and the international status
        return LineupManager.getLineupForCohort(cohortKey, isInternational);
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
                    }
                }
                adjustedPlan.add(completedSubjects);
            } else {
                // Include the remaining semesters as-is
                adjustedPlan.add(new ArrayList<>(basePlan.get(i)));
            }
        }

        return adjustedPlan;
    }
}
