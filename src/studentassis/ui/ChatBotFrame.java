
package studentassis.ui;




import javax.swing.*;
import java.awt.*;

public class ChatBotFrame extends JFrame {

    public ChatBotFrame() {

        setTitle("Student Assistant AI");
        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea chat = new JTextArea();
        JTextField message = new JTextField();
        JButton send = new JButton("Send");

        JPanel bottom = new JPanel(new BorderLayout());

        bottom.add(message,BorderLayout.CENTER);
        bottom.add(send,BorderLayout.EAST);

        add(new JScrollPane(chat),BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);

    }

}