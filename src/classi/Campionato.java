package classi;

import java.util.ArrayList;

/**
 * Created by alessandro on 14/03/15.
 */
public class Campionato {
    private String nome;
    private int numeroPartecipanti;
    private boolean astaLive;
    private int giornataInizio;
    private int giornataFine;
    private int prossimaGiornata;
    private int creditiIniziali;
    private int orarioConsegna;
    private int primaFascia;
    private int largFascia;
    private int bonusCasa;
    private boolean giocatoriDaInserire;
    private ArrayList<Squadra> listaSquadrePartecipanti;
    private Persona presidente;
    private ArrayList<Classifica> classifica;
    private ArrayList<Giornata> calendario;
    private ArrayList<String[]> listaAvvisi;
    private ArrayList<String []> listaMessaggi;

    public Campionato(String nome){
        this.nome = nome;
    }

    public Campionato(String nome, int numerop, boolean asta, int inizio, int fine, int crediti, int orario, int primaf, int fasce, int bonusc,Persona presidente,ArrayList<Squadra> listaSquadrePartecipanti,boolean giocatoriDaInserire, int prossimaGiornata ){
        this.nome = nome;
        this.numeroPartecipanti = numerop;
        this.astaLive = asta;
        this.giornataInizio = inizio;
        this.giornataFine = fine;
        this.creditiIniziali = crediti;
        this.orarioConsegna = orario;
        this.primaFascia = primaf;
        this.largFascia = fasce;
        this.bonusCasa = bonusc;
        this.presidente = presidente;
        this.listaSquadrePartecipanti = listaSquadrePartecipanti;
        this.giocatoriDaInserire = giocatoriDaInserire;
        this.prossimaGiornata = prossimaGiornata;
    }

    public Campionato(String nome, int numerop, boolean asta, int inizio, int fine, int crediti, int orario, int primaf, int fasce, int bonusc,Persona presidente ){
        this.nome = nome;
        this.numeroPartecipanti = numerop;
        this.astaLive = asta;
        this.giornataInizio = inizio;
        this.giornataFine = fine;
        this.creditiIniziali = crediti;
        this.orarioConsegna = orario;
        this.primaFascia = primaf;
        this.largFascia = fasce;
        this.bonusCasa = bonusc;
        this.presidente = presidente;
    }

    public ArrayList<Giornata> getCalendario() {
        return calendario;
    }

    public void setCalendario(ArrayList<Giornata> calendario) {
        this.calendario = calendario;
    }

    public ArrayList<Squadra> getListaSquadrePartecipanti() {
        return listaSquadrePartecipanti;
    }

    public void setListaSquadrePartecipanti(ArrayList<Squadra> listaSquadrePartecipanti) {
        this.listaSquadrePartecipanti = listaSquadrePartecipanti;
    }

    public Persona getPresidente() {
        return presidente;
    }

    public void setPresidente(Persona presidente) {
        presidente = presidente;
    }

    public ArrayList<Classifica> getClassifica() {
        return classifica;
    }

    public void setClassifica(ArrayList<Classifica> classifica) {
        this.classifica = classifica;
    }

    public int getNumeroPartecipanti() {
        return numeroPartecipanti;
    }

    public void setNumeroPartecipanti(int numeroPartecipanti) {
        numeroPartecipanti = numeroPartecipanti;
    }

    public boolean isAstaLive() {
        return astaLive;
    }

    public void setAstaLive(boolean astaLive) {
        astaLive = astaLive;
    }

    public int getGiornataInizio() {
        return giornataInizio;
    }

    public void setGiornataInizio(int giornataInizio) {
        giornataInizio = giornataInizio;
    }

    public int getGiornataFine() {
        return giornataFine;
    }

    public void setGiornataFine(int giornataFine) {
        giornataFine = giornataFine;
    }

    public int getCreditiIniziali() {
        return creditiIniziali;
    }

    public void setCreditiIniziali(int creditiIniziali) {
        creditiIniziali = creditiIniziali;
    }

    public int getOrarioConsegna() {
        return orarioConsegna;
    }

    public void setOrarioConsegna(int orarioConsegna) {
        orarioConsegna = orarioConsegna;
    }

    public int getPrimaFascia() {
        return primaFascia;
    }

    public void setPrimaFascia(int primaFascia) {
        primaFascia = primaFascia;
    }

    public int getLargFascia() {
        return largFascia;
    }

    public void setLargFascia(int largFascia) {
        largFascia = largFascia;
    }

    public int getBonusCasa() {
        return bonusCasa;
    }

    public void setBonusCasa(int bonusCasa) {
        bonusCasa = bonusCasa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        nome = nome;
    }

    public ArrayList<String[]> getListaAvvisi() {
        return listaAvvisi;
    }

    public void setListaAvvisi(ArrayList<String[]> listaAvvisi) {
        this.listaAvvisi = listaAvvisi;
    }

    public boolean isGiocatoriDaInserire() {
        return giocatoriDaInserire;
    }

    public void setGiocatoriDaInserire(boolean giocatoriDaInserire) {
        this.giocatoriDaInserire = giocatoriDaInserire;
    }

