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

        // Check if all required subjects are present
        Set<String> baseSubjectCodes = flattenPlan(basePlan);
        Set<String> chromosomeSubjectCodes = flattenPlan(chromosome.getSemesterPlan());

        int missingSubjects = 0;
        for (String subjectCode : baseSubjectCodes) {
            if (!chromosomeSubjectCodes.contains(subjectCode)) {
                missingSubjects++;
            }
        }

        // Penalize missing subjects
        fitness -= missingSubjects * 10;

        // Reward plans with all required subjects
        if (missingSubjects == 0) {
            fitness += 50;
        }

        // Penalize semesters with more than the max credits (e.g., 19)
        for (List<Subject> semester : chromosome.getSemesterPlan()) {
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
            if (totalCredits > 19) {
                fitness -= 5 * (totalCredits - 19); // Penalize for exceeding credit limits
            }
        }

        // Reward semesters that match the base plan
        List<List<Subject>> chromosomeSemesters = chromosome.getSemesterPlan();
        for (int i = 0; i < Math.min(basePlan.size(), chromosomeSemesters.size()); i++) {
            List<Subject> baseSemester = basePlan.get(i);
            List<Subject> chromosomeSemester = chromosomeSemesters.get(i);

            // Check if the semester subjects match exactly
            if (subjectsMatch(baseSemester, chromosomeSemester)) {
                fitness += 5; // Reward for matching the base plan
            }
        }

        // Ensure fitness is within the range [0, MAX_FITNESS]
        fitness = Math.max(0, Math.min(MAX_FITNESS, fitness));

        return fitness;
    }

    // Helper method to check if two semesters have the same subjects
    private boolean subjectsMatch(List<Subject> semester1, List<Subject> semester2) {
        Set<String> semester1SubjectCodes = semester1.stream()
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());

        Set<String> semester2SubjectCodes = semester2.stream()
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());

        return semester1SubjectCodes.equals(semester2SubjectCodes);
    }

    // Helper method to flatten a plan (list of lists) into a set of subject codes
    private Set<String> flattenPlan(List<List<Subject>> plan) {
        return plan.stream()
                .flatMap(List::stream)
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());
    }
}
