// ==========================================
// LeadDAO.java
// Handles all database operations (CRUD) for the "leads" table.
// ==========================================

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeadDAO {

    // ---- CREATE: Add a new lead ----
    public void addLead(Lead l) {
        String sql = "INSERT INTO leads (name, email, phone, status, source) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getName());
            ps.setString(2, l.getEmail());
            ps.setString(3, l.getPhone());
            ps.setString(4, l.getStatus());
            ps.setString(5, l.getSource());

            ps.executeUpdate();
            System.out.println("Lead added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---- READ: Get all leads ----
    public List<Lead> getAllLeads() {
        List<Lead> list = new ArrayList<>();
        String sql = "SELECT * FROM leads";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Lead l = new Lead(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("status"),
                        rs.getString("source")
                );
                list.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ---- DELETE: Remove a lead by ID ----
    public void deleteLead(int id) {
        String sql = "DELETE FROM leads WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Lead deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---- COUNT: Used for dashboard "Total Leads" ----
    public int getLeadCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM leads";

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
