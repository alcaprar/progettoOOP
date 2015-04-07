import javax.swing.*;

import interfacce.login.Login;


/**
 * Gestore fantacalcio.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Fantacalcio{
    public static void main(String args[]){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            exit(-1);
        }
        catch (ClassNotFoundException e) {
            exit(-1);
        }
        catch (InstantiationException e) {
            exit(-1);
        }
        catch (IllegalAccessException e) {
            exit(-1);
        }

        Login logingui = new Login();
  	}
}
