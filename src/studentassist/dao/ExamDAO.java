
package studentassist.dao;

import studentassis.database.DBConnection;
import studentassist.model.Exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ExamDAO {


    public Exam getExam(int id) {

        Exam exam = null;

        String sql = "SELECT * FROM EXAM WHERE ID=?";


        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();


            if(rs.next()) {

                exam = new Exam();

                // ضع أسماء الأعمدة هنا

            }


            con.close();


        } catch(Exception e) {

            e.printStackTrace();

        }


        return exam;

    }

}