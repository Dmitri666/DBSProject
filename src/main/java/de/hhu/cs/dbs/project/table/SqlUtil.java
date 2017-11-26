package de.hhu.cs.dbs.project.table;

import com.alexanderthelen.applicationkit.Application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtil {
    public static String getRedacteurByBlogeintrag(Integer blogeintrag) throws SQLException {
        String selectQuery = String.format("SELECT B.Redakteur FROM Blogeintrag B WHERE B.ID = '%s'", blogeintrag);

        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        if(!result.next()) {
            throw new SQLException("Fehler");
        }
        return result.getString("Redakteur");
    }

    public static String getRedakteurByBild(Integer id) throws SQLException{
        String selectQuery = String.format("SELECT B.Redakteur FROM Blogeintrag B, Bild BL WHERE B.ID = BL.Album AND BL.ID = '%s'", id);

        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        if(!result.next()) {
            throw new SQLException("Fehler");
        }



        return result.getString("Redakteur");
    }

    public static Integer getBildCount(Integer id) throws SQLException{
        String selectQuery = String.format("select count(*) as count from Bild B WHERE B.Album = '%s'", id);

        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        if(!result.next()) {
            throw new SQLException("Fehler");
        }



        return result.getInt("count");
    }

    public static Boolean isFrend(String benutzername) throws SQLException{
        String username = (String) Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT B.Benutzername1 FROM NutzerBefreundetNutzer B WHERE B.Benutzername1 = '%s' AND B.Benutzername2 = '%s'", username, benutzername);

        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        return result.next();

    }


    public static String getNutzerByKommentar(Integer id) throws SQLException{
        String selectQuery = String.format("SELECT K.Nutzer FROM Kommentar K WHERE K.ID = '%s'", id);

        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        if(!result.next()) {
            throw new SQLException("Fehler");
        }



        return result.getString("Nutzer");
    }

    public static Boolean isChiefRedacteur(String Benutzername) throws SQLException{
        String selectQuery = String.format("SELECT B.Benutzername FROM Chefredakteur B WHERE B.Benutzername = '%s'", Benutzername);

        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        return result.next();

    }
}
