package classi;

import java.util.ArrayList;

/**
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Storico {
    private int ID;
    private String nome;
    private int anno;
    private String presidente;
    private ArrayList<Squadra> listaSquadrePartecipanti;
    private ArrayList<Giornata> calendario;
    private ArrayList<Classifica> classifica;

    public Storico(int ID, String nome, int anno, String presidente){
        this.ID = ID;
        this.nome = nome;
        this.anno = anno;
        this.presidente = presidente;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public ArrayList<Giornata> getCalendario() {
        return calendario;
    }

    public void setCalendario(ArrayList<Giornata> calendario) {
        this.calendario = calendario;
    }

    public ArrayList<Classifica> getClassifica() {
        return classifica;
    }

    public void setClassifica(ArrayList<Classifica> classifica) {
        this.classifica = classifica;
    }

    public ArrayList<Squadra> getListaSquadrePartecipanti() {
        return listaSquadrePartecipanti;
    }

    public void setListaSquadrePartecipanti(ArrayList<Squadra> listaSquadrePartecipanti) {
        this.listaSquadrePartecipanti = listaSquadrePartecipanti;
    }
}
