
package studentassis.ui;

import studentassist.model.student;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(student student) {

        setTitle("Student Assistant Dashboard");
        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel lblName = new JLabel("👤 Name : " + student.getName());
        JLabel lblMajor = new JLabel("🎓 Major : " + student.getMajor());
        JLabel lblGpa = new JLabel("📊 GPA : " + student.getGpa());
        JLabel lblCompleted = new JLabel("✔ Completed Hours : " + student.getCompletedHours());
        JLabel lblRemaining = new JLabel("⌛ Remaining Hours : " + student.getRemainingHours());
        JLabel lblProgress = new JLabel("📈 Progress : "
                + String.format("%.2f", student.getProgress()) + "%");

        lblName.setFont(new Font("Arial", Font.BOLD,18));
        lblMajor.setFont(new Font("Arial", Font.PLAIN,16));
        lblGpa.setFont(new Font("Arial", Font.PLAIN,16));
        lblCompleted.setFont(new Font("Arial", Font.PLAIN,16));
        lblRemaining.setFont(new Font("Arial", Font.PLAIN,16));
        lblProgress.setFont(new Font("Arial", Font.BOLD,16));

        JProgressBar progressBar = new JProgressBar(0,100);
        progressBar.setValue((int)student.getProgress());
        progressBar.setStringPainted(true);

        JButton btnCourses = new JButton("📚 My Courses");
        JButton btnExams = new JButton("📅 My Exams");
        JButton btnGPA = new JButton("🧮 GPA Calculator");
        JButton btnTranscript = new JButton("📄 Upload Transcript");
        JButton btnChat = new JButton("🤖 AI Assistant");
        JButton btnLogout = new JButton("🚪 Logout");

        panel.add(lblName);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblMajor);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblGpa);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblCompleted);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblRemaining);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblProgress);
        panel.add(Box.createVerticalStrut(10));

        panel.add(progressBar);
        panel.add(Box.createVerticalStrut(20));

        JPanel buttonsPanel = new JPanel(new GridLayout(2,3,10,10));

        buttonsPanel.add(btnCourses);
        buttonsPanel.add(btnExams);
        buttonsPanel.add(btnGPA);
        buttonsPanel.add(btnTranscript);
        buttonsPanel.add(btnChat);
        buttonsPanel.add(btnLogout);

        panel.add(buttonsPanel);

        add(panel);

        btnCourses.addActionListener(e -> {
            new CoursesFrame().setVisible(true);
        });

        btnExams.addActionListener(e -> {
            new ExamsFrame().setVisible(true);
        });

        
        btnGPA.addActionListener(e -> {
    new GPACalculatorFrame().setVisible(true);
});

       btnTranscript.addActionListener(e -> {
    new UplodePDFFrame().setVisible(true);
});
        btnChat.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "AI Assistant - Coming Soon");
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

    }

}