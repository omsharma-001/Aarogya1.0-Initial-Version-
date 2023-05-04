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

class visit2 extends JFrame implements ActionListener {
    private JLabel regNoLabel;
    private JTextField regNoField;
    private JLabel dateLabel;
    private JTextField dateField;
    private JButton visitButton;
    private JTable table;
    private JScrollPane scrollPane;
    private Connection conn;
    private JButton updateButton;
    private JFrame visit;

    public visit2() {
        super("Patient Visit GUI");

        // Initialize the visit variable
        visit = this;

        // Create GUI components
        regNoLabel = new JLabel("Registration No:");
        regNoField = new JTextField(20);
        dateLabel = new JLabel("Date (ddmmyyyy):");
        dateField = new JTextField(20);
        visitButton = new JButton("Visit");
        updateButton = new JButton("Update Visit");
        table = new JTable();
        scrollPane = new JScrollPane(table);

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(regNoLabel);
        inputPanel.add(regNoField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(visitButton);
        inputPanel.add(updateButton);
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        visitButton.addActionListener(this);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int rowIndex = table.getSelectedRow();

                if (rowIndex != -1) { // If a row is selected
                    try {
                        // Retrieve the patient's registration number and visit count from the selected
                        // row
                        String regNo = table.getValueAt(rowIndex, 0).toString();
                        // int visitCount = Integer.parseInt(table.getValueAt(rowIndex, 2).toString());

                        // Increment the visit count by 1
                        // visitCount++;

                        // Create the SQL statement to update the visit count for the patient
                        String sql = "UPDATE patients SET Visit=visit + 1 WHERE Registration_no='" + regNo + "'";

                        // Execute the SQL statement
                        Statement stmt = conn.createStatement();
                        int rowsAffected = stmt.executeUpdate(sql);

                        // If the update was successful, display a message and refresh the table
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(visit,
                                    "Visit count updated for patient with registration number " + regNo);
                            Window window = SwingUtilities.getWindowAncestor(visit);
                            window.dispose();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else { // If no row is selected
                    JOptionPane.showMessageDialog(visit, "Please select a row to update the visit count.");
                }
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // set to full screen
        // setSize(600, 400);
        setVisible(true);

        // Connect to database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/arogya", "root", "2004@life");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String regNo = regNoField.getText();
        String dateStr = dateField.getText();
        boolean isSearchByRegNo = !regNo.isEmpty();
        boolean isSearchByDate = !dateStr.isEmpty();

        try {
            // Create SQL statement to retrieve rows with matching registration number
            // and/or date
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM patients";
            if (isSearchByRegNo && !isSearchByDate) {
                sql += " WHERE Registration_no='" + regNo + "'";
            } else if (!isSearchByRegNo && isSearchByDate) {
                int date = Integer.parseInt(dateStr);
                sql += " WHERE Date=" + date;
            } else if (isSearchByRegNo && isSearchByDate) {
                int date = Integer.parseInt(dateStr);
                sql += " WHERE Registration_no='" + regNo + "' AND Date=" + date;
            }
            ResultSet rs = stmt.executeQuery(sql);

            // Create table model with the results
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            String[] columnNames = new String[numCols];
            for (int i = 1; i <= numCols; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = new Object[numCols];
                for (int i = 1; i <= numCols; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }

            // Display the results in the table
            table.setModel(tableModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new visit2();
    }

}
