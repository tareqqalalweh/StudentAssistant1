
package studentassist.dao;

import studentassis.database.DBConnection;
import studentassist.model.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class GradeDAO {


    public Grade getGrade(int id) {


        Grade grade = null;


        String sql = "SELECT * FROM GRADE WHERE ID=?";


        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,id);


            ResultSet rs = ps.executeQuery();


            if(rs.next()) {

                grade = new Grade();

                // ضع أسماء الأعمدة هنا

            }


            con.close();


        } catch(Exception e) {

            e.printStackTrace();

        }


        return grade;

    }

}