    public int getProssimaGiornata() {
        return prossimaGiornata;
    }

    public void setProssimaGiornata(int prossimaGiornata) {
        this.prossimaGiornata = prossimaGiornata;
    }

    public ArrayList<String[]> getListaMessaggi() {
        return listaMessaggi;
    }

    public void setListaMessaggi(ArrayList<String[]> listaMessaggi) {
        this.listaMessaggi = listaMessaggi;
    }

    public Giornata prossimaGiornata(){
        Giornata prossima = null;
        for(Giornata giorn:calendario){
            if(giorn.getNumGiornata()==this.prossimaGiornata) prossima = giorn;
        }
        return prossima;
    }

    public Giornata ultimaGiornata(){
        Giornata ultima = null;
        for(Giornata giorn:calendario){
            if(giorn.getNumGiornata()==(prossimaGiornata-1)) ultima =giorn;
        }
        return ultima;
    }

    public void aggiornaClassifica(Giornata giornata){
        for(Partita partita : giornata.getPartite()){
            boolean vittoriaCasa=false,  vittoriaOspite=false;
            if(partita.getGolCasa()>partita.getGolFuori()){
                vittoriaCasa=true;
            } else if( partita.getGolCasa()<partita.getGolFuori()){
                vittoriaOspite=true;
            }
            for(classi.Classifica rigaClassifica : this.classifica){
                if(rigaClassifica.getSquadra().equals(partita.getFormCasa().getSquadra())){
                    rigaClassifica.setGiocate(rigaClassifica.getGiocate()+1);
                    if(vittoriaCasa){
                        rigaClassifica.setVinte(rigaClassifica.getVinte()+1);
                        rigaClassifica.setPunti(rigaClassifica.getPunti()+3);
                    } else if(vittoriaOspite){
                        rigaClassifica.setPerse(rigaClassifica.getPerse()+1);
                    } else{
                        rigaClassifica.setPareggiate(rigaClassifica.getPareggiate()+1);
                        rigaClassifica.setPunti(rigaClassifica.getPunti()+1);
                    }
                    rigaClassifica.setGolFatti(rigaClassifica.getGolFatti()+partita.getGolCasa());
                    rigaClassifica.setGolSubiti(rigaClassifica.getGolSubiti()+partita.getGolFuori());
                    rigaClassifica.setPunteggio(rigaClassifica.getPunteggio()+partita.getPuntiCasa());
                    rigaClassifica.setDiffReti(rigaClassifica.getGolFatti()-rigaClassifica.getGolSubiti());
                } else if(rigaClassifica.getSquadra().equals(partita.getFormOspite().getSquadra())){
                    rigaClassifica.setGiocate(rigaClassifica.getGiocate()+1);
                    if(vittoriaCasa){
                        rigaClassifica.setPerse(rigaClassifica.getPerse()+1);
                    } else if(vittoriaOspite){
                        rigaClassifica.setVinte(rigaClassifica.getVinte()+1);
                        rigaClassifica.setPunti(rigaClassifica.getPunti()+3);
                    } else{
                        rigaClassifica.setPareggiate(rigaClassifica.getPareggiate()+1);
                        rigaClassifica.setPunti(rigaClassifica.getPunti()+1);
                    }
                    rigaClassifica.setGolFatti(rigaClassifica.getGolFatti()+partita.getGolFuori());
                    rigaClassifica.setGolSubiti(rigaClassifica.getGolSubiti()+partita.getGolCasa());
                    rigaClassifica.setPunteggio(rigaClassifica.getPunteggio()+partita.getPuntiFuori());
                    rigaClassifica.setDiffReti(rigaClassifica.getGolFatti()-rigaClassifica.getGolSubiti());
                }
            }
        }

    }

    public Object[] squadreToArray(){
        Object[] arrayObject = new Object[this.listaSquadrePartecipanti.size()];
        for(int i=0;i<this.listaSquadrePartecipanti.size();i++){
            arrayObject[i] =this.listaSquadrePartecipanti.get(i).getNome()+" - " + listaSquadrePartecipanti.get(i).getProprietario().getNickname();
        }
        return arrayObject;
    }

    /**
     * Serve per la creazione della classifica nella home.
     * Ritorna un array di object prendendo i valori dall'oggetto classifica.
     * Nell'array ci sono solo i nomi delle squadre e i punti.
     * @see #classifica
     * @see interfacce.Applicazione.Home
     * @return classifica come array di objet
     */
    public Object[][] classificaToArrayPiccola(){
        Object[][] listaObject = new Object[this.classifica.size()][2];

        for(int i=0;i<this.classifica.size();i++){
            listaObject[i][0] = this.classifica.get(i).getSquadra().getNome();
            listaObject[i][1] = new Integer(this.classifica.get(i).getPunti());
        }
        return listaObject;
    }

    /**
     * Serve per la creazione della classifica dettagliata.
     * Ritorna un array di object prendendo i valori dall'oggetto classifica.
     * Nell'array ci sono tutti i valori presenti in classifica.
     * @see #classifica
     * @see interfacce.Applicazione.Classifica
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
