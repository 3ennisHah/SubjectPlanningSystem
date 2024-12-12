import java.util.Scanner;

public class AuthenticationCheck {

    /**
     * Authenticates the user based on role selection and password.
     *
     * @return true if authentication is successful, false otherwise
     */
    public static boolean authenticate() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for login type
        System.out.println("Login: ");
        System.out.println("1. Student");
        System.out.println("2. Admin");
        System.out.print("Please enter the number (1/2): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String role = choice == 1 ? "Student" : choice == 2 ? "Admin" : null;

        // Invalid input handling
        if (role == null) {
            System.out.println("Invalid selection. Access denied!");
            return false;
        }

        // Prompt for password
        System.out.print("Enter password for " + role + ": ");
        String password = scanner.nextLine();

        // Validate password
        if ((role.equals("Student") && password.equals("student")) ||
                (role.equals("Admin") && password.equals("admin"))) {
            System.out.println("Access granted. Welcome " + role + "!");
            return true;
        } else {
            System.out.println("Access denied!");
            return false;
        }
    }
}
