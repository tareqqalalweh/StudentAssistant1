
package studentassist.dao;


import studentassis.database.DBConnection;
import studentassist.model.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class studentDAO {

    public student login(int id, String password) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM STUDENTS WHERE ID=? AND PASSWORD=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                student student = new student();

                student.setId(rs.getInt("ID"));
                student.setName(rs.getString("NAME"));
                student.setPassword(rs.getString("PASSWORD"));
                student.setMajor(rs.getString("MAJOR"));
                student.setGpa(rs.getDouble("GPA"));
                student.setCompletedHours(rs.getInt("COMPLETED_HOURS"));
                student.setTotalHours(rs.getInt("TOTAL_HOURS"));

                return student;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}