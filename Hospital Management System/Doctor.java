package Hospital_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor
{
    private Connection connection;

    // Constructor to initialize the database connection
    public Doctor(Connection connection)
    {
        this.connection = connection;
    }

    // Method to view all doctors in the database
    public void viewDoctor()
    {
        String query = "select * from Doctors";  // SQL query to select all doctors
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Print table header
            System.out.println("\nDOCTORS TABLE");
            System.out.println("-------------\n");
            System.out.println("+----+--------------------------+----------------+");
            System.out.println("| ID | Name                     | Specialization |");
            System.out.println("+----+--------------------------+------+---------+");
            // Iterate through the result set and print each doctor's details
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String specialization = resultSet.getString("Specialization");
                System.out.printf("| %-3s| %-25s| %-15s|\n", id , name, specialization);
                System.out.println("+----+--------------------------+----------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Method to check if a doctor exists in the database by ID
    public boolean checkDoctor(int id)
    {
        String query = "select * from Doctors where ID = ?";  // SQL query to select doctor by ID
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
