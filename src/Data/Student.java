package Data;

import java.util.*;
import java.util.stream.Collectors;

public class Student {
    private String studentId;
    private String name;
    private List<Subject> completedSubjects;
    private List<Subject> failedSubjects;
    private boolean directEntry;
    private boolean mathRequirement;
    private boolean international; // Indicates if the student is an international student
    private String enrollmentYear;
    private String enrollmentIntake;
    private int currentSemester;
    private List<List<Subject>> basePlan;

    public Student(String studentId, String name, String enrollmentYear, String enrollmentIntake, int currentSemester) {
        this.studentId = studentId;
        this.name = name;
        this.completedSubjects = new ArrayList<>();
        this.failedSubjects = new ArrayList<>();
        this.directEntry = false;
        this.mathRequirement = false;
        this.international = false; // Default to false
        this.enrollmentYear = enrollmentYear;
        this.enrollmentIntake = sanitizeIntakeMonth(enrollmentIntake);
        this.currentSemester = currentSemester;
        this.basePlan = new ArrayList<>(); // Initialize basePlan as an empty list
    }

    // Getters and Setters

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public boolean isInternational() {
        return international;
    }

    private String sanitizeIntakeMonth(String intakeMonth) {
        switch (intakeMonth) {
            case "MathMarch":
                return "March"; // Map custom intake value to recognized value
            default:
                return intakeMonth; // Return the original value if already valid
        }
    }

    public void setInternational(boolean international) {
        this.international = international;
    }

    public Set<Subject> getCompletedSubjects() {
        return new HashSet<>(completedSubjects);
    }

    public void addCompletedSubject(Subject subject) {
        if (!completedSubjects.contains(subject)) {
            completedSubjects.add(subject);
            if (subject.getSubjectCode().equals("MTH1114")) {
                this.mathRequirement = true; // Math requirement fulfilled
            }
        }
    }

    public String getProgrammeCode() {
        return "BCS";
    }

    public String constructCohortKey() {
        String sanitizedIntake = getEnrollmentIntake().equals("March") ? "MathMarch" : getEnrollmentIntake();
        String cohortKey = getProgrammeCode() + getEnrollmentYear() + sanitizedIntake;
        System.out.println("[DEBUG] Constructed cohort key: " + cohortKey);
        return cohortKey;
    }

    public List<Subject> getFailedSubjects() {
        return failedSubjects;
    }

    public boolean hasMathRequirement() {
        return mathRequirement;
    }

    public String getEnrollmentYear() {
        return enrollmentYear;
    }

    public String getEnrollmentIntake() {
        return enrollmentIntake;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public List<List<Subject>> getBasePlan() {
        return basePlan;
    }

    public boolean isOnTrack(Map<String, List<Subject>> baseLineup) {
        Set<String> completedSubjectCodes = this.getCompletedSubjectCodes();
        List<String> semesterKeys = new ArrayList<>(baseLineup.keySet());

        // Check all semesters prior to the current semester
        for (int semesterIndex = 0; semesterIndex < currentSemester - 1; semesterIndex++) {
            String semesterKey = semesterKeys.get(semesterIndex);
            List<Subject> semesterSubjects = baseLineup.get(semesterKey);

            for (Subject subject : semesterSubjects) {
                // Ignore electives when determining if the student is on track
                if (!subject.isElective() && !completedSubjectCodes.contains(subject.getSubjectCode())) {
                    System.out.println("Missing Subject from Prior Semesters: " + subject.getSubjectCode());
                    return false; // Not on track if a required core subject from prior semesters is missing
                }
            }
        }

        System.out.println("Current semester is Semester " + currentSemester + ". Skipping strict validation.");
        return true; // On track if all prior core subjects are completed
    }

    public Set<String> getCompletedSubjectCodes() {
        return completedSubjects.stream()
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());
    }

    // Returns the original semester of a given subject
    public int findOriginalSemesterForSubject(Subject subject) {
        for (int semesterIndex = 0; semesterIndex < basePlan.size(); semesterIndex++) {
            if (basePlan.get(semesterIndex).contains(subject)) {
                return semesterIndex + 1; // Semester index is zero-based
            }
        }
        return -1; // Subject not found
    }

    public String getIntakeMonth() {
        return enrollmentIntake; // Assumes enrollmentIntake stores the month of intake
    }

    public List<Subject> getAllSubjects() {
        Set<Subject> allSubjects = new HashSet<>();

        // Add all subjects from the base plan
        for (List<Subject> semester : basePlan) {
            allSubjects.addAll(semester);
        }

        // Add completed and failed subjects
        allSubjects.addAll(completedSubjects);
        allSubjects.addAll(failedSubjects);

        return new ArrayList<>(allSubjects); // Return as a list
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", completedSubjects=" + completedSubjects +
                ", failedSubjects=" + failedSubjects +
                ", directEntry=" + directEntry +
                ", mathRequirement=" + mathRequirement +
                ", international=" + international +
                ", enrollmentYear='" + enrollmentYear + '\'' +
                ", enrollmentIntake='" + enrollmentIntake + '\'' +
                ", currentSemester=" + currentSemester +
                '}';
    }

