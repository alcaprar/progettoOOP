package interfacce;

import javax.swing.*;

/**
 * Created by alessandro on 11/03/15.
 */
public class Home {
    private JPanel panel1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Home");
        frame.setContentPane(new Home().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
