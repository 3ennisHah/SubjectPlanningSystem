package SubjectPlan;

import Data.*;
import Operators.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPlanner {
    private final GeneticAlgorithm geneticAlgorithm;

    public SubjectPlanner() {
        this.geneticAlgorithm = new GeneticAlgorithm();
    }

    public List<List<Subject>> planSubjects(Student student) {
        String cohortKey = "BCS" + student.getEnrollmentYear() + student.getEnrollmentIntake();
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey);

        if (lineup == null) {
            throw new IllegalArgumentException("No subject lineup found for cohort: " + cohortKey);
        }

        List<List<Subject>> basePlan = new ArrayList<>(lineup.values());
        Chromosome baseChromosome = new Chromosome(basePlan);

        int fitnessScore = geneticAlgorithm.calculateFitness(baseChromosome, student, 19, 10);
        if (fitnessScore == 100) {
            System.out.println("Perfect fitness detected. Returning base lineup.");
            return basePlan;
        }

        Chromosome optimizedChromosome = geneticAlgorithm.evolve(basePlan, student, 19, 10);
        return optimizedChromosome.getSemesterPlan();
    }
}
