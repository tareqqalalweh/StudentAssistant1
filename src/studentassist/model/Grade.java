
package studentassist.model;



public class Grade {


    private int gradeId;
    private int studentId;
    private int courseId;
    private double mark;



    public Grade() {

    }



    public Grade(int gradeId, int studentId, int courseId, double mark) {

        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.mark = mark;

    }




    public int getGradeId() {

        return gradeId;

    }



    public void setGradeId(int gradeId) {

        this.gradeId = gradeId;

    }





    public int getStudentId() {

        return studentId;

    }



    public void setStudentId(int studentId) {

        this.studentId = studentId;

    }





    public int getCourseId() {

        return courseId;

    }



    public void setCourseId(int courseId) {

        this.courseId = courseId;

    }





    public double getMark() {

        return mark;

    }



    public void setMark(double mark) {

        this.mark = mark;

    }


}