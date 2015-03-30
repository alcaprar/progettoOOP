package db;

import java.sql.*;
import java.util.ArrayList;

import classi.*;
import utils.*;


/**
 * Classe per la comunicazione col database
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Mysql{
    static final private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final private String DB_URL = "jdbc:mysql://db4free.net/progogg";
    static final private String USER = "progogg";
    static final private String PASS = "pagliarecci";
    private Utils utils = new Utils();

    /**
     * Controlla nel database se le credenziali inserite e passate tramite
     * l'oggetto Persona sono giuste.
     * @param utente utente da loggare
     * @return true se l'utente esiste e ha inserito la password giusta
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean login(Persona utente) throws SQLException,ClassNotFoundException{
        Connection conn = null ;
        PreparedStatement loginStmt = null;
        String loginSql = "SELECT * FROM Utente where Nickname=? and Password=?";
        ResultSet loginRs = null;
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            loginStmt = conn.prepareStatement(loginSql);
            loginStmt.setString(1,utente.getNickname());
            loginStmt.setString(2, utente.getPassword());
            loginRs = loginStmt.executeQuery();
            if(loginRs.next()){
                utente.setNome(loginRs.getString("Nome"));
                utente.setCognome(loginRs.getString("Cognome"));
                utente.setEmail(loginRs.getString("Email"));
                return true;
            }
            else return false;
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(loginStmt!=null){
                try{
                    loginStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(loginRs!=null){
                try{
                    loginRs.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * Registra un nuovo utente.
     * @param utente utente da registrare
     * @return true se la registrazione è andata a buon fine
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean registra(Persona utente)throws SQLException, ClassNotFoundException{
        Connection conn = null ;
        PreparedStatement registraStmt = null;
        String registraSql ="INSERT into Utente value(?,?,?,?,?,?)";
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            registraStmt = conn.prepareStatement(registraSql);
            registraStmt.setString(1, utente.getNickname());
            registraStmt.setString(2, utente.getPassword());
            registraStmt.setString(3, utente.getNome());
            registraStmt.setString(4,utente.getCognome());
            registraStmt.setString(5,utente.getEmail());
            registraStmt.setString(6, "u");  //tipo: utente

            int rs = registraStmt.executeUpdate();
            if(rs==1){
                return true;
            }
            else{
                return false;
            }
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(registraStmt!=null){
                try{
                    registraStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Scarica dal database la lista degli utenti.
     * Viene utilizzate per mostrare la lista degli utenti disponibili durante
     * la creazione del campionato.
     * @return lista degli utenti disponibili
     */
    public ArrayList<Persona> selectUtenti(){
        Connection conn=null ;
        PreparedStatement contaStmt = null ;
        PreparedStatement utentiStmt = null;

        String contaString = "SELECT count(*) from Utente where TipoUtente='u'";
        String utentiString = "SELECT * from Utente where TipoUtente='u'";

        ResultSet rsCount =null;
        ResultSet rsUtenti = null;
        //String[] utenti=null;
        ArrayList<Persona> listaUtenti = new ArrayList<Persona>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            contaStmt = conn.prepareStatement(contaString);

            rsCount = contaStmt.executeQuery();
            rsCount.next();
            int numeroUtenti = rsCount.getInt("count(*)");

            if(numeroUtenti!=0){
                utentiStmt = conn.prepareStatement(utentiString);
                rsUtenti = utentiStmt.executeQuery();
                int i =0;
                while(rsUtenti.next()){
                    listaUtenti.add(new Persona(rsUtenti.getString("Nickname"),rsUtenti.getString("Nome"),rsUtenti.getString("Cognome"),rsUtenti.getString("Email")));
                    i++;
                }
            }

            return  listaUtenti;

        }catch(SQLException se){
            se.printStackTrace();
            return listaUtenti;

        }catch(Exception e){
            e.printStackTrace();
            return listaUtenti;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(contaStmt!=null){
                try {
                    contaStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(utentiStmt!=null){
                try{
                    utentiStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsCount!=null){
                try{
                    rsCount.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsUtenti!= null){
                try{
                    rsUtenti.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Scarica la lista completa dei giocatori.
     * Serve al momento della creazione delle squadre
     * @return lista di tutti i giocatori
     */
    public ArrayList<Giocatore> selectGiocatoriAdmin(){
        Connection conn = null;
        PreparedStatement giocatoriStmt = null;
        String giocatoriSql ="SELECT * from CalciatoreAnno";

        PreparedStatement contaStmt = null;
        String contaSql ="SELECT count(*) from CalciatoreAnno";

        ResultSet rsGiocatori = null;
        ResultSet rsConta =null;

        ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            contaStmt = conn.prepareStatement(contaSql);
            rsConta = contaStmt.executeQuery();
            rsConta.next();
            int numeroGiocaori = rsConta.getInt("count(*)");


            if(numeroGiocaori!=0) {
                giocatoriStmt = conn.prepareStatement(giocatoriSql);
                rsGiocatori = giocatoriStmt.executeQuery();
                int i = 0;
                while (rsGiocatori.next()) {
                    listaGiocatori.add(new Giocatore(rsGiocatori.getString("Cognome"), rsGiocatori.getInt("ID"), rsGiocatori.getInt("Costo"), rsGiocatori.getString("SqReale"), rsGiocatori.getString("Ruolo").charAt(0)));
                    i++;
                }
            }
            return  listaGiocatori;

        }catch(SQLException se){
            se.printStackTrace();
            return listaGiocatori;

        }catch(Exception e){
            e.printStackTrace();
            return listaGiocatori;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
            if(contaStmt!=null){
                try{
                    contaStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(giocatoriStmt!=null){
                try{
                    giocatoriStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsConta!=null){
                try{
                    rsConta.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsGiocatori!=null){
                try{
                    rsGiocatori.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Scarica la lista dei giocatori disponibili per il campionato passato.
     * Per giocatori disponibili si intende quei giocatori che non appertongono
     * a nessuna squadra di quel campionato.
     * @param campionato
     * @return lista di giocatori liberi (non tesserati da nessuno)
     */
    public ArrayList<Giocatore> selectGiocatoriDisponibili(Campionato campionato){
        Connection conn=null;
        PreparedStatement giocatoriStmt = null;
        String giocatoriSql = "select * from CalciatoreAnno left join Tesseramento on CalciatoreAnno.ID=Tesseramento.IDcalcAnno and (Tesseramento.NomeCampionato=?) where Tesseramento.IDcalcAnno is null";
        ResultSet rsGiocatori=null;

        ArrayList<Giocatore> listaGiocatoriDisponibili = new ArrayList<Giocatore>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            giocatoriStmt = conn.prepareStatement(giocatoriSql);
            giocatoriStmt.setString(1, campionato.getNome());

            rsGiocatori = giocatoriStmt.executeQuery();
            while(rsGiocatori.next()){
                int ID = rsGiocatori.getInt("ID");
                String cognome = rsGiocatori.getString("Cognome");
                int costo = rsGiocatori.getInt("Costo");
                int prezzoPagato = rsGiocatori.getInt("PrezzoPagato");
                String squadraReale = rsGiocatori.getString("SqReale");
                char ruolo=rsGiocatori.getString("Ruolo").charAt(0);
                Giocatore giocatore = new Giocatore(ID,cognome,costo,prezzoPagato,squadraReale,ruolo);
                listaGiocatoriDisponibili.add(giocatore);

            }
            return  listaGiocatoriDisponibili;
        } catch(SQLException se){
            se.printStackTrace();
            return listaGiocatoriDisponibili;
        } catch (Exception e){
            e.printStackTrace();
            return listaGiocatoriDisponibili;
        } finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(giocatoriStmt!=null){
                try{
                    giocatoriStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsGiocatori!=null){
                try{
                    rsGiocatori.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Aggiunge un giocatore ad una squadra.
     * Viene utilizzata per modificare le rose mentre il campionato è in corso.
     * Inserisce un giocatore ad una squadra.
     * Per inserire i giocatori ad inizio campionato viene utilizzata un'altra funzione.
     * @see #inserisciGiocatoriAnno(ArrayList)
     * @see #rimuoviGiocatore(Campionato, Squadra, int, int)
     * @param campionato
     * @param squadra
     * @param ID id del giocatore da aggiungere
     * @param prezzoPagato prezzo pagato per il giocatore
     * @return true se l'aggiornamento è andato a buon fine
     */
    public boolean aggiungiGiocatore(Campionato campionato,Squadra squadra, int ID, int prezzoPagato){
        Connection conn = null;
        PreparedStatement giocatorestmt = null;
        String giocatoreSql = "INSERT into Tesseramento value(?,?,?,?)";

        PreparedStatement soldiDisponibilistmt = null;
        String soldiDisponibiliSql = "UPDATE Iscrizione set CreditiDisponibili=CreditiDisponibili - ? where IDsq=?";

        int rsGiocatore = 0;
        int rsCrediti = 0;

        try {
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            giocatorestmt = conn.prepareStatement(giocatoreSql);
            giocatorestmt.setInt(1,ID);
            giocatorestmt.setString(2, campionato.getNome());
            giocatorestmt.setInt(3,prezzoPagato);
            giocatorestmt.setInt(4,squadra.getID());

            rsGiocatore = giocatorestmt.executeUpdate();

            soldiDisponibilistmt = conn.prepareStatement(soldiDisponibiliSql);
            soldiDisponibilistmt.setInt(1,prezzoPagato);
            soldiDisponibilistmt.setInt(2, squadra.getID());

            rsCrediti = soldiDisponibilistmt.executeUpdate();

            return (rsGiocatore==1 && rsCrediti==1);

        } catch(SQLException se){
            se.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Viene utilizzata per modificare le rose mentre il campionato è in corso.
     * Rimuovere un giocatore da una squadra.
     * @see #aggiungiGiocatore(Campionato, Squadra, int, int)
     * @param campionato
     * @param squadra
     * @param ID id del giocatore da vendere
     * @param prezzoVendita prezzo di vendita del giocatore
     * @return true se l'aggiornamento è andato a buon fine
     */
    public boolean rimuoviGiocatore(Campionato campionato,Squadra squadra, int ID, int prezzoVendita){
        Connection conn = null;
        PreparedStatement giocatorestmt = null;
        String giocatoreSql = "DELETE from Tesseramento where IDsq=? and IDcalcAnno=?";

        PreparedStatement soldiDisponibilistmt = null;
        String soldiDisponibiliSql = "UPDATE Iscrizione set CreditiDisponibili=CreditiDisponibili + ? where IDsq=?";

        int rsGiocatore = 0;
        int rsCrediti = 0;

        try {
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            giocatorestmt = conn.prepareStatement(giocatoreSql);
            giocatorestmt.setInt(1, squadra.getID());
            giocatorestmt.setInt(2, ID);

            rsGiocatore = giocatorestmt.executeUpdate();

            soldiDisponibilistmt = conn.prepareStatement(soldiDisponibiliSql);
            soldiDisponibilistmt.setInt(1,prezzoVendita);
            soldiDisponibilistmt.setInt(2, squadra.getID());

            rsCrediti = soldiDisponibilistmt.executeUpdate();

            return (rsGiocatore==1 && rsCrediti==1);

        } catch(SQLException se){
            se.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Scarica la lista di giocatori di una squadra.
     * Se ancora il presidente di lega non ha inserito le rose viene
     * restituito lo stesso un ArrayList ma vuoto. (Controllare isEmpty)
     * @param squadra squadra di cui si vuole trovare la lista di giocatori
     * @return lista dei giocatori della squadra
     */
    public ArrayList<Giocatore> selectGiocatori(Squadra squadra){
        Connection conn = null;
        PreparedStatement listaGiocatoristmt = null;
        String listaGiocatoriSql = "select IDcalcAnno,PrezzoPagato,Costo,SqReale,Cognome,Ruolo from Tesseramento JOIN Fantasquadra on Fantasquadra.ID=Tesseramento.IDsq JOIN CalciatoreAnno on CalciatoreAnno.ID=Tesseramento.IDcalcAnno where IDsq=?";

        ResultSet rsGiocatori=null;

        int IDsq = squadra.getID();

        ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            listaGiocatoristmt = conn.prepareStatement(listaGiocatoriSql);
            listaGiocatoristmt.setInt(1, IDsq);

            rsGiocatori = listaGiocatoristmt.executeQuery();

            while(rsGiocatori.next()){
                int ID = rsGiocatori.getInt("IDcalcAnno");
                String cognome = rsGiocatori.getString("Cognome");
                int costo = rsGiocatori.getInt("Costo");
                int prezzoPagato = rsGiocatori.getInt("PrezzoPagato");
                String squadraReale = rsGiocatori.getString("SqReale");
                char ruolo=rsGiocatori.getString("Ruolo").charAt(0);
                Giocatore giocatore = new Giocatore(ID,cognome,costo,prezzoPagato,squadraReale,ruolo);
                listaGiocatori.add(giocatore);
            }

            return listaGiocatori;


        }catch(SQLException se){
            se.printStackTrace();
            return listaGiocatori;

        }catch(Exception e){
            e.printStackTrace();
            return listaGiocatori;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(listaGiocatoristmt!=null){
                try{
                    listaGiocatoristmt.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(rsGiocatori!=null){
                try{
                    rsGiocatori.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Scarica la lista delle squadre di cui è presidente l'utente loggato.
     * Inoltre scarica anche la lista di partecipanti al campionato delle singole squadre.
     * Tutti gli altri dati del campionato verranno popolati in seguito.
     * @param utente utente loggato
     * @return lista delle squadre di cui è presidente l'utente loggato
     */
    public ArrayList<Squadra> selectSquadre(Persona utente){
        Connection conn = null;
        PreparedStatement squadreStmt = null;
        String squadreSql ="SELECT * from Iscrizione JOIN Fantasquadra on Iscrizione.IDsq=Fantasquadra.ID JOIN Campionato on Iscrizione.Campionato = Campionato.Nome JOIN Regolamento on Campionato.Nome=Regolamento.NomeCampionato where NickUt=?";
        ResultSet rsSquadre = null;

        PreparedStatement partecipantiStmt = null;
        String partecipantiSql ="SELECT Fantasquadra.ID, Fantasquadra.Nome, Fantasquadra.NickUt from Iscrizione JOIN Fantasquadra on Iscrizione.IDsq=Fantasquadra.ID JOIN Campionato on Iscrizione.Campionato = Campionato.Nome JOIN Regolamento on Campionato.Nome=Regolamento.NomeCampionato where Campionato=?";
        ResultSet rsPartecipanti = null;

        ArrayList<Squadra> listaSquadre = new ArrayList<Squadra>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            squadreStmt = conn.prepareStatement(squadreSql);
            squadreStmt.setString(1, utente.getNickname());
            rsSquadre = squadreStmt.executeQuery();
            int i = 0;
            while (rsSquadre.next()) {
                partecipantiStmt = conn.prepareStatement(partecipantiSql);
                partecipantiStmt.setString(1,rsSquadre.getString("Campionato"));
                rsPartecipanti = partecipantiStmt.executeQuery();

                ArrayList<Squadra> listaSquadrePartecipanti = new ArrayList<Squadra>();

                while(rsPartecipanti.next()){
                    listaSquadrePartecipanti.add(new Squadra(rsPartecipanti.getInt("ID"),rsPartecipanti.getString("Nome"),new Persona(rsPartecipanti.getString("NickUt"))));
                }
                Persona presidente;
                if(utente.getNome().equals(rsSquadre.getString("Presidente"))) presidente=utente;
                else presidente=new Persona(rsSquadre.getString("Presidente"));
                Squadra squadra = new Squadra(rsSquadre.getInt("ID"),rsSquadre.getString("Nome"),utente,new Campionato(rsSquadre.getString("Campionato"),rsSquadre.getInt("NrPartecipanti"),rsSquadre.getBoolean("Asta"),rsSquadre.getInt("GiornataInizio"),rsSquadre.getInt("GiornataFine"),rsSquadre.getInt("CreditiIniziali"),rsSquadre.getInt("OrarioConsegna"),rsSquadre.getInt("PrimaFascia"),rsSquadre.getInt("LargFascia"),rsSquadre.getInt("BonusCasa"),presidente,listaSquadrePartecipanti,rsSquadre.getBoolean("GiocatoriDaInserire"),rsSquadre.getInt("ProssimaGiornata")),rsSquadre.getInt("CreditiDisponibili"));
                listaSquadre.add(squadra);
                i++;
            }

            return  listaSquadre;

        }catch(SQLException se){
            se.printStackTrace();
            return listaSquadre;

        }catch(Exception e){
            e.printStackTrace();
            return listaSquadre;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(squadreStmt!=null){
                try{
                    squadreStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(partecipantiStmt!=null){
                try{
                    partecipantiStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsSquadre!=null){
                try{
                    rsSquadre.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsPartecipanti!=null){
                try{
                    rsPartecipanti.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Scarica la classifica di un campionato.
     * Anche se il campionato deve ancora cominciare la classifica esiste già,
     * con tutti i campi a zero. (tranne i nomi delle squadre)
     * @param campionato
     * @return classifica
     */
    public ArrayList<Classifica> selectClassifica(Campionato campionato){
        Connection conn = null;
        PreparedStatement classificastmt = null;
        String classificaSql ="SELECT * from Classifica JOIN Fantasquadra on Classifica.IDsq=Fantasquadra.ID where NomeCampionato=?";
        ResultSet rsClassifica=null;

        ArrayList<Classifica> classifica = new ArrayList<Classifica>();

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);


            classificastmt = conn.prepareStatement(classificaSql);
            classificastmt.setString(1, campionato.getNome());
            rsClassifica = classificastmt.executeQuery();
            int i = 0;
            while (rsClassifica.next()) {
                Squadra squadra = new Squadra(rsClassifica.getInt("ID"),rsClassifica.getString("Nome"));
                int vinte = rsClassifica.getInt("Vinte");
                int pareggiate = rsClassifica.getInt("Pareggiate");
                int perse = rsClassifica.getInt("Perse");
                int punti = rsClassifica.getInt("Punti");
                float punteggio = rsClassifica.getInt("SommaPunteggi");
                int golFatti = rsClassifica.getInt("GolF");
                int golSubiti = rsClassifica.getInt("GolS");
                int giocate = vinte+pareggiate+perse;
                int diffReti = golFatti-golSubiti;
                classifica.add(new Classifica(squadra, giocate, vinte, perse, pareggiate, golFatti, golSubiti, diffReti, punteggio, punti));
                i++;
            }

            return  classifica;

        }catch(SQLException se){
            se.printStackTrace();
            return classifica;

        }catch(Exception e){
            e.printStackTrace();
            return classifica;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(classificastmt!=null){
                try{
                    classificastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsClassifica!=null){
                try{
                    rsClassifica.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Scarica la lista delle giornate reali.
     * Serve per la parte Admin per modificare la data e l'ora di inizio e fine giornata
     * durante lo svolgimento del campionato, in caso di cambiamenti da parte della Serie A.
     * Se è il primo avvio e nel database non esistono ancora le giornate, vengono
     * create con solo il numero giornata.
     * @return lista delle giornate reali
     */
    public ArrayList<GiornataReale> selectGiornateAdmin(){
        Connection conn = null;
        PreparedStatement giornatestmt = null;
        String giornateSql ="SELECT * from GiornataAnno";
        ResultSet rsGiornate =null;

        PreparedStatement contastmt = null;
        String contaSql ="SELECT count(*) from GiornataAnno";
        ResultSet rscount=null;

        PreparedStatement giornateDefaultstmt=null;
        String giornateDefaultSql = "INSERT into GiornataAnno(NrGioReale) value(?)";

        ArrayList<GiornataReale> listaGiornate = new ArrayList<GiornataReale>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            contastmt = conn.prepareStatement(contaSql);
            rscount = contastmt.executeQuery();
            rscount.next();
            int numeroGiornate = rscount.getInt("count(*)");


            if(numeroGiornate!=0) {
                giornatestmt = conn.prepareStatement(giornateSql);
                rsGiornate = giornatestmt.executeQuery();
                int i = 0;
                while (rsGiornate.next()) {
                    listaGiornate.add(new GiornataReale(rsGiornate.getInt("NrGioReale"),rsGiornate.getTimestamp("DataOraInizio"),rsGiornate.getTimestamp("DataOraFine")));
                    i++;
                }
            } else{
                for(int i =1; i<=38;i++){
                    listaGiornate.add(new GiornataReale(i));
                    giornateDefaultstmt = conn.prepareStatement(giornateDefaultSql);
                    giornateDefaultstmt.setInt(1,i);
                    giornateDefaultstmt.executeUpdate();
                }
            }
            return  listaGiornate;

        }catch(SQLException se){
            se.printStackTrace();
            return listaGiornate;

        }catch(Exception e){
            e.printStackTrace();
            return listaGiornate;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(contastmt!=null){
                try{
                    contastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(giornatestmt!=null){
                try {
                    giornatestmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(giornateDefaultstmt!=null){
                try{
                    giornateDefaultstmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rscount!=null){
                try{
                    rscount.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsGiornate!=null){
                try{
                    rsGiornate.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Scarica il calendario di un campionato passato per parametro.
     * @param campionato
     * @return lista delle giornate(calendario)
     */
    public ArrayList<Giornata> selectGiornate(Campionato campionato){
        Connection conn = null;
        PreparedStatement giornatastmt = null;
        String giornataSql = "SELECT * from Giornata JOIN GiornataAnno on Giornata.NrGioReale=GiornataAnno.NrGioReale where NomeCampionato=?";
        ResultSet rsgiornata =null;

        PreparedStatement partitastmt = null;
        String partitaSql = "SELECT P.ID, P.NrPart, P.PunteggioCasa,P.PunteggioOspite,P.GolCasa,P.GolOspite,FC.ID as FCID,FO.ID as FOID,FC.Nome as FCNome, FO.Nome as FONome FROM Partita as P JOIN Fantasquadra as FC on P.IDFantasquadraCasa=FC.ID JOIN Fantasquadra as FO on P.IDFantasquadraOspite=FO.ID where IDgiorn=?";
        ResultSet rspartita=null;

        ArrayList<Giornata> listaGiornate = new ArrayList<Giornata>();
        try {
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            giornatastmt = conn.prepareStatement(giornataSql);
            giornatastmt.setString(1, campionato.getNome());
            rsgiornata = giornatastmt.executeQuery();
            while(rsgiornata.next()){
                Giornata giornata = new Giornata(rsgiornata.getInt("ID"),rsgiornata.getInt("Nrgio"),new GiornataReale(rsgiornata.getInt("NrGioReale"),rsgiornata.getTimestamp("DataOraInizio"),rsgiornata.getTimestamp("DataOraFine")));

                partitastmt = conn.prepareStatement(partitaSql);
                partitastmt.setInt(1,giornata.getID());

                rspartita = partitastmt.executeQuery();

                ArrayList<Partita> listaPartite = new ArrayList<Partita>();
                while(rspartita.next()){
                    Squadra squadraCasa = null;
                    Squadra squadraOspite=null;
                    for(Squadra squadra : campionato.getListaSquadrePartecipanti()){
                        if(squadra.getID()==rspartita.getInt("FCID")) squadraCasa=squadra;
                        if(squadra.getID()==rspartita.getInt("FOID")) squadraOspite = squadra;
                    }
                    Partita partita = new Partita(rspartita.getInt("ID"),rspartita.getInt("NrPart"),squadraCasa,squadraOspite,rspartita.getInt("GolCasa"),rspartita.getInt("GolOspite"),rspartita.getFloat("PunteggioCasa"),rspartita.getFloat("PunteggioOspite"));
                    listaPartite.add(partita);

                }

                giornata.setPartite(listaPartite);

                listaGiornate.add(giornata);

            }

            return listaGiornate;

        }catch(SQLException se){
            se.printStackTrace();
            return listaGiornate;

        }catch(Exception e){
            e.printStackTrace();
            return listaGiornate;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(giornatastmt!=null){
                try{
                    giornatastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(partitastmt!=null){
                try{
                    partitastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsgiornata!=null){
                try{
                    rsgiornata.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rspartita!=null){
                try{
                    rspartita.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Trova l'ultima giornata(reale) in cui sono stati inseriti i voti.
     * Serve per la parte admin per settare lo spinner,
     * dal quale l'admin sceglie la giornata per cui sta inserendo i voti
     * @return numero giornata(reale)
     */
    public int selectGiornateVotiInseriti(){
        Connection conn = null;
        PreparedStatement votistmt = null;
        String votiSql = "select NrGioReale from Voto group by NrGioReale order by NrGioReale desc limit 1";
        ResultSet rsVoti = null;

        int giornata=0;

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            votistmt = conn.prepareStatement(votiSql);
            rsVoti = votistmt.executeQuery();

            if(rsVoti.next()) giornata = rsVoti.getInt("NrGioReale");


            return giornata;

        }catch(SQLException se){
            se.printStackTrace();
            return giornata;

        }catch(Exception e){
            e.printStackTrace();
            return giornata;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(votistmt!=null){
                try{
                    votistmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsVoti!=null){
                try{
                    rsVoti.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean selectFormazioneInserita(Squadra squadra){
        Connection conn = null;
        PreparedStatement formazioneInseritastmt = null;
        String formazioneInseritaSql = "select * from Formazione where IDpart=? and NomeSq=? group by NomeSq";
        ResultSet rsFormazioneInserita = null;

        boolean flag = false;

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //se la formazione era già stata inserito la cancello per reinserirla
            if(squadra.isFormazioneInserita()){
                deleteFormazioneInserita(squadra);
            }

            //preparo lo statemant e inserisco la formazione
            formazioneInseritastmt = conn.prepareStatement(formazioneInseritaSql);
            formazioneInseritastmt.setInt(1, squadra.prossimaPartita().getID());
            formazioneInseritastmt.setString(2, squadra.getNome());

            rsFormazioneInserita = formazioneInseritastmt.executeQuery();

            if(rsFormazioneInserita.next()) flag = true;
            return flag;


        }catch(SQLException se){
            se.printStackTrace();
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(formazioneInseritastmt!=null){
                try{
                    formazioneInseritastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsFormazioneInserita!=null){
                try{
                    rsFormazioneInserita.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * La funzione scarica dal database la formazione della squadra per la partita in questione.
     * Viene ritornato un arraylist di giocatori in ordine di schieramento.
     * @param IDpart
     * @param squadra
     * @return formazione
     */
    public ArrayList<Voto> selectFormazioni(int IDpart, Squadra squadra){
        Connection conn = null;
        PreparedStatement formazionestmt = null;
        //String formazioneSql ="select Formazione.IDcalcAnno from Formazione Join Partita on Formazione.IDpart=Partita.ID join Giornata on Partita.IDgiorn=Giornata.ID Join Voto on Formazione.IDcalcAnno=Voto.IDcalcAnno where IDpart=? and NomeSq=? and Voto.NrGioReale=(select NrGioReale from Partita join Giornata on Partita.IDgiorn=Giornata.ID where Partita.ID=?)";
        String formazioneSql ="SELECT * from Formazione where IDpart=? and NomeSq=? order by Pos";
        ResultSet rsFormazione = null;

        PreparedStatement votostmt = null;
        String votoSql = "select * from Formazione as F left join Voto as V on F.IDcalcAnno=V.IDcalcAnno where IDpart=? and F.IDcalcAnno=? and V.NrGioReale=(select NrGioReale from Partita as P join Giornata as G on P.IDgiorn=G.ID where P.ID=?)";
        ResultSet rsvoto =null;

        ArrayList<Voto> formazione = new ArrayList<Voto>();
        try {
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            //formazione
            formazionestmt = conn.prepareStatement(formazioneSql);
            formazionestmt.setInt(1, IDpart);
            formazionestmt.setString(2, squadra.getNome());
            //formazionestmt.setInt(3,IDpart);
            rsFormazione = formazionestmt.executeQuery();


            while(rsFormazione.next()) {
                for (Giocatore giocatore : squadra.getGiocatori()) {
                    if(giocatore.getID()==rsFormazione.getInt("IDcalcAnno")){
                        votostmt = conn.prepareStatement(votoSql);
                        votostmt.setInt(1,IDpart);
                        votostmt.setInt(2,giocatore.getID());
                        votostmt.setInt(3,IDpart);
                        rsvoto = votostmt.executeQuery();
                        Voto votoG = null;
                        if(rsvoto.next()){
                            int golF = rsvoto.getInt("GolF");
                            int golS = rsvoto.getInt("GolS");
                            int autoG = rsvoto.getInt("AutoG");
                            int rigF = rsvoto.getInt("RigF");
                            int rigS = rsvoto.getInt("RigS");
                            int rigP = rsvoto.getInt("RigP");
                            int ass = rsvoto.getInt("Ass");
                            int amm = rsvoto.getInt("Amm");
                            int esp = rsvoto.getInt("Esp");
                            int assFermo = rsvoto.getInt("AssFermo");
                            float voto = rsvoto.getFloat("Voto");
                            votoG = new Voto(giocatore,golF, voto,golS,rigP,rigS,rigF,autoG,amm,esp,ass,assFermo);

                            giocatore.setVoti(votoG);
                        }else{
                            votoG = new Voto(giocatore);
                        }

                        formazione.add(votoG);
                    }
                }
            }
            return formazione;
        } catch(SQLException se){
            se.printStackTrace();
            return formazione;
        } catch(ClassNotFoundException e){
            e.printStackTrace();
            return formazione;
        } finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(formazionestmt!=null){
                try{
                    formazionestmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(votostmt!=null){
                try{
                    votostmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsFormazione!=null){
                try{
                    rsFormazione.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsvoto!=null){
                try{
                    rsvoto.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Aggiorna il nome della squadra.
     * Serve per il primo login per settare il nome della squadra,
     * che al momento della creazione è null.
     * @param squadra
     */
    public void aggiornaNomeSquadra(Squadra squadra){
        Connection conn = null;
        PreparedStatement aggiornaNomeStmt = null;
        String aggiornaNomeSql = "UPDATE Fantasquadra set Nome =? where ID=?";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            aggiornaNomeStmt = conn.prepareStatement(aggiornaNomeSql);
            aggiornaNomeStmt.setString(1, squadra.getNome());
            aggiornaNomeStmt.setInt(2, squadra.getID());
            aggiornaNomeStmt.executeUpdate();

        }catch(SQLException se){
            se.printStackTrace();


        }catch(Exception e){
            e.printStackTrace();


        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(aggiornaNomeStmt!=null){
                try{
                    aggiornaNomeStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Aggiorna il nome dell'utente quando viene cambiato nel pannello info.
     * @param utente
     */
    public void aggiornaNomeUtente(Persona utente){
            Connection conn = null;
            PreparedStatement aggiornastmt = null;
            String aggiornaSql = "UPDATE Utente set Nome =? where Nickname=?";

            try{
                //registra il JBCD driver
                Class.forName(JDBC_DRIVER);
                //apre la connessionename
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                aggiornastmt = conn.prepareStatement(aggiornaSql);
                aggiornastmt.setString(1, utente.getNome());
                aggiornastmt.setString(2, utente.getNickname());
                aggiornastmt.executeUpdate();

            }catch(SQLException se){
                se.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                if(conn!=null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(aggiornastmt!=null){
                    try{
                        aggiornastmt.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
    }

    /**
     * Aggiorna il cognome dell'utente quando viene cambiato nel pannello info.
     * @param utente
     */
    public void aggiornaCognomeUtente(Persona utente){
        Connection conn = null;
        PreparedStatement aggiornastmt = null;
        String aggiornaSql = "UPDATE Utente set Cognome =? where Nickname=?";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessionename
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            aggiornastmt = conn.prepareStatement(aggiornaSql);
            aggiornastmt.setString(1, utente.getCognome());
            aggiornastmt.setString(2, utente.getNickname());
            aggiornastmt.executeUpdate();

        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(aggiornastmt!=null){
                try{
                    aggiornastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Aggiorna la giornata reale quando viene cambiato dall'admin.
     * @param giornata
     */
    public void aggiornaGiornataReale(GiornataReale giornata){
        Connection conn=null;
        PreparedStatement giornatastmt = null;
        String giornataSql = "UPDATE GiornataAnno set DataOraInizio=?, DataOraFine=? where NrGioReale=?";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessionename
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            giornatastmt = conn.prepareStatement(giornataSql);
            giornatastmt.setTimestamp(1, new Timestamp(giornata.getDataOraInizio().getTime()));
            giornatastmt.setTimestamp(2, new Timestamp(giornata.getDataOraFine().getTime()));
            giornatastmt.setInt(3, giornata.getNumeroGiornata());

            giornatastmt.executeUpdate();

        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(giornatastmt!=null){
                try{
                    giornatastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Cancella la formazione inserita.
     * Viene utilizzato quando si vuole aggiornare la formazione già inserita.
     * Viene prima cancellata con questo metodo e poi inserita con il metodo per l'inserimento.
     * @param squadra
     * @return true se l'aggiornamento è andato a buon fine
     */
    public boolean deleteFormazioneInserita(Squadra squadra){
        Connection conn = null;
        PreparedStatement deleteFormazioneInseritastmt = null;
        String deleteFormazioneInseritaSql = "delete from Formazione where IDpart=? and NomeSq=?";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            int i =0;

            deleteFormazioneInseritastmt = conn.prepareStatement(deleteFormazioneInseritaSql);
            deleteFormazioneInseritastmt.setInt(1, squadra.prossimaPartita().getID());
            deleteFormazioneInseritastmt.setString(2, squadra.getNome());

            i = deleteFormazioneInseritastmt.executeUpdate();

            return (i!=0);

        }catch(SQLException se){
            se.printStackTrace();
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(deleteFormazioneInseritastmt!=null){
                try{
                    deleteFormazioneInseritastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * Inserisci i risultati della giornata.
     * Viene richiamato dal metodo che calcola i risultati.
     * @param campionato
     * @return
     */
    public boolean inserisciRisultatiGiornata(Campionato campionato){
        Connection conn = null;
        PreparedStatement partitastmt = null;
        String partitaSql="UPDATE Partita set PunteggioCasa=?, PunteggioOspite=?, GolCasa=?, GolOspite=? where ID=?";

        PreparedStatement prossimaGiornatastmt = null;
        String prossimaGiornataSql = "UPDATE Campionato set ProssimaGiornata=? where Nome=?";

        PreparedStatement classificastmt = null;
        String classificaSql = "UPDATE Classifica set Vinte=?, Pareggiate=?,Perse=?,Punti=?,GolF=?,GolS=?,SommaPunteggi=? where IDsq=?";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            int rsp =0;
            int rsc = 0;

            //inserisco i risultati delle singole partite
            for(Partita partita : campionato.prossimaGiornata().getPartite()){
                partitastmt = conn.prepareStatement(partitaSql);
                //punti casa
                partitastmt.setFloat(1,partita.getPuntiCasa());
                //punti ospite
                partitastmt.setFloat(2, partita.getPuntiFuori());
                //gol casa
                partitastmt.setInt(3, partita.getGolCasa());
                //gol fuori
                partitastmt.setInt(4,partita.getGolFuori());
                //ID partita
                partitastmt.setInt(5,partita.getID());

                rsp =partitastmt.executeUpdate();

            }

            //aggiorno il contatore della prossima giornata
            prossimaGiornatastmt = conn.prepareStatement(prossimaGiornataSql);
            prossimaGiornatastmt.setInt(1,campionato.getProssimaGiornata()+1);
            prossimaGiornatastmt.setString(2, campionato.getNome());
            rsc = prossimaGiornatastmt.executeUpdate();

            //aggiorno la classifica
            for(Classifica rigaClassifica : campionato.getClassifica()){
                classificastmt = conn.prepareStatement(classificaSql);
                //partite vinte
                classificastmt.setInt(1,rigaClassifica.getVinte());
                //partite pareggiate
                classificastmt.setInt(2,rigaClassifica.getPareggiate());
                //partite perse
                classificastmt.setInt(3,rigaClassifica.getPerse());
                //punti
                classificastmt.setInt(4,rigaClassifica.getPunti());
                //gol fatti
                classificastmt.setInt(5,rigaClassifica.getGolFatti());
                //gol subiti
                classificastmt.setInt(6,rigaClassifica.getGolSubiti());
                //somma punteggi
                classificastmt.setFloat(7,rigaClassifica.getPunteggio());
                //IDsquadra
                classificastmt.setInt(8,rigaClassifica.getSquadra().getID());

                classificastmt.executeUpdate();
            }

            return (rsp==1 && rsc==1);
        } catch (SQLException se){
            se.printStackTrace();
            return false;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            return false;
        } finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(classificastmt!=null){
                try{
                    classificastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(partitastmt!=null){
                try{
                    partitastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(prossimaGiornatastmt!=null){
                try{
                    prossimaGiornatastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * Scarica la lista degli avvisi che il presidente di lega ha inviato a tutti i partecipanti.
     * @param squadra
     * @return lista con array di stringhe. [0] =titolo, [1]=testo
     */
    public ArrayList<String[]> selectAvvisi(Squadra squadra){
        Connection conn = null;
        PreparedStatement avvisistmt = null;
        String avvisiSql = "SELECT * from Avvisi where NomeCampionato=? order by ID desc";

        ArrayList<String[]> listaAvvisi = new ArrayList<String[]>();

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            avvisistmt = conn.prepareStatement(avvisiSql);
            avvisistmt.setString(1, squadra.getCampionato().getNome());

            ResultSet rs = avvisistmt.executeQuery();

            while (rs.next()){
                String[] avviso ={rs.getString("Titolo"),rs.getString("Testo")};
                listaAvvisi.add(avviso);
            }

            return listaAvvisi;

        }catch(SQLException se){
            se.printStackTrace();
            return listaAvvisi;
        }catch(Exception e){
            e.printStackTrace();
            return listaAvvisi;
        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(avvisistmt!=null){
                try{
                    avvisistmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Scarica la lista di messaggi che gli utenti hanno inviato al presidente di lega
     * @param squadra
     * @return lista con array di stringhe. [0]= titolo, [0]=testo,[3]=nick utente
     */
    public ArrayList<String[]> selectMessaggi(Squadra squadra){
        Connection conn = null;
        PreparedStatement messaggiStmt = null;
        String messaggiSql = "SELECT M.Titolo, M.Testo, F.Nome from Messaggi as M JOIN Fantasquadra as F on F.ID=M.IDSquadra where NomeCampionato=? order by M.ID desc";
        ResultSet rsMessaggi = null;

        ArrayList<String[]> listaMessaggi = new ArrayList<String[]>();

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            messaggiStmt = conn.prepareStatement(messaggiSql);
            messaggiStmt.setString(1, squadra.getCampionato().getNome());

            rsMessaggi = messaggiStmt.executeQuery();

            while (rsMessaggi.next()){
                String[] avviso ={rsMessaggi.getString("Nome"),rsMessaggi.getString("Titolo"),rsMessaggi.getString("Testo")};
                listaMessaggi.add(avviso);
            }

            return listaMessaggi;

        }catch(SQLException se){
            se.printStackTrace();
            return listaMessaggi;

        }catch(Exception e){
            e.printStackTrace();
            return listaMessaggi;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(messaggiStmt!=null){
                try{
                    messaggiStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(rsMessaggi!=null){
                try{
                    rsMessaggi.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Crea il campionato passato come parametro.
     * Nel database vengono inseriti il regolamento, le squadre partecipanti e il calendario.
     * Le squadre vengono create senza nome, che sarà inserito da ogni utente al primo login.
     * @param campionato
     * @return true se l'inserimento è andato a buon fine
     */
    public boolean creaCampionato(Campionato campionato){
        Connection conn = null ;
        PreparedStatement campionatostmt = null;
        String campionatoSql ="INSERT into Campionato value(?,?,?,?,?,?)";

        PreparedStatement regolamentstmt = null;
        String regolamentoSql = "INSERT into Regolamento value(?,?,?,?,?,?,?,?,?)";

        PreparedStatement iscrizionestmt = null;
        String iscrizioneSql = "INSERT into Fantasquadra(NickUt) value(?)";

        PreparedStatement partecipantistmt = null;
        String partecipantiSql = "INSERT into Iscrizione value(?,?,?)";

        PreparedStatement giornatastmt = null;
        String giornataSql = "INSERT into Giornata(NomeCampionato, NrGio, NrGioReale) value(?,?,?)";

        PreparedStatement partitastmt = null;
        String partitaSql = "INSERT into Partita(IDgiorn,NrPart,IDFantasquadraCasa,IDFantasquadraOspite) value(?,?,?,?)";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //inserisco il campionato
            campionatostmt = conn.prepareStatement(campionatoSql);
            campionatostmt.setString(1,campionato.getNome());
            campionatostmt.setInt(2, campionato.getNumeroPartecipanti());
            campionatostmt.setBoolean(3, campionato.isAstaLive());
            campionatostmt.setString(4, campionato.getPresidente().getNickname());
            campionatostmt.setBoolean(5, campionato.isGiocatoriDaInserire());
            campionatostmt.setInt(6, campionato.getProssimaGiornata());
            int rscampionato = campionatostmt.executeUpdate();

            if(rscampionato==1) {

                //inserisco il regolamento
                regolamentstmt = conn.prepareStatement(regolamentoSql);
                regolamentstmt.setString(1, campionato.getNome());
                regolamentstmt.setInt(2, campionato.getGiornataInizio());
                regolamentstmt.setInt(3, campionato.getGiornataFine());
                regolamentstmt.setInt(4, campionato.getCreditiIniziali());
                regolamentstmt.setInt(5, campionato.getOrarioConsegna());
                regolamentstmt.setInt(6, campionato.getPrimaFascia());
                regolamentstmt.setInt(7, campionato.getLargFascia());
                regolamentstmt.setInt(8, campionato.getBonusCasa());
                regolamentstmt.setInt(9, 0);
                int rsregolamento = regolamentstmt.executeUpdate();

                //creazione squadre e iscrizione
                for (int i = 0; i < campionato.getListaSquadrePartecipanti().size(); i++) {
                    //creo la nuova squadra
                    iscrizionestmt = conn.prepareStatement(iscrizioneSql, Statement.RETURN_GENERATED_KEYS);
                    iscrizionestmt.setString(1, campionato.getListaSquadrePartecipanti().get(i).getProprietario().getNickname());
                    iscrizionestmt.executeUpdate();
                    //trovo l'id della squadra appena inserita
                    ResultSet rs = iscrizionestmt.getGeneratedKeys();
                    rs.next();
                    campionato.getListaSquadrePartecipanti().get(i).setID(rs.getInt(1));

                    //iscrivo la squadra appena creata
                    partecipantistmt = conn.prepareStatement(partecipantiSql);
                    partecipantistmt.setString(1, campionato.getNome());
                    partecipantistmt.setInt(2, campionato.getCreditiIniziali());
                    partecipantistmt.setInt(3, campionato.getListaSquadrePartecipanti().get(i).getID());
                    int rsPartecipanti = partecipantistmt.executeUpdate();
                }

                //creazione e inserimento calendario
                utils.creaCalendario(campionato.getGiornataInizio(),campionato.getGiornataFine(),campionato);


                //inserimento delle giornate
                for(Giornata giornata : campionato.getCalendario()){
                    //inserisco la giornata
                    giornatastmt = conn.prepareStatement(giornataSql, Statement.RETURN_GENERATED_KEYS);
                    giornatastmt.setString(1,campionato.getNome());
                    giornatastmt.setInt(2,giornata.getNumGiornata());
                    giornatastmt.setInt(3,giornata.getGioReale().getNumeroGiornata());
                    giornatastmt.executeUpdate();
                    //trovo l'id della giornata appena inserita
                    ResultSet rs = giornatastmt.getGeneratedKeys();
                    rs.next();
                    giornata.setID(rs.getInt(1));

                    //inserisco le partite della giornata
                    for(Partita partita:giornata.getPartite()){
                        //inserisco la singola partita
                        partitastmt = conn.prepareStatement(partitaSql);
                        partitastmt.setInt(1,giornata.getID());
                        partitastmt.setInt(2,partita.getNumeroPartita());
                        partitastmt.setInt(3,partita.getFormCasa().getSquadra().getID());
                        partitastmt.setInt(4,partita.getFormOspite().getSquadra().getID());

                        partitastmt.executeUpdate();
                    }
                }
            }

            return(rscampionato==1);
        }catch(SQLException se){
            se.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(campionatostmt!=null){
                try{
                    campionatostmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(regolamentstmt!=null){
                try{
                    regolamentstmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(partecipantistmt!=null){
                try{
                    partecipantistmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(iscrizionestmt!=null){
                try{
                    iscrizionestmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(giornatastmt!=null){
                try{
                    giornatastmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(partitastmt!=null){
                try{
                    partecipantistmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Inserisce la lista dei giocatori disponibili in serie A.
     * La lista dei giocatori viene presa da un file csv/xls.
     * @param listaGiocatori
     * @return true se l'inserimento è andato a buon fine
     */
    public boolean inserisciGiocatoriAnno(ArrayList<Giocatore> listaGiocatori){
        Connection conn = null;
        PreparedStatement giocatorestmt = null;
        String giocatoreSql = "INSERT into CalciatoreAnno value(?,?,?,?,?)";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            int rsgiocatore=1;

            for(int i =0;i<listaGiocatori.size() && rsgiocatore==1;i++) {
                giocatorestmt = conn.prepareStatement(giocatoreSql);
                giocatorestmt.setInt(1, listaGiocatori.get(i).getID());
                giocatorestmt.setString(2,Character.toString(listaGiocatori.get(i).getRuolo()));
                giocatorestmt.setString(3,listaGiocatori.get(i).getCognome());
                giocatorestmt.setString(4, listaGiocatori.get(i).getSquadraReale());
                giocatorestmt.setInt(5,listaGiocatori.get(i).getPrezzoBase());

                rsgiocatore= giocatorestmt.executeUpdate();
            }
            return rsgiocatore==1;

        }catch(SQLException se){
            se.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
           if(conn!=null){
               try{
                   conn.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
            if(giocatorestmt!=null){
                try{
                    giocatorestmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Inserisce la lista dei giocatori per ogni squadra del campionato.
     * Dopo aver inserito i giocatori aggiorna i crediti disponibili di ogni squadra.
     * Dopo aver inserito tutti i giocatori aggiorna la flag del campionato
     * che indica se sono stati inseriti i giocatori delle squadre.
     * @param campionato
     * @return
     */
    public boolean inserisciGiocatori(Campionato campionato){
        Connection conn = null;
        PreparedStatement giocatorestmt = null;
        String giocatoreSql = "INSERT into Tesseramento value(?,?,?,?)";

        PreparedStatement soldiDisponibilistmt = null;
        String soldiDisponibiliSql = "UPDATE Iscrizione set CreditiDisponibili=? where IDsq=?";

        PreparedStatement aggiornaInfoCampionatostmt = null;
        String aggiornaInfoCampionatoSql = "UPDATE Campionato set GiocatoriDaInserire=?";
        int rs=0;
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String nomeCampionato = campionato.getNome();

            for(int i=0;i<campionato.getListaSquadrePartecipanti().size();i++){
                int IDsq = campionato.getListaSquadrePartecipanti().get(i).getID();
                soldiDisponibilistmt = conn.prepareStatement(soldiDisponibiliSql);
                soldiDisponibilistmt.setInt(1, campionato.getListaSquadrePartecipanti().get(i).getSoldiDisponibili());
                soldiDisponibilistmt.setInt(2,IDsq);
                soldiDisponibilistmt.executeUpdate();
                for(int k=0;k<25;k++){
                    int IDcalc = campionato.getListaSquadrePartecipanti().get(i).getGiocatori().get(k).getID();
                    int prezzoPagato = campionato.getListaSquadrePartecipanti().get(i).getGiocatori().get(k).getPrezzoAcquisto();

                    giocatorestmt = conn.prepareStatement(giocatoreSql);
                    giocatorestmt.setInt(1,IDcalc);
                    giocatorestmt.setString(2,nomeCampionato);
                    giocatorestmt.setInt(3,prezzoPagato);
                    giocatorestmt.setInt(4,IDsq);

                    rs = giocatorestmt.executeUpdate();
                }
            }
            aggiornaInfoCampionatostmt = conn.prepareStatement(aggiornaInfoCampionatoSql);
            aggiornaInfoCampionatostmt.setBoolean(1, false);
            aggiornaInfoCampionatostmt.executeUpdate();

            if(rs==1)return true;
            else return false;
        }catch(SQLException se){
        se.printStackTrace();
        return false;

        }catch(Exception e){
        e.printStackTrace();
        return false;

        }finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(giocatorestmt!=null){
                try{
                    giocatorestmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(soldiDisponibilistmt!=null){
                try{
                    soldiDisponibilistmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(aggiornaInfoCampionatostmt!=null){
                try{
                    aggiornaInfoCampionatostmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean inserisciVoti(ArrayList<ArrayList<String>> listaVoti, int numeroGiornta){
        Connection conn = null;
        PreparedStatement votostmt=null;
        String votoSql ="INSERT into Voto value(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            int rs=0;

            for(ArrayList<String> voto: listaVoti){
                votostmt = conn.prepareStatement(votoSql);
                votostmt.setInt(1,Integer.parseInt(voto.get(0)));
                votostmt.setInt(2,numeroGiornta);
                //voto
                votostmt.setFloat(3,Float.valueOf(voto.get(1)));
                //gol fatto
                votostmt.setInt(4, Integer.parseInt(voto.get(2)));
                //gol subito
                votostmt.setInt(5,Integer.parseInt(voto.get(3)));
                //autogol
                votostmt.setInt(6,Integer.parseInt(voto.get(7)));
                //rigore fatto
                votostmt.setInt(7,Integer.parseInt(voto.get(6)));
                //rigore subito
                votostmt.setInt(8,Integer.parseInt(voto.get(5)));
                //rigore parato
                votostmt.setInt(9,Integer.parseInt(voto.get(4)));
                //assist
                votostmt.setInt(10,Integer.parseInt(voto.get(10)));
                //ammonizione
                votostmt.setInt(11,Integer.parseInt(voto.get(8)));
                //espulsione
                votostmt.setInt(12,Integer.parseInt(voto.get(9)));
                //assist da fermo
                votostmt.setInt(13,Integer.parseInt(voto.get(11)));

                rs = votostmt.executeUpdate();
            }

            return (rs==1);

        }catch(SQLException se){
            se.printStackTrace();
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }finally {
            try { conn.close(); } catch (Exception e) {  }
        }

    }

    public boolean inserisciFormazione(Squadra squadra, Partita partita){
        Connection conn = null;
        PreparedStatement formazionestmt = null;
        String formazioneSql = "INSERT into Formazione value(?,?,?,?)";

        int rs=0;

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            int i=1;
            for(Voto gioc :squadra.getFormazione().getListaGiocatori()) {
                formazionestmt = conn.prepareStatement(formazioneSql);
                formazionestmt.setInt(1, gioc.getGiocatore().getID());
                formazionestmt.setInt(2,partita.getID());
                formazionestmt.setString(3,squadra.getNome());
                formazionestmt.setInt(4,i);
                i++;
                rs = formazionestmt.executeUpdate();
            }

            return (rs==1);

        }catch(SQLException se){
            se.printStackTrace();
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }finally {
            try { conn.close(); } catch (Exception e) {  }
        }

    }

    public boolean inserisciAvviso(Campionato campionato, String titolo, String testo){
        Connection conn = null;
        PreparedStatement avvisostmt = null;
        String avvisoSql = "INSERT into Avvisi(NomeCampionato, Titolo, Testo) value(?,?,?)";

        int rs=0;

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            avvisostmt = conn.prepareStatement(avvisoSql);
            avvisostmt.setString(1, campionato.getNome());
            avvisostmt.setString(2,titolo);
            avvisostmt.setString(3,testo);

            rs = avvisostmt.executeUpdate();

            return (rs==1);

        }catch(SQLException se){
            se.printStackTrace();
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }finally {
            try { conn.close(); } catch (Exception e) {  }
        }



    }

    /**
     * Inserisce un messaggio di una squadra per il presidente di lega nel database.
     * @param squadra
     * @param titolo
     * @param testo
     * @return true se l'inserimento è andato a buon fine
     */
    public boolean inserisciMessaggio(Squadra squadra, String titolo, String testo){
        Connection conn = null;
        PreparedStatement messaggioStmt = null;
        String messaggioSql = "INSERT into Messaggi(NomeCampionato, IDSquadra, Titolo, Testo) value(?,?,?,?)";

        int rs=0;

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            messaggioStmt = conn.prepareStatement(messaggioSql);
            messaggioStmt.setString(1,squadra.getCampionato().getNome());
            messaggioStmt.setInt(2,squadra.getID());
            messaggioStmt.setString(3,titolo);
            messaggioStmt.setString(4,testo);

            rs = messaggioStmt.executeUpdate();

            return (rs==1);

        }catch(SQLException se){
            se.printStackTrace();
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(messaggioStmt!=null){
                try{
                    messaggioStmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }



    }

    public int deleteCampionati(){
        Connection conn = null;
        PreparedStatement deletestmt = null;
        String[] deleteSql = {"DELETE FROM Campionato","delete from  Iscrizione","delete from Fantasquadra","delete from Classifica","delete from Regolamento","delete from Partita","delete from Giornata"};

        int rs  =0;
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            int i =0;

            for(String delete : deleteSql){
                deletestmt = conn.prepareStatement(delete);
                deletestmt.execute();
                System.out.println(i);
                i++;
            }

            return rs;

        }catch(SQLException se){
            se.printStackTrace();
            return rs;

        }catch(Exception e){
            e.printStackTrace();
            return rs;

        }finally {
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }
}

