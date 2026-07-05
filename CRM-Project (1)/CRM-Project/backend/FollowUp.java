// ==========================================
// FollowUp.java
// Model class for a "Follow-up" task with a customer
// ==========================================

public class FollowUp {
    private int id;
    private String customerName;
    private String followUpDate; // stored as String in format YYYY-MM-DD
    private String notes;
    private String status;

    public FollowUp() {
    }

    public FollowUp(int id, String customerName, String followUpDate, String notes, String status) {
        this.id = id;
        this.customerName = customerName;
        this.followUpDate = followUpDate;
        this.notes = notes;
        this.status = status;
    }

    // ---- Getters and Setters ----
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getFollowUpDate() { return followUpDate; }
    public void setFollowUpDate(String followUpDate) { this.followUpDate = followUpDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return id + " | " + customerName + " | " + followUpDate + " | " + notes + " | " + status;
    }
}
