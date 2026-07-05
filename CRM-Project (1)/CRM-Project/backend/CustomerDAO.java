// ==========================================
// CustomerDAO.java
// DAO = "Data Access Object"
// This class contains all the database operations (CRUD)
// for the "customers" table.
// ==========================================

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // ---- CREATE: Add a new customer ----
    public void addCustomer(Customer c) {
        String sql = "INSERT INTO customers (name, email, phone, company) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getCompany());

            ps.executeUpdate();
            System.out.println("Customer added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---- READ: Get all customers ----
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("company")
                );
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ---- DELETE: Remove a customer by ID ----
    public void deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Customer deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---- COUNT: Used for dashboard "Total Customers" ----
    public int getCustomerCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM customers";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
