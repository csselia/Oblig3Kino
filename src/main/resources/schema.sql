--Denne tabellen skal lages ved oppstart av webapplikasjonen, og vi skal kunne sette ting i den.
CREATE TABLE Bestilling
(
    id INTEGER AUTO_INCREMENT NOT NULL,
    film VARCHAR(30) NOT NULL,
    antall INTEGER,
    fornavn VARCHAR(30) NOT NULL,
    etternavn VARCHAR(30) NOT NULL,
    telefonnummer INTEGER,
    epost VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE Filmer
(
    Tittel VARCHAR(50) NOT NULL,
    FNr INTEGER,
    PRIMARY KEY (FNr)
);

INSERT INTO Filmer (FNr, Tittel) VALUES
    (1,'Oppenheimer'),
    (2,'Barbie'),
    (3,'Parasite'),
    (4,'Lord of the Rings'),
    (5,'Little Women');

