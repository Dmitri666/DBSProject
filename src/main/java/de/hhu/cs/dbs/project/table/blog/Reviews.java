package de.hhu.cs.dbs.project.table.blog;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reviews extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT P.ID,B.Redakteur, B.Titel, P.Produktbezeichnung, P.Fazit FROM Produktrezension P, Blogeintrag B WHERE B.ID = P.ID ";

        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE P.Produktbezeichnung LIKE '%"+ filter +"%' ";
        }
        selectQuery = selectQuery + " ORDER BY B.Aenderungsdatum desc";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = String.format("SELECT P.* FROM Produktrezension P WHERE P.ID = '%s' ",data.get("Produktrezension.ID"));
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        String redakteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("Produktrezension.ID"));
        if (!username.equals(redakteur)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Produktrezension(ID,Produktbezeichnung,Fazit) VALUES (?, ?, ?)");
        preparedStatement.setObject(1, data.get("Produktrezension.ID"));
        preparedStatement.setObject(2, data.get("Produktrezension.Produktbezeichnung"));
        preparedStatement.setObject(3, data.get("Produktrezension.Fazit"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String kommentator = SqlUtil.getRedacteurByBlogeintrag((Integer) oldData.get("Produktrezension.ID"));
        if (!username.equals(kommentator)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Produktrezension SET Produktbezeichnung = ?,Fazit = ? WHERE ID = ? ");
        preparedStatement.setObject(1, newData.get("Produktrezension.Produktbezeichnung"));
        preparedStatement.setObject(1, newData.get("Produktrezension.Fazit"));
        preparedStatement.setObject(2, oldData.get("Produktrezension.ID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        String redakteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("Produktrezension.ID"));
        if (!username.equals(redakteur)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Produktrezension WHERE ID = ?");
        preparedStatement.setObject(1, data.get("Produktrezension.ID"));

        preparedStatement.executeUpdate();
    }
}
