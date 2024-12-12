import Data.*;
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
        // Initialize SubjectPlanner for Genetic Algorithm
        SubjectPlanner subjectPlanner = new SubjectPlanner();

        // Connect to Cassandra
        try (CqlSession session = connectToCassandra()) {
            System.out.println("Connected to Cassandra successfully!");

            // Fetch the specified students
            List<Student> students = fetchStudentsByIds(session);

            // Process each student
            for (Student student : students) {
                System.out.println("\nProcessing Student: " + student.getName());

                // Transform database subjects to real subjects
                transformDatabaseSubjectsToRealSubjects(student);

                // Display the student's details
                displayStudentDetails(student);

                // Check if the student is on track
                boolean isOnTrack = checkStudentOnTrack(student);

                if (isOnTrack) {
                    System.out.println(student.getName() + " is on track.");
                } else {
                    System.out.println(student.getName() + " is NOT on track.");
                }

                // If there is a failing subject, invoke the genetic algorithm
                if (!student.getFailingSubjects().isEmpty()) {
                    List<List<Subject>> optimizedPlan = invokeGeneticAlgorithm(subjectPlanner, student);

                    // Export the optimized plan to an Excel file
                    exportToExcel(optimizedPlan, student);
                }
            }

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

    private static List<Student> fetchStudentsByIds(CqlSession session) {
        String query = "SELECT id, name, cohort, status, country, sem, subjects FROM students WHERE id IN (7592245, 1740544) ALLOW FILTERING;";
        System.out.println("Executing query: " + query);

        ResultSet resultSet = session.execute(query);
        List<Student> students = new ArrayList<>();

        for (Row row : resultSet) {
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

            // Create a Student object
            Student student = new Student(id, name, cohort, status, country, isInternational, allSubjects);

            // Set current semester and adjust semester for algorithm
            student.setCurrentSemester("Semester " + currentSemester);
            if ("Active".equalsIgnoreCase(status)) {
                student.setAlgorithmSemester(currentSemester + 1);
            } else {
                student.setAlgorithmSemester(currentSemester);
            }

            students.add(student);
        }

        return students;
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

    private static void displayStudentDetails(Student student) {
        System.out.println("[INFO] Student Details:");
        System.out.println(" - ID: " + student.getId());
        System.out.println(" - Name: " + student.getName());
        System.out.println(" - Cohort: " + student.getCohort());
        System.out.println(" - Status: " + student.getStatus());
        System.out.println(" - Current Semester (Displayed): " + student.getCurrentSemester());
        System.out.println(" - Semester for Algorithm: Semester " + student.getAlgorithmSemester());
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

    private static boolean checkStudentOnTrack(Student student) {
        String cohortKey = student.getCohortKey();
        boolean isInternational = student.isInternational();

        // Get the lineup for the student's cohort
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey, isInternational);

        System.out.println("[DEBUG] Requested cohort key: " + cohortKey);
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
        return bestChromosome.getSemesterPlan();
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
