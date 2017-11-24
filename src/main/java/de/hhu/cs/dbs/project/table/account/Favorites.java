package de.hhu.cs.dbs.project.table.account;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Favorites extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String username = (String)Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT B.*  FROM Blogeintrag B INNER JOIN NutzerFavorisiertBlogeintrag F ON B.ID = F.Blogeintrag  WHERE F.Nutzer = '%s' ", username);
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " AND B.Redakteur LIKE '%" + filter + "%'";
        }
        selectQuery = selectQuery + " ORDER BY B.Aenderungsdatum";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String username = (String)Application.getInstance().getData().get("username");
        Integer id = (Integer) data.get("Blogeintrag.ID");
        String selectQuery = String.format("SELECT F.Blogeintrag FROM NutzerFavorisiertBlogeintrag F WHERE F.Nutzer = '%s' AND F.Blogeintrag = '%s' ", username, id);
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        String username = (String)Application.getInstance().getData().get("username");
        String redacteur = getRedacteur((Integer) data.get("NutzerFavorisiertBlogeintrag.Blogeintrag"));
        if (username.equals(redacteur)) {
            throw new SQLException("Gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO NutzerFavorisiertBlogeintrag(Nutzer, Blogeintrag) VALUES (?, ?)");
        preparedStatement.setObject(1, username);
        preparedStatement.setObject(2, data.get("NutzerFavorisiertBlogeintrag.Blogeintrag"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        String username = (String)Application.getInstance().getData().get("username");
        String redacteur = getRedacteur((Integer) newData.get("NutzerFavorisiertBlogeintrag.Blogeintrag"));
        if (username.equals(redacteur)) {
            throw new SQLException("Gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE NutzerFavorisiertBlogeintrag SET Blogeintrag = ? WHERE Nutzer = ? AND Blogeintrag = ? ");
        preparedStatement.setObject(1, newData.get("NutzerFavorisiertBlogeintrag.Blogeintrag"));
        preparedStatement.setObject(2, username);
        preparedStatement.setObject(3, oldData.get("NutzerFavorisiertBlogeintrag.Blogeintrag"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM NutzerFavorisiertBlogeintrag WHERE Nutzer = ? AND Blogeintrag = ?");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Blogeintrag.ID"));
        preparedStatement.executeUpdate();
    }

    private String getRedacteur(Integer blogeintrag) throws SQLException{
        String selectQuery = String.format("SELECT B.Redakteur FROM Blogeintrag B WHERE B.ID = '%s'", blogeintrag);

        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        if(!result.next()) {
            throw new SQLException("Fehler");
        }
        return result.getString("Redakteur");
    }
}
