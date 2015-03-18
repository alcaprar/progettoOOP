package classi;

/**
 * Created by Christian on 03/03/2015.
 */
public class Giocatore {
    private String cognome;
    private int ID;
    private int prezzoBase;
    private String squadraReale;
    private char ruolo;
    private int datogliere;

    public Giocatore(String cognome,  int id, int prezzo,  String squadra, char ruolo) {
        this.cognome = cognome;
        this.ID = id;
        this.prezzoBase = prezzo;
        this.squadraReale = squadra;
        this.ruolo = ruolo;
    }

    public String getCognome() {
        return cognome;
    }

    public void setNome(String nome) {
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
}
