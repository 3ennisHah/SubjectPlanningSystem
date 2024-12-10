package SubjectPlan;

import Data.*;
import Operators.*;
import Utils.PlacementHandler;
import Utils.SemesterValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPlanner {
    private final GeneticAlgorithm geneticAlgorithm;

    public SubjectPlanner() {
        PlacementHandler placementHandler = new PlacementHandler();
        SemesterValidator semesterValidator = new SemesterValidator();
        FitnessFunction fitnessFunction = new FitnessFunction();
        SelectionOperator selectionOperator = new SelectionOperator();
        CrossoverOperator crossoverOperator = new CrossoverOperator();
        MutationOperator mutationOperator = new MutationOperator();

        this.geneticAlgorithm = new GeneticAlgorithm(
                placementHandler,
                semesterValidator,
                fitnessFunction,
                selectionOperator,
                crossoverOperator,
                mutationOperator
        );
    }

    public Map<String, List<Subject>> initializeBaseLineup(String cohortKey, boolean isInternational) {
        System.out.println("[DEBUG] Initializing base lineup for cohort key: " + cohortKey);
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey, isInternational);
        if (lineup == null || lineup.isEmpty()) {
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
        if (basePlan == null || basePlan.isEmpty()) {
            System.out.println("[ERROR] Base plan is empty or null. Cannot run genetic algorithm.");
            return null;
        }
        List<List<Subject>> adjustedPlan = filterAndFixSemesters(basePlan, student);
        return geneticAlgorithm.optimizePlan(adjustedPlan, student, student.getFailedSubjects());
    }

    private List<List<Subject>> filterAndFixSemesters(List<List<Subject>> basePlan, Student student) {
        List<List<Subject>> adjustedPlan = new ArrayList<>();
        int currentSemester = student.getCurrentSemester();

        for (int i = 0; i < basePlan.size(); i++) {
            if (i < currentSemester - 1) {
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
                adjustedPlan.add(new ArrayList<>(basePlan.get(i)));
                System.out.println("[DEBUG] Keeping Semester " + (i + 1) + " as-is: " + basePlan.get(i));
            }
        }
        return adjustedPlan;
    }
}
