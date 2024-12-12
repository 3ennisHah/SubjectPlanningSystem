package com.example.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import Data.Student;
import Data.DatabaseSubject;

import java.net.InetSocketAddress;
import java.util.*;

public class CassandraConnector {

    private static final String CONTACT_POINT = "sunway.hep88.com";
    private static final int PORT = 9042;
    private static final String USERNAME = "planusertest";
    private static final String PASSWORD = "Ic7cU8K965Zqx";
    private static final String KEYSPACE = "subjectplanning";
    private static final String DATACENTER = "datacenter1";

    public static void main(String[] args) {
        CassandraConnector connector = new CassandraConnector();
        try (CqlSession session = connector.connect()) {
            System.out.println("Connected to Cassandra successfully!");

            // Retrieve student details dynamically
            List<Student> students = connector.fetchSpecificStudents(session);

            // Output the stored students
            System.out.println("\nStored students:");
            for (Student student : students) {
                System.out.println(student);
            }

            // Pass the student objects to further processing as needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CqlSession connect() {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(CONTACT_POINT, PORT))
                .withAuthCredentials(USERNAME, PASSWORD)
                .withKeyspace(KEYSPACE)
                .withLocalDatacenter(DATACENTER)
                .build();
    }

    // Fetch details of the two specific students
    public List<Student> fetchSpecificStudents(CqlSession session) {
        // Define specific student IDs to fetch
        List<Integer> studentIds = Arrays.asList(7592245, 1740544);

        // Query the students table
        String studentQuery = "SELECT * FROM students WHERE id IN (7592245, 1740544) ALLOW FILTERING;";
        ResultSet resultSet = session.execute(studentQuery);

        List<Student> students = new ArrayList<>();
        for (Row row : resultSet) {
            int id = row.getInt("id");
            String name = row.getString("name");
            String cohort = row.getString("cohort");
            String status = row.getString("status");
            String country = row.getString("country");

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

            // Create a Student object and add it to the list
            Student student = new Student(id, name, cohort, status, country, isInternational, allSubjects);
            students.add(student);
        }

        return students;
    }
}
