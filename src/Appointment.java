import java.sql.*;
import java.util.*;

public class Appointment {

    private Connection connection;

    public Appointment(Connection connection){
        this.connection = connection;
    }

    public void viewAppointment(){
        String query = "select * from appointments";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("+----+------------+-----------+------------------+");
            System.out.println("| id | patient_id | doctor_id | appointment_date |");
            System.out.println("+----+------------+-----------+------------------+");
            while(rs.next()){
                int id = rs.getInt("id");
                int patientId = rs.getInt("Patient_id");
                int doctorid = rs.getInt("doctor_id");
                String date = rs.getString("appointment_date");
                System.out.printf("|%-4s|%-12s|%-11s|%-18s|\n",id,patientId,doctorid,date);
                System.out.println("+----+------------+-----------+------------------+");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void bookAppointment(Patient patient, Doctor doctor, Scanner scanner){
        System.out.println("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor Id:");
        int doctorId = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD):");
        String appointmentDate = scanner.next();

        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId, appointmentDate, connection)){
                String appointmentQuery = "insert into appointments(patient_id, doctor_id, appointment_date) values (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3,appointmentDate);

                    int rowsaffected = preparedStatement.executeUpdate();
                    if(rowsaffected > 0){
                        System.out.println("Appointment Booked");
                    }else{
                        System.out.println("Failed to Book Appointment");
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesn't existed");
        }
    }

    public boolean checkDoctorAvailability(int doctorid, String appointmentDate, Connection connection){
        String query = "select count(*) from appointments where doctor_id = ? and appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorid);
            preparedStatement.setString(2, appointmentDate);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                if(count == 0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
}
