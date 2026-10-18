
package studentassist.model;

import java.sql.Date;


public class Exam {


    private int examId;
    private int courseId;
    private String examType;
    private Date examDate;



    public Exam() {

    }



    public Exam(int examId, int courseId, String examType, Date examDate) {

        this.examId = examId;
        this.courseId = courseId;
        this.examType = examType;
        this.examDate = examDate;

    }



    public int getExamId() {

        return examId;

    }


    public void setExamId(int examId) {

        this.examId = examId;

    }



    public int getCourseId() {

        return courseId;

    }


    public void setCourseId(int courseId) {

        this.courseId = courseId;

    }



    public String getExamType() {

        return examType;

    }


    public void setExamType(String examType) {

        this.examType = examType;

    }



    public Date getExamDate() {

        return examDate;

    }


    public void setExamDate(Date examDate) {

        this.examDate = examDate;

    }


}