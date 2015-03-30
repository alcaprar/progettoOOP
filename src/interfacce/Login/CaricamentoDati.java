package interfacce.Login;

import javax.swing.*;

/**
 *
 */
public class CaricamentoDati extends JFrame {
    private JLabel caricamentoGif;
    private JPanel mainPanel;
    private JLabel label1;

    public CaricamentoDati(){
        super("Caricamento dati");
        ClassLoader classLoader = getClass().getClassLoader();

        //ImageIcon caricamentoIcon = new ImageIcon(classLoader.getResource("/loading.gif"));
        //System.out.println(classLoader.getResource("loading.gif"));


        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
