import Data.*;
import Utils.SemesterHelper;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import SubjectPlan.SubjectPlanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String CONTACT_POINT = "sunway.hep88.com";
    private static final int PORT = 9042;
    private static final String USERNAME = "planusertest";
    private static final String PASSWORD = "Ic7cU8K965Zqx";
    private static final String KEYSPACE = "subjectplanning";
    private static final String DATACENTER = "datacenter1";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Perform Authentication Check
        String role = AuthenticationCheck.authenticate();
        if (role == null) {
            System.out.println("Exiting program due to failed authentication.");
            return; // Exit program if authentication fails
        }

        // Student ID is collected during authentication if the user is a student
        int studentId = AuthenticationCheck.getStudentId(); // Retrieve the student ID entered earlier
        if (role.equals("Admin")) {
            System.out.print("Enter Student ID to fetch data: ");
            studentId = scanner.nextInt(); // Admin manually enters the student ID here
        }

        // Initialize SubjectPlanner for Genetic Algorithm
        SubjectPlanner subjectPlanner = new SubjectPlanner();

        // Connect to Cassandra
        try (CqlSession session = connectToCassandra()) {
            System.out.println("Connected to Cassandra successfully!");

            // Fetch the student by ID
            Student student = fetchStudentById(session, studentId);

            if (student == null) {
                System.out.println("No student found with ID: " + studentId);
                return;
            }

            // Process the student
            System.out.println("\nProcessing Student: " + student.getName());

            long startTime = System.nanoTime(); // Start time for processing

            // Transform database subjects to real subjects
            transformDatabaseSubjectsToRealSubjects(student);

            // Check if the student is on track
            boolean isOnTrack = checkStudentOnTrack(student, subjectPlanner);

            // Display the student's details
            displayStudentDetails(student, isOnTrack);

            List<List<Subject>> subjectPlan; // Declare the subject plan

            if (isOnTrack) {
                System.out.println(student.getName() + " is on track.");
                String cohortKey = student.getCohortKey();
                boolean isInternational = student.isInternational();

                // Initialize base lineup and convert to a plan list
                Map<String, List<Subject>> lineup = subjectPlanner.initializeBaseLineup(cohortKey, isInternational);
                subjectPlan = subjectPlanner.convertToPlanList(lineup);

                // Display the base subject plan
                SemesterHelper.displayPlan("Base Subject Plan", subjectPlan);
            } else {
                System.out.println(student.getName() + " is NOT on track.");
                // Run the genetic algorithm to generate the optimized plan
                long algoStartTime = System.nanoTime(); // Start genetic algorithm timing
                subjectPlan = invokeGeneticAlgorithm(subjectPlanner, student);
                long algoEndTime = System.nanoTime(); // End genetic algorithm timing
                System.out.println("Genetic algorithm execution time: " + (algoEndTime - algoStartTime) / 1_000_000 + " ms");

                // Display the optimized subject plan
                SemesterHelper.displayPlan("Optimized Subject Plan", subjectPlan);
            }

            // Only export to Excel if the user is an Admin
            if (role.equals("Admin")) {
                long excelStartTime = System.nanoTime(); // Start Excel export timing
                exportToExcel(subjectPlan, student);
                long excelEndTime = System.nanoTime(); // End Excel export timing
                System.out.println("Excel export time: " + (excelEndTime - excelStartTime) / 1_000_000 + " ms");
            } else {
                System.out.println("[INFO] Excel export is only available for Admin users.");
            }

            long endTime = System.nanoTime(); // End time for processing
            long durationInMillis = (endTime - startTime) / 1_000_000; // Convert to milliseconds

            System.out.println("Time taken to fully process the subject plan for "
                    + student.getName() + ": " + durationInMillis + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CqlSession connectToCassandra() {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(CONTACT_POINT, PORT))
                .withAuthCredentials(USERNAME, PASSWORD)
                .withKeyspace(KEYSPACE)
                .withLocalDatacenter(DATACENTER)
                .build();
    }

    private static Student fetchStudentById(CqlSession session, int studentId) {
        String query = "SELECT id, name, cohort, status, country, sem, subjects FROM students WHERE id = " + studentId + " ALLOW FILTERING;";
        System.out.println("Executing query: " + query);

        long startTime = System.nanoTime(); // Start measuring query execution time
        ResultSet resultSet = session.execute(query);
        long endTime = System.nanoTime(); // End measuring query execution time

        System.out.println("Database query execution time: " + (endTime - startTime) / 1_000_000 + " ms");

        Row row = resultSet.one();
        if (row == null) {
            return null; // No matching student found
        }

        int id = row.getInt("id");
        String name = row.getString("name");
        String cohort = row.getString("cohort");
        String status = row.getString("status");
        String country = row.getString("country");
        int currentSemester = row.getInt("sem");

        // Determine if the student is international
        boolean isInternational = !"MALAYSIA".equalsIgnoreCase(country);

        // Parse subjects from the database
        Set<Map<String, String>> subjectsSet = row.getSet("subjects", (Class<Map<String, String>>) (Class<?>) Map.class);
        List<DatabaseSubject> allSubjects = new ArrayList<>();
        if (subjectsSet != null) {
            for (Map<String, String> subjectMap : subjectsSet) {
                DatabaseSubject databaseSubject = DatabaseSubject.fromMap(subjectMap);
                allSubjects.add(databaseSubject);
            }
        }

        // Create and return the Student object
        return new Student(id, name, cohort, status, country, isInternational, allSubjects, currentSemester);
    }

    private static void transformDatabaseSubjectsToRealSubjects(Student student) {
        List<DatabaseSubject> dbSubjects = student.getAllSubjects();
        List<Subject> completedSubjects = new ArrayList<>();

        for (DatabaseSubject dbSubject : dbSubjects) {
            String dbSubjectCode = dbSubject.getSubjectCode();

            // Match the database subject code with the available subjects in Subject class
            Subject realSubject = Subject.valueOf(dbSubjectCode);

            if (realSubject != null) {
                realSubject.setCompleted(true);
                completedSubjects.add(realSubject);
            }
        }

        // Update student's completed subjects
        student.setCompletedSubjects(completedSubjects);

        System.out.println("Transformed Database Subjects to Real Subjects for " + student.getName());
    }

    private static void displayStudentDetails(Student student, boolean isOnTrack) {
        System.out.println("[INFO] Student Details:");
        System.out.println(" - ID: " + student.getId());
        System.out.println(" - Name: " + student.getName());
        System.out.println(" - Cohort: " + student.getCohort());
        System.out.println(" - Status: " + student.getStatus());
        System.out.println(" - Country: " + student.getCountry());
        System.out.println(" - Current Semester (Displayed): " + student.getCurrentSemester());
        if (!isOnTrack) {
            System.out.println(" - Semester for Algorithm: Semester " + student.getAlgorithmSemester());
        }
        System.out.println(" - Completed Subjects:");
        for (Subject subject : student.getCompletedSubjects()) {
            System.out.println("   * " + subject.getSubjectName() + " (" + subject.getSubjectCode() + ")");
        }
        System.out.println(" - Failed Subjects:");
        for (DatabaseSubject dbSubject : student.getFailingSubjects()) {
            System.out.println("   * " + dbSubject.getSubjectName() + " (" + dbSubject.getSubjectCode() + ") - Grade: " + dbSubject.getGrade());
        }
        System.out.println("==================================================");
    }

    private static boolean checkStudentOnTrack(Student student, SubjectPlanner subjectPlanner) {
        String cohortKey = student.getCohortKey();
        boolean isInternational = student.isInternational();

        // Get the lineup for the student's cohort
        Map<String, List<Subject>> lineup = subjectPlanner.initializeBaseLineup(cohortKey, isInternational);

        // Convert the lineup to a plan list
        List<List<Subject>> basePlan = subjectPlanner.convertToPlanList(lineup);

        // Check if the student is on track
        return student.isOnTrack(lineup);
    }

    private static List<List<Subject>> invokeGeneticAlgorithm(SubjectPlanner subjectPlanner, Student student) {
        System.out.println("Invoking Genetic Algorithm for Student: " + student.getName());

        String cohortKey = student.getCohortKey();
        boolean isInternational = student.isInternational();

        // Initialize base lineup
        Map<String, List<Subject>> lineup = subjectPlanner.initializeBaseLineup(cohortKey, isInternational);

        // Convert the lineup to a plan list
        List<List<Subject>> basePlan = subjectPlanner.convertToPlanList(lineup);

        // Run the genetic algorithm and get the best Chromosome
        Chromosome bestChromosome = subjectPlanner.runGeneticAlgorithm(student, basePlan);

        // Extract and return the optimized semester plan from the Chromosome
        return bestChromosome != null ? bestChromosome.getSemesterPlan() : null;
    }

    private static void exportToExcel(List<List<Subject>> subjectPlan, Student student) {
        // Convert the subject plan to a list of strings for each semester
        List<List<String>> planForExport = subjectPlan.stream()
                .map(semester -> semester.stream()
                        .map(subject -> subject.getSubjectName() + " (" + subject.getSubjectCode() + ")")
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        // Generate the file path
        String filePath = "Subject_Plan_" + student.getName().replace(" ", "_") + ".xlsx";

        try {
            ExcelExporter.exportSubjectPlan(filePath, planForExport, student.getName());
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to export subject plan to Excel for " + student.getName());
            e.printStackTrace();
        }
    }
}
