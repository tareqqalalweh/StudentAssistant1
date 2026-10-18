
package studentassist.main;


import java.util.Scanner;
import studentassist.dao.studentDAO;
import studentassist.model.student;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        studentDAO dao = new studentDAO();

        System.out.println("================================");
        System.out.println("      Student Assistant");
        System.out.println("================================");

        System.out.print("Student ID : ");
        int id = input.nextInt();
        input.nextLine();

        System.out.print("Password : ");
        String password = input.nextLine();

        student loggedStudent = dao.login(id, password);

        if (loggedStudent != null) {

            System.out.println("\nLogin Successful.");
            System.out.println("Welcome " + loggedStudent.getName());
            System.out.println("Major: " + loggedStudent.getMajor());
            System.out.println("GPA: " + loggedStudent.getGpa());

        } else {

            System.out.println("\nWrong ID or Password.");

        }

        input.close();
    }
}