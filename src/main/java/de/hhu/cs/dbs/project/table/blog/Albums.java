package de.hhu.cs.dbs.project.table.blog;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Albums extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String selectQuery = String.format("select A.ID, B.Titel, A.Sichtbarkeit, B.Redakteur from Album A,Blogeintrag B WHERE A.ID = B.ID AND (B.Redakteur = '%s' OR A.Sichtbarkeit = 'oeffentlich')", username);

        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " AND B.Titel LIKE '%" + filter +"%' ";
        }
        selectQuery = selectQuery + " ORDER BY B.Titel ";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = String.format("select A.* from Album A WHERE A.ID = '%s'", data.get("Album.ID"));
        return selectQuery;

    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        String redakteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("Album.ID"));
        if (!username.equals(redakteur)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Album(ID,Sichtbarkeit) VALUES (?, ?)");
        preparedStatement.setObject(1, data.get("Album.ID"));
        preparedStatement.setObject(2, data.get("Album.Sichtbarkeit"));

        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String kommentator = SqlUtil.getRedacteurByBlogeintrag((Integer) oldData.get("Album.ID"));
        if (!username.equals(kommentator)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Album SET Sichtbarkeit = ? WHERE ID = ? ");
        preparedStatement.setObject(1, newData.get("Album.Sichtbarkeit"));
        preparedStatement.setObject(2, oldData.get("Album.ID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        String redakteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("Album.ID"));
        if (!username.equals(redakteur)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Album WHERE ID = ?");
        preparedStatement.setObject(1, data.get("Album.ID"));

        preparedStatement.executeUpdate();
    }


}
