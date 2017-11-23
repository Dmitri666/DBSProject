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

INSERT INTO Nutzer(Benutzername,EMail,Geburtsdatum,Passwort,Geschlecht) VALUES ('user1','user1@gmx.de','01.10.1995','123','maennlich');
INSERT INTO Nutzer(Benutzername,EMail,Geburtsdatum,Passwort,Geschlecht) VALUES ('user2','user2@gmx.de','01.11.1995','123','maennlich');
INSERT INTO Nutzer(Benutzername,EMail,Geburtsdatum,Passwort,Geschlecht) VALUES ('user3','user3@gmx.de','01.10.1997','123','maennlich');
INSERT INTO Nutzer(Benutzername,EMail,Geburtsdatum,Passwort,Geschlecht) VALUES ('redakteur1','redakteur1@gmx.de','06.11.1999','123','maennlich');
INSERT INTO Nutzer(Benutzername,EMail,Geburtsdatum,Passwort,Geschlecht) VALUES ('redakteur2','redakteur2@gmx.de','01.03.1992','123','maennlich');
INSERT INTO Nutzer(Benutzername,EMail,Geburtsdatum,Passwort,Geschlecht) VALUES ('chefredakteur1','chefredakteur1@gmx.de','01.03.1992','123','weiblich');


INSERT INTO Redakteur(Benutzername,Nachname,Vorname,Biographie) VALUES ('redakteur1','Fischer','Dirk','bbb');
INSERT INTO Redakteur(Benutzername,Nachname,Vorname,Biographie) VALUES ('redakteur2','Sebastian','Schröder',null);
INSERT INTO Redakteur(Benutzername,Nachname,Vorname,Biographie) VALUES ('chefredakteur1','Lisa','Levin',null);

INSERT INTO Chefredakteur(Benutzername,Telefonnummer) VALUES ('chefredakteur1','01789839');


INSERT INTO NutzerBefreundetNutzer (Benutzername1,Benutzername2) VALUES ('user1','user3');