package classi;

import java.util.ArrayList;

/**
 * Created by alessandro on 25/02/15.
 */
public class Persona {
    private String nickname;
    private String password;
    private String nome;
    private String cognome;
    private String email;
    private ArrayList<Squadra> presidenza;

    public Persona(String nick){
        this.nickname = nick;
    }

    public Persona(String nick, String pass){
        this.nickname = nick;
        this.password = pass;
    }

    public Persona(String nick, String nome, String cognome, String email){
        this.nickname = nick;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public  Persona(String nick, String pass, String nome,String cognome, String email){
        this.nickname = nick;
        this.password = pass;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }


    public String getNickname() {
        return this.nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public String getEmail() {
        return this.email;
    }

    public ArrayList<Squadra> getPresidenza() {
        return this.presidenza;
    }

    public void setNome(String n){
        this.nome = n;
    }

    public void  setCognome(String c){
        this.cognome = c;
    }

    public void setEmail(String e){
        this.email = e;
    }

    public void duplicate(Persona a) {
        a.nickname = this.nickname;
        a.password = this.password;
        a.cognome = this.cognome;
        a.nome = this.nome;
        a.email = this.email;
        a.presidenza = this.presidenza;
    }

    public boolean equal(Persona a) {
        if (this.nickname == a.nickname) return true;
        else return false;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPresidenza(ArrayList<Squadra> presidenza) {
        this.presidenza = presidenza;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}