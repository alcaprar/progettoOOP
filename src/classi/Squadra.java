package classi;

/**
 * Created by Christian on 03/03/2015.
 */
public class Squadra {
    private String nomeSq;
    private String proprietario;
    private Giocatore[] giocatori;

    public Squadra(String nome, String prop, Giocatore[] gioc) {
        this.nomeSq = nome;
        this.proprietario = prop;
        for (Giocatore i : gioc) this.giocatori = gioc;
    }

    public String getNome() {
        return this.nomeSq;
    }

    public String getProprietario() {
        return this.proprietario;
    }

    public String[] getLista() {
        int i = 0;
        String[] gioc = new String[25];
        for (Giocatore g : giocatori) {
            gioc[i] = g.getCognome();
            i++;
        }
        return gioc;
    }

}
