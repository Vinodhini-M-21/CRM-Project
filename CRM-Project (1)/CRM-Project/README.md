# CRM System

A simple full-stack CRM built with plain HTML/CSS/JS frontend, Java JDBC backend, and MySQL database. No frameworks.

## Features
- Manage customers, leads, and follow-ups
- Dashboard with live counts
- REST API built using Java's built-in HttpServer
- Data persisted in MySQL

## Tech Stack
- Frontend: HTML, CSS, JavaScript
- Backend: Java (JDBC, HttpServer)
- Database: MySQL

## How to Run
1. Make sure MySQL is running and the `crm_db` database is set up
2. Navigate to the `backend` folder
3. Compile: `javac -cp ".;mysql-connector-j-9.7.0.jar" *.java`
4. Run: `java -cp ".;mysql-connector-j-9.7.0.jar" ApiServer`
5. Open the frontend (`index.html`) in your browser
