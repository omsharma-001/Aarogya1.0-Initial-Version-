import javax.sql.StatementEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class MyFrame
            extends JFrame
            implements ActionListener {

      // Components of the Form
      private Container c;
      private JLabel title;
      private JLabel title2;
      private JLabel name;
      private JTextField tname;
      private JLabel gname;
      private JTextField tgname;
      private JLabel mno;
      private JTextField tmno;
      private JTextField tage;
      private JTextField tdate;
      private JLabel gender;
      private JLabel age;
      private JLabel Date;
      private JRadioButton male;
      private JRadioButton female;
      private JRadioButton other;
      private JLabel Remark;
      private JRadioButton paid;
      private JRadioButton anc;
      private JRadioButton ages;
      private JRadioButton borne;
      private ButtonGroup gengp;
      private ButtonGroup gengps;
      private JLabel add;
      private JTextArea tadd;
      private JLabel dept;
      private JCheckBox term;
      private JCheckBox ent;
      private JCheckBox medicine;
      private JCheckBox ong;
      private JCheckBox surgery;
      private JCheckBox dental;
      private JCheckBox accidentandemergeny;
      private JCheckBox otherdep;
      private JCheckBox ortho;

      private JButton sub;
      private JButton reset;
      private JTextArea tout;
      private JLabel res;
      private JTextArea resadd;
      private Connection connec;

      // JFrame jf = new JFrame();
      // MyFrame.setSize(1650,1080);

      // constructor, to initialize the components
      // with default values.
      public MyFrame(Connection conn) {

            connec = conn;
            setTitle("Mirza Hospital");
            // setBounds(300, 200, 900, 600);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(true);

            c = getContentPane();
            c.setLayout(null);

            title = new JLabel("REG. FORM");
            title.setFont(new Font("Arial", Font.PLAIN, 30));
            title.setSize(300, 30);
            title.setLocation(170, 20);
            c.add(title);
            title2 = new JLabel("PRESCRIPTION SLIP");
            title2.setFont(new Font("Arial", Font.PLAIN, 30));
            title2.setSize(300, 30);
            title2.setLocation(700, 20);
            c.add(title2);

            name = new JLabel("Name");
            name.setFont(new Font("Arial", Font.PLAIN, 20));
            name.setSize(100, 20);
            name.setLocation(170, 90);
            c.add(name);

            tname = new JTextField();
            tname.setFont(new Font("Arial", Font.PLAIN, 15));
            tname.setSize(190, 20);
            tname.setLocation(270, 90);
            c.add(tname);

            gname = new JLabel("Guardian's Name");
            gname.setFont(new Font("Arial", Font.PLAIN, 20));
            gname.setSize(200, 20);
            gname.setLocation(170, 130);
            c.add(gname);

            tgname = new JTextField();
            tgname.setFont(new Font("Arial", Font.PLAIN, 15));
            tgname.setSize(190, 20);
            tgname.setLocation(370, 130);
            c.add(tgname);

            mno = new JLabel("Mobile");
            mno.setFont(new Font("Arial", Font.PLAIN, 20));
            mno.setSize(100, 20);
            mno.setLocation(170, 170);
            c.add(mno);

            tmno = new JTextField();
            tmno.setFont(new Font("Arial", Font.PLAIN, 15));
            tmno.setSize(150, 20);
            tmno.setLocation(270, 170);
            c.add(tmno);

            gender = new JLabel("Gender");
            gender.setFont(new Font("Arial", Font.PLAIN, 20));
            gender.setSize(100, 20);
            gender.setLocation(170, 210);
            c.add(gender);

            male = new JRadioButton("Male");
            male.setFont(new Font("Arial", Font.PLAIN, 15));
            male.setSelected(true);
            male.setSize(75, 20);
            male.setLocation(300, 210);
            c.add(male);

            female = new JRadioButton("Female");
            female.setFont(new Font("Arial", Font.PLAIN, 15));
            female.setSelected(true);
            female.setSize(80, 20);
            female.setLocation(400, 210);
            c.add(female);

            other = new JRadioButton("Other");
            other.setFont(new Font("Arial", Font.PLAIN, 15));
            other.setSelected(true);
            other.setSize(85, 20);
            other.setLocation(500, 210);
            c.add(other);

            gengp = new ButtonGroup();
            gengp.add(male);
            gengp.add(female);
            gengp.add(other);

            age = new JLabel("Age");
            age.setFont(new Font("Arial", Font.PLAIN, 20));
            age.setSize(100, 20);
            age.setLocation(170, 250);
            c.add(age);

            tage = new JTextField();
            tage.setFont(new Font("Arial", Font.PLAIN, 15));
            tage.setSize(90, 20);
            tage.setLocation(270, 250);
            c.add(tage);

            Date = new JLabel("Date");
            Date.setFont(new Font("Arial", Font.PLAIN, 20));
            Date.setSize(100, 20);
            Date.setLocation(450, 250);
            c.add(Date);

            tdate = new JTextField();
            tdate.setFont(new Font("Arial", Font.PLAIN, 15));
            tdate.setSize(90, 20);
            tdate.setLocation(500, 250);
            c.add(tdate);

            add = new JLabel("Address");
            add.setFont(new Font("Arial", Font.PLAIN, 20));
            add.setSize(100, 20);
            add.setLocation(170, 290);
            c.add(add);

            tadd = new JTextArea();
            tadd.setFont(new Font("Arial", Font.PLAIN, 15));
            tadd.setSize(200, 75);
            tadd.setLocation(270, 290);
            tadd.setLineWrap(true);
            c.add(tadd);

            dept = new JLabel("Dept.");
            dept.setFont(new Font("Arial", Font.PLAIN, 20));
            dept.setSize(100, 20);
            dept.setLocation(170, 380);
            c.add(dept);
            ent = new JCheckBox("ENT");
            ent.setFont(new Font("Arial", Font.PLAIN, 15));
            ent.setSize(250, 20);
            ent.setLocation(220, 410);
            c.add(ent);
            medicine = new JCheckBox("Medicine");
            medicine.setFont(new Font("Arial", Font.PLAIN, 15));
            medicine.setSize(250, 20);
            medicine.setLocation(220, 430);
            c.add(medicine);
            surgery = new JCheckBox("Surgery");
            surgery.setFont(new Font("Arial", Font.PLAIN, 15));
            surgery.setSize(250, 20);
            surgery.setLocation(220, 450);
            c.add(surgery);
            dental = new JCheckBox("Dental");
            dental.setFont(new Font("Arial", Font.PLAIN, 15));
            dental.setSize(250, 20);
            dental.setLocation(220, 470);
            c.add(dental);
            accidentandemergeny = new JCheckBox("Accidet & Emergency");
            accidentandemergeny.setFont(new Font("Arial", Font.PLAIN, 15));
            accidentandemergeny.setSize(250, 20);
            accidentandemergeny.setLocation(220, 490);
            c.add(accidentandemergeny);

            ong = new JCheckBox("O&G");
            ong.setFont(new Font("Arial", Font.PLAIN, 15));
            ong.setSize(250, 20);
            ong.setLocation(220, 510);
            c.add(ong);
            ortho = new JCheckBox("Orthopaedic");
            ortho.setFont(new Font("Arial", Font.PLAIN, 15));
            ortho.setSize(250, 20);
            ortho.setLocation(220, 530);
            c.add(ortho);
            otherdep = new JCheckBox("Other");
            otherdep.setFont(new Font("Arial", Font.PLAIN, 15));
            otherdep.setSize(250, 20);
            otherdep.setLocation(220, 550);
            c.add(otherdep);

            Remark = new JLabel("REMARK");
            Remark.setFont(new Font("Arial", Font.PLAIN, 20));
            Remark.setSize(100, 20);
            Remark.setLocation(550, 380);
            c.add(Remark);

            paid = new JRadioButton("paid");
            paid.setFont(new Font("Arial", Font.PLAIN, 15));
            paid.setSelected(true);
            paid.setSize(75, 20);
            paid.setLocation(550, 420);
            c.add(paid);

            anc = new JRadioButton("anc");
            anc.setFont(new Font("Arial", Font.PLAIN, 15));
            anc.setSelected(true);
            anc.setSize(75, 20);
            anc.setLocation(550, 450);
            c.add(anc);

            ages = new JRadioButton("ages");
            ages.setFont(new Font("Arial", Font.PLAIN, 15));
            ages.setSelected(true);
            ages.setSize(75, 20);
            ages.setLocation(550, 480);
            c.add(ages);

            borne = new JRadioButton("borne");
            borne.setFont(new Font("Arial", Font.PLAIN, 15));
            borne.setSelected(true);
            borne.setSize(75, 20);
            borne.setLocation(550, 510);
            c.add(borne);

            gengps = new ButtonGroup();
            gengps.add(paid);
            gengps.add(anc);
            gengps.add(ages);
            gengps.add(borne);

            term = new JCheckBox("Verified");
            term.setFont(new Font("Arial", Font.PLAIN, 15));
            term.setSize(250, 20);
            term.setLocation(170, 580);
            c.add(term);

            sub = new JButton("Submit");
            sub.setFont(new Font("Arial", Font.PLAIN, 15));
            sub.setSize(100, 20);
            sub.setLocation(200, 610);
            sub.addActionListener(this);
            c.add(sub);

            reset = new JButton("Reset");
            reset.setFont(new Font("Arial", Font.PLAIN, 15));
            reset.setSize(100, 20);
            reset.setLocation(370, 610);
            reset.addActionListener(this);
            c.add(reset);

            tout = new JTextArea();
            tout.setFont(new Font("Arial", Font.PLAIN, 15));
            tout.setSize(500, 570);
            tout.setLocation(700, 70);
            tout.setLineWrap(true);
            tout.setEditable(false);
            c.add(tout);

            res = new JLabel("");
            res.setFont(new Font("Arial", Font.PLAIN, 20));
            res.setSize(500, 25);
            res.setLocation(100, 640);
            c.add(res);

            resadd = new JTextArea();
            resadd.setFont(new Font("Arial", Font.PLAIN, 15));
            resadd.setSize(200, 75);
            resadd.setLocation(780, 175);
            resadd.setLineWrap(true);
            c.add(resadd);

            setVisible(true);
      }

      // method actionPerformed()
      // to get the action performed
      // by the user and act accordingly
      public void actionPerformed(ActionEvent e) {

            if (e.getSource() == sub) {
                  String data1 = "";
                  String data9 = "",
                              data18 = "", data2 = "", data3 = "", name = "", gname = "", mobile = "", age = "",
                              address = "",
                              date = "";
                  int x = 0;

                  if (term.isSelected()) {
                        name = tname.getText();
                        gname = tgname.getText();
                        mobile = tmno.getText();
                        age = tage.getText();
                        address = tadd.getText();
                        date = tdate.getText();
                        String data = "Name : " + name + "\n" + "Guardian's name : " + gname + "\n" + "Mobile : "
                                    + mobile + "\n";
                        if (male.isSelected()) {
                              data1 = "m";
                              System.out.print("\n");
                        } else if (female.isSelected()) {
                              data1 = "f";
                              System.out.print("\n");
                        } else {
                              data1 = "o";
                              System.out.print("\n");
                        }
                        data2 = "Age: " + age + "\n";
                        data3 = "Address : " + address + "\n";

                        if (paid.isSelected())
                              data18 = "10";
                        else if (anc.isSelected())
                              data18 = "anc";
                        else if (ages.isSelected())
                              data18 = "age";
                        else if (borne.isSelected())
                              data18 = "borne";

                        if (ent.isSelected()) {
                              data9 = "ENT ";
                        } else if (medicine.isSelected()) {
                              data9 = "Medicine ";
                        } else if (surgery.isSelected()) {
                              data9 = "Surgery ";
                        } else if (dental.isSelected()) {
                              data9 = "Dental ";
                        } else if (accidentandemergeny.isSelected()) {
                              data9 = "Accident and Emergency";
                        } else if (ong.isSelected()) {
                              data9 = "O&G";
                        } else if (ortho.isSelected()) {
                              data9 = "Orthopaedic ";
                        } else if (otherdep.isSelected()) {
                              data9 = "Others";
                              System.out.print("\n");
                        }

                        try {
                              PreparedStatement pstmt = connec
                                          .prepareStatement(
                                                      "insert into patients(name,gname, age, sex, phone, address, visit, remark, date, department) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                                      PreparedStatement.RETURN_GENERATED_KEYS);

                              pstmt.setString(1, name);
                              pstmt.setString(2, gname);
                              pstmt.setString(3, age);
                              pstmt.setString(4, data1);
                              pstmt.setString(5, mobile);
                              pstmt.setString(6, address);
                              pstmt.setInt(7, 1);
                              pstmt.setString(8, data18);
                              pstmt.setString(9, date);
                              pstmt.setString(10, data9);

                              pstmt.executeUpdate();

                              ResultSet generatedKeys = pstmt.getGeneratedKeys();
                              if (generatedKeys.next()) {
                                    x = generatedKeys.getInt(1);
                              }

                        } catch (Exception f) {
                              f.printStackTrace();
                        }
                        String regNo = String.format("%d", x);
                        tout.setText("MIRZA HOSPITAL\n" + "Registration id: " + regNo + "\n" + data + "Sex: " + data1
                                    + "\n"
                                    + data2
                                    + data3
                                    + "Dept: " + data9 + "\n" + "Remark : "
                                    + data18 + "\n" + "Date: " + date);
                        tout.setEditable(false);
                        res.setText("Registration Successfully..");

                  }

                  else {
                        tout.setText("");
                        resadd.setText("");
                        res.setText("Verified");
                  }

            } else if (e.getSource() == reset) {
                  String def = "";
                  tname.setText(def);
                  tgname.setText(def);
                  tadd.setText(def);
                  tmno.setText(def);
                  res.setText(def);
                  tout.setText(def);
                  term.setSelected(false);

                  tage.setText(def);
                  resadd.setText(def);
            }
      }
}

// Driver Code
public class Registration {

      static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      static final String DB_URL = "jdbc:mysql://localhost/";

      static final String USER = "root";
      static final String PASS = "2004@life";

      public static void main(String[] args) throws Exception {

            Connection conn = null;
            Statement stmt = null;

            try {

                  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/arogya", "root", "2004@life");
                  MyFrame f = new MyFrame(conn);
                  f.setSize(1650, 1080);

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
