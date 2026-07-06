const API_BASE = "http://localhost:8080/api";


// ---------------------------------------------
// SECTION 1: LOGIN / LOGOUT

function login() {
  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value.trim();

  if (username === "admin" && password === "admin123") {
    localStorage.setItem("loggedIn", "true");
    window.location.href = "dashboard.html";
  } else {
    document.getElementById("error-msg").innerText = "Invalid username or password!";
  }
}

function logout() {
  localStorage.removeItem("loggedIn");
  window.location.href = "index.html";
}


// ---------------------------------------------
// SECTION 2: DASHBOARD COUNTS
// ---------------------------------------------

async function updateDashboardCounts() {
  try {
    const res = await fetch(`${API_BASE}/counts`);
    const data = await res.json();

    document.getElementById("customerCount").innerText = data.customers;
    document.getElementById("leadCount").innerText = data.leads;
    document.getElementById("followupCount").innerText = data.followups;
  } catch (err) {
    console.error("Could not load dashboard counts:", err);
    alert("Could not connect to the server. Is ApiServer.java running?");
  }
}


// ---------------------------------------------
// SECTION 3: CUSTOMERS PAGE
// ---------------------------------------------

async function loadCustomers() {
  try {
    const res = await fetch(`${API_BASE}/customers`);
    const customers = await res.json();

    const tbody = document.getElementById("customerTableBody");
    tbody.innerHTML = "";

    customers.forEach((cust) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${cust.name}</td>
        <td>${cust.email}</td>
        <td>${cust.phone}</td>
        <td>${cust.company}</td>
        <td><button class="delete-btn" onclick="deleteCustomer(${cust.id})">Delete</button></td>
      `;
      tbody.appendChild(row);
    });
  } catch (err) {
    console.error("Could not load customers:", err);
    alert("Could not connect to the server. Is ApiServer.java running?");
  }
}

async function addCustomer() {
  const name = document.getElementById("custName").value.trim();
  const email = document.getElementById("custEmail").value.trim();
  const phone = document.getElementById("custPhone").value.trim();
  const company = document.getElementById("custCompany").value.trim();

  if (name === "") {
    alert("Customer name is required!");
    return;
  }

  await fetch(`${API_BASE}/customers`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email, phone, company })
  });

  document.getElementById("custName").value = "";
  document.getElementById("custEmail").value = "";
  document.getElementById("custPhone").value = "";
  document.getElementById("custCompany").value = "";

  loadCustomers();
}

async function deleteCustomer(id) {
  await fetch(`${API_BASE}/customers?id=${id}`, { method: "DELETE" });
  loadCustomers();
}


// ---------------------------------------------
// SECTION 4: LEADS PAGE
// ---------------------------------------------

async function loadLeads() {
  try {
    const res = await fetch(`${API_BASE}/leads`);
    const leads = await res.json();

    const tbody = document.getElementById("leadTableBody");
    tbody.innerHTML = "";

    leads.forEach((lead) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${lead.name}</td>
        <td>${lead.email}</td>
        <td>${lead.phone}</td>
        <td>${lead.status}</td>
        <td>${lead.source}</td>
        <td><button class="delete-btn" onclick="deleteLead(${lead.id})">Delete</button></td>
      `;
      tbody.appendChild(row);
    });
  } catch (err) {
    console.error("Could not load leads:", err);
    alert("Could not connect to the server. Is ApiServer.java running?");
  }
}

async function addLead() {
  const name = document.getElementById("leadName").value.trim();
  const email = document.getElementById("leadEmail").value.trim();
  const phone = document.getElementById("leadPhone").value.trim();
  const status = document.getElementById("leadStatus").value;
  const source = document.getElementById("leadSource").value.trim();

  if (name === "") {
    alert("Lead name is required!");
    return;
  }

  await fetch(`${API_BASE}/leads`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email, phone, status, source })
  });

  document.getElementById("leadName").value = "";
  document.getElementById("leadEmail").value = "";
  document.getElementById("leadPhone").value = "";
  document.getElementById("leadSource").value = "";

  loadLeads();
}

async function deleteLead(id) {
  await fetch(`${API_BASE}/leads?id=${id}`, { method: "DELETE" });
  loadLeads();
}


// ---------------------------------------------
// SECTION 5: FOLLOW-UPS PAGE
// ---------------------------------------------

async function loadFollowUps() {
  try {
    const res = await fetch(`${API_BASE}/followups`);
    const followups = await res.json();

    const tbody = document.getElementById("followupTableBody");
    tbody.innerHTML = "";

    followups.forEach((fu) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${fu.customerName}</td>
        <td>${fu.date}</td>
        <td>${fu.notes}</td>
        <td>${fu.status}</td>
        <td><button class="delete-btn" onclick="deleteFollowUp(${fu.id})">Delete</button></td>
      `;
      tbody.appendChild(row);
    });
  } catch (err) {
    console.error("Could not load follow-ups:", err);
    alert("Could not connect to the server. Is ApiServer.java running?");
  }
}

async function addFollowUp() {
  const customerName = document.getElementById("fuCustomerName").value.trim();
  const date = document.getElementById("fuDate").value;
  const notes = document.getElementById("fuNotes").value.trim();
  const status = document.getElementById("fuStatus").value;

  if (customerName === "" || date === "") {
    alert("Customer name and date are required!");
    return;
  }

  await fetch(`${API_BASE}/followups`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ customerName, date, notes, status })
  });

  document.getElementById("fuCustomerName").value = "";
  document.getElementById("fuDate").value = "";
  document.getElementById("fuNotes").value = "";

  loadFollowUps();
}

async function deleteFollowUp(id) {
  await fetch(`${API_BASE}/followups?id=${id}`, { method: "DELETE" });
  loadFollowUps();
}
