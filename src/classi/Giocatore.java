/**
 * Created by Christian on 03/03/2015.
 */
public class Giocatore {
    private String nome;
    private String cognome;
    private int ID;
    private int prezzoBase;
    private int prezzoAcquisto;
    private int voto;
    private boolean acquistato;
    private String squadra;

    public Giocatore(String no, String cog, int id, int pB, int pA, boolean acq, String sq) {
        this.nome = no;
        this.cognome = cog;
        this.ID = id;
        this.prezzoBase = pB;
        this.prezzoAcquisto = pA;
        this.voto = 0;
        this.acquistato = acq;
        this.squadra = sq;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public int getID() {
        return this.ID;
    }

    public int getPB() {
        return this.prezzoBase;
    }

    public int getPA() {
        return this.prezzoAcquisto;
    }

    public int getVoto() {
        return this.voto;
    }

    public boolean checkAcq() {
        return this.acquistato;
    }

    public String getSquadra() {
        return this.squadra;
    }

}
