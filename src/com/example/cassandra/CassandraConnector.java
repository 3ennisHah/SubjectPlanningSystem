package com.example.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;
import java.util.*;

public class CassandraConnector {

    public static void main(String[] args) {
        // Define connection details
        String contactPoint = "sunway.hep88.com";
        int port = 9042;
        String username = "planusertest";
        String password = "Ic7cU8K965Zqx";
        String keyspace = "subjectplanning";

        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoint, port))
                .withAuthCredentials(username, password)
                .withKeyspace(keyspace)
                .withLocalDatacenter("datacenter1")
                .build()) {

            System.out.println("Connected to Cassandra successfully!");

            // Fetch all students
            String studentQuery = "SELECT * FROM students WHERE cohort IN ('202401', '202404', '202409') ALLOW FILTERING;";
            ResultSet studentResultSet = session.execute(studentQuery);

            Map<Integer, Row> studentsMap = new HashMap<>();
            for (Row studentRow : studentResultSet) {
                int studentId = studentRow.getInt("id");
                studentsMap.put(studentId, studentRow);
            }
            System.out.println("Number of students fetched: " + studentsMap.size());
            for (Integer studentId : studentsMap.keySet()) {
                System.out.println("  - " + studentId);
            }

            // Fetch all subjects
            String subjectQuery = "SELECT * FROM subjects;";
            ResultSet subjectResultSet = session.execute(subjectQuery);

            // Map to store subjects grouped by student ID
            Map<Integer, List<Row>> subjectsMap = new HashMap<>();
            for (Row subjectRow : subjectResultSet) {
                int studentId = subjectRow.getInt("id"); // Use the correct column name
                subjectsMap.computeIfAbsent(studentId, k -> new ArrayList<>()).add(subjectRow);
            }
            System.out.println("Number of subjects fetched: " + subjectsMap.size());
            // Crosscheck and display results for the first 20 students
            System.out.println("Displaying the first 20 students with their subjects:");

            List<Integer> studentIds = new ArrayList<>(studentsMap.keySet());
            Collections.sort(studentIds); // Ensure consistent ordering of student IDs

            int counter = 0;

            for (Integer studentId : studentIds) {
                if (counter >= 20) break;

                Row studentRow = studentsMap.get(studentId);

                System.out.println("Student ID: " + studentId);
                System.out.println("Name: " + studentRow.getString("name"));
                System.out.println("Cohort: " + studentRow.getString("cohort"));
                System.out.println("Status: " + studentRow.getString("status"));
                System.out.println("Subjects:");

                // Retrieve the subjects for this student
                List<Row> subjectRows = subjectsMap.get(studentId);
                if (subjectRows != null && !subjectRows.isEmpty()) {
                    for (Row subjectRow : subjectRows) {
                        System.out.println("  - Subject Code: " + subjectRow.getString("subjectcode"));
                        System.out.println("    Subject Name: " + subjectRow.getString("subjectname"));
                        System.out.println("    Grade: " + subjectRow.getString("grade"));
                    }
                } else {
                    System.out.println("  No subjects found for this student.");
                }
                System.out.println("---");

                counter++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
