package de.hhu.cs.dbs.project.table.account;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Friends extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT N.*  FROM Nutzer N JOIN NutzerBefreundetNutzer B  ON N.Benutzername = B.Benutzername2 WHERE B.Benutzername1 = '" + Application.getInstance().getData().get("username") + "'";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " AND B.Benutzername2 LIKE '%" + filter + "%'";
        }
        selectQuery = selectQuery + " ORDER BY N.Benutzername";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT B.Benutzername2  AS Benutzername  FROM NutzerBefreundetNutzer B  WHERE B.Benutzername2 = '" + data.get("Nutzer.Benutzername") + "'";
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if (Application.getInstance().getData().get("username").equals(data.get("NutzerBefreundetNutzer.Benutzername"))) {
            throw new SQLException("Der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO NutzerBefreundetNutzer(Benutzername1, Benutzername2) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("NutzerBefreundetNutzer.Benutzername"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if (Application.getInstance().getData().get("username").equals(newData.get("NutzerBefreundetNutzer.Benutzername"))) {
            throw new SQLException("Gleiche Nutzer.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE NutzerBefreundetNutzer SET Benutzername2 = ? WHERE Benutzername1 = ? AND Benutzername2 = ? ");
        preparedStatement.setObject(1, newData.get("NutzerBefreundetNutzer.Benutzername"));
        preparedStatement.setObject(2, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(3, oldData.get("NutzerBefreundetNutzer.Benutzername"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM NutzerBefreundetNutzer WHERE Benutzername1 = ? AND Benutzername2 = ?");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Nutzer.Benutzername"));
        preparedStatement.executeUpdate();
    }
}
