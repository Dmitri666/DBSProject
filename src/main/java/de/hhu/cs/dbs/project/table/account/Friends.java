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
            selectQuery += " AND" +
                    " N.Benutzername LIKE '%" + filter + "%'";
        }
        selectQuery = selectQuery + " ORDER BY B.Benutzername2 desc, N.Benutzername";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String user = (String)data.get("Nutzer.Benutzername");
        if(user == null) {
            return "SELECT N.Benutzername AS Befreunden  FROM Nutzer N  WHERE N.Benutzername = '" + data.get("Nutzer.Benutzername") + "'";
        }
        else
        {
            if(SqlUtil.isFrend(user)) {
                return "SELECT N.Benutzername AS Entfreunden   FROM Nutzer N  WHERE N.Benutzername = '" + data.get("Nutzer.Benutzername") + "'";
            }
            else {
                return "SELECT N.Benutzername AS Befreunden   FROM Nutzer N  WHERE N.Benutzername = '" + data.get("Nutzer.Benutzername") + "'";
            }
        }


    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if (Application.getInstance().getData().get("username").equals(data.get("Nutzer.Befreunden"))) {
            throw new SQLException("Der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO NutzerBefreundetNutzer(Benutzername1, Benutzername2) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Nutzer.Befreunden"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        String newUser = null;
        String oldUser = null;
        if(newData.keySet().contains("Nutzer.Befreunden")) {
            newUser = (String) newData.get("Nutzer.Befreunden");
        }
        else {
            newUser = (String) newData.get("Nutzer.Entfreunden");

        }

        if(oldData.keySet().contains("Nutzer.Befreunden")) {
            oldUser = (String) oldData.get("Nutzer.Befreunden");
        }
        else {
            oldUser = (String) oldData.get("Nutzer.Entfreunden");
        }

        if (Application.getInstance().getData().get("username").equals(newUser)) {
            throw new SQLException("Gleiche Nutzer.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        if(SqlUtil.isFrend(oldUser))
        {
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM NutzerBefreundetNutzer WHERE Benutzername1 = ? AND Benutzername2 = ?");
            preparedStatement.setObject(1,username);
            preparedStatement.setObject(2, oldUser);
            preparedStatement.executeUpdate();
        }
        else
        {
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO NutzerBefreundetNutzer(Benutzername1, Benutzername2) VALUES (?, ?)");
            preparedStatement.setObject(1, username);
            preparedStatement.setObject(2, oldUser);
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
