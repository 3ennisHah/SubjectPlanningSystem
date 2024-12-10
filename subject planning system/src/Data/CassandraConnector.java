package Data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;

public class CassandraConnector {

    public static void main(String[] args) {
        // Define connection details
        String contactPoint = "sunway.hep88.com"; // External IP
        int port = 9042;
        String username = "planusertest";
        String password = "Ic7cU8K965Zqx";
        String keyspace = "subjectplanning";

        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoint, port))
                .withAuthCredentials(username, password)
                .withKeyspace(keyspace)
                .withLocalDatacenter("datacenter1") // Replace with your Cassandra datacenter name
                .build()) {

            System.out.println("Connected to Cassandra successfully!");

            // Example query to retrieve data
            String query = "SELECT * FROM students LIMIT 20"; // Replace with your table name
            ResultSet resultSet = session.execute(query);

            // Iterate through the results
            for (Row row : resultSet) {
                System.out.println("ID: " + row.getInt("id"));
                System.out.println("Name: " + row.getString("name"));
                System.out.println("Programme: " + row.getString("programme"));
                System.out.println("Status:" + row.getString("status"));
                System.out.println("---");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

