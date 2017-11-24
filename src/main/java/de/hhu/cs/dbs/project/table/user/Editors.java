package de.hhu.cs.dbs.project.table.user;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Editors extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String username = (String)Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT N.Benutzername, N.EMail, N.Geburtsdatum, N.Geschlecht, R.Vorname, R.Nachname, R.Biographie FROM Nutzer N INNER JOIN Redakteur R ON N.Benutzername = R.Benutzername LEFT OUTER JOIN NutzerBefreundetNutzer F ON N.Benutzername = F.Benutzername2 AND F.Benutzername1 = '%s' WHERE N.Benutzername != '%s'", username, username);
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " AND N.Benutzername LIKE '%" + filter + "%'";
        }
        selectQuery = selectQuery + " ORDER BY N.Benutzername";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String username = (String)data.get("Nutzer.Benutzername");
        return String.format("SELECT N.Benutzername, N.EMail, N.Geburtsdatum, N.Geschlecht, R.Vorname, R.Nachname, R.Biographie  FROM Nutzer N INNER JOIN Redakteur R ON N.Benutzername = R.Benutzername  WHERE N.Benutzername = '%s'", username);
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 0) {
            throw new SQLException("Keine Berechtigungen.");
        }
        if(!Validator.isValidEmail((String)data.get("Nutzer.EMail"))) {
            throw new SQLException("Invalide Email");
        }
        if(!Validator.isValidDate((String)data.get("Nutzer.Geburtsdatum"))) {
            throw new SQLException("Invalide Geburtsdatum");
        }

        {
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Nutzer(Benutzername, EMail, Geburtsdatum, Passwort, Geschlecht) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setObject(1, data.get("Nutzer.Benutzername"));
            preparedStatement.setObject(2, data.get("Nutzer.EMail"));
            preparedStatement.setObject(3, data.get("Nutzer.Geburtsdatum"));
            preparedStatement.setObject(4, "abc");
            preparedStatement.setObject(5, data.get("Nutzer.Geschlecht"));
            preparedStatement.executeUpdate();
        }

        {
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Redakteur(Benutzername, Vorname, Nachname, Biographie) VALUES (?, ?, ?, ?)");
            preparedStatement.setObject(1, data.get("Nutzer.Benutzername"));
            preparedStatement.setObject(2, data.get("Redakteur.Vorname"));
            preparedStatement.setObject(3, data.get("Redakteur.Nachname"));
            preparedStatement.setObject(4, data.get("Redakteur.Biographie"));
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + ".updateRowWithData(Data, Data) nicht implementiert.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 0) {
            throw new SQLException("Keine Berechtigungen.");
        }
        if (isChiefRedacteur((String) data.get("Nutzer.Benutzername"))) {
            throw new SQLException("Keine Berechtigungen.");
        }

        {
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Redakteur WHERE Benutzername = ?");
            preparedStatement.setObject(1, data.get("Nutzer.Benutzername"));
            preparedStatement.executeUpdate();
        }
        {
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Nutzer WHERE Benutzername = ?");
            preparedStatement.setObject(1, data.get("Nutzer.Benutzername"));
            preparedStatement.executeUpdate();
        }
    }

    private Boolean isChiefRedacteur(String Benutzername) throws SQLException{
        String selectQuery = String.format("SELECT B.Benutzername FROM Chefredakteur B WHERE B.Benutzername = '%s'", Benutzername);

        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        return result.next();

    }
}
