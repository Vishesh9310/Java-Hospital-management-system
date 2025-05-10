import java.sql.*;
import java.util.*;

public class HospitalManagementsystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Optum@12";

    public static void main(String[] args) {
        try {
            Class.forName("con.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(conn, scanner);
            Doctor doctor = new Doctor(conn);
            Appointment appointment = new Appointment(conn);

            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. Update Patient");
                System.out.println("4. Delete Patient");
                System.out.println("5. View Doctors");
                System.out.println("6. Book Appointment");
                System.out.println("7. View Appointments");
                System.out.println("8. Exit");
                System.out.println("Enter your choice:");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        //Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                        
                    case 2:
                        //View Patient
                        patient.viewPatients();
                        System.out.println();
                        break;

                    case 3:
                        //Update Patient
                        patient.updatePatient();
                        System.out.println();
                        break;
                    
                    case 4:
                        //Update Patient
                        patient.deletePatient();
                        System.out.println();
                        break;
                        
                    case 5:
                        //View Doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                        
                    case 6:
                        //Book appointment
                        appointment.bookAppointment(patient, doctor, scanner);
                        System.out.println();
                        break;
                    
                    case 7:
                        //View appointment
                        appointment.viewAppointment();
                        System.out.println();
                        break;
                        
                    case 8:
                        //Exit
                        System.out.println("Thank You! for using Hospital Management System!!");
                        return;
                    default:
                        System.out.println("Enter valid Choice!!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}