package classi;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe per la gestione dello storico.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Storico{
    private int ID;
    private String nome;
    private int anno;
    private String presidente;
    private ArrayList<Squadra> listaSquadrePartecipanti;
    private ArrayList<Giornata> calendario;
    private ArrayList<Classifica> classifica;

    /**
     * Costruttore.
     * @param ID id campionato
     * @param nome nome campionato
     * @param anno anno in cui si è svolto il campionato
     * @param presidente presidente del campionato
     */
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

    /**
     * Serve per la creazione della classifica dettagliata.
     * Ritorna un array di object prendendo i valori dall'oggetto classifica.
     * Nell'array ci sono tutti i valori presenti in classifica.
     * @see #classifica
     * @see interfacce.Applicazione.HomeStorico
     * @return
     */
    public Object[][] classificaToArray(){
        Object[][] listaObject = new Object[this.classifica.size()][10];

        for(int i=0;i<this.classifica.size();i++){
            listaObject[i][0] = this.classifica.get(i).getSquadra().getNome();
            listaObject[i][1] = new Integer(this.classifica.get(i).getGiocate());
            listaObject[i][2] = new Integer(this.classifica.get(i).getVinte());
            listaObject[i][3] = new Integer(this.classifica.get(i).getPareggiate());
            listaObject[i][4] = new Integer(this.classifica.get(i).getPerse());
            listaObject[i][5] = new Integer(this.classifica.get(i).getDiffReti());
            listaObject[i][6] = new Integer(this.classifica.get(i).getGolFatti());
            listaObject[i][7] = new Integer(this.classifica.get(i).getGolSubiti());
            listaObject[i][8] = new Float(this.classifica.get(i).getPunteggio());
            listaObject[i][9] = new Integer(this.classifica.get(i).getPunti());
        }
        return listaObject;
    }
}
