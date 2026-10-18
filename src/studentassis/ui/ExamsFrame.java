
package studentassis.ui;





import studentassis.database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExamsFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ExamsFrame() {

        setTitle("My Exams");
        setSize(700,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();

        model.addColumn("Course");
        model.addColumn("Date");
        model.addColumn("Time");
        model.addColumn("Location");

        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);

        JButton btnRefresh = new JButton("Refresh");
       JButton btnAdd = new JButton("Add Exam");

btnAdd.addActionListener(e -> {
    new AddExamFrame().setVisible(true);
});

        btnRefresh.addActionListener(e -> loadExams());

        add(scroll, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);

        loadExams();
    }

    private void loadExams() {

        model.setRowCount(0);

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT COURSE_NAME,EXAM_DATE,EXAM_TIME,LOCATION FROM EXAMS";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getString("COURSE_NAME"),
                        rs.getDate("EXAM_DATE"),
                        rs.getString("EXAM_TIME"),
                        rs.getString("LOCATION")

                });

            }

            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e.getMessage());

        }

    }

}