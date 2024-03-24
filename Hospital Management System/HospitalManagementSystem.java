package Hospital_Management_System;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem
{
    private static final String url = "jdbc:mysql://localhost:3306/hospital_management_system";
    private static final String username = "********************";
    private static final String password = "********************";

    public static void main(String args[])
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while(true)
            {
                System.out.println("\nHOSPITAL MANAGEMENT SYSTEM");
                System.out.println("--------------------------\n");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. View Appointments");
                System.out.println("6. Exit");
                System.out.print("\nEnter your choice : ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch(choice)
                {
                    case 1:
                        patient.addPatient();
                        System.out.println("\n\n");
                    break;
                    case 2:
                        patient.viewPatient();
                        System.out.println("\n\n");
                    break;
                    case 3:
                        doctor.viewDoctor();
                        System.out.println("\n\n");
                    break;
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println("\n\n");
                    break;
                    case 5:
                        viewAppointment(patient, doctor, connection, scanner);
                        System.out.println("\n\n");
                    break;
                    case 6:
                        System.out.println("\nTHANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM");
                        return;
                    default:
                        System.out.println("\nINVALID CHOICE\n\n\n");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner)
    {
        System.out.print("\nEnter Patient ID : ");
        int patientID = Integer.parseInt(scanner.nextLine());
        System.out.print("\nEnter Doctor ID : ");
        int doctorID = Integer.parseInt(scanner.nextLine());
        System.out.print("\nEnter Appointment Date (YYYY-MM-DD) : ");
        String appointmentDate = scanner.nextLine();
        if(patient.checkPatient(patientID) && doctor.checkDoctor(doctorID))
        {
            if(checkDoctorAvailability(doctorID, appointmentDate, connection))
            {
                String appointmentQuery = "insert into Appointments(Patient_ID, Doctor_ID, Appointment_Date) values(?, ?, ?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientID);
                    preparedStatement.setInt(2, doctorID);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected > 0)
                        System.out.println("\nAPPOINTMENT BOOKED SUCCESSFULLY");
                    else
                        System.out.println("\nFAILED TO BOOK APPOINTMENT");
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else
                System.out.println("\nDOCTOR NOT AVAILABLE ON THIS DATE");
        }
        else
            System.out.println("\nPATIENT OR DOCTOR DOES NOT EXIST");
    }

    public static void viewAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner)
    {
        String query = "select * from Appointments";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("\nAPPOINTMENTS TABLE");
            System.out.println("------------------\n");
            System.out.println("+----+------------+-----------+------------------+");
            System.out.println("| ID | Patient_ID | Doctor_ID | Appointment_Date |");
            System.out.println("+----+------------+-----------+------------------+");
            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                int patientID = resultSet.getInt("Patient_ID");
                int doctorID = resultSet.getInt("Doctor_ID");
                String appointmentDate = resultSet.getString("Appointment_Date");
                System.out.printf("| %-3s| %-11s| %-10s| %-17s|\n", id, patientID, doctorID, appointmentDate);
                System.out.println("+----+------------+-----------+------------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection connection)
    {
        String query = "select count(*) from Appointments where Doctor_ID = ? and Appointment_Date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int count = resultSet.getInt(1);
                if(count == 0)
                    return true;
                else
                    return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}