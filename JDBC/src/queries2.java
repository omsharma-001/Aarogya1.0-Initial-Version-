import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class queries2 extends JFrame implements ActionListener {

    // GUI components
    private JLabel lblDate;
    private JTextField txtDate;
    private JButton btnSubmit;
    private JTextArea txtResult;

    // Database connection
    private Connection conn;
    private Statement stmt;

    queries2() {
        // Set up the GUI
        setTitle("OPD Statistics");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblDate = new JLabel("Enter Date (ddmmyyyy):");
        txtDate = new JTextField(10);
        btnSubmit = new JButton("Submit");
        txtResult = new JTextArea(15, 50);
        txtResult.setEditable(false);

        btnSubmit.addActionListener(this);

        add(lblDate);
        add(txtDate);
        add(btnSubmit);
        add(new JScrollPane(txtResult));

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            String dateInput = txtDate.getText().trim();

            // Validate date input

            // Clear previous result
            txtResult.setText("");

            // Connect to the database
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/arogya", "root",
                        "2004@life");
                stmt = conn.createStatement();

                // Query 1: Total number of patients who attended the OPD service
                String query1 = "SELECT COUNT(*) as total_patients FROM Patients where date = ?";
                PreparedStatement stmt1 = conn.prepareStatement(query1);
                stmt1.setString(1, dateInput);
                ResultSet rs1 = stmt1.executeQuery();
                if (rs1.next()) {
                    int totalPatients = rs1.getInt("total_patients");
                    txtResult.append("Total number of patients who attended the OPD service: " + totalPatients + "\n");
                }

                // Query 2: Male patients
                String query2 = "SELECT COUNT(*) as male_patients FROM Patients WHERE sex = 'm' AND date = ?";
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                stmt2.setString(1, dateInput);
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    int malePatients = rs2.getInt("male_patients");
                    txtResult.append("Male patients: " + malePatients + "\n");
                }

                // Query 3: Female patients
                String query3 = "SELECT COUNT(*) as female_patients FROM Patients WHERE sex = 'f' AND date = ?";
                PreparedStatement stmt3 = conn.prepareStatement(query3);
                stmt3.setString(1, dateInput);
                ResultSet rs3 = stmt3.executeQuery();
                if (rs3.next()) {
                    int femalePatients = rs3.getInt("female_patients");
                    txtResult.append("Female patients: " + femalePatients + "\n");
                }

                // Query 4: Other patients
                String query4 = "SELECT COUNT(*) as Other_patients FROM Patients WHERE sex = 'o' AND date = ?";
                PreparedStatement stmt4 = conn.prepareStatement(query4);
                stmt4.setString(1, dateInput);
                ResultSet rs4 = stmt4.executeQuery();
                if (rs4.next()) {
                    int otherPatients = rs4.getInt("Other_patients");
                    txtResult.append("Other patients: " + otherPatients + "\n");
                }

                // Query 5: Number of children below the age of 5 years
                String query5 = "SELECT COUNT(*) as children_below_5 FROM Patients WHERE age < 5 AND date = ?";
                PreparedStatement stmt5 = conn.prepareStatement(query5);
                stmt5.setString(1, dateInput);
                ResultSet rs5 = stmt5.executeQuery();
                if (rs5.next()) {
                    int childrenBelow5 = rs5.getInt("children_below_5");
                    txtResult.append("Number of children below the age of 5 years: " + childrenBelow5 + "\n");
                }

                // Query 6: Number of patients in the age range of 0 to 12 months
                String query6 = "SELECT COUNT(*) as patients_0_to_12_months FROM Patients WHERE age = 0 AND date = ?";
                PreparedStatement stmt6 = conn.prepareStatement(query6);
                stmt6.setString(1, dateInput);
                ResultSet rs6 = stmt6.executeQuery();
                if (rs6.next()) {
                    int patients0To12Months = rs6.getInt("patients_0_to_12_months");
                    txtResult.append(
                            "Number of patients in the age range of 0 to 12 months: " + patients0To12Months + "\n");
                }

                // Query 7: Number of ANC (Ante Natal Case, Pregnancy related) patients
                String query7 = "SELECT COUNT(*) as anc_patients FROM Patients WHERE remark = 'anc' AND date = ?";
                PreparedStatement stmt7 = conn.prepareStatement(query7);
                stmt7.setString(1, dateInput);
                ResultSet rs7 = stmt7.executeQuery();
                if (rs7.next()) {
                    int ancPatients = rs7.getInt("anc_patients");
                    txtResult.append(
                            "Number of ANC (Ante Natal Case, Pregnancy related) patients: " + ancPatients + "\n");
                }

                // Query 8: Department-wise patient count
                String query8 = "SELECT department, COUNT(*) as department_count FROM Patients where date = ? GROUP BY department";
                PreparedStatement stmt8 = conn.prepareStatement(query8);
                stmt8.setString(1, dateInput);
                ResultSet rs8 = stmt8.executeQuery();
                txtResult.append("Department-wise patient count:\n");
                while (rs8.next()) {
                    String department = rs8.getString("department");
                    int departmentCount = rs8.getInt("department_count");
                    txtResult.append(department + ": " + departmentCount + "\n");
                }

                // Query 9: OPD registration fee collected
                String query9 = "SELECT count(remark) as total_fee FROM Patients where remark = '10' AND date = ?";
                PreparedStatement stmt9 = conn.prepareStatement(query9);
                stmt9.setString(1, dateInput);
                ResultSet rs9 = stmt9.executeQuery();
                if (rs9.next()) {
                    double totalFee = rs9.getDouble("total_fee");
                    txtResult.append("OPD registration fee collected: Rs " + (totalFee) * 10 + "\n");
                }

                // Query 10: repeats
                String query11 = "SELECT COUNT(*) as repeats FROM Patients WHERE visit > 1 AND date = ?";
                PreparedStatement stmt11 = conn.prepareStatement(query11);
                stmt11.setString(1, dateInput);
                ResultSet rs11 = stmt11.executeQuery();
                if (rs11.next()) {
                    int repeats = rs11.getInt("repeats");
                    txtResult.append("patients visiting again : " + repeats + "\n");
                }

                // Close database connection
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String args[]) {
        new queries2();
    }
}
