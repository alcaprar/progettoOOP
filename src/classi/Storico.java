package classi;

import java.util.ArrayList;

/**
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Storico {
    private int Anno;
    private String nomeCampionato;
    private ArrayList<Squadra> squadrePartecipanti;
    private ArrayList<Classifica> classifica;
    private ArrayList<Giornata> calendario;

    public int getAnno() {
        return Anno;
    }

    public void setAnno(int anno) {
        Anno = anno;
    }

    public ArrayList<Classifica> getClassifica() {
        return classifica;
    }

    public void setClassifica(ArrayList<Classifica> classifica) {
        this.classifica = classifica;
    }

    public ArrayList<Giornata> getCalendario() {
        return calendario;
    }

    public void setCalendario(ArrayList<Giornata> calendario) {
        this.calendario = calendario;
    }

    public String getNomeCampionato() {
        return nomeCampionato;
    }

    public void setNomeCampionato(String nomeCampionato) {
        this.nomeCampionato = nomeCampionato;
    }

    public ArrayList<Squadra> getSquadrePartecipanti() {
        return squadrePartecipanti;
    }

    public void setSquadrePartecipanti(ArrayList<Squadra> squadrePartecipanti) {
        this.squadrePartecipanti = squadrePartecipanti;
    }
}
