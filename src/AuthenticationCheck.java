import java.util.InputMismatchException;
import java.util.Scanner;

public class AuthenticationCheck {

    private static int studentId = -1; // Store student ID for retrieval after authentication

    /**
     * Authenticates the user based on role selection, student ID, and password.
     *
     * @return the role if authentication is successful, null otherwise
     */
    public static String authenticate() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        // Prompt user for login type with input validation
        while (true) {
            System.out.println("Login: ");
            System.out.println("1. Student");
            System.out.println("2. Admin");
            System.out.print("Please enter the number (1/2): ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1 || choice == 2) {
                    break; // Valid input
                } else {
                    System.out.println("Invalid selection. Please enter 1 for Student or 2 for Admin.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value (1 or 2).");
                scanner.nextLine(); // Clear invalid input
            }
        }

        String role = (choice == 1) ? "Student" : "Admin";

        if (role.equals("Student")) {
            // Prompt for Student ID
            while (true) {
                System.out.print("Enter your Student ID: ");
                try {
                    studentId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    break; // Valid student ID
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a numeric Student ID.");
                    scanner.nextLine(); // Clear invalid input
                }
            }

            // Prompt for password
            System.out.print("Enter password for Student: ");
            String password = scanner.nextLine();

            // Validate password
            if (password.equals("student")) {
                System.out.println("Access granted. Welcome Student!");
                return role; // Successfully authenticated
            } else {
                System.out.println("Access denied! Invalid password.");
                studentId = -1; // Reset student ID
                return null; // Authentication failed
            }
        } else {
            // Prompt for Admin password
            System.out.print("Enter password for Admin: ");
            String password = scanner.nextLine();

            // Validate password
            if (password.equals("admin")) {
                System.out.println("Access granted. Welcome Admin!");
                return role; // Successfully authenticated
            } else {
                System.out.println("Access denied! Invalid password.");
                return null; // Authentication failed
            }
        }
    }

    /**
     * Retrieves the authenticated Student ID.
     *
     * @return the student ID, or -1 if no valid student ID was entered
     */
    public static int getStudentId() {
        return studentId;
    }
}
