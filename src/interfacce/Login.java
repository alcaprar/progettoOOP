package interfacce;

import utils.*;
import db.*;
import classi.Persona;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public  class Login extends JFrame {
    JPanel userpnl = new JPanel();
    JPanel passpnl = new JPanel();
    JPanel buttonpnl = new JPanel();
    JPanel squadrepnl = new JPanel();
    JPanel iscrivitipnl = new JPanel();
    JPanel creapnl = new JPanel();


    JLabel userlbl = new JLabel("Username: ");
    JLabel passlbl = new JLabel("Password: ");
    JLabel infolbl = new JLabel("Info:");
    JLabel squadrelbl = new JLabel("Gestisci la squadra:");
    JLabel iscrivitilbl = new JLabel("Iscriviti a un campionato pubblico");
    JLabel crealbl = new JLabel("Crea un campionato");


    JTextField usertxt = new JTextField(20);
    JPasswordField passtxt = new JPasswordField(20);

    public JComboBox squadrebox = new JComboBox();
    JComboBox iscrivitibox = new JComboBox();

    JButton loginbtn = new JButton("Login");
    JButton registrabtn = new JButton("Registrati");
    JButton gestiscibtn = new JButton("Vai");
    JButton iscrivitibtn = new JButton("Vai");
    JButton creabtn = new JButton("Vai");

    JSeparator squadrespt = new JSeparator(SwingConstants.HORIZONTAL);
    JSeparator iscrivitispt = new JSeparator(SwingConstants.HORIZONTAL);

    Persona utente;

    Utils utils = new Utils();

    Registra registra;

    public Login(){
        //titolo del frame
        super("Login");

        //oggetto per il database
        final Mysql db = new Mysql();

        //abilita la chiusura al premere di X
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //setta il layout boxlayout
        //Y_AXIS mette un componente per riga
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        userpnl.setLayout(new FlowLayout());
        userpnl.add(userlbl);
        userpnl.add(usertxt);

        passpnl.setLayout(new FlowLayout());
        passpnl.add(passlbl);
        passpnl.add(passtxt);

        //evento al clic di enter
        passtxt.addKeyListener
                (new KeyAdapter() {
                     public void keyPressed(KeyEvent e) {
                         int key = e.getKeyCode();
                         if (key == KeyEvent.VK_ENTER) {
                             utente = new Persona(usertxt.getText(),utils.passwordString(passtxt.getPassword()));
                             if(db.login(utente)){
                                 loginTrue();
                             }

                             else{
                                 loginFalse();
                             }
                         }
                     }
                 }
                );

        //evento al clic di login
        loginbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utente = new Persona(usertxt.getText(),utils.passwordString(passtxt.getPassword()));
                if(db.login(utente)){
                    loginTrue();

                }

                else{
                    loginFalse();
                }
            }
        });

        //evento al clic di registrati
        registrabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registra = new Registra();

            }
        });

        gestiscibtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Applicazione app = new Applicazione();
                getFrame().dispose();

                

            }
        });

        buttonpnl.setLayout(new FlowLayout());
        buttonpnl.add(loginbtn);
        buttonpnl.add(registrabtn);

        squadrepnl.setLayout(new BoxLayout(squadrepnl, BoxLayout.X_AXIS));
        squadrepnl.add(squadrelbl);
        squadrebox.addItem("Squadra - Campionato");
        squadrepnl.add(squadrebox);
        squadrebox.setBorder(new EmptyBorder(0, 10, 0, 10));
        squadrepnl.add(gestiscibtn);

        iscrivitipnl.setLayout(new BoxLayout(iscrivitipnl, BoxLayout.X_AXIS));
        iscrivitipnl.add(iscrivitilbl);
        iscrivitibox.addItem("Campionato");
        iscrivitipnl.add(iscrivitibox);
        iscrivitibox.setBorder(new EmptyBorder(0, 10, 0, 10));
        iscrivitipnl.add(iscrivitibtn);

        creapnl.setLayout(new FlowLayout());
        creapnl.add(crealbl);
        creapnl.add(creabtn);

        getContentPane().add(userpnl);
        getContentPane().add(passpnl);
        getContentPane().add(buttonpnl);
        getContentPane().add(infolbl);
        getContentPane().add(squadrepnl);
        squadrepnl.setBorder(new EmptyBorder(10, 0, 10, 0));
        getContentPane().add(squadrespt);
        getContentPane().add(iscrivitipnl);
        iscrivitipnl.setBorder(new EmptyBorder(10, 0, 10, 0));
        getContentPane().add(iscrivitispt);
        getContentPane().add(creapnl);
        creapnl.setBorder(new EmptyBorder(10, 0, 10, 0));


        squadrepnl.setVisible(false);
        iscrivitipnl.setVisible(false);
        creapnl.setVisible(false);

        squadrespt.setVisible(false);
        iscrivitispt.setVisible(false);




        //calcola le dimensioni della finestra
        //in base ai componenti che sono stati messi
        pack();
        infolbl.setVisible(false);

        setLocationRelativeTo(null);

        setVisible(true);

        //vieta il ridimensionamento della finestra
        setResizable(false);
    }

    public void loginTrue(){
        creapnl.setVisible(true);
        iscrivitipnl.setVisible(true);
        squadrepnl.setVisible(true);
        squadrespt.setVisible(true);
        iscrivitispt.setVisible(true);
        userpnl.setVisible(false);
        passpnl.setVisible(false);
        buttonpnl.setVisible(false);
        infolbl.setVisible(false);
        pack();
        setLocationRelativeTo(null);
    }

    public void loginFalse(){
        infolbl.setText("Nickname o password errati.");
        infolbl.setVisible(true);
    }

    public  Login getFrame(){
        return this;
    }
}
