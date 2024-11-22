package Operators;

import Data.Chromosome;
import Data.Student;
import Data.Subject;

import java.util.List;

public class FitnessFunction {
    private static final int HARD_CONSTRAINT_PENALTY = 100;


    public int calculateFitness(Chromosome chromosome, Student student, int maxCreditsPerLongSemester, int maxCreditsPerShortSemester) {
        int fitness = 100; // Start with perfect fitness

        List<List<Subject>> semesterPlan = chromosome.getSemesterPlan();

        for (List<Subject> semester : semesterPlan) {
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

            // Hard constraint: Check if credit limits are exceeded
            if (totalCredits > maxCreditsPerLongSemester && totalCredits > maxCreditsPerShortSemester) {
                fitness -= HARD_CONSTRAINT_PENALTY;
            }
        }

        return Math.max(fitness, 0); // Ensure fitness is non-negative
    }
}
