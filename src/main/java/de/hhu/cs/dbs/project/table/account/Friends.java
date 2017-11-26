package de.hhu.cs.dbs.project.table.account;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Friends extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT instr(N.Benutzername,B.Benutzername2) AS Befreundet ,N.Benutzername  FROM Nutzer N LEFT OUTER JOIN NutzerBefreundetNutzer B  ON N.Benutzername = B.Benutzername2 AND B.Benutzername1 = '%s'  WHERE N.Benutzername != '%s'" , username, username);
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE N.Benutzername LIKE '%" + filter + "%'";
        }
        selectQuery = selectQuery + " ORDER BY B.Benutzername2 desc, N.Benutzername";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT N.Benutzername   FROM Nutzer N  WHERE N.Benutzername = '" + data.get("Nutzer.Benutzername") + "'";
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if (Application.getInstance().getData().get("username").equals(data.get("Nutzer.Benutzername"))) {
            throw new SQLException("Der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO NutzerBefreundetNutzer(Benutzername1, Benutzername2) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Nutzer.Benutzername"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if (Application.getInstance().getData().get("username").equals(newData.get("Nutzer.Benutzername"))) {
            throw new SQLException("Gleiche Nutzer.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        if(SqlUtil.isFrend((String) oldData.get("Nutzer.Benutzername")))
        {
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM NutzerBefreundetNutzer WHERE Benutzername1 = ? AND Benutzername2 = ?");
            preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
            preparedStatement.setObject(2, oldData.get("Nutzer.Benutzername"));
            preparedStatement.executeUpdate();
        }
        else
        {
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO NutzerBefreundetNutzer(Benutzername1, Benutzername2) VALUES (?, ?)");
            preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
            preparedStatement.setObject(2, oldData.get("Nutzer.Benutzername"));
            preparedStatement.executeUpdate();
        }

    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM NutzerBefreundetNutzer WHERE Benutzername1 = ? AND Benutzername2 = ?");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Nutzer.Benutzername"));
        preparedStatement.executeUpdate();
    }


}
