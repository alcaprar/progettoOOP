package entità;

import java.util.Date;

/**
 * Classe per la gestione delle giornate reali di campionato
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class GiornataReale {
    private int NumeroGiornata;
    private Date DataOraInizio;
    private Date DataOraFine;

    /**
     * Costruttore che inizializza esclusivamente il numero della giornata (1-38).
     * @param numeroGiornata numero della giornata
     */
    public GiornataReale(int numeroGiornata){
        NumeroGiornata = numeroGiornata;
    }

    /**
     * Costruttore che inizializza il numero della giornata (1-38) e le date di inizio e fine giornata.
     * @param numeroGiornata
     * @param dataOraInizio
     * @param dataOraFine
     */
    public GiornataReale(int numeroGiornata, Date dataOraInizio, Date dataOraFine) {
        NumeroGiornata = numeroGiornata;
        DataOraInizio = dataOraInizio;
        DataOraFine = dataOraFine;
    }

    public int getNumeroGiornata() {
        return NumeroGiornata;
    }

    public void setNumeroGiornata(int numeroGiornata) {
        NumeroGiornata = numeroGiornata;
    }

    public Date getDataOraInizio() {
        return DataOraInizio;
    }

    public void setDataOraInizio(Date dataOraInizio) {
        DataOraInizio = dataOraInizio;
    }

    public Date getDataOraFine() {
        return DataOraFine;
    }

    public void setDataOraFine(Date dataOraFine) {
        DataOraFine = dataOraFine;
    }
}
