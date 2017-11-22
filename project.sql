-- PRAGMA Anweisungen

PRAGMA auto_vacuum = 1;
PRAGMA automatic_index = 1;
PRAGMA case_sensitive_like = 0;
PRAGMA defer_foreign_keys = 0;
PRAGMA encoding = 'UTF-8';
PRAGMA foreign_keys = 1;
PRAGMA ignore_check_constraints = 0;
PRAGMA journal_mode = WAL;
PRAGMA query_only = 0;
PRAGMA recursive_triggers = 1;
PRAGMA reverse_unordered_selects = 0;
PRAGMA secure_delete = 0;
PRAGMA synchronous = NORMAL;

-- CREATE Anweisungen

CREATE TABLE IF NOT EXISTS Nutzer (
  Benutzername VARCHAR NOT NULL PRIMARY KEY ASC COLLATE NOCASE,
  EMail        VARCHAR NOT NULL UNIQUE COLLATE NOCASE,
  Geburtsdatum VARCHAR NOT NULL,
  Passwort     VARCHAR NOT NULL,
  Geschlecht   VARCHAR NOT NULL CHECK (Geschlecht IN ('weiblich', 'maennlich'))
);

CREATE TABLE IF NOT EXISTS Redakteur (
  Benutzername VARCHAR NOT NULL PRIMARY KEY ASC REFERENCES Nutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  Nachname     VARCHAR NOT NULL,
  Vorname      VARCHAR NOT NULL,
  Biographie   TEXT
);

CREATE TABLE IF NOT EXISTS Chefredakteur (
  Benutzername  VARCHAR NOT NULL PRIMARY KEY ASC REFERENCES Redakteur (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  Telefonnummer INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS Blogeintrag (
  ID               INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT,
  Titel            VARCHAR NOT NULL COLLATE NOCASE,
  Erstellungsdatum VARCHAR NOT NULL,
  Aenderungsdatum  VARCHAR,
  Text             TEXT    NOT NULL,
  Redakteur        VARCHAR NOT NULL REFERENCES Redakteur (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE
);

CREATE TABLE IF NOT EXISTS Schlagwort (
  Bezeichnung VARCHAR NOT NULL PRIMARY KEY ASC COLLATE NOCASE
);

CREATE TABLE IF NOT EXISTS Videoblog (
  ID          INTEGER NOT NULL PRIMARY KEY ASC REFERENCES Blogeintrag (ID) ON UPDATE CASCADE ON DELETE CASCADE,
  Spiellaenge INTEGER NOT NULL,
  Link720p    VARCHAR NOT NULL COLLATE NOCASE,
  Link1080p   VARCHAR COLLATE NOCASE
);

CREATE TABLE IF NOT EXISTS Album (
  ID           INTEGER NOT NULL PRIMARY KEY ASC REFERENCES Blogeintrag (ID) ON UPDATE CASCADE ON DELETE CASCADE,
  Sichtbarkeit VARCHAR NOT NULL CHECK (Sichtbarkeit IN ('privat', 'oeffentlich'))
);

CREATE TABLE IF NOT EXISTS Bild (
  ID          INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT,
  Bild        BLOB    NOT NULL,
  Bezeichnung VARCHAR NOT NULL COLLATE NOCASE,
  Aufnahmeort VARCHAR NOT NULL COLLATE NOCASE
);

CREATE TABLE IF NOT EXISTS Produktrezension (
  ID                 INTEGER NOT NULL PRIMARY KEY ASC REFERENCES Blogeintrag (ID) ON UPDATE CASCADE ON DELETE CASCADE,
  Produktbezeichnung VARCHAR NOT NULL COLLATE NOCASE,
  Fazit              TEXT    NOT NULL COLLATE NOCASE CHECK (length(Fazit) <= 1000)
);

CREATE TABLE IF NOT EXISTS Kommentar (
  ID               INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT,
  Text             TEXT    NOT NULL COLLATE NOCASE,
  Erstellungsdatum VARCHAR NOT NULL,
  Blogeintrag      INTEGER NOT NULL REFERENCES Blogeintrag (ID) ON UPDATE CASCADE ON DELETE CASCADE,
  Nutzer           VARCHAR NOT NULL REFERENCES Nutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE
);

CREATE TABLE IF NOT EXISTS NutzerBewertetBlogeintrag (
  Nutzer      VARCHAR NOT NULL REFERENCES Nutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  Blogeintrag INTEGER NOT NULL REFERENCES Blogeintrag (ID) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  Bewertung   INTEGER NOT NULL CHECK (Bewertung IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
  PRIMARY KEY (Nutzer ASC, Blogeintrag ASC)
);

CREATE TABLE IF NOT EXISTS NutzerFavorisiertBlogeintrag (
  Nutzer      VARCHAR NOT NULL REFERENCES Nutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  Blogeintrag INTEGER NOT NULL REFERENCES Blogeintrag (ID) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  PRIMARY KEY (Nutzer ASC, Blogeintrag ASC)
);

CREATE TABLE IF NOT EXISTS NutzerBefreundetNutzer (
  Benutzername1 VARCHAR NOT NULL REFERENCES Nutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  Benutzername2 VARCHAR NOT NULL REFERENCES Nutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  PRIMARY KEY (Benutzername1 ASC, Benutzername2 ASC)
);

CREATE TABLE IF NOT EXISTS BlogeintragHatSchlagwort (
  Blogeintrag INTEGER NOT NULL REFERENCES Blogeintrag (ID) ON UPDATE CASCADE ON DELETE CASCADE,
  Schlagwort  VARCHAR NOT NULL REFERENCES Schlagwort (Bezeichnung) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  PRIMARY KEY (Blogeintrag ASC, Schlagwort ASC)
);

CREATE TABLE IF NOT EXISTS AlbumHatBild (
  Album INTEGER NOT NULL REFERENCES Album (ID) ON UPDATE CASCADE ON DELETE CASCADE,
  Bild  INTEGER NOT NULL REFERENCES Bild (ID) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY (Album ASC, Bild ASC)
);

CREATE TABLE IF NOT EXISTS NutzerBewertetKommentar (
  Nutzer    VARCHAR NOT NULL REFERENCES Nutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE COLLATE NOCASE,
  Kommentar INTEGER NOT NULL REFERENCES Kommentar (ID) ON UPDATE CASCADE ON DELETE CASCADE,
  Bewertung INTEGER NOT NULL CHECK (Bewertung IN (-1, 1)),
  PRIMARY KEY (Nutzer ASC, Bewertung ASC)
);