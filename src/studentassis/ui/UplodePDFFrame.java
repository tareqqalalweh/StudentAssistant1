package studentassis.ui;


import student.service.PDFReader;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class UplodePDFFrame extends JFrame {
    private JTextArea textArea;
    public UplodePDFFrame() {
        setTitle("Upload Transcript");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton upload = new JButton("Choose PDF");
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        setLayout(new BorderLayout(10, 10));
        add(upload, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        upload.addActionListener(e -> choosePDF());
    }
    private void choosePDF() {
       JFileChooser fileChooser = new JFileChooser(
        new File(System.getProperty("user.home"), "Desktop"));
        fileChooser.setDialogTitle("Select Transcript PDF");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file == null || !file.exists()) {
                JOptionPane.showMessageDialog(
                        this,
                        "File was not found."
                );
                return;
            }
            try {
                String text = PDFReader.readPDF(file);
                if (text == null || text.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "The PDF contains no readable text."
                    );
                    return;
                }
                // عرض النص المستخرج
                textArea.setText(text);
                // استخراج بيانات الطالب
                extractStudentData(text);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error reading PDF:\n" + ex.getMessage()
                );
                ex.printStackTrace();
            }
        }
    }
    private void extractStudentData(String text) {
        String name = extractFirst(
                text,
                "Student Name:\\s*(.*?)\\s+Student Id:"
        );
        String studentId = extractFirst(
                text,
                "Student Id:\\s*(\\d+)"
        );
        String major = extractFirst(
                text,
                "Major:\\s*(.*?)\\s+Semester:"
        );
        // البحث عن جميع Cumulative GPA
        // وأخذ آخر معدل لأنه الأحدث
        Pattern gpaPattern = Pattern.compile(
                "Cumulative GPA:\\s*([0-9]+(?:\\.[0-9]+)?)"
        );
        Matcher gpaMatcher = gpaPattern.matcher(text);
        String cumulativeGPA = "Not found";
        while (gpaMatcher.find()) {
            cumulativeGPA = gpaMatcher.group(1);
        }
        String message =
                "Student Information\n\n"
                + "Name: " + name + "\n"
                + "Student ID: " + studentId + "\n"
                + "Major: " + major + "\n"
                + "Cumulative GPA: " + cumulativeGPA;
        JOptionPane.showMessageDialog(
                this,
                message,
                "Extracted Student Data",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    private String extractFirst(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "Not found";
    }
}