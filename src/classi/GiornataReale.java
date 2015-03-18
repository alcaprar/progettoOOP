package classi;

import java.util.Date;

/**
 * Created by alessandro on 17/03/15.
 */
public class GiornataReale {
    private int NumeroGiornata;
    private Date DataInizio;
    private Date OraInizio;
    private Date DataFine;
    private Date OraFine;

    public GiornataReale(int numeroGiornata){
        NumeroGiornata = numeroGiornata;
    }

    public GiornataReale(int numeroGiornata, Date dataInizio, Date oraInizio, Date dataFine, Date oraFine) {
        NumeroGiornata = numeroGiornata;
        DataInizio = dataInizio;
        OraInizio = oraInizio;
        DataFine = dataFine;
        OraFine = oraFine;
    }

    public Date getDataInizio() {
        return DataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        DataInizio = dataInizio;
    }

    public Date getOraInizio() {
        return OraInizio;
    }

    public void setOraInizio(Date oraInizio) {
        OraInizio = oraInizio;
    }

    public Date getDataFine() {
        return DataFine;
    }

    public void setDataFine(Date dataFine) {
        DataFine = dataFine;
    }

    public Date getOraFine() {
        return OraFine;
    }

    public void setOraFine(Date oraFine) {
        OraFine = oraFine;
    }

    public int getNumeroGiornata() {

        return NumeroGiornata;
    }

    public void setNumeroGiornata(int numeroGiornata) {
        NumeroGiornata = numeroGiornata;
    }
}
