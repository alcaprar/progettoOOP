/**
 * Created by Christian on 04/03/2015.
 */
public class Formazione {
    private enum Modulo {M343, M352, M433, M442, M451, M532, M541}

    private Giocatore[] rosa = new Giocatore[18];

    //funzione per la dichiarazione della formazione, tramite gui si sceglie il modulo e si compone il vettore con le giuste posizioni
    public void setFormazione(Giocatore[] g) {
        for (Giocatore i : g) rosa = g;
    }

    public Giocatore[] getFormazione() {
        return rosa;
    }

}
