package Operators;

import Data.Chromosome;
import Data.Student;
import Data.Subject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    private static final int MAX_CREDITS_LONG_SEMESTER = 19; // Maximum credit hours for long semesters
    private static final int MAX_CREDITS_SHORT_SEMESTER = 10; // Maximum credit hours for short semesters
    private static final int MAX_FITNESS = 100; // Maximum fitness value
    private static final List<String> SEMESTER_CYCLE = List.of("January", "March", "August"); // Semester months

    public Chromosome optimizePlan(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        List<List<Subject>> optimizedPlan = deepCopyPlan(basePlan);
        List<Subject> failedSubjects = new ArrayList<>(student.getFailedSubjects());

        int currentSemester = student.getCurrentSemester();
        System.out.println("[DEBUG] Starting optimization for student: " + student.getName());
        System.out.println("[DEBUG] Current semester: " + currentSemester);
        System.out.println("[DEBUG] Failed subjects: " + failedSubjects);

        // Step 1: Place Failed Subjects
        for (Subject failedSubject : failedSubjects) {
            System.out.println("[DEBUG] Attempting to place failed subject: " + failedSubject.getSubjectCode());
            boolean placed = placeFailedSubject(failedSubject, optimizedPlan, currentSemester, student);
            if (!placed) {
                System.out.println("[DEBUG] Failed to place subject " + failedSubject.getSubjectCode() + " due to constraints.");
            }
        }

        // Step 2: Validate Credit Hours (only for future semesters)
        System.out.println("[DEBUG] Validating and adjusting credit hours...");
        validateAndAdjustCreditHours(optimizedPlan, currentSemester, student);

        // Step 3: Calculate Fitness Score
        int fitnessScore = calculateFitness(optimizedPlan, student, basePlan);
        System.out.println("[DEBUG] Fitness score calculated: " + fitnessScore);

        // Step 4: Create Final Chromosome
        Chromosome optimizedChromosome = new Chromosome(optimizedPlan);
        optimizedChromosome.setFitness(fitnessScore);

        System.out.println("[DEBUG] Optimization complete with final fitness score: " + fitnessScore);
        return optimizedChromosome;
    }

    private boolean placeFailedSubject(Subject failedSubject, List<List<Subject>> plan, int currentSemester, Student student) {
        for (int i = currentSemester - 1; i < plan.size(); i++) { // Only process semesters >= current semester
            List<Subject> semester = plan.get(i);

            System.out.println("[DEBUG] Checking placement for " + failedSubject.getSubjectCode() + " in semester " + (i + 1));

            if (i < currentSemester - 1) {
                System.out.println("[DEBUG] Skipping Semester " + (i + 1) + " as it is before the current semester.");
                continue;
            }

            // Check if failed subject is a Y1 subject and restrict it to be placed before semester 6
            if (failedSubject.isYear1() && i >= 6) {
                System.out.println("[DEBUG] Skipping placement of Y1 subject " + failedSubject.getSubjectCode() + " in semester " + (i + 1) + " as it exceeds semester 6.");
                continue;
            }

            // Check if semester is fixed or if placement is allowed
            if (isFixedSemester(semester)) {
                System.out.println("[DEBUG] Semester " + (i + 1) + " is fixed (e.g., internship). Skipping.");
                continue;
            }

            if (canFitInSemester(semester, failedSubject, i, student)) { // Pass the student object
                semester.add(failedSubject);
                System.out.println("[DEBUG] Placed " + failedSubject.getSubjectCode() + " in semester " + (i + 1));
                return true;
            }

            // If not possible to fit, attempt displacement
            if (failedSubject.isYear1() && i < 6) { // Only displace for Y1 subjects within first 6 semesters
                if (tryDisplacingSubject(failedSubject, plan, i, currentSemester, student)) {
                    System.out.println("[DEBUG] Displaced a subject to fit " + failedSubject.getSubjectCode() + " in semester " + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tryDisplacingSubject(Subject subjectToPlace, List<List<Subject>> plan, int currentSemesterIndex, int currentSemester, Student student) {
        List<Subject> currentSemesterSubjects = plan.get(currentSemesterIndex);
        System.out.println("[DEBUG] Attempting to displace a subject in semester " + (currentSemesterIndex + 1) + " to fit " + subjectToPlace.getSubjectCode());

        Optional<Subject> subjectToDisplace = currentSemesterSubjects.stream()
                .filter(subject -> !subject.isYear1()) // Displace Y2 or higher subjects first
                .min(Comparator.comparingInt(Subject::getCreditHours));

        if (subjectToDisplace.isPresent()) {
            Subject displacedSubject = subjectToDisplace.get();
            System.out.println("[DEBUG] Selected subject to displace: " + displacedSubject.getSubjectCode());

            // Attempt to find a valid semester for the displaced subject
            int targetSemesterIndex = findNextOfferingSemester(currentSemesterIndex + 1, displacedSubject, student, plan);

            if (targetSemesterIndex != -1) {
                plan.get(targetSemesterIndex).add(displacedSubject);
                System.out.println("[DEBUG] Placed displaced subject " + displacedSubject.getSubjectCode() + " in semester " + (targetSemesterIndex + 1));
                currentSemesterSubjects.remove(displacedSubject);
                currentSemesterSubjects.add(subjectToPlace);
                return true;
            } else {
                // No valid semester found, create a new one
                System.out.println("[DEBUG] No valid semester found for displaced subject " + displacedSubject.getSubjectCode() + ". Creating a new semester...");
                createNewSemesterAndPlace(plan, displacedSubject, currentSemesterIndex);
                currentSemesterSubjects.remove(displacedSubject);
                currentSemesterSubjects.add(subjectToPlace);
                return true;
            }
        } else {
            System.out.println("[DEBUG] No suitable subject found for displacement in semester " + (currentSemesterIndex + 1));
        }

        return false;
    }



    private int findMatchingSemesterForDisplacedSubject(Subject displacedSubject, int currentSemesterIndex, List<List<Subject>> plan, int currentSemester, Student student) {
        String offeringMonth = getSemesterMonth(currentSemesterIndex, student);

        // Search only for semesters that match the same month as the displaced subject
        for (int i = Math.max(currentSemester - 1, currentSemesterIndex + 1); i < plan.size(); i++) {
            String currentMonth = getSemesterMonth(i, student);
            if (offeringMonth.equals(currentMonth) && canFitInSemester(plan.get(i), displacedSubject, i, student)) {
                System.out.println("[DEBUG] Found matching semester " + (i + 1) + " for displaced subject " + displacedSubject.getSubjectCode());
                return i;
            }
        }

        // If no valid semester found, return -1
        return -1;
    }

    private void createNewSemesterAndPlace(List<List<Subject>> plan, Subject displacedSubject, int currentSemesterIndex) {
        int newSemesterIndex = plan.size(); // Index of the new semester
        boolean isShortSemester = (newSemesterIndex % 3 == 2); // Determine if the new semester is short

        // Create a new semester
        List<Subject> newSemester = new ArrayList<>();
        plan.add(newSemester);

        // Add the displaced subject to the new semester
        newSemester.add(displacedSubject);

        System.out.println("[DEBUG] Created new semester " + (newSemesterIndex + 1) + " for displaced subject " + displacedSubject.getSubjectCode() +
                ". Type: " + (isShortSemester ? "Short" : "Long"));
    }


    private void validateAndAdjustCreditHours(List<List<Subject>> plan, int currentSemester, Student student) {
        for (int i = currentSemester - 1; i < plan.size(); i++) { // Start from the current semester
            List<Subject> semester = plan.get(i);
            boolean isShortSemester = isShortSemester(i, student); // Pass the student object
            int maxCredits = isShortSemester ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER;
            int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

            System.out.println("[DEBUG] Validating semester " + (i + 1) + " with current credit hours: " + currentCredits);
            System.out.println("[DEBUG] Semester " + (i + 1) + " is " + (isShortSemester ? "short" : "long") + ". Max allowed: " + maxCredits);

            while (currentCredits > maxCredits) {
                Subject subjectToMove = semester.remove(semester.size() - 1);
                System.out.println("[DEBUG] Removed subject " + subjectToMove.getSubjectCode() + " from semester " + (i + 1) + " to reduce credit hours.");

                boolean moved = false;
                for (int j = i + 1; j < plan.size(); j++) {
                    if (canFitInSemester(plan.get(j), subjectToMove, j, student)) {
                        plan.get(j).add(subjectToMove);
                        System.out.println("[DEBUG] Moved subject " + subjectToMove.getSubjectCode() + " to semester " + (j + 1));
                        moved = true;
                        break;
                    }
                }

                if (!moved) {
                    createNewSemesterAndPlace(plan, subjectToMove, i);
                    System.out.println("[DEBUG] Created new semester to accommodate subject " + subjectToMove.getSubjectCode());
                    break;
                }

                currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
            }
        }
    }


    private int findNextOfferingSemester(int currentSemesterIndex, Subject displacedSubject, Student student, List<List<Subject>> plan) {
        int originalSemesterIndex = student.findOriginalSemesterForSubject(displacedSubject) - 1;

        if (originalSemesterIndex < 0) {
            System.out.println("[ERROR] Invalid original semester for " + displacedSubject.getSubjectCode());
            return -1; // Return -1 if the original semester is invalid
        }

        String originalOfferingMonth = getSemesterMonth(originalSemesterIndex, student);

        for (int i = currentSemesterIndex; i < plan.size(); i++) { // Search forward for a valid semester
            String currentMonth = getSemesterMonth(i, student);

            // Check if the semester matches the offering month
            if (originalOfferingMonth.equals(currentMonth) && canFitInSemester(plan.get(i), displacedSubject, i, student)) {
                System.out.println("[DEBUG] Found next offering semester for " + displacedSubject.getSubjectCode() + ": Semester " + (i + 1));
                return i;
            }
        }

        System.out.println("[DEBUG] No valid offering semester found for " + displacedSubject.getSubjectCode());
        return -1; // No valid offering semester found
    }





    private boolean canFitInSemester(List<Subject> semester, Subject subject, int semesterIndex, Student student) {
        boolean isShortSemester = isShortSemester(semesterIndex, student); // Pass the student object
        int maxCredits = isShortSemester ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER;
        int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
        int newTotalCredits = currentCredits + subject.getCreditHours();

        System.out.println("[DEBUG] Semester " + (semesterIndex + 1) + " current credit hours: " + currentCredits);
        System.out.println("[DEBUG] Adding subject " + subject.getSubjectCode() + " with " + subject.getCreditHours() + " credit hours.");
        System.out.println("[DEBUG] Total credit hours after adding: " + newTotalCredits + ". Max allowed: " + maxCredits);

        return newTotalCredits <= maxCredits;
    }


    /**
     * Determines if a semester is a short semester based on the index.
     *
     * @param semesterIndex The index of the semester (0-based).
     * @return True if the semester is a short semester, false otherwise.
     */
    private boolean isShortSemester(int semesterIndex, Student student) {
        // Determine the semester month based on the student's intake month
        String semesterMonth = getSemesterMonth(semesterIndex, student);

        // January is always the short semester
        boolean isShort = semesterMonth.equals("January");

        System.out.println("[DEBUG] Determining if Semester " + (semesterIndex + 1) + " is short. Month: " + semesterMonth + ", Is short: " + isShort);
        return isShort;
    }

    private boolean isFixedSemester(List<Subject> semester) {
        return semester.stream().anyMatch(s -> s.getSubjectCode().equals("SEG3203")); // Internship semester
    }

    private int calculateFitness(List<List<Subject>> plan, Student student, List<List<Subject>> basePlan) {
        int fitness = MAX_FITNESS;

        // Flatten all subjects from base plan, excluding electives
        List<String> baseSubjectCodes = flattenPlan(basePlan).stream()
                .filter(code -> !code.startsWith("Elective")) // Exclude electives
                .collect(Collectors.toList());

        List<String> optimizedSubjectCodes = flattenPlan(plan);

        // Penalize for missing subjects
        for (String subjectCode : baseSubjectCodes) {
            if (!optimizedSubjectCodes.contains(subjectCode)) {
                System.out.println("[DEBUG] Missing subject: " + subjectCode + ". Penalizing 10 points.");
                fitness -= 10; // Deduct 10 points for each missing subject
            }
        }

        // Penalize semesters that exceed credit hour limits
        for (int i = 0; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
            boolean isShortSemester = isShortSemester(i, student);
            int maxCredits = isShortSemester ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER;

            System.out.println("[DEBUG] Semester " + (i + 1) + " credit hours: " + totalCredits + ". Max allowed: " + maxCredits);

            if (totalCredits > maxCredits) {
                System.out.println("[DEBUG] Semester " + (i + 1) + " exceeds credit hour limit. Penalizing 100 points.");
                fitness -= 100; // Heavy penalty for exceeding credit hour constraints
            }
        }

        System.out.println("[DEBUG] Final fitness score: " + Math.max(0, Math.min(MAX_FITNESS, fitness)));
        return Math.max(0, Math.min(MAX_FITNESS, fitness));
    }


    private List<String> flattenPlan(List<List<Subject>> plan) {
        List<String> subjectCodes = new ArrayList<>();
        for (List<Subject> semester : plan) {
            for (Subject subject : semester) {
                subjectCodes.add(subject.getSubjectCode());
            }
        }
        return subjectCodes;
    }

    private List<List<Subject>> deepCopyPlan(List<List<Subject>> original) {
        List<List<Subject>> copy = new ArrayList<>();
        for (List<Subject> semester : original) {
            copy.add(new ArrayList<>(semester));
        }
        return copy;
    }

    /**
     * Determines the month of a given semester based on the intake.
     *
     * @param semesterIndex The index of the semester.
     * @param student       The student object to determine the intake starting month.
     * @return The month of the semester (e.g., "January", "March", "August").
     */
    private String getSemesterMonth(int semesterIndex, Student student) {
        System.out.println("[DEBUG] Calculating semester month for semester index: " + semesterIndex);
        int offset = semesterIndex % 3;
        int startMonthIndex = SEMESTER_CYCLE.indexOf(student.getIntakeMonth());
        if (startMonthIndex == -1) {
            throw new IllegalStateException("[ERROR] Invalid intake month: " + student.getIntakeMonth());
        }
        int adjustedIndex = (startMonthIndex + offset) % SEMESTER_CYCLE.size();
        String semesterMonth = SEMESTER_CYCLE.get(adjustedIndex);
        System.out.println("[DEBUG] Semester month for index " + semesterIndex + ": " + semesterMonth);
        return semesterMonth;
    }

}

