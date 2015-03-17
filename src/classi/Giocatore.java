package classi;

/**
 * Created by Christian on 03/03/2015.
 */
public class Giocatore {
    private String nome;
    private int ID;
    private int prezzoBase;
    private String squadraReale;
    private char ruolo;

    public Giocatore(String no,  int id, int pB,  String sq, char ruolo) {
        this.nome = no;
        this.ID = id;
        this.prezzoBase = pB;
        this.squadraReale = sq;
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
