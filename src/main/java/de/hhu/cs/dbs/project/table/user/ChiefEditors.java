package de.hhu.cs.dbs.project.table.user;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class ChiefEditors extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT N.Benutzername, N.EMail, N.Geburtsdatum, N.Geschlecht, R.Vorname, R.Nachname, R.Biographie, substr(C.Telefonnummer,2,length(C.Telefonnummer) - 2) AS Telefonnummer FROM Nutzer N INNER JOIN Redakteur R  ON N.Benutzername = R.Benutzername INNER JOIN Chefredakteur C ON N.Benutzername = C.Benutzername LEFT OUTER JOIN NutzerBefreundetNutzer F ON N.Benutzername = F.Benutzername2 AND F.Benutzername1 = '%s' WHERE N.Benutzername != '%s'", username, username);
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
        return String.format("SELECT N.Benutzername, N.EMail, N.Geburtsdatum, N.Geschlecht, R.Vorname, R.Nachname, R.Biographie, substr(C.Telefonnummer,2,length(C.Telefonnummer) - 2) AS Telefonnummer  FROM Nutzer N INNER JOIN Redakteur R ON N.Benutzername = R.Benutzername INNER JOIN Chefredakteur C ON N.Benutzername = C.Benutzername  WHERE N.Benutzername = '%s'", username);
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".insertRowWithData(Data) nicht implementiert.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + ".updateRowWithData(Data, Data) nicht implementiert.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".deleteRowWithData(Data) nicht implementiert.");
    }
}
