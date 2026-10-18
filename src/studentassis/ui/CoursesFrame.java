
package studentassis.ui;



import studentassis.database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CoursesFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    public CoursesFrame() {

        setTitle("Courses");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();

        model.addColumn("Course");
        model.addColumn("Credit Hours");
        model.addColumn("Mark");
        model.addColumn("Grade");
        model.addColumn("Status");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnRefresh = new JButton("Refresh");

        btnRefresh.addActionListener(e -> loadCourses());

        add(scrollPane, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);

        loadCourses();
    }

    private void loadCourses() {

        model.setRowCount(0);

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT COURSE_NAME,CREDIT_HOURS,MARK,GRADE,STATUS FROM COURSES";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                model.addRow(new Object[]{

                        rs.getString("COURSE_NAME"),
                        rs.getInt("CREDIT_HOURS"),
                        rs.getDouble("MARK"),
                        rs.getString("GRADE"),
                        rs.getString("STATUS")

                });

            }

            con.close();

        }catch(Exception e){

            JOptionPane.showMessageDialog(this,e.getMessage());

        }

    }

}