package entità;

import java.util.ArrayList;

/**
 * Classe per la gestione delle formazioni.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Formazione {

    private Squadra squadra;
    private String modulo;
    private ArrayList<Voto> listaGiocatori;

    /**
     * Costruttore che imposta esclusivamente la squadra a cui è associata la formazione.
     * @param squadra squadra
     */
    public Formazione(Squadra  squadra){
        this.squadra = squadra;
    }

    /**
     * Costruttore che crea la formazione e inserisce la lista dei giocatori, calcolando il modulo utilizzato in base
     * ai giocatori.
     * @param form
     */
    public Formazione(ArrayList<Voto> form){
        this.listaGiocatori = form;
        if(!listaGiocatori.isEmpty()) {
            int d=0;
            int c=0;
            int a=0;
            for (int i = 0; i < 11; i++) {
                if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'D') d++;
                else if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'C') c++;
                else if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'A') a++;
            }
            this.modulo = d + "-" + c + "-" + a;
        }
    }

    /**
     * Costruttore che crea un oggetto formazione da una lista di giocatori e una stringa che definisce il modulo.
     * @param form lista di giocatori della formazione
     * @param modulo stringa del modulo utilizzato
     */
    public Formazione(ArrayList<Giocatore> form, String modulo){
        ArrayList<Voto> listaGioc = new ArrayList<Voto>();
        for(Giocatore gioc:form){
            listaGioc.add(new Voto(gioc));
        }
        this.listaGiocatori = listaGioc;
        this.modulo  = modulo;
    }

    /**
     * Funzione che calcola i punteggi ottenuti dalla squadra per la giornata attuale.
     * @return punteggio
     */
    public float calcolaNew(){
        float p=0;
        if(!listaGiocatori.isEmpty()) {
            int n = 0;
            int d = 0, c = 0, a = 0;
            int dGiocato = 0, cGiocato = 0, aGiocato = 0;
            for (int i = 0; i < 11; i++) {
                if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'D') {
                    d++;
                    if (listaGiocatori.get(i).getVoto() != 0) {
                        p += listaGiocatori.get(i).getMagicVoto();
                        dGiocato++;
                    }
                } else if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'C') {
                    c++;
                    if (listaGiocatori.get(i).getVoto() != 0) {
                        p += listaGiocatori.get(i).getMagicVoto();
                        cGiocato++;
                    }
                } else if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'A') {
                    a++;
                    if (listaGiocatori.get(i).getVoto() != 0) {
                        p += listaGiocatori.get(i).getMagicVoto();
                        aGiocato++;
                    }
                }
            }
            //entra il portiere
            if (listaGiocatori.get(0).getVoto() == 0) p += listaGiocatori.get(11).getMagicVoto();

            //entrano i difensori
            if (dGiocato < d) {
                p += listaGiocatori.get(12).getMagicVoto();
                dGiocato++;
            }
            if (dGiocato < d) {
                p += listaGiocatori.get(13).getMagicVoto();
            }
            //entrano i centrocampisti
            if (cGiocato < c) {
                p += listaGiocatori.get(14).getMagicVoto();
                cGiocato++;
            }
            if (cGiocato < c) {
                p += listaGiocatori.get(15).getMagicVoto();
            }
            //entrano gli attaccanti
            if (aGiocato < a) {
                p += listaGiocatori.get(16).getMagicVoto();
                aGiocato++;
            }
            if (aGiocato < a) {
                p += listaGiocatori.get(17).getMagicVoto();
            }
        }
        return p;
    }


    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    /**
     * Metodo che a partire da una formazione crea un array di array di oggetti, utile per la creazione di una tabella.
     * @return array di array di oggetti
     */
    public Object[][] listaFormToArray(){

        Object[][] listaObject = new Object[listaGiocatori.size()][2];

        for(int i=0;i<listaGiocatori.size();i++){
            listaObject[i][0] = listaGiocatori.get(i).getGiocatore().getCognome();
            listaObject[i][1] = listaGiocatori.get(i).getMagicVoto();
        }
        return listaObject;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public ArrayList<Voto> getListaGiocatori() {
        return listaGiocatori;
    }

    public void setListaGiocatori(ArrayList<Voto> listaGiocatori) {
        this.listaGiocatori = listaGiocatori;
    }
}
