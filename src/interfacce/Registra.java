package interfacce;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import db.*;
import interfacce.*;
import classi.*;
import utils.*;

/**
 * Created by alessandro on 04/03/15.
 */
public class Registra extends JFrame {
    JLabel nomelbl = new JLabel("Nome:");
    JLabel cognomelbl = new JLabel("Cognome:");
    JLabel emailbl = new JLabel("Email:");
    JLabel nicknamelbl = new JLabel("Nickname:");
    JLabel passwordlbl = new JLabel("Password:");

    JTextField nometxt = new JTextField(20);
    JTextField cognometxt = new JTextField(20);
    JTextField emailtxt = new JTextField(30);
    JTextField nicknametxt = new JTextField(20);
    JPasswordField passwordtxt = new JPasswordField(20);

    JPanel nomepnl = new JPanel();
    JPanel cognomepnl = new JPanel();
    JPanel emailpnl = new JPanel();
    JPanel nicknamepnl = new JPanel();
    JPanel passwordpnl = new JPanel();

    JButton registratibtn = new JButton("Registrati!");

    Persona utente;

    Utils utils;

    public Registra(){
        super("Registrati");

        //oggetto per il database
        final Mysql db = new Mysql();

        utils = new Utils();

        //abilita la chiusura al premere di X
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //setta il layout boxlayout
        //Y_AXIS mette un componente per riga
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        nicknamepnl.setLayout(new BoxLayout(nicknamepnl, BoxLayout.X_AXIS));
        nicknamepnl.add(nicknamelbl);
        nicknamepnl.add(nicknametxt);

        passwordpnl.setLayout(new BoxLayout(passwordpnl, BoxLayout.X_AXIS));
        passwordpnl.add(passwordlbl);
        passwordpnl.add(passwordtxt);

        nomepnl.setLayout(new BoxLayout(nomepnl, BoxLayout.X_AXIS));
        nomepnl.add(nomelbl);
        nomepnl.add(nometxt);

        cognomepnl.setLayout(new BoxLayout(cognomepnl, BoxLayout.X_AXIS));
        cognomepnl.add(cognomelbl);
        cognomepnl.add(cognometxt);

        emailpnl.setLayout(new BoxLayout(emailpnl, BoxLayout.X_AXIS));
        emailpnl.add(emailbl);
        emailpnl.add(emailtxt);

        registratibtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!"".equals(nicknametxt.getText()) && !"".equals(utils.passwordString(passwordtxt.getPassword())) && !"".equals(nometxt.getText()) && !"".equals(cognometxt.getText()) && !"".equals(emailtxt.getText())){
                    utente = new Persona(nicknametxt.getText(), utils.passwordString(passwordtxt.getPassword()), nometxt.getText(), cognometxt.getText(),emailtxt.getText());
                    db.registra(utente, getFrame());


                }

                }
            });

        getContentPane().add(nicknamepnl);
        getContentPane().add(passwordpnl);
        getContentPane().add(nomepnl);
        getContentPane().add(cognomepnl);
        getContentPane().add(emailpnl);
        getContentPane().add(registratibtn);

        pack();

        setVisible(true);

        //vieta il ridimensionamento della finestra
        setResizable(false);


    }

    public void registraTrue(){
        Object[] options = {"OK"};
        int succesDialog = JOptionPane.showOptionDialog(getContentPane(),"Registrazione effettuata con successo!",
                "Risposta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if(succesDialog==0 || succesDialog==-1) dispose();
    }

    public Registra getFrame(){
        return this;
    }
}