    public static Student getStudentByChoice(int choice, Map<String, List<Subject>> baseLineup) {
        Student student = null;

        switch (choice) {
            case 1: // Alice Perfect
                student = new Student("S1001", "Alice Perfect", "2024", "January", 5); // Current semester is 5
                student.setInternational(false); // Alice is not an international student

                student.addCompletedSubject(Subject.CSC1024);
                student.addCompletedSubject(Subject.MPU3193);
                student.addCompletedSubject(Subject.MPU3183);
                student.addCompletedSubject(Subject.MAT1013);
                student.addCompletedSubject(Subject.ENG1044);
                student.addCompletedSubject(Subject.CSC1202);
                student.addCompletedSubject(Subject.MTH1114);
                student.addCompletedSubject(Subject.PRG1203);
                student.addCompletedSubject(Subject.SEG1201);
                student.addCompletedSubject(Subject.NET1014);
                student.addCompletedSubject(Subject.CSC2104);
                student.addCompletedSubject(Subject.WEB1201);
                student.addCompletedSubject(Subject.MPU3222);
                student.addCompletedSubject(Subject.MPU3412);
                student.addCompletedSubject(Subject.SEG2202);
                student.addCompletedSubject(Subject.KIAR);
                break;

            case 2: // May (International Student in January 2024 Intake, Semester 4)
                student = new Student("S1003", "May International", "2024", "January", 4); // Current semester is 4
                student.setInternational(true); // May is an international student

                // Completed subjects up to semester 3
                student.addCompletedSubject(Subject.CSC1024); // Programming Principles
                student.addCompletedSubject(Subject.MPU3203); // Appreciation of Ethics and Civilisation (International alternative to MPU3193)
                student.addCompletedSubject(Subject.MPU3213); // Malay Language for Communication 2 (International alternative to MPU3183)
                student.addCompletedSubject(Subject.MAT1013); // Micro-credential in Computer Mathematics Fundamentals
                student.addCompletedSubject(Subject.ENG1044); // English for Computer Technology Studies
                student.addCompletedSubject(Subject.CSC1202); // Computer Organisation
                student.addCompletedSubject(Subject.MTH1114); // Computer Mathematics
                student.addCompletedSubject(Subject.PRG1203); // Object-Oriented Programming Fundamentals
                student.addCompletedSubject(Subject.SEG1201); // Database Fundamentals
                student.addCompletedSubject(Subject.NET1014); // Networking Principles
                student.addCompletedSubject(Subject.CSC2104); // Operating System Fundamentals
                student.addCompletedSubject(Subject.WEB1201); // Web Fundamentals
                break;

            case 3: // Bob NoMath (March 2024 Intake, Semester 3)
                student = new Student("S1002", "Bob (Fail Y1 Subjects)", "2024", "MathMarch", 3);
                student.setInternational(false); // Bob is not an international student

                // Completed subjects that do not align with Semesters 1 and 2
                student.addCompletedSubject(Subject.ENG1044);  // English for Computer Technology Studies
                student.addCompletedSubject(Subject.CSC1202);  // Computer Organisation
                student.addCompletedSubject(Subject.CSC1024);  // Programming Principles
                student.addCompletedSubject(Subject.MAT1013);  // Micro-credential in Computer Mathematics Fundamentals
                student.addCompletedSubject(Subject.SEG1201);  // Database Fundamentals
                student.addCompletedSubject(Subject.PRG1203);  // Object-Oriented Programming Fundamentals
                student.addCompletedSubject(Subject.CSC2104);  // Operating System Fundamentals
                student.addCompletedSubject(Subject.MTH1114);  // Computer Mathematics

                // Failed subjects
                student.getFailedSubjects().add(Subject.WEB1201);  // Failed Web Fundamentals
                break;

            case 4: // Mark (International Student, August 2024 Intake, Semester 7)
                student = new Student("S1004", "Mark International", "2024", "August", 7); // Current semester is 7
                student.setInternational(true); // Mark is an international student

                // Completed subjects up to semester 6
                student.addCompletedSubject(Subject.MPU3203); // Appreciation of Ethics and Civilisation
                student.addCompletedSubject(Subject.MPU3213); // Malay Language for Communication 2
                student.addCompletedSubject(Subject.CSC1024); // Programming Principles
                student.addCompletedSubject(Subject.ENG1044); // English for Computer Technology Studies
                student.addCompletedSubject(Subject.CSC1202); // Computer Organisation
                student.addCompletedSubject(Subject.MTH1114); // Computer Mathematics
                student.addCompletedSubject(Subject.PRG1203); // Object-Oriented Programming Fundamentals
                student.addCompletedSubject(Subject.SEG1201); // Database Fundamentals
                student.addCompletedSubject(Subject.NET1014); // Networking Principles
                student.addCompletedSubject(Subject.CSC2104); // Operating System Fundamentals
                student.addCompletedSubject(Subject.WEB1201); // Web Fundamentals
                student.addCompletedSubject(Subject.CSC2103); // Data Structure & Algorithms
                student.addCompletedSubject(Subject.PRG2104); // Object-Oriented Programming
                student.addCompletedSubject(Subject.CSC2014); // Digital Image Processing
                student.addCompletedSubject(Subject.ENG2044); // Communication Skills
                student.addCompletedSubject(Subject.MPU3222); // Entrepreneurial Mindset and Skills
                student.addCompletedSubject(Subject.SEG2202); // Software Engineering
                student.addCompletedSubject(Subject.KIAR); // Integrity and Anti-Corruption]
                student.addCompletedSubject(Subject.FreeElective1);
                student.addCompletedSubject(Subject.FreeElective2);
                student.addCompletedSubject(Subject.FreeElective3);
                student.addCompletedSubject(Subject.Elective1);

                // Failed subjects
                student.getFailedSubjects().add(Subject.CSC3206); // Failed Artificial Intelligence
                break;

            default:
                System.out.println("Invalid choice.");
                break;
        }

        return student;
    }


}
