package classi;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Voto {
    private float voto;
    private int gol;
    private int golSubito;
    private int rigParato;
    private int rigSbagliato;
    private int rigSegnato;
    private int autogol;
    private int ammonizione;
    private int espulsione;
    private int assist;
    private int assistFermo;
    private float magicVoto;


    public Voto(int gol, float voto, int golSubito, int rigParato, int rigSbagliato, int rigSegnato, int autogol, int ammonizione, int espulsione, int assist, int assistFermo) {
        this.voto = voto;
        this.gol = gol;
        this.golSubito = golSubito;
        this.rigParato = rigParato;
        this.rigSbagliato = rigSbagliato;
        this.rigSegnato = rigSegnato;
        this.autogol = autogol;
        this.ammonizione = ammonizione;
        this.espulsione = espulsione;
        this.assist = assist;
        this.assistFermo = assistFermo;
        this.magicVoto = calcolaMagicVoto(gol, voto, golSubito, rigParato, rigSbagliato, rigSegnato, autogol, ammonizione, espulsione, assist, assistFermo);
    }

    private float calcolaMagicVoto(int gol, float voto, int golSubito, int rigParato, int rigSbagliato, int rigSegnato, int autogol, int ammonizione, int espulsione, int assist, int assistFermo) {
        if (voto==0) return 0;
        else return voto+3*gol;//–golSubito+3*rigParato–3*rigSbagliato+3*rigSegnato–2*autogol–0,5*ammonizione–espulsione+assist+assistFermo;
    }

    public float getVoto() {
        return voto;
    }

    public void setVoto(float voto) {
        this.voto = voto;
    }

    public int getGol() {
        return gol;
    }

    public void setGol(int gol) {
        this.gol = gol;
    }

    public int getGolSubito() {
        return golSubito;
    }

    public void setGolSubito(int golSubito) {
        this.golSubito = golSubito;
    }

    public int getRigParato() {
        return rigParato;
    }

    public void setRigParato(int rigParato) {
        this.rigParato = rigParato;
    }

    public int getRigSbagliato() {
        return rigSbagliato;
    }

    public void setRigSbagliato(int rigSbagliato) {
        this.rigSbagliato = rigSbagliato;
    }

    public int getRigSegnato() {
        return rigSegnato;
    }

    public void setRigSegnato(int rigSegnato) {
        this.rigSegnato = rigSegnato;
    }

    public int getAutogol() {
        return autogol;
    }

    public void setAutogol(int autogol) {
        this.autogol = autogol;
    }

    public int getAmmonizione() {
        return ammonizione;
    }

    public void setAmmonizione(int ammonizione) {
        this.ammonizione = ammonizione;
    }

    public int getEspulsione() {
        return espulsione;
    }

    public void setEspulsione(int espulsione) {
        this.espulsione = espulsione;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getAssistFermo() {
        return assistFermo;
    }

    public void setAssistFermo(int assistFermo) {
        this.assistFermo = assistFermo;
    }

    public float getMagicVoto() {
        return magicVoto;
    }

    public void setMagicVoto(float magicVoto) {
        this.magicVoto = magicVoto;
    }
}
