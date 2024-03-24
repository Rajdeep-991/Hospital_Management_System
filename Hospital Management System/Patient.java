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

    public Patient(Connection connection, Scanner scanner)
    {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient()
    {
        System.out.print("\nEnter Patient Name : ");
        String name = scanner.nextLine();
        System.out.print("\nEnter Patient Age : ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("\nEnter Patient Gender : ");
        String gender = scanner.nextLine();
        try{
            String query = "insert into Patients(Name, Age, Gender) values(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0)
                System.out.println("\nPATIENT DETAILS ADDED SUCCESSFULLY");
            else
                System.out.println("\nFAILED TO ADD PATIENT DETAILS");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatient()
    {
        String query = "select * from Patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("\nPATIENTS TABLE");
            System.out.println("--------------\n");
            System.out.println("+----+--------------------------+-----+--------+");
            System.out.println("| ID | Name                     | Age | Gender |");
            System.out.println("+----+--------------------------+-----+--------+");
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

    public boolean checkPatient(int id)
    {
        String query = "select * from Patients where ID = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
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