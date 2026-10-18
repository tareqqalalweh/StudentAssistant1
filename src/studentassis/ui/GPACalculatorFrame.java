
package studentassis.ui;



import javax.swing.*;
import java.awt.*;

public class GPACalculatorFrame extends JFrame {

    private JTextField currentGPA;
    private JTextField completedHours;
    private JTextField expectedHours;
    private JTextField expectedGPA;

    public GPACalculatorFrame() {

        setTitle("GPA Calculator");
        setSize(500,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(5,2,10,10));

        add(new JLabel("Current GPA"));
        currentGPA = new JTextField();
        add(currentGPA);

        add(new JLabel("Completed Hours"));
        completedHours = new JTextField();
        add(completedHours);

        add(new JLabel("Expected Semester Hours"));
        expectedHours = new JTextField();
        add(expectedHours);

        add(new JLabel("Expected Semester GPA"));
        expectedGPA = new JTextField();
        add(expectedGPA);

        JButton calculate = new JButton("Calculate");
        add(calculate);

        JLabel result = new JLabel("");
        add(result);

       calculate.addActionListener(e -> {

    try {

        double gpa = Double.parseDouble(currentGPA.getText());
        int completed = Integer.parseInt(completedHours.getText());
        int hours = Integer.parseInt(expectedHours.getText());
        double semester = Double.parseDouble(expectedGPA.getText());

        double finalGPA =
                ((gpa * completed) + (semester * hours))
                        / (completed + hours);

        result.setText("Expected GPA : " +
                String.format("%.2f", finalGPA));

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter valid numbers."
        );

    }

});

    }

}