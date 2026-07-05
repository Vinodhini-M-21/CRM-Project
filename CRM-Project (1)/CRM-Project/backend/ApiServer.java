// ==========================================
// ApiServer.java
// This is the "bridge" between your website and MySQL.
//
// It starts a small web server (using Java's built-in
// HttpServer — no Spring Boot needed) that your frontend
// JavaScript talks to using fetch().
//
// Flow:
//   Browser (script.js) --> ApiServer (this file) --> DAO classes --> MySQL
//
// Run this file (instead of MainApp.java) whenever you
// want the website to actually save data into MySQL.
// ==========================================

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ApiServer {

    static CustomerDAO customerDAO = new CustomerDAO();
    static LeadDAO leadDAO = new LeadDAO();
    static FollowUpDAO followUpDAO = new FollowUpDAO();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/customers", ApiServer::handleCustomers);
        server.createContext("/api/leads", ApiServer::handleLeads);
        server.createContext("/api/followups", ApiServer::handleFollowUps);
        server.createContext("/api/counts", ApiServer::handleCounts);

        server.setExecutor(null); // use default single-threaded executor
        server.start();

        System.out.println("=========================================");
        System.out.println(" CRM API Server running at http://localhost:8080");
        System.out.println(" Keep this terminal open while using the website.");
        System.out.println("=========================================");
    }

    // ---------------- CUSTOMERS ----------------
    static void handleCustomers(HttpExchange ex) throws IOException {
        addCorsHeaders(ex);
        String method = ex.getRequestMethod();

        if (method.equals("OPTIONS")) {
            ex.sendResponseHeaders(204, -1);
            return;
        }

        if (method.equals("GET")) {
            List<Customer> list = customerDAO.getAllCustomers();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                Customer c = list.get(i);
                json.append(String.format(
                    "{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\",\"company\":\"%s\"}",
                    c.getId(), esc(c.getName()), esc(c.getEmail()), esc(c.getPhone()), esc(c.getCompany())
                ));
                if (i < list.size() - 1) json.append(",");
            }
            json.append("]");
            sendResponse(ex, 200, json.toString());

        } else if (method.equals("POST")) {
            Map<String, String> data = JsonUtil.parseObject(readBody(ex));
            Customer c = new Customer(0,
                data.getOrDefault("name", ""),
                data.getOrDefault("email", ""),
                data.getOrDefault("phone", ""),
                data.getOrDefault("company", ""));
            customerDAO.addCustomer(c);
            sendResponse(ex, 201, "{\"message\":\"Customer added\"}");

        } else if (method.equals("DELETE")) {
            int id = getIdFromQuery(ex);
            customerDAO.deleteCustomer(id);
            sendResponse(ex, 200, "{\"message\":\"Customer deleted\"}");

        } else {
            sendResponse(ex, 405, "{\"error\":\"Method not allowed\"}");
        }
    }

    // ---------------- LEADS ----------------
    static void handleLeads(HttpExchange ex) throws IOException {
        addCorsHeaders(ex);
        String method = ex.getRequestMethod();

        if (method.equals("OPTIONS")) {
            ex.sendResponseHeaders(204, -1);
            return;
        }

        if (method.equals("GET")) {
            List<Lead> list = leadDAO.getAllLeads();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                Lead l = list.get(i);
                json.append(String.format(
                    "{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\",\"status\":\"%s\",\"source\":\"%s\"}",
                    l.getId(), esc(l.getName()), esc(l.getEmail()), esc(l.getPhone()), esc(l.getStatus()), esc(l.getSource())
                ));
                if (i < list.size() - 1) json.append(",");
            }
            json.append("]");
            sendResponse(ex, 200, json.toString());

        } else if (method.equals("POST")) {
            Map<String, String> data = JsonUtil.parseObject(readBody(ex));
            Lead l = new Lead(0,
                data.getOrDefault("name", ""),
                data.getOrDefault("email", ""),
                data.getOrDefault("phone", ""),
                data.getOrDefault("status", "New"),
                data.getOrDefault("source", ""));
            leadDAO.addLead(l);
            sendResponse(ex, 201, "{\"message\":\"Lead added\"}");

        } else if (method.equals("DELETE")) {
            int id = getIdFromQuery(ex);
            leadDAO.deleteLead(id);
            sendResponse(ex, 200, "{\"message\":\"Lead deleted\"}");

        } else {
            sendResponse(ex, 405, "{\"error\":\"Method not allowed\"}");
        }
    }

    // ---------------- FOLLOWUPS ----------------
    static void handleFollowUps(HttpExchange ex) throws IOException {
        addCorsHeaders(ex);
        String method = ex.getRequestMethod();

        if (method.equals("OPTIONS")) {
            ex.sendResponseHeaders(204, -1);
            return;
        }

        if (method.equals("GET")) {
            List<FollowUp> list = followUpDAO.getAllFollowUps();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                FollowUp f = list.get(i);
                json.append(String.format(
                    "{\"id\":%d,\"customerName\":\"%s\",\"date\":\"%s\",\"notes\":\"%s\",\"status\":\"%s\"}",
                    f.getId(), esc(f.getCustomerName()), esc(f.getFollowUpDate()), esc(f.getNotes()), esc(f.getStatus())
                ));
                if (i < list.size() - 1) json.append(",");
            }
            json.append("]");
            sendResponse(ex, 200, json.toString());

        } else if (method.equals("POST")) {
            Map<String, String> data = JsonUtil.parseObject(readBody(ex));
            FollowUp f = new FollowUp(0,
                data.getOrDefault("customerName", ""),
                data.getOrDefault("date", ""),
                data.getOrDefault("notes", ""),
                data.getOrDefault("status", "Pending"));
            followUpDAO.addFollowUp(f);
            sendResponse(ex, 201, "{\"message\":\"Follow-up added\"}");

        } else if (method.equals("DELETE")) {
            int id = getIdFromQuery(ex);
            followUpDAO.deleteFollowUp(id);
            sendResponse(ex, 200, "{\"message\":\"Follow-up deleted\"}");

        } else {
            sendResponse(ex, 405, "{\"error\":\"Method not allowed\"}");
        }
    }

    // ---------------- COUNTS (for dashboard cards) ----------------
    static void handleCounts(HttpExchange ex) throws IOException {
        addCorsHeaders(ex);
        if (ex.getRequestMethod().equals("OPTIONS")) {
            ex.sendResponseHeaders(204, -1);
            return;
        }
        int c = customerDAO.getCustomerCount();
        int l = leadDAO.getLeadCount();
        int f = followUpDAO.getFollowUpCount();
        String json = String.format("{\"customers\":%d,\"leads\":%d,\"followups\":%d}", c, l, f);
        sendResponse(ex, 200, json);
    }

    // ---------------- HELPER METHODS ----------------

    // Allows the browser (running on a different port, e.g. Live Server 5500)
    // to call this server (running on port 8080) without being blocked.
    static void addCorsHeaders(HttpExchange ex) {
        ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        ex.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    static String readBody(HttpExchange ex) throws IOException {
        return new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    static int getIdFromQuery(HttpExchange ex) {
        // Expects a URL like /api/customers?id=5
        String query = ex.getRequestURI().getQuery();
        return Integer.parseInt(query.split("=")[1]);
    }

    static void sendResponse(HttpExchange ex, int statusCode, String response) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "application/json");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = ex.getResponseBody();
        os.write(bytes);
        os.close();
    }

    static String esc(String s) {
        return JsonUtil.escape(s);
    }
}
