import java.sql.*;
import java.util.Scanner;

public class Database {
    Connection connection;
    public Database(String username, String password, int state) {
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl", username, password);

            if (state == 1) {
                ReportPatientInformation();
            }
            else if (state == 2) {
                ReportEmployeeInformation();
            }
            else if (state == 3) {
                UpdateEmployeePassword();
            }

            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ReportPatientInformation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Patient First Name: ");
        String fName = scanner.nextLine();

        System.out.print("Enter Patient Last Name: ");
        String lName = scanner.nextLine();

        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Patient WHERE firstName = '" + fName + "' AND lastName = '" + lName + "'");

            System.out.println("Patient Information");
            while (rs.next()) {
                System.out.println("Patient ID: " + rs.getString("PATIENTID"));
                System.out.println("Patient Name: " + rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME"));
                System.out.println("Address: " + rs.getString("CITY") + ", " + rs.getString("STATE"));
                System.out.println();
            }

            rs.close();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ReportEmployeeInformation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Employee ID: ");
        int empId = scanner.nextInt();

        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Employee WHERE employeeID = " + empId);

            while (rs.next()) {
                String npi = rs.getString("NPI");
                String salaryGrade = rs.getString("SALARYGRADE");
                String securityClearance = rs.getString("SECURITYCLEARANCE");
                String name = (npi != null) ? "Dr. " + rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME") : rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME");

                System.out.println("Employee Information");
                System.out.println("Employee ID: " + rs.getString("EMPLOYEEID"));
                if (npi != null) { System.out.println("NPI: " + npi); }
                System.out.println("Employee Name: " + name);
                System.out.println("Username: " + rs.getString("USERNAME"));
                System.out.println("Password: " + rs.getString("PASSWORD"));
                if (salaryGrade != null) { System.out.println("Salary Grade: " + salaryGrade); }
                if (securityClearance != null) { System.out.println("Security Clearance: " + securityClearance); }
            }

            rs.close();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void UpdateEmployeePassword() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the employee ID: ");
        String empID = scanner.nextLine();

        System.out.print("Enter the updated password: ");
        String newPassword = scanner.nextLine();

        try {
            Statement s = connection.createStatement();

            String sql = "UPDATE Employee SET password = '" + newPassword + "' WHERE employeeID = " + empID;
            int result = s.executeUpdate(sql);

            if (result == 1) {
                System.out.println("Your password was updated.");
            }

            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
