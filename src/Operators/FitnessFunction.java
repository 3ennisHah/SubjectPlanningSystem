package Operators;

import Data.Chromosome;
import Data.Student;
import Data.Subject;

import java.util.List;

public class FitnessFunction {
    public int calculateFitness(Chromosome chromosome, Student student, int maxCredits, int shortSemesterCredits) {
        List<List<Subject>> semesterPlan = chromosome.getSemesterPlan();
        int fitness = 100;

        for (int i = 0; i < semesterPlan.size(); i++) {
            List<Subject> semester = semesterPlan.get(i);
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

            // Check credit hour constraints
            if (i == semesterPlan.size() - 1) {
                if (totalCredits > shortSemesterCredits) fitness -= 10;
            } else if (totalCredits > maxCredits) {
                fitness -= 10;
            }

            // Check Capstone Project constraints
            if (semester.contains(Subject.PRJ3213)) {
                if (i < 6 || i > 8) {
                    fitness -= 20; // Capstone Project 1 must be in semester 7, 8, or 9
                }
            }
            if (semester.contains(Subject.PRJ3223)) {
                if (i < 7 || i > 8) {
                    fitness -= 20; // Capstone Project 2 must be in semester 8 or 9
                }
                boolean capstone1Found = false;
                for (int j = 0; j < i; j++) {
                    if (semesterPlan.get(j).contains(Subject.PRJ3213)) {
                        capstone1Found = true;
                        break;
                    }
                }
                if (!capstone1Found) fitness -= 30; // Capstone Project 2 must follow Capstone Project 1
            }
        }

        // Penalize if a completed subject is added to the plan
        for (List<Subject> semester : semesterPlan) {
            for (Subject subject : semester) {
                if (student.hasCompleted(subject.getSubjectCode())) {
                    fitness -= 5;
                }
            }
        }

        chromosome.setFitness(fitness);
        return fitness;
    }
}
