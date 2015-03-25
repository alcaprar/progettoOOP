package classi;

import jxl.write.DateTime;

import java.util.Date;

/**
 * Created by alessandro on 17/03/15.
 */
public class GiornataReale {
    private int NumeroGiornata;
    private Date DataOraInizio;
    private Date DataOraFine;

    public GiornataReale(int numeroGiornata){
        NumeroGiornata = numeroGiornata;
    }

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
