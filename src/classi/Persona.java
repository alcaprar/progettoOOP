package classi;

import java.util.ArrayList;

/**
 * Entità persona.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Persona {
    private String nickname;
    private String password;
    private String nome;
    private String cognome;
    private String email;
    private boolean presidenteLega;
    private ArrayList<Squadra> presidenza;
    private ArrayList<Storico> listaStorico;

    /**
     * Costruttore utilizzato nella selezione delle squadre e nella creazione del campionato.
     * @param nick nickname dell'utente
     */
    public Persona(String nick){
        this.nickname =nick;
    }

    /**
     * Costruttore utilizzato per il controllo del login
     * @param nick
     * @param pass
     */
    public Persona(String nick, String pass){
        this.nickname = nick;
        this.password = pass;
    }

    /**
     * Costruttore utlizzato per la selezione degli utenti.
     * @param nick nickname dell'utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email indirizzo email dell'utente
     */
    public Persona(String nick, String nome, String cognome, String email){
        this.nickname = nick;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    /**
     * Costruttore utilizzato nella registrazione degli utenti.
     * @param nick nickname dell'utente
     * @param pass password dell'utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email indirizzo email dell'utente
     */
    public  Persona(String nick, String pass, String nome,String cognome, String email){
        this.nickname = nick;
        this.password = pass;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    /**
     * Metodo per la comparazione di due utenti.
     * @param persona2 utente da confrontare
     * @return true se gli utenti sono uguali
     */
    public boolean equals(Persona persona2) {
        if (this.nickname.equals(persona2.nickname)) return true;
        else return false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPresidenteLega() {
        return presidenteLega;
    }

    public void setPresidenteLega(boolean presidenteLega) {
        this.presidenteLega = presidenteLega;
    }

    public ArrayList<Squadra> getPresidenza() {
        return presidenza;
    }

    public void setPresidenza(ArrayList<Squadra> presidenza) {
        this.presidenza = presidenza;
    }

    public ArrayList<Storico> getListaStorico() {
        return listaStorico;
    }

    public void setListaStorico(ArrayList<Storico> listaStorico) {
        this.listaStorico = listaStorico;
    }
}