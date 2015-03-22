package classi;

/**
 * Created by Christian on 03/03/2015.
 */
public class Giocatore {
    private String cognome;
    private int ID;
    private int prezzoBase;
    private int prezzoAcquisto;
    private String squadraReale;
    private char ruolo;
    private Voto voti;

    public Giocatore(String cognome,  int id, int prezzoBase,  String squadra, char ruolo) {
        this.cognome = cognome;
        this.ID = id;
        this.prezzoBase = prezzoBase;
        this.squadraReale = squadra;
        this.ruolo = ruolo;
    }

    public Giocatore(int id, String cognome,   int prezzoBase, int prezzoAcquisto, String squadra, char ruolo) {
        this.cognome = cognome;
        this.ID = id;
        this.prezzoBase = prezzoBase;
        this.prezzoAcquisto = prezzoAcquisto;
        this.squadraReale = squadra;
        this.ruolo = ruolo;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String nome) {
        this.cognome = nome;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPrezzoBase() {
        return prezzoBase;
    }

    public void setPrezzoBase(int prezzoBase) {
        this.prezzoBase = prezzoBase;
    }

    public String getSquadraReale() {
        return squadraReale;
    }

    public void setSquadraReale(String squadraReale) {
        this.squadraReale = squadraReale;
    }

    public char getRuolo() {
        return ruolo;
    }

    public void setRuolo(char ruolo) {
        this.ruolo = ruolo;
    }

    public Voto getVoti() {
        return voti;
    }

    public void setVoti(Voto voti) {
        this.voti = voti;
    }

    public int getPrezzoAcquisto() {
        return prezzoAcquisto;
    }

    public void setPrezzoAcquisto(int prezzoAcquisto) {
        this.prezzoAcquisto = prezzoAcquisto;
    }
}
