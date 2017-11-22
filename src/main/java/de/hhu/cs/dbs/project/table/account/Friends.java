package de.hhu.cs.dbs.project.table.account;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Friends extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Benutzername AS Nutzer , Benutzername1 AS Freund  FROM NutzerBefreundetNutzer ";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Benutzername1 LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Benutzername AS Nutzer ,Benutzername1 AS Freund FROM NutzerBefreundetNutzer  WHERE Benutzername = '" + data.get("NutzerBefreundetNutzer.Nutzer") + "' AND Benutzername1 = '" + data.get("NutzerBefreundetNutzer.Freund") + "'";

    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(data.get("NutzerBefreundetNutzer.Freund"))) {
            throw new SQLException("Der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO NutzerBefreundetNutzer(Benutzername, Benutzername1) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("NutzerBefreundetNutzer.Freund"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(oldData.get("NutzerBefreundetNutzer.Nutzer"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE NutzerBefreundetNutzer SET Benutzername1 = ? WHERE Benutzername = ? AND Benutzername1 = ? ");
        preparedStatement.setObject(1, newData.get("NutzerBefreundetNutzer.Freund"));
        preparedStatement.setObject(2, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(3, oldData.get("NutzerBefreundetNutzer.Freund"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(data.get("NutzerBefreundetNutzer.Nutzer"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM NutzerBefreundetNutzer WHERE Benutzername = ? AND Benutzername1 = ?");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("NutzerBefreundetNutzer.Freund"));
        preparedStatement.executeUpdate();
    }
}
