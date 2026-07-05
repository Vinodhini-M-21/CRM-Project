# CRM System (Zoho Placement Project)

A simple full-stack CRM built with plain HTML/CSS/JS frontend,
Java JDBC backend, and MySQL database. No frameworks.

## How the project works (important to understand)

This is a REAL connected full-stack app:

```
Browser (HTML/CSS/JS)  --fetch()-->  ApiServer.java (Java web server)  --JDBC-->  MySQL (crm_db)
```

- **Frontend (frontend/ folder)** — the CRM UI (login, dashboard,
  customers/leads/follow-ups pages). It uses `fetch()` to call a
  local Java web server instead of localStorage.
- **ApiServer.java** — a small web server built with Java's built-in
  `HttpServer` (no Spring Boot). It exposes endpoints like
  `/api/customers` that the frontend calls, and uses your DAO classes
  underneath to actually read/write MySQL.
- **DAO classes + DBConnection.java** — the JDBC layer that talks to
  MySQL, unchanged from before.
- **MainApp.java** — still there as an optional console tester if you
  just want to sanity-check the database without running the website.

So when you add a customer on the website, it really does travel:
website → ApiServer → CustomerDAO → MySQL table `customers`.

## Setup with XAMPP

### Step 1: Start MySQL in XAMPP
Open XAMPP Control Panel → click **Start** next to MySQL.

### Step 2: Create the database (phpMyAdmin)
Go to `http://localhost/phpmyadmin` → **SQL** tab → paste the
contents of `database/crm.sql` → click **Go**.

### Step 3: Download the MySQL Connector JAR
Download `mysql-connector-j-8.x.x.jar` from:
https://dev.mysql.com/downloads/connector/j/
Place the `.jar` file inside the `backend` folder.

### Step 4: Check DBConnection.java
XAMPP's default MySQL login is `root` with an EMPTY password —
this is already set in `DBConnection.java`:
```java
private static final String PASSWORD = ""; // XAMPP default = empty password
```

### Step 5: Compile the backend (VS Code terminal)
```bash
cd backend
javac -cp .;mysql-connector-j-8.x.x.jar *.java        (Windows)
javac -cp .:mysql-connector-j-8.x.x.jar *.java        (Mac/Linux)
```

### Step 6: Start the API server (keep this terminal running)
```bash
java -cp .;mysql-connector-j-8.x.x.jar ApiServer      (Windows)
java -cp .:mysql-connector-j-8.x.x.jar ApiServer      (Mac/Linux)
```
You should see:
```
CRM API Server running at http://localhost:8080
```
Leave this terminal open the whole time you're using the website.

### Step 7: Open the frontend
Open the `frontend` folder in VS Code → right-click `index.html` →
"Open with Live Server" (or just double-click the file).
Login with `admin` / `admin123`, then add a customer — check
phpMyAdmin's `customers` table and you'll see it appear there.

## Folder Structure

```
CRM-Project/
├── frontend/
│   ├── index.html       (Login page)
│   ├── dashboard.html   (Live counts)
│   ├── customers.html
│   ├── leads.html
│   ├── followups.html
│   ├── style.css
│   └── script.js        (calls ApiServer using fetch)
├── backend/
│   ├── DBConnection.java
│   ├── Customer.java
│   ├── Lead.java
│   ├── FollowUp.java
│   ├── CustomerDAO.java
│   ├── LeadDAO.java
│   ├── FollowUpDAO.java
│   ├── JsonUtil.java     (tiny JSON parse/escape helper)
│   ├── ApiServer.java    (web server: frontend <-> MySQL bridge)
│   └── MainApp.java      (optional console tester)
└── database/
    └── crm.sql
```

## How to explain this in an interview (simple words)

- "Frontend" = what the user sees and clicks (like the CRM screens).
- "Backend" = the Java code that talks to the database.
- "ApiServer" = a small Java web server that sits between the
  website and the database — the browser sends it a request
  (e.g. "add this customer"), it runs the matching DAO method,
  and sends back a response. This is exactly what a real backend
  does, just built without a framework.
- "Database" = MySQL, where customer/lead/follow-up data is
  permanently stored in tables (like organized Excel sheets).
- "DAO" (Data Access Object) = a Java class whose only job is to
  read/write one specific table — keeps code organized.
- "CRUD" = Create, Read, Update, Delete — the 4 basic operations
  every system needs to manage data.
- "CORS" = a browser security rule that blocks requests between
  different ports/domains unless the server explicitly allows it —
  that's why `ApiServer.java` adds `Access-Control-Allow-Origin`
  headers to its responses.
