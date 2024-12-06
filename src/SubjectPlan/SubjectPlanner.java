package SubjectPlan;

import Data.*;
import Operators.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPlanner {
    private final GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

    public Map<String, List<Subject>> initializeBaseLineup(String cohortKey) {
        return LineupManager.getLineupForCohort(cohortKey);
    }

    public List<List<Subject>> convertToPlanList(Map<String, List<Subject>> lineupMap) {
        List<List<Subject>> planList = new ArrayList<>();
        for (Map.Entry<String, List<Subject>> entry : lineupMap.entrySet()) {
            planList.add(entry.getValue());
        }
        return planList;
    }

    public Chromosome runGeneticAlgorithm(Student student, List<List<Subject>> basePlan) {
        // Filter and lock prior semesters
        List<List<Subject>> adjustedPlan = filterAndFixSemesters(basePlan, student);

        // Flatten all subjects for optimization
        List<Subject> allSubjects = flattenSubjects(basePlan);

        // Run genetic algorithm on the filtered plan
        return geneticAlgorithm.evolve(adjustedPlan, student, allSubjects);
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

    private List<Subject> flattenSubjects(List<List<Subject>> basePlan) {
        List<Subject> allSubjects = new ArrayList<>();
        for (List<Subject> semester : basePlan) {
            allSubjects.addAll(semester);
        }
        return allSubjects;
    }
}
