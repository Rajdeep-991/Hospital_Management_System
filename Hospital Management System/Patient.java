package Hospital_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient
{
    private Connection connection;
    private Scanner scanner;

    // Constructor to initialize the database connection and scanner object
    public Patient(Connection connection, Scanner scanner)
    {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Method to add a new patient to the database
    public void addPatient()
    {
        System.out.print("\nEnter Patient Name : ");
        String name = scanner.nextLine();  // Read patient name
        System.out.print("\nEnter Patient Age : ");
        int age = Integer.parseInt(scanner.nextLine());  // Read patient age
        System.out.print("\nEnter Patient Gender : ");
        String gender = scanner.nextLine();  // Read patient gender
        try{
            // SQL query to insert patient details into the Patients table
            String query = "insert into Patients(Name, Age, Gender) values(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameters for the prepared statement
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            // Execute the query and get the number of affected rows
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0)
                System.out.println("\nPATIENT DETAILS ADDED SUCCESSFULLY");
            else
                System.out.println("\nFAILED TO ADD PATIENT DETAILS");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Method to view all patients in the database
    public void viewPatient()
    {
        String query = "select * from Patients";  // SQL query to select all patients
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Print table header
            System.out.println("\nPATIENTS TABLE");
            System.out.println("--------------\n");
            System.out.println("+----+--------------------------+-----+--------+");
            System.out.println("| ID | Name                     | Age | Gender |");
            System.out.println("+----+--------------------------+-----+--------+");
            // Iterate through the result set and print each patient's details
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String gender = resultSet.getString("Gender");
                System.out.printf("| %-3s| %-25s| %-4s| %-7s|\n", id, name, age, gender);
                System.out.println("+----+--------------------------+-----+--------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Method to check if a patient exists in the database by ID
    public boolean checkPatient(int id)
    {
        String query = "select * from Patients where ID = ?";  // SQL query to select patient by ID
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);  // Set the ID parameter
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return true;
            else
                return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
