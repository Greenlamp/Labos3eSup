--Suppression des tables De la base de Donn�e CC2.
DROP TABLE Erreur CASCADE CONSTRAINTS;
DROP TABLE Movies CASCADE CONSTRAINTS;
DROP TABLE Countries CASCADE CONSTRAINTS;
DROP TABLE Copies CASCADE CONSTRAINTS;
DROP TABLE Studios CASCADE CONSTRAINTS;
DROP TABLE Produire CASCADE CONSTRAINTS;
DROP TABLE Participer CASCADE CONSTRAINTS;
DROP TABLE Classifier CASCADE CONSTRAINTS;
DROP TABLE Genres CASCADE CONSTRAINTS;
DROP TABLE Appartenir CASCADE CONSTRAINTS;
DROP TABLE Languages CASCADE CONSTRAINTS;
DROP TABLE Parler CASCADE CONSTRAINTS;
DROP TABLE Casts CASCADE CONSTRAINTS;
DROP TABLE Jouer CASCADE CONSTRAINTS;

DROP TABLE Gestionnaires CASCADE CONSTRAINTS;
DROP TABLE Projection CASCADE CONSTRAINTS;
DROP TABLE Salle CASCADE CONSTRAINTS;

DROP TABLE Ecole CASCADE CONSTRAINTS;
DROP TABLE PasserCommande CASCADE CONSTRAINTS;

DROP TABLE my_constraints CASCADE CONSTRAINTS;

--Cr�ation des tables De la base de Donn�e CC2.
create table Erreur(
	dateErreur date default sysdate,
	errcode integer default 0,
	errmessage varchar2(600),
	namePackage varchar2(100),
	nameMethod varchar2(100),
	username varchar2(100)
);

create table my_constraints (
	name varchar2(100) constraint my_constraints_pk primary key,
	message varchar2(512)
);

create table Movies(
	idMovie number(6)
		constraints pk_movies primary key,
	imdb_id varchar2(255),
	name varchar2(255)
		constraint NN_MOVIES_name check(name is not null),
	overview varchar2(1500),
	rating number(6,2),
	released date,
	trailer varchar2(255),
	translated number(1)
		constraint NN_MOVIES_TRANSLATED check(translated is not null)
		constraints CK_MOVIES_TRANSLATED check(translated in ('0', '1')),
	votes number(6),
	name_image_blob varchar2(255),
	image_blob blob default empty_blob(),
	runtime number(6)
);

create table Copies(
	idCopie number(6),
	physique number(1)
		constraint nn_copies_physique check(physique is not null)
		constraints ck_copies_physique check(physique in ('0', '1')),
	idMovie number(6)
		constraints fk_copies_idMovie references Movies,
    deleted number(1) default '0'
		constraints CK_COPIES_deleted check(deleted in ('0', '1')),
        
	constraints pk_copies primary key(idCopie, deleted)
);

create table Studios(
	idStudio number(6)
		constraints pk_studios primary key,
	name varchar2(255)
		constraint NN_STUDIOS_name check(name is not null)
);

create table Produire(
	idStudio number(6)
		constraints fk_produire_idStudio references Studios,
	idMovie number(6)
		constraints fk_produire_idMovie references Movies,
		
	constraints pk_produire primary key(idStudio, idMovie)
);

create table Countries(
	nameCountry varchar2(255)
		constraints pk_countries primary key
);

create table Participer(
	nameCountry varchar2(255)
		constraints fk_participer_nameCountry references Countries,
	idMovie number(6)
		constraints fk_participer_idMovie references Movies,
		
	constraints pk_participer primary key(nameCountry, idMovie)
);

create table Classifier(
	nameCertification varchar2(255)
		constraints ck_classifier_name check(nameCertification in ('G', 'MA', 'NC-17', 'NR', 'PG', 'PG-13', 'R', 'TV-14', 'UR', 'XXX')),
	idMovie number(6)
		constraints fk_classifier_idMovie references Movies,
		
	constraints pk_classifier primary key(nameCertification, idMovie)
);

create table Genres(
	nameGenre varchar2(255)
		constraints pk_genres primary key
);

create table Appartenir(
	nameGenre varchar2(255)
		constraints fk_appartenir_nameGenre references Genres,
	idMovie number(6)
		constraints fk_appartenir_idMovie references Movies,
		
	constraints pk_appartenir primary key(nameGenre, idMovie)
);

create table Languages(
	namelanguage varchar2(255)
		constraints pk_languages primary key
);

create table Parler(
	nameLanguage varchar2(255)
		constraints fk_parler_nameLanguage references Languages,
	idMovie number(6)
		constraints fk_parler_idMovie references Movies,
		
	constraints pk_parler primary key(nameLanguage, idMovie)
);

create table Casts(
	idCast number(6)
		constraints pk_casts primary key,
	nameCast varchar2(255)
		constraint NN_CASTS_nc check(nameCast is not null)
);

create table Jouer(
	idCast number(6)
		constraints fk_jouer_idCast references Casts,
	nameJob varchar2(255)
		constraints ck_jouer_nj check(nameJob in ('actor', 'director')),
	idMovie number(6)
		constraints fk_jouer_idMovie references Movies,
	characters varchar2(255),
		
	constraints pk_jouer primary key(idCast, nameJob, idMovie)
);


create table Gestionnaires(
	login varchar2(255)
		constraints pk_gestionnaires primary key,
	password varchar2(255)
);

create table Salle(
	numeroSalle number(6)
		constraints pk_salle primary key,
	capacite number(6)
);

--Corriger pour que dateheure corresponde a idCopie avec un check
create table Projection(
	dateHeureProjection date,
	numeroSalle number(6)
		constraints fk_projection_numeroSalle references Salle,
	idCopie number(6),
		
	constraints pk_projection primary key(dateHeureProjection, numeroSalle),
	constraints u_projection_heureCopie unique(dateHeureProjection, idCopie)
);

create table Ecole(
	idEcole number(6)
		constraint pk_ecole primary key,
	name varchar2(255)
);

create table PasserCommande(
	idEcole number(6)
		constraints fk_passerCommande references Ecole,
	dateHeureCommande date,
	dateHeureProjection date,
	numeroSalle number(6),
	nbre number(6),
		
	constraints pk_commanderTicket primary key(idEcole, dateHeureCommande, dateHeureProjection, numeroSalle),
	constraints fk_ct_pk foreign key(dateHeureProjection, numeroSalle) references Projection(dateHeureProjection, numeroSalle)
);

