package utils;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe per validare i campi per la registrazione.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Validator {

    /**
     * Controlla che una stringa sia una email valida
     *
     * @param str Indirizzo email
     * @return True se l'indirizzo è valido, False altrimenti
     */
    public static boolean email(String str) {
        Pattern p = Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
        Matcher m = p.matcher(str);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(null, "Il campo 'Email' non è corretto");
        }
        return m.matches();
    }

    /**
     * Controlla che una stringa sia un nickname  valido
     *
     * @param str nickname
      @return True se il nickname è valido, False altrimenti
     */
    public static boolean nickname(String str){
        if(!(str.length()>5 && str.length()<20)){
            JOptionPane.showMessageDialog(null, "Il campo 'Nickname' non è corretto");
        }
        return (str.length()>5 && str.length()<20);
    }
    /**
     * Controlla che una stringa sia una password valida
     *
     * @param str password
     * @return True se la password è valida, False altrimenti
     */
    public static boolean password(String str){
        if(!(str.length()>5 && str.length()<20)){
            JOptionPane.showMessageDialog(null, "Il campo 'Password' non è corretto");
        }
        return(str.length()>5 && str.length()<20);
    }

    /**
     * Controlla che una stringa sia una nome valido
     *
     * @param str nome
     * @return True se il nome è valido, False altrimenti
     */
    public static boolean nome(String str){
        if(!(str.length()>2 && str.length()<20)){
            JOptionPane.showMessageDialog(null, "Il campo 'Nome' non è corretto");
        }
        return (str.length()>5 && str.length()<20);
    }

    /**
     * Controlla che una stringa sia un cognome valido
     *
     * @param str cognome
     * @return True se il cognome è valido, False altrimenti
     */
    public static boolean cognome(String str){
        if(!(str.length()>2 && str.length()<20)){
            JOptionPane.showMessageDialog(null, "Il campo 'Cognome' non è corretto");
        }
        return (str.length()>5 && str.length()<20);
    }
}
