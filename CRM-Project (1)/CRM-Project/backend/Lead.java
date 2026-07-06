
public class Lead {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String status;
    private String source;

    public Lead() {
    }

    public Lead(int id, String name, String email, String phone, String status, String source) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.source = source;
    }

    // ---- Getters and Setters ----
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + email + " | " + phone + " | " + status + " | " + source;
    }
}
