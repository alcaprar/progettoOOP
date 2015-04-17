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
            //niente da fare
        }
        catch (ClassNotFoundException e) {
            //niente da fare
        }
        catch (InstantiationException e) {
            //niente da fare
        }
        catch (IllegalAccessException e) {
            //niente da fare
        }

        Login logingui = new Login();
  	}
}
