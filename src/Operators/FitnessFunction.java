package Operators;

import Data.LineupManager;
import Data.Subject;
import Data.Student;
import Utils.SemesterHelper;

import java.util.*;
import java.util.stream.Collectors;

public class FitnessFunction {
    private static final int MAX_CREDITS_LONG_SEMESTER = 19; // Maximum credit hours for long semesters
    private static final int MAX_CREDITS_SHORT_SEMESTER = 10; // Maximum credit hours for short semesters
    private static final int MAX_FITNESS = 100; // Maximum fitness value
    private static final int PREREQUISITE_PENALTY = 15; // Penalty for unmet prerequisites
    private static final int MISSING_SUBJECT_PENALTY = 10; // Penalty for missing subjects
    private static final int OVERLOAD_SEMESTER_PENALTY = 20; // Penalty for exceeding credit hour constraints

    public int calculateFitness(List<List<Subject>> semesterPlan, List<Subject> failingSubjects, Student student) {
        int fitness = MAX_FITNESS;

        // 1. Ensure all failing subjects are placed
        for (Subject failingSubject : failingSubjects) {
            boolean isPlaced = semesterPlan.stream()
                    .flatMap(List::stream)
                    .anyMatch(subject -> subject.getSubjectCode().equals(failingSubject.getSubjectCode()));

            if (!isPlaced) {
                System.out.println("[DEBUG] Missing failing subject: " + failingSubject.getSubjectCode());
                fitness -= MISSING_SUBJECT_PENALTY;
            }
        }

        // 2. Check for unmet prerequisites
        for (int i = 0; i < semesterPlan.size(); i++) {
            List<Subject> semester = semesterPlan.get(i);
            List<Subject> completedSubjects = flattenSubjectsUpToSemester(semesterPlan, i);

            for (Subject subject : semester) {
                if (subject.hasPrerequisite()) {
                    for (String prerequisiteCode : subject.getPrerequisites()) {
                        boolean prerequisiteMet = completedSubjects.stream()
                                .anyMatch(completedSubject -> completedSubject.getSubjectCode().equals(prerequisiteCode));
                        if (!prerequisiteMet) {
                            System.out.println("[DEBUG] Unmet prerequisite for subject: " + subject.getSubjectCode() +
                                    " (Prerequisite: " + prerequisiteCode + ").");
                            fitness -= PREREQUISITE_PENALTY;
                        }
                    }
                }
            }
        }

        // 3. Penalize for credit overloads
        for (int i = 0; i < semesterPlan.size(); i++) {
            List<Subject> semester = semesterPlan.get(i);
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
            int maxCredits = SemesterHelper.isShortSemester(i, student) ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER;

            System.out.println("[DEBUG] Semester " + (i + 1) + " has " + totalCredits + " credit hours. Max allowed: " + maxCredits);

            if (totalCredits > maxCredits) {
                System.out.println("[DEBUG] Semester " + (i + 1) + " exceeds credit hour limit. Penalizing " + OVERLOAD_SEMESTER_PENALTY + " points.");
                fitness -= OVERLOAD_SEMESTER_PENALTY;
            }
        }

        // 4. Penalize for missing core subjects
        List<String> requiredSubjects = getAllCoreSubjects(student);
        List<String> placedSubjects = semesterPlan.stream()
                .flatMap(List::stream)
                .map(Subject::getSubjectCode)
                .collect(Collectors.toList());

        for (String required : requiredSubjects) {
            if (!placedSubjects.contains(required)) {
                System.out.println("[DEBUG] Missing core subject: " + required + ". Penalizing " + MISSING_SUBJECT_PENALTY + " points.");
                fitness -= MISSING_SUBJECT_PENALTY;
            }
        }

        // Final fitness score adjustments
        System.out.println("[DEBUG] Final fitness score: " + Math.max(0, Math.min(MAX_FITNESS, fitness)));
        return Math.max(0, Math.min(MAX_FITNESS, fitness));
    }

    private List<Subject> flattenSubjectsUpToSemester(List<List<Subject>> semesterPlan, int limit) {
        return semesterPlan.subList(0, limit).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<String> getAllCoreSubjects(Student student) {
        Map<String, List<Subject>> cohortPlan = LineupManager.getLineupForCohort(student.getCohortKey(), student.isInternational());
        if (cohortPlan == null) return Collections.emptyList();

        return cohortPlan.values().stream()
                .flatMap(List::stream)
                .filter(Subject::isCore)
                .map(Subject::getSubjectCode)
                .distinct()
                .collect(Collectors.toList());
    }
}
