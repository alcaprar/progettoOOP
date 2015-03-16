package interfacce;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 16/03/15.
 */
public class AdminApp extends JFrame {
    private JButton CSVButton;
    private JPanel panel;
    private JTextField pathtxt;
    private JButton inviaButton;
    private JButton browseButton;
    private JTextField textField1;
    private JButton inviaButton1;
    private JButton browseButton1;
    private JComboBox comboBox1;
    private JTextField textField2;
    private JButton inviaButton2;

    public AdminApp() {
        //titolo del frame
        super("Admin - Gestore fantacalcio");

        setContentPane(panel);

        pack();

        //centra il frame
        setLocationRelativeTo(null);

        setVisible(true);

        setResizable(false);

        CSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fc = new FileDialog(getFrame(), "Choose a file", FileDialog.LOAD);
                fc.setDirectory("/");
                fc.setVisible(true);
                String filename = fc.getFile();
                String path = fc.getDirectory();
                String pathFile = fc.getDirectory()+fc.getFile();
                if (pathFile == null) {
                    System.out.println("You cancelled the choice");
                }
                else {
                    pathtxt.setText(pathFile);
                    pack();
                }
            }
        });
    }

    public AdminApp getFrame(){
        return this;
    }
}
