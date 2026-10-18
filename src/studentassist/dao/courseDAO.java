
package studentassist.dao;





import studentassis.database.DBConnection;
import studentassist.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class courseDAO {

    public Course getCourse(int id) {

        Course course = null;

        String sql = "SELECT * FROM COURSE WHERE ID=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();


            if(rs.next()) {

                course = new Course();

                // ضع أسماء الأعمدة هنا بعد معرفة جدولك

            }

            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return course;
    }
}