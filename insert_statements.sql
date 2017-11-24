-- PRAGMA Anweisungen

PRAGMA encoding = 'UTF-8';
PRAGMA foreign_keys = 1;
PRAGMA auto_vacuum = 1;
PRAGMA automatic_index = 1;
PRAGMA case_sensitive_like = 0;
PRAGMA defer_foreign_keys = 0;
PRAGMA ignore_check_constraints = 0;
PRAGMA journal_mode= 'WAL';
PRAGMA query_only = 0;
PRAGMA recursive_triggers = 1;
PRAGMA reverse_unordered_selects = 0;
PRAGMA secure_delete = 0;
PRAGMA synchronous = 'NORMAL';




INSERT INTO Nutzer (EMail, Geburtsdatum, Geschlecht, Benutzername, Passwort) VALUES ('ellev100@hhu.de', '07.06.1995', 'weiblich', 'Lisa', 'abc');
INSERT INTO Nutzer (EMail, Geburtsdatum, Geschlecht, Benutzername, Passwort) VALUES ('anker100@hhu.de', '01.01.1995', 'weiblich', 'Anna', 'abc');
INSERT INTO Nutzer (EMail, Geburtsdatum, Geschlecht, Benutzername, Passwort) VALUES ('dagro100@hhu.de', '11.11.1991', 'maennlich', 'Danny', 'abc');
INSERT INTO Nutzer (EMail, Geburtsdatum, Geschlecht, Benutzername, Passwort) VALUES ('david123@gmx.de', '02.04.1990', 'maennlich', 'David', 'abc');
INSERT INTO Nutzer (EMail, Geburtsdatum, Geschlecht, Benutzername, Passwort) VALUES ('sebastian123@gmx.de', '02.04.1990', 'maennlich', 'Sebastian', 'abc');


INSERT INTO Redakteur (Benutzername, Vorname, Nachname, Biographie) VALUES ('Lisa', 'Elisabeth', 'Levin', NULL);
INSERT INTO Redakteur (Benutzername, Vorname, Nachname, Biographie) VALUES ('David', 'David', 'Meier', NULL);
INSERT INTO Redakteur (Benutzername, Vorname, Nachname, Biographie) VALUES ('Sebastian', 'Sebastian', 'Weigert', NULL);


INSERT INTO Chefredakteur (Benutzername, Telefonnummer) VALUES ('Lisa', '''+49 01766160452''');
INSERT INTO Chefredakteur (Benutzername, Telefonnummer) VALUES ('David', '''017249582834''');


INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (1, 'Videoblog1', 'abc', '11.11.2017', '11.11.2017', 'Lisa');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (2, 'Videoblog2', 'abc', '11.11.2017', '11.10.2017', 'Lisa');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (3, 'Album1', 'abc', '10.11.2017', '11.11.2017', 'Lisa');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (4, 'Album2', 'abc', '01.11.2017', '11.11.2017', 'Lisa');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (5, 'RezensionV1', 'abc', '11.01.2017', '11.11.2017', 'David');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (6, 'RezensionV2', 'abc', '18.09.2017', '11.11.2017', 'David');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (7, 'RezensionA1', 'abc', '11.11.2016', '11.11.2017', 'David');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (8, 'RezensionA2', 'abc', '11.10.2017', '11.11.2017', 'David');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (9, 'Album3', 'abc', '11.03.2017', '11.11.2017', 'Lisa');
INSERT INTO Blogeintrag (ID, Titel, Text, Erstellungsdatum, Aenderungsdatum, Redakteur) VALUES (10, 'Videoblog3', 'abc', '05.11.2017', '11.11.2017', 'David');


INSERT INTO Produktrezension (ID, Produktbezeichnung, Fazit) VALUES (5, 'V1', 'abc');
INSERT INTO Produktrezension (ID, Produktbezeichnung, Fazit) VALUES (6, 'V2', 'abc');
INSERT INTO Produktrezension (ID, Produktbezeichnung, Fazit) VALUES (7, 'A1', 'abc');
INSERT INTO Produktrezension (ID, Produktbezeichnung, Fazit) VALUES (8, 'A2', 'abc');


