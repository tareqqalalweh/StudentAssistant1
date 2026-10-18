
package studentassis.ui;




import javax.swing.*;

public class SettingsFrame extends JFrame {

    public SettingsFrame(){

        setTitle("Settings");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Settings",SwingConstants.CENTER));

    }

}