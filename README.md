Hospital Management System
--------------------------

Overview
--------
This project is a simple hospital management system developed in Java. It includes functionalities for managing patients, doctors, and appointments. The system interacts with a MySQL database to store and retrieve data.

Features
--------
- Patient Management:
  - Add new patients
  - View all patients
  - Check if a patient exists by ID

- Doctor Management:
  - View all doctors
  - Check if a doctor exists by ID

- Appointment Management:
  - Book appointments
  - View all appointments
  - Check doctor availability for a specific date

Requirements
------------
- Java Development Kit (JDK)
- MySQL Database
- MySQL Connector/J (JDBC Driver)

How to Use
----------
- Database Setup:
  - Create a MySQL database named hospital_management_system.
  - Create tables Patients, Doctors, and Appointments with appropriate columns.
  - Update the database connection details in HospitalManagementSystem.java with your MySQL username and password.

- Compile and Run:
  - Compile the Java files:

    javac -d . *.java
  - Run the main class:

    java Hospital_Management_System.HospitalManagementSystem

- Interacting with the System:
  - Use the main menu displayed in the console to navigate through the options for managing patients, doctors, and appointments.

Tables Structure
----------------
Ensure the database tables are created with the following structure:

- CREATE TABLE Patients (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(50),
    Age INT,
    Gender VARCHAR(10)
);

- CREATE TABLE Doctors (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(50),
    Specialization VARCHAR(50)
);

- CREATE TABLE Appointments (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Patient_ID INT,
    Doctor_ID INT,
    Appointment_Date DATE,
    FOREIGN KEY (Patient_ID) REFERENCES Patients(ID),
    FOREIGN KEY (Doctor_ID) REFERENCES Doctors(ID)
);

How It Works
------------
The Hospital Management System is designed to simplify and automate the process of managing patients, doctors, and appointments in a hospital setting. Here's a detailed breakdown of how each component of the system works:

- Database Connection
  - Setup: The system connects to a MySQL database using JDBC (Java Database Connectivity).
  - Configuration: Database connection details (URL, username, and password) are specified in the HospitalManagementSystem.java file.
  - Driver Loading: The MySQL JDBC driver is loaded using Class.forName("com.mysql.cj.jdbc.Driver");.

- Patient Management
  - Adding Patients:
    - The addPatient() method in the Patient class prompts the user to enter patient details (name, age, gender).
    - These details are inserted into the Patients table in the database using an INSERT SQL query.
    - The system confirms whether the patient details were added successfully.
  
  - Viewing Patients:
    - The viewPatient() method retrieves all records from the Patients table.
    - It displays the patient details in a formatted table.
  
  - Checking Patient Existence:
    - The checkPatient() method checks if a patient exists in the database by their ID.
    - This is done using a SELECT query with the patient ID as a parameter.

- Doctor Management
  - Viewing Doctors:
    - The viewDoctor() method retrieves all records from the Doctors table.
    - It displays the doctor details (ID, name, specialization) in a formatted table.
  
  - Checking Doctor Existence:
    - The checkDoctor() method checks if a doctor exists in the database by their ID.
    - This is done using a SELECT query with the doctor ID as a parameter.

- Appointment Management
  - Booking Appointments:
    - The bookAppointment() method allows users to book an appointment by entering the patient ID, doctor ID, and appointment date.
    - The system first checks if the patient and doctor exist using the checkPatient() and checkDoctor() methods.
    - It then checks if the doctor is available on the specified date using the checkDoctorAvailability() method.
    - If all checks pass, the appointment details are inserted into the Appointments table using an INSERT SQL query.
    - The system confirms whether the appointment was booked successfully.
  
  - Viewing Appointments:
    - The viewAppointment() method retrieves all records from the Appointments table.
    - It displays the appointment details (ID, patient ID, doctor ID, appointment date) in a formatted table.
  
  - Checking Doctor Availability:
    - The checkDoctorAvailability() method checks if a doctor is available on a given date by counting the number of appointments for that doctor on the specified date.
    - This is done using a SELECT query with the doctor ID and appointment date as parameters.

Notes
-----
- Ensure the database is running before starting the application.
- Handle SQL exceptions appropriately in production-level code.
- Add validations for user inputs to enhance the robustness of the application.
