package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.*;

public class CrossoverOperator {
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        List<List<Subject>> childSemesterPlan = new ArrayList<>();
        Set<Subject> assignedSubjects = new HashSet<>();

        for (int semesterIndex = 0; semesterIndex < parent1.getSemesterPlan().size(); semesterIndex++) {
            List<Subject> childSemester = new ArrayList<>();
            List<Subject> parent1Semester = parent1.getSemesterPlan().get(semesterIndex);
            List<Subject> parent2Semester = parent2.getSemesterPlan().get(semesterIndex);

            for (Subject subject : parent1Semester) {
                if (!assignedSubjects.contains(subject)) {
                    childSemester.add(subject);
                    assignedSubjects.add(subject);
                }
            }

            for (Subject subject : parent2Semester) {
                if (!assignedSubjects.contains(subject)) {
                    childSemester.add(subject);
                    assignedSubjects.add(subject);
                }
            }

            childSemesterPlan.add(childSemester);
        }

        return new Chromosome(childSemesterPlan);
    }



    private List<Subject> distributeSubjectsToSemester(List<Subject> subjects, int semesterIndex) {
        List<Subject> distributedSubjects = new ArrayList<>();
        int currentCredits = 0;
        int maxCredits = semesterIndex == 0 || semesterIndex == 3 || semesterIndex == 6 ? 10 : 19;

        for (Subject subject : subjects) {
            if (currentCredits + subject.getCreditHours() <= maxCredits) {
                distributedSubjects.add(subject);
                currentCredits += subject.getCreditHours();
            }
        }

        return distributedSubjects;
    }
}
