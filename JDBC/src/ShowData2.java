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

class ShowData2 extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/arogya";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "2004@life";
    private static final int PAGE_SIZE = 30;
    private int currentPage = 0;
    private JTable table;
    private JButton prevButton;
    private JButton nextButton;

    public ShowData2() {
        setTitle("Patient Registration Records");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Create the table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Registration_no");
        model.addColumn("Name");
        model.addColumn("Guardian's Name");
        model.addColumn("Age");
        model.addColumn("Sex");
        model.addColumn("Phone");
        model.addColumn("Address");
        model.addColumn("Visit");
        model.addColumn("Remark");
        model.addColumn("Date");
        model.addColumn("Department");

        // Create the table and set its model
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create the panel with the previous and next buttons
        JPanel buttonPanel = new JPanel();
        prevButton = new JButton("Previous");
        prevButton.setEnabled(false);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage--;
                if (currentPage <= 0) {
                    currentPage = 0;
                    prevButton.setEnabled(false);
                }
                updateTable();
            }
        });
        buttonPanel.add(prevButton);

        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage++;
                if (currentPage >= getTotalPages() - 1) {
                    currentPage = getTotalPages() - 1;
                    nextButton.setEnabled(false);
                }
                prevButton.setEnabled(true);
                updateTable();
            }
        });
        buttonPanel.add(nextButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Update the table with the most recent entries
        updateTable();
    }

    private int getTotalPages() {
        int totalPages = 0;
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Query the database to count the number of entries
            String countQuery = "SELECT COUNT(*) AS count FROM patients";
            PreparedStatement countStmt = conn.prepareStatement(countQuery);
            ResultSet countRs = countStmt.executeQuery();
            countRs.next();
            int totalCount = countRs.getInt("count");

            // Calculate the total number of pages
            totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPages;
    }

    private void updateTable() {
        List<String[]> data = new ArrayList<>();

        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Query the database for the most recent entries
            int offset = currentPage * PAGE_SIZE;
            String query = "SELECT * FROM patients ORDER BY Registration_no DESC LIMIT ? OFFSET ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, PAGE_SIZE);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] row = new String[11];
                row[0] = rs.getString("Registration_no");
                row[1] = rs.getString("name");
                row[2] = rs.getString("gname");
                row[3] = rs.getString("age");
                row[4] = rs.getString("sex");
                row[5] = rs.getString("phone");
                row[6] = rs.getString("address");
                row[7] = rs.getString("visit");
                row[8] = rs.getString("remark");
                row[9] = rs.getString("date");
                row[10] = rs.getString("department");
                data.add(row);
            }

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the table model with the new data
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (String[] row : data) {
            model.addRow(row);
        }

        // Enable or disable the "Previous" button based on the current page
        prevButton.setEnabled(currentPage > 0);

        // Update the title of the window with the current page number
        setTitle("Patient Registration Records - Page " + (currentPage + 1));

        // Check if there are more pages to display
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Query the database to count the number of entries
            String countQuery = "SELECT COUNT(*) AS count FROM patients";
            PreparedStatement countStmt = conn.prepareStatement(countQuery);
            ResultSet countRs = countStmt.executeQuery();
            countRs.next();
            int totalCount = countRs.getInt("count");

            // Calculate the total number of pages
            int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

            // Enable or disable the "Next" button based on the current page
            JButton nextButton = new JButton("Next");
            nextButton.setEnabled(currentPage < totalPages - 1);
            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentPage++;
                    if (currentPage >= totalPages - 1) {
                        currentPage = totalPages - 1;
                        nextButton.setEnabled(false);
                    }
                    prevButton.setEnabled(true);
                    updateTable();
                }
            });

            // Remove any existing "Next" button and add the new one
            JPanel buttonPanel = (JPanel) getContentPane().getComponent(1);
            Component[] components = buttonPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JButton && ((JButton) component).getText().equals("Next")) {
                    buttonPanel.remove(component);
                }
            }
            buttonPanel.add(nextButton);

            // Close the database connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getContentPane().validate();
    }

    public static void main(String[] args) {
        ShowData2 gui = new ShowData2();
        gui.setVisible(true);
    }
}
