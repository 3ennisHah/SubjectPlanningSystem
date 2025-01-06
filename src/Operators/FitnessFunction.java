package Operators;

import Data.Subject;
import Data.Student;

import java.util.List;
import java.util.stream.Collectors;

public class FitnessFunction {

    public int calculateFitness(List<List<Subject>> semesterPlan, List<Subject> failingSubjects, Student student) {
        int fitness = 100;

        // 1. Penalize for missing Year 1 subjects
        for (Subject failingSubject : failingSubjects) {
            if (failingSubject.isYear1() && !isSubjectPlaced(semesterPlan, failingSubject)) {
                System.out.println("[DEBUG] Failed to place Year 1 subject: " + failingSubject.getSubjectCode());
                fitness -= 20; // Higher penalty for Year 1 subjects
            }
        }

        // 2. Penalize for missing core subjects
        List<String> requiredCoreSubjects = getRequiredCoreSubjects(student);
        List<String> placedSubjects = semesterPlan.stream()
                .flatMap(List::stream)
                .map(Subject::getSubjectCode)
                .collect(Collectors.toList());

        for (String coreSubject : requiredCoreSubjects) {
            if (!placedSubjects.contains(coreSubject)) {
                System.out.println("[DEBUG] Missing core subject: " + coreSubject);
                fitness -= 10;
            }
        }

        // 3. Penalize for unfulfilled electives
        List<String> requiredElectives = student.getRequiredElectives();
        for (String elective : requiredElectives) {
            if (!placedSubjects.contains(elective)) {
                System.out.println("[DEBUG] Missing elective: " + elective);
                fitness -= 10;
            }
        }

        // Ensure fitness does not go below zero
        return Math.max(0, fitness);
    }

    private boolean isSubjectPlaced(List<List<Subject>> semesterPlan, Subject subject) {
        return semesterPlan.stream()
                .flatMap(List::stream)
                .anyMatch(s -> s.getSubjectCode().equals(subject.getSubjectCode()));
    }

    private List<String> getRequiredCoreSubjects(Student student) {
        // Placeholder logic to retrieve required core subjects
        // This should be replaced by actual logic to retrieve core subjects from LineupManager
        return List.of("CSC1202", "CSC2103", "PRG2104"); // Example required core subjects
    }
}
