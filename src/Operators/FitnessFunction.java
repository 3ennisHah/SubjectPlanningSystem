package Operators;

import Data.Chromosome;
import Data.Student;
import Data.Subject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FitnessFunction {
    public static final int SHORT_SEMESTER_MAX_CREDITS = 10;
    public static final int LONG_SEMESTER_MAX_CREDITS = 19;

    public int calculateFitness(Chromosome chromosome, Student student) {
        List<List<Subject>> semesterPlan = chromosome.getSemesterPlan();
        int fitness = 100; // Start with a base score
        Set<Subject> allSubjects = new HashSet<>();

        for (int i = 0; i < semesterPlan.size(); i++) {
            List<Subject> semester = semesterPlan.get(i);
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

            // Penalize exceeding credit limits
            int maxCredits = isShortSemester(i) ? SHORT_SEMESTER_MAX_CREDITS : LONG_SEMESTER_MAX_CREDITS;
            if (totalCredits > maxCredits) {
                fitness -= 100; // Hard penalty for exceeding credit limit
            }

            // Penalize duplicate subjects
            for (Subject subject : semester) {
                if (allSubjects.contains(subject)) {
                    fitness -= 50; // Deduct heavily for duplicates
                } else {
                    allSubjects.add(subject);
                }
            }
        }

        return fitness;
    }

    private boolean isShortSemester(int semesterIndex) {
        return semesterIndex == 0 || semesterIndex == 3 || semesterIndex == 6; // Example: Semesters 1, 4, and 7
    }
}
