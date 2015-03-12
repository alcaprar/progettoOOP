package interfacce;

import classi.*;
import db.Mysql;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 12/03/15.
 */
public class Login2 extends JFrame {

    private JTextField usertxt;
    private JPasswordField passtxt;
    private JButton loginButton;
    private JButton registratiButton;
    private JComboBox comboBox1;
    private JButton vaiButton;
    private JComboBox comboBox2;
    private JButton vaiButton1;
    private JButton vaiButton2;
    private JPanel panel1;
    private JPanel login1;
    private JPanel login2;
    public Persona utente;
    public Utils utils = new Utils();

    public Login2(){
        final Mysql db = new Mysql();
        setContentPane(panel1);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utente = new Persona(usertxt.getText(),utils.passwordString(passtxt.getPassword()));
                if(db.login(utente)){
                    System.out.print("ok");
                    CardLayout c1 = (CardLayout) (panel1.getLayout());
                    c1.show(panel1,"login2");
                }
                else System.out.print("no");
            }
        });
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
}
