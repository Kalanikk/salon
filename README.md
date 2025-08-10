Diwya Salon Website

Tech stack: HTML, CSS, JavaScript (frontend) | Java (backend) | MySQL (database)

Project Overview

A simple, professional salon website that allows customers to browse services, view packages, view gallery images, register/login, and book appointments. The frontend is built with plain HTML/CSS/JavaScript. The backend is implemented in Java and connects to a MySQL database using JDBC (or a Java framework of your choice). This README explains how to set up and run the project locally, the database schema, API endpoints, and useful development notes.

Features

Home page with navigation (Home, Gallery, Services, Packages, Appointment, Contact)

Service gallery where clicking an image auto-fills the booking form with the selected service

Appointment booking form with date/time and customer details

Customer registration and login (simple session handling)

Admin panel (optional) to view appointments and manage services/packages

Static-friendly frontend that can be served from any static host

Prerequisites

Java Development Kit (JDK) 11 or newer

Maven or Gradle (if using a build tool)

MySQL Server (or MariaDB)

Git (optional)

A modern web browser

Folder Structure (recommended)

/diwya-salon
├── README.md
├── frontend
│   ├── index.html
│   ├── assets/
│   │   ├── css/
│   │   ├── js/
│   │   └── images/
│   └── pages/
│       ├── gallery.html
│       ├── services.html
│       ├── appointment.html
│       └── login.html
├── backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/diwya/...
│   │   │   └── resources/application.properties
│   └── pom.xml (or build.gradle)
└── sql
    └── diwya_schema.sql

Database - Example Schema

Save this as sql/diwya_schema.sql and import to your MySQL server.

CREATE DATABASE IF NOT EXISTS diwya_salon CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE diwya_salon;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  phone VARCHAR(20),
  password_hash VARCHAR(255) NOT NULL,
  role ENUM('CUSTOMER','ADMIN') DEFAULT 'CUSTOMER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE services (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  description TEXT,
  price DECIMAL(8,2) NOT NULL,
  image VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  service_id INT NOT NULL,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  notes TEXT,
  status ENUM('BOOKED','COMPLETED','CANCELLED') DEFAULT 'BOOKED',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

-- Optional sample data
INSERT INTO services(name, description, price) VALUES
('Haircut', 'Basic haircut service', 1200.00),
('Manicure', 'Classic manicure', 800.00);

Backend (Java) - Quick Setup

This section assumes a simple Java backend using JDBC. If you use Spring Boot, adapt application.properties accordingly.

Configure database connection in backend/src/main/resources/application.properties (or a config file):

db.url=jdbc:mysql://localhost:3306/diwya_salon?useSSL=false&serverTimezone=UTC
db.user=root
db.password=your_mysql_password

If using plain Java + Maven:

Ensure pom.xml includes the MySQL JDBC driver dependency (mysql:mysql-connector-java) and any web server dependencies (e.g., Jetty or if using servlets javax.servlet-api).

Typical flow in backend code:

Establish JDBC connection using the properties above.

Implement DAOs for Users, Services, Appointments (CRUD operations).

Expose REST endpoints (recommended) or serve HTML via servlets.

Example REST endpoints (suggested):

POST /api/auth/register     -> register a new user
POST /api/auth/login        -> login and return a session/token
GET  /api/services          -> list all services
GET  /api/services/{id}     -> service details
POST /api/appointments      -> create a new appointment
GET  /api/appointments     -> (admin) list appointments
GET  /api/users/{id}        -> get user profile

Frontend - Quick Setup

The frontend is static files in frontend/.

index.html is the landing page with navigation links to other pages.

frontend/assets/js/app.js handles UI interactions:

Service click -> open booking modal and auto-fill service name & price

Booking form submit -> send fetch() POST to /api/appointments

Login/register -> POST to /api/auth/login or /api/auth/register

During development you can serve the frontend with any static server, or open index.html directly in the browser (if your backend uses relative API paths, use a local server to avoid CORS/file:// restrictions). Example static server using Python:

# from frontend folder
python3 -m http.server 5500
# then open http://localhost:5500

CORS and Running Locally

If frontend and backend run on different ports, enable CORS on the backend or use a proxy. For a simple dev environment, configure your backend to allow http://localhost:5500 (or whatever port) origins.

Environment Variables (recommended)

Use environment variables for sensitive values:

DB_URL, DB_USER, DB_PASS

JWT_SECRET (if using JSON Web Tokens for auth)

Building and Running

With Maven (example):

# from backend folder
mvn clean package
java -jar target/diwya-backend.jar

Without build tool (simple run):

Compile Java sources and run the main class that starts the server.

Testing

Use Postman or curl to test REST endpoints.

Example appointment POST:

curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{"userId":1, "serviceId":2, "appointmentDate":"2025-08-15", "appointmentTime":"14:30:00", "notes":"Please be on time"}'

Deployment Tips

Host backend on any Java-capable host or containerize with Docker.

Use a managed MySQL instance in production or RDS/Aurora.

Serve frontend via Netlify, GitHub Pages, or the same server as your backend.

Use HTTPS in production and secure cookies / auth tokens.

