package Operators;

import Data.Chromosome;
import Data.Student;
import Data.Subject;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FitnessFunction {
    private static final int MAX_FITNESS = 100;

    public int calculateFitness(Chromosome chromosome, Student student, List<List<Subject>> basePlan) {
        int fitness = 0;

        // Ensure all required subjects are present
        Set<String> baseSubjectCodes = flattenPlan(basePlan);
        Set<String> chromosomeSubjectCodes = flattenPlan(chromosome.getSemesterPlan());

        int missingSubjects = 0;
        for (String subjectCode : baseSubjectCodes) {
            if (!chromosomeSubjectCodes.contains(subjectCode)) {
                missingSubjects++;
            }
        }

        fitness -= missingSubjects * 10; // Penalize for missing subjects

        // Penalize semesters that exceed credit hour limits
        for (int i = 0; i < chromosome.getSemesterPlan().size(); i++) {
            List<Subject> semester = chromosome.getSemesterPlan().get(i);
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

            int maxCredits = i % 2 == 0 ? 19 : 10; // Long semester: 19, short semester: 10
            if (totalCredits > maxCredits) {
                fitness -= 100; // Heavy penalty for violating credit hour constraints
            }
        }

        // Reward plans that include all failed subjects in earlier semesters
        for (Subject failedSubject : student.getFailedSubjects()) {
            for (int i = 0; i < chromosome.getSemesterPlan().size(); i++) {
                if (chromosome.getSemesterPlan().get(i).contains(failedSubject)) {
                    fitness += 10 - i; // Higher reward for earlier placement
                    break;
                }
            }
        }

        return Math.max(0, Math.min(MAX_FITNESS, fitness)); // Keep fitness within [0, MAX_FITNESS]
    }

    private Set<String> flattenPlan(List<List<Subject>> plan) {
        return plan.stream()
                .flatMap(List::stream)
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());
    }
}
