// ==========================================
// DBConnection.java
// This class connects Java to MySQL database.
// Every DAO class uses this to talk to the database.
// ==========================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ---- CHANGE THESE VALUES TO MATCH YOUR MYSQL SETUP ----
    // NOTE: XAMPP's default MySQL login is username "root" with an EMPTY password.
    private static final String URL = "jdbc:mysql://localhost:3306/crm_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // XAMPP default = empty password

    // This method returns a live connection to the database
    public static Connection getConnection() {
        Connection con = null;
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create connection
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Did you add the connector JAR?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
        return con;
    }

    // Quick test: run this file directly to check if connection works
    public static void main(String[] args) {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("✅ Connected to database successfully!");
        } else {
            System.out.println("❌ Connection failed.");
        }
    }
}