INSERT INTO Videoblog (ID, Spiellaenge, Link720p, Link1080p) VALUES (1, '05:00', 'link1', NULL);
INSERT INTO Videoblog (ID, Spiellaenge, Link720p, Link1080p) VALUES (2, '01:30', 'link2', 'link3');
INSERT INTO Videoblog (ID, Spiellaenge, Link720p, Link1080p) VALUES (10, '06:00', 'link4', 'link5');


INSERT INTO Album (ID, Sichtbarkeit) VALUES (3, 'oeffentlich');
INSERT INTO Album (ID, Sichtbarkeit) VALUES (4, 'privat');
INSERT INTO Album (ID, Sichtbarkeit) VALUES (9, 'oeffentlich');


--INSERT INTO Bild (ID, Bezeichnung, Aufnahmeort, Album, Bild) VALUES (1, 'B1', 'Europa', '3', 'link1');
--INSERT INTO Bild (ID, Bezeichnung, Aufnahmeort, Album, Bild) VALUES (2, 'B2', 'Asia', '4', 'link2');
--INSERT INTO Bild (ID, Bezeichnung, Aufnahmeort, Album, Bild) VALUES (3, 'B3', 'Europa', '9', 'link3');
--INSERT INTO Bild (ID, Bezeichnung, Aufnahmeort, Album, Bild) VALUES (4, 'B4', 'Europa', '4', 'link4');
--INSERT INTO Bild (ID, Bezeichnung, Aufnahmeort, Album, Bild) VALUES (5, 'B5', 'Europa', '3', 'link5');


INSERT INTO Schlagwort (Bezeichnung) VALUES ('abc');
INSERT INTO Schlagwort (Bezeichnung) VALUES ('xxx');


INSERT INTO Kommentar (ID, Text, Erstelldatum, Nutzer, Blogeintrag) VALUES (200, 'abc', '01.12.2017', 'Lisa', '5');
INSERT INTO Kommentar (ID, Text, Erstelldatum, Nutzer, Blogeintrag) VALUES (201, 'abc', '09.01.2017', 'Anna', '3');


INSERT INTO BewertungBlogeintrag (Bewertungsskala, Nutzer, Blogeintrag) VALUES (9, 'Anna', 2);
INSERT INTO BewertungBlogeintrag (Bewertungsskala, Nutzer, Blogeintrag) VALUES (6, 'Danny', 2);
INSERT INTO BewertungBlogeintrag (Bewertungsskala, Nutzer, Blogeintrag) VALUES (5, 'Anna', 6);
INSERT INTO BewertungBlogeintrag (Bewertungsskala, Nutzer, Blogeintrag) VALUES (3, 'Anna', 9);
INSERT INTO BewertungBlogeintrag (Bewertungsskala, Nutzer, Blogeintrag) VALUES (3, 'Danny', 6);
INSERT INTO BewertungBlogeintrag (Bewertungsskala, Nutzer, Blogeintrag) VALUES (9, 'Danny', 9);
INSERT INTO BewertungBlogeintrag (Bewertungsskala, Nutzer, Blogeintrag) VALUES (2, 'Danny', 1);
INSERT INTO BewertungBlogeintrag (Bewertungsskala, Nutzer, Blogeintrag) VALUES (1, 'Anna', 1);


INSERT INTO NutzerFavorisiertBlogeintrag (Nutzer, Blogeintrag) VALUES ('Lisa', 7);
INSERT INTO NutzerFavorisiertBlogeintrag (Nutzer, Blogeintrag) VALUES ('Danny', 3);


INSERT INTO NutzerBewertetKommentar (Bewertung, Nutzer, Kommentar) VALUES (-1, 'Danny', 200);
INSERT INTO NutzerBewertetKommentar (Bewertung, Nutzer, Kommentar) VALUES (1, 'Danny', 201);



INSERT INTO NutzerBefreundetNutzer (Benutzername1, Benutzername2) VALUES ('Lisa', 'Anna');
INSERT INTO NutzerBefreundetNutzer (Benutzername1, Benutzername2) VALUES ('Anna', 'Danny');
INSERT INTO NutzerBefreundetNutzer (Benutzername1, Benutzername2) VALUES ('Danny', 'David');


INSERT INTO BlogeintragHatSchlagwort (Blogeintrag, Schlagwort) VALUES (5, 'abc');
INSERT INTO BlogeintragHatSchlagwort (Blogeintrag, Schlagwort) VALUES (7, 'xxx');
