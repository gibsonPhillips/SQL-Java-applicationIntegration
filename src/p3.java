//James Cao, Gibson Phillips, Nathan Wong

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class p3 {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("You need to include your UserID and Password parameters on the command line");
            return;
        }

        if (args.length == 2 && EstablishConnection(args[0], args[1])) {
            System.out.println("Include the number of the following menu item as the third parameter on the command line.");
            System.out.println("1 - Report Patient Information");
            System.out.println("2 - Report Employee Information");
            System.out.println("3 - Update Employee's Password");
            return;
        }

        if (args.length == 3 && EstablishConnection(args[0], args[1])) {
            try {
                int state = Integer.parseInt(args[2]);
                new Database(args[0], args[1], state);
            } catch (NumberFormatException e) {
                System.out.println("Invalid command line input");
            }
        }
        else {
            System.out.println("Invalid command line inputs");
        }
    }

    public static boolean EstablishConnection(String username, String password) {
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        System.out.println("-------- Step 1: Registering Oracle Driver ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver? Did you follow the execution steps. ");
            System.out.println("");
            System.out.println("*****Open the file and read the comments in the beginning of the file****");
            System.out.println("");
            e.printStackTrace();
            return false;
        }

        System.out.println("Oracle JDBC Driver Registered Successfully !");
        System.out.println("-------- Step 2: Building a Connection ------");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl",
                    username,
                    password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return false;
        }

        if (connection != null) {
            System.out.println("You made it. Connection is successful. Take control of your database now!");
        } else {
            System.out.println("Failed to make connection!");
            return false;
        }

        try {
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Connection could not close");
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
