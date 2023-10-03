CREATE TABLE client (
   idClient serial PRIMARY KEY,
   prenom VARCHAR(30),
   nom VARCHAR(60),
   age int
);

CREATE TABLE Chambre (
   idChambre int PRIMARY KEY,
   nomChambre VARCHAR(30),
   typeLit VARCHAR(30),
   prixBase FLOAT
);

CREATE TABLE Commodite (
   idCommodite int PRIMARY KEY,
   description VARCHAR(30),
   surprusPrix FLOAT
);

CREATE TABLE Reservation (
   idClient int REFERENCES client(idClient),
   idChambre int REFERENCES chambre(idChambre),
   debut date,
   fin time
);

CREATE TABLE ChambreCommodite (
   idChambre int REFERENCES chambre(idChambre),
   idCommodite int REFERENCES commodite(idCommodite)
);