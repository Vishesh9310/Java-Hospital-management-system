import java.sql.*;
import java.util.*;

public class Patient {
    private final Connection connection;
    private final Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.println("Enter Patient Name: ");
        String name = scanner.next();
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();

        try {
            String query = "insert into patients(name, age, gender) values(?, ?, ?)";
            PreparedStatement preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1,name);
            preparedstatement.setInt(2,age);
            preparedstatement.setString(3,gender);
            int affectedrows = preparedstatement.executeUpdate();
            if(affectedrows > 0){
                System.out.println("Patient Add Successfully!!");
            }else{
                System.out.println("Failed to add Patient!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query = "select * from patients;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            
            System.out.println("+------------+-----------------------+------------+--------------+");
            System.out.println("| Patient Id | Name                  | Age        | Gender       |");
            System.out.println("+------------+-----------------------+------------+--------------+");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("|%-12s|%-23s|%-12s|%-14s\n",id,name,age,gender);
                System.out.println("+------------+-----------------------+------------+--------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatient(){
        System.out.println("Enter Patient Id: ");
        int id = scanner.nextInt();
        if(getPatientById(id)){
            System.out.println("Enter Patient Name: ");
            String name = scanner.next();
            System.out.println("Enter Patient Age: ");
            int age = scanner.nextInt();
            System.out.println("Enter Patient Gender: ");
            String gender = scanner.next();
            try {
                String query = "update patients set name = ?, age = ?, gender = ? where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,name);
                preparedStatement.setInt(2,age);
                preparedStatement.setString(3,gender);
                preparedStatement.setInt(4,id);
                int rowsaffected = preparedStatement.executeUpdate();
                if(rowsaffected > 0){
                    System.out.println("Updation successfully");
                }else{
                    System.out.println("Updation failed");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Patient id invalid");
        }

    }

    public void deletePatient(){
        System.out.println("Enter Patient Id: ");
        int id = scanner.nextInt();
        if(getPatientById(id)){
            try {
                String query = "delete from patients where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,id);
                int rowsaffected = preparedStatement.executeUpdate();
                if(rowsaffected > 0){
                    System.out.println("Deletion successfully");
                }else{
                    System.out.println("Deletion failed");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Patient id invalid");
        }
    }

    public boolean getPatientById(int id){
        String query = "select * from patients where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
