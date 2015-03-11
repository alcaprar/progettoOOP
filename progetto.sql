
create table Campionato(
     Nome varchar(20) not null unique,
     NrPartecipanti tinyint not null,
     check(NrPartecipanti=4 or NrPartecipanti=6 or NrPartecipanti=8 or NrPartecipanti=10),
	 Asta boolean,
);

create table Regolamento(
     NomeCampionato varchar(20) primary key references Campionato(Nome) on update cascade on delete no action,
     GiornataInizio tinyint not null default 1,
     GiornataFine tinyint not null default 38,
     check(GiornataInizio>=1 and GiornataInizio<=38),
     check(GiornataFine>=1 and GiornataFine<=38),
     check(GiornataFine > GiornataInizio),
     CreditiIniziali SmallInt unsigned not null default 500,
     OrarioConsegna Tinyint unsigned not null default 5,
     PrimaFascia tinyint unsigned default 66,
     LargFascia tinyint unsigned default 6,
     BonusCasa numeric(2,1) not null default 0,
     ModDifesa tinyint not null default 0
);

create table RegolamentoM(
     IDed int references Edizione(ID) on update cascade on delete no action,
     Dif tinyint not null,
     check(Dif>=0 and Dif<=10),
     Cen tinyint not null,
     check(Cen>=0 and Cen<=10),
     Att tinyint not null,
     check(Att>=0 and Cen<=10),
     primary key modulo (IDed, Dif, Cen, Att)
);

create table Fantasquadra(
     Nome varchar(20) primary key,
     NickUt varchar(20) not null,
     unique key(Nome, NickUt)
);

create table Utente(
     Nickname varchar(20) primary key,
     Password varchar(20),
     Nome varchar(20),
     Cognome varchar(20),
     Email varchar(30),
     TipoUtente tinyint
);

create table Presidenza(
     NomeSq varchar(20) references Fantasquadra(Nome) on update cascade on delete no action,
     NickUt varchar(20) references Utente(Nickname) on update cascade on delete no action,
     primary key(NomeSq, NickUt)
);

create table Iscrizione(
     NomeSq varchar(20) references Fantasquadra(Nome) on update cascade on delete no action,
     Campionato varchar(20) references Campionato(Nome) on update cascade on delete no action,
     CreditiDisponibili smallint,
     primary key (NomeSq, IDed)
);

create trigger Classifica
after insert on Iscrizione
for each row
insert into Classifica (IDed, NomeSq) values(NEW.IDed, NEW.NomeSq);

create table CalciatoreAnno(
     ID int primary key ,
     Cognome varchar(20) not null,
     SqReale varchar(20),
     Ruolo char(1) not null,
     check( Ruolo ="p" or Ruolo="P" or Ruolo="d" or Ruolo="D" or Ruolo="c" or Ruolo="C" or Ruolo="a" or Ruolo="A"),
	 PrezzoIniziale tinyint unsigned,
     IDfg smallint not null,
     unique key(IDfg, Anno)
     );

create table Tesseramento(
     IDcalcAnno int references CalciatoreAnno(ID) on update cascade on delete no action,
     NomeCampionato varchar(20) references Campionato(Nome) on update cascade on delete no action,
     NomeSq varchar(20) references Fantasquadra(Nome) on update cascade on delete no action,
     PrezzoPagato smallint not null,
     primary key(IDcalcAnno, NomeCampionato , NomeSq)
);


create table Voto(
     IDcalcAnno int references CalciatoreAnno(ID) on update cascade on delete no action,
     NrGioReale int,
     Anno year,
     Voto numeric(2,1) unsigned not null,
     GolF tinyint unsigned not null,
     GolS tinyint unsigned not null,
     AutoG tinyint unsigned not null,
     RigF tinyint unsigned not null,
     RigS tinyint unsigned not null,
     RigP tinyint unsigned not null,
     Ass tinyint unsigned not null,
     Amm tinyint unsigned not null,
     Esp tinyint unsigned not null,
     primary key(IDcalcAnno, NrGioReale ,Anno)
);

create table Formazione(
     IDcalcAnno int references CalciatoreAnno(ID) on update cascade on delete no action,
     IDpart int references Partita(ID) on update cascade on delete no action,
     NomeSq varchar(20) references Fantasquadra(Nome) on update cascade on delete no action,
     Pos tinyint unsigned not null,
     Ruolo char(1) not null,
     check( Ruolo ="p" or Ruolo="P" or Ruolo="d" or Ruolo="D" or Ruolo="c" or Ruolo="C" or Ruolo="a" or Ruolo="A"),
     primary key(IDcalcAnno, IDpart)
);

create table GiornataAnno(
    NrGioReale tinyint unsigned primary key,
    DataInizio date not null,
    OraInizio time not null
)


create table Giornata(
     ID int primary key auto_increment,
     NomeCampionato varchar(20) not null references Campionato(Nome) on update cascade on delete no action,
     NrGio tinyint unsigned not null,
     NrGioReale tinyint unsigned not null,
     unique key (NomeCampionato, NrGio, NrGioReale),

);

create table Partita(
     ID int primary key auto_increment,
     IDgiorn int not null references Giornata(ID) on update cascade on delete no action,
     NrPart tinyint not null,
     unique key(IDgiorn, NrPart),
     FantasquadraCasa varchar(20) not null references Fantasquadra(nome) on update cascade on delete no action,
     FantasquadraOspite varchar(20) not null references Fantasquadra(nome) on update cascade on delete no action,
     PunteggioCasa numeric(3,1) default 0,
     PunteggioOspite numeric(3,1) default 0,
     GolCasa tinyint default null,
     GolOspite tinyint default null
);

create table Classifica(
     NomeCampionato varchar(20) not null references Campionato(Nome) on update cascade on delete no action,
     NomeSq varchar(20) not null references Fantasquadra(nome) on update cascade on delete no action,
     Vinte tinyint default 0,
     Pareggiate tinyint default 0,
     Perse tinyint default 0,
     Punti tinyint default 0,
     GolF tinyint default 0,
     GolS tinyint default 0,
     SommaPunteggi numeric(4,1) default 0,
     primary key(Campionato, NomeSq)
);
