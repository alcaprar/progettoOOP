package classi;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Partita {
    private Formazione formCasa;
    private Formazione formOspite;

    public Partita(Formazione formCasa, Formazione formOspite) {
        this.formCasa = formCasa;
        this.formOspite = formOspite;
    }

    public Formazione getFormCasa() {
        return formCasa;
    }

    public void setFormCasa(Formazione formCasa) {
        this.formCasa = formCasa;
    }

    public Formazione getFormOspite() {
        return formOspite;
    }

    public void setFormOspite(Formazione formOspite) {
        this.formOspite = formOspite;
    }
}
