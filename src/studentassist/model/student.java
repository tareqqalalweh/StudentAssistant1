
package studentassist.model;


public class student {

    private int id;
    private String name;
    private String password;
    private String major;
    private double gpa;
    private int completedHours;
    private int totalHours;

    public student() {
    }

    public student(int id,String name,String password,
                   String major,double gpa,
                   int completedHours,int totalHours){

        this.id=id;
        this.name=name;
        this.password=password;
        this.major=major;
        this.gpa=gpa;
        this.completedHours=completedHours;
        this.totalHours=totalHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major=major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa=gpa;
    }

    public int getCompletedHours() {
        return completedHours;
    }

    public void setCompletedHours(int completedHours) {
        this.completedHours=completedHours;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours=totalHours;
    }

    public int getRemainingHours() {
        return totalHours-completedHours;
    }

    public double getProgress() {
        return (completedHours*100.0)/totalHours;
    }

}