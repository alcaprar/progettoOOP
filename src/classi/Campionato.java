package classi;

/**
 * Created by alessandro on 14/03/15.
 */
public class Campionato {
    private String Nome;
    private int NumeroPartecipanti;
    private boolean AstaLive;
    private boolean Pubblico;
    private int GiornataInizio;
    private int GiornataFine;
    private int CreditiIniziali;
    private int OrarioConsegna;
    private int PrimaFascia;
    private int LargFascia;
    private int BonusCasa;

    public Campionato(String nome, int numerop, boolean asta, boolean pubblico, int inizio, int fine, int crediti, int orario, int primaf, int fasce, int bonusc ){
        this.Nome = nome;
        this.NumeroPartecipanti = numerop;
        this.AstaLive = asta;
        this.Pubblico = pubblico;
        this.GiornataInizio = inizio;
        this.GiornataFine = fine;
        this.CreditiIniziali = crediti;
        this.OrarioConsegna = orario;
        this.PrimaFascia = primaf;
        this.LargFascia = fasce;
        this.BonusCasa = bonusc;

    }


}
