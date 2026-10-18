

package studentassis.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database Information
   private static final String URL =
        "jdbc:oracle:thin:@localhost:1521:XE";

private static final String USER =
        "STUDENTASSIST";

private static final String PASSWORD =
        "pass";
    // Method To Connect
    public static Connection getConnection() {

        try {

            // Solve Oracle Timezone Problem
            System.setProperty("oracle.jdbc.timezoneAsRegion", "false");

            // Load Oracle Driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Create Connection
            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            return con;

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
            return null;
        }
    }
}