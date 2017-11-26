package de.hhu.cs.dbs.project.table.blog;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tags extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT S.Bezeichnung FROM Schlagwort S";

        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE S.Bezeichnung LIKE '%"+ filter +"%' ";
        }
        selectQuery = selectQuery + " ORDER BY S.Bezeichnung";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = String.format("SELECT S.Bezeichnung FROM Schlagwort S WHERE S.Bezeichnung = '%s'", data.get("Schlagwort.Bezeichnung"));
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Schlagwort(Bezeichnung) VALUES (?)");
        preparedStatement.setObject(1, data.get("Schlagwort.Bezeichnung"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Schlagwort SET Bezeichnung = ? WHERE Bezeichnung = ? ");
        preparedStatement.setObject(1, newData.get("Schlagwort.Bezeichnung"));
        preparedStatement.setObject(2, oldData.get("Schlagwort.Bezeichnung"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Schlagwort WHERE Bezeichnung = ?");
        preparedStatement.setObject(1, data.get("Schlagwort.Bezeichnung"));

        preparedStatement.executeUpdate();
    }
}
