
package studentassis.ui;


import studentassist.dao.studentDAO;
import studentassist.model.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JTextField txtId;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame() {

        setTitle("Student Assistant");
        setSize(400,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,2,10,10));

        add(new JLabel("Student ID"));

        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Password"));

        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel());

        btnLogin = new JButton("Login");
        add(btnLogin);

        btnLogin.addActionListener((ActionEvent e) -> {

            int id = Integer.parseInt(txtId.getText());
            String password = String.valueOf(txtPassword.getPassword());

            studentDAO dao = new studentDAO();

            student loggedStudent = dao.login(id,password);

            if(loggedStudent != null){

                JOptionPane.showMessageDialog(null,
                        "Welcome " + loggedStudent.getName());

                DashboardFrame dashboard = new DashboardFrame(loggedStudent);
                dashboard.setVisible(true);

                dispose();

            }else{

                JOptionPane.showMessageDialog(null,
                        "Wrong ID or Password");

            }

        });

    }

}