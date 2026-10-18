
package student.service;




public class GPACalculator {

    public static double calculate(double currentGPA,
                                   int completedHours,
                                   double semesterGPA,
                                   int semesterHours){

        return ((currentGPA*completedHours)
                +(semesterGPA*semesterHours))
                /(completedHours+semesterHours);

    }

}