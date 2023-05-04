
import java.io.FileWriter;
import java.sql.*;

public class Export {

    Export() {

        try {
            // Connect to MySQL database
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/arogya", "root", "2004@life");

            // Create a statement
            Statement stmt = conn.createStatement();

            // Execute a query to fetch all rows from the table
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");

            // Create a CSV file and write the contents of the ResultSet to it
            FileWriter writer = new FileWriter("patient_registration.csv");
            while (rs.next()) {
                writer.append(rs.getString("Registration_no"));
                writer.append(',');
                writer.append(rs.getString("name"));
                writer.append(',');
                writer.append(Integer.toString(rs.getInt("age")));
                writer.append(',');
                writer.append(rs.getString("sex"));
                writer.append(',');
                writer.append(rs.getString("address"));
                writer.append(',');
                writer.append(rs.getString("date"));
                writer.append(',');
                writer.append(rs.getString("phone"));
                writer.append(',');
                writer.append(rs.getString("department"));
                writer.append(',');
                writer.append(rs.getString("remark"));
                writer.append('\n');
            }
            writer.flush();
            writer.close();

            // Close the database connection
            conn.close();

            System.out.println("Export completed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Export();
    }
}