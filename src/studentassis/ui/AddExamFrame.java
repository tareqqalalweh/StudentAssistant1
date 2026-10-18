
package studentassis.ui;





import studentassis.database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddExamFrame extends JFrame {

    JTextField txtCourse;
    JTextField txtDate;
    JTextField txtTime;
    JTextField txtLocation;

    JButton btnSave;

    public AddExamFrame() {

        setTitle("Add Exam");

        setSize(400,350);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5,2,10,10));

        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        txtCourse = new JTextField();

        txtDate = new JTextField();

        txtTime = new JTextField();

        txtLocation = new JTextField();

        btnSave = new JButton("Save");

        panel.add(new JLabel("Course"));

        panel.add(txtCourse);

        panel.add(new JLabel("Date (YYYY-MM-DD)"));

        panel.add(txtDate);

        panel.add(new JLabel("Time"));

        panel.add(txtTime);

        panel.add(new JLabel("Location"));

        panel.add(txtLocation);

        panel.add(new JLabel());

        panel.add(btnSave);

        add(panel);

        btnSave.addActionListener(e -> saveExam());

    }

    private void saveExam(){

        try{

            Connection con = DBConnection.getConnection();

            String sql =
            "INSERT INTO EXAMS VALUES (EXAMS_SEQ.NEXTVAL,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,1);

            ps.setString(2,txtCourse.getText());

            ps.setDate(3,
            java.sql.Date.valueOf(txtDate.getText()));

            ps.setString(4,txtTime.getText());

            ps.setString(5,txtLocation.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Exam Added Successfully");

            dispose();

            con.close();

        }catch(Exception ex){

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }

    }

}