// ==========================================
// FollowUpDAO.java
// Handles all database operations (CRUD) for the "followups" table.
// ==========================================

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FollowUpDAO {

    // ---- CREATE: Add a new follow-up ----
    public void addFollowUp(FollowUp f) {
        String sql = "INSERT INTO followups (customer_name, followup_date, notes, status) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getCustomerName());
            ps.setDate(2, Date.valueOf(f.getFollowUpDate())); // format: YYYY-MM-DD
            ps.setString(3, f.getNotes());
            ps.setString(4, f.getStatus());

            ps.executeUpdate();
            System.out.println("Follow-up added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---- READ: Get all follow-ups ----
    public List<FollowUp> getAllFollowUps() {
        List<FollowUp> list = new ArrayList<>();
        String sql = "SELECT * FROM followups";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                FollowUp f = new FollowUp(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("followup_date"),
                        rs.getString("notes"),
                        rs.getString("status")
                );
                list.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ---- DELETE: Remove a follow-up by ID ----
    public void deleteFollowUp(int id) {
        String sql = "DELETE FROM followups WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Follow-up deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---- COUNT: Used for dashboard "Total Follow-ups" ----
    public int getFollowUpCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM followups";

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
