package de.hhu.cs.dbs.project.table.comment;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Votings extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT BW.Kommentar, K.Nutzer, K.Text, BW.Bewertung  FROM Kommentar K, NutzerBewertetKommentar BW  WHERE K.ID = BW.Kommentar  AND BW.Nutzer = '%s'",username);
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE K.Nutzer LIKE '%" + filter + "%'";
        }
        selectQuery = selectQuery + " ORDER BY K.Erstelldatum desc";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT BW.Kommentar, BW.Bewertung  FROM NutzerBewertetKommentar BW  WHERE BW.Nutzer = '%s' AND BW.Kommentar  = '%s'",username,data.get("NutzerBewertetKommentar.Kommentar"));
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        String username = (String)Application.getInstance().getData().get("username");
        String redacteur = SqlUtil.getNutzerByKommentar((Integer) data.get("NutzerBewertetKommentar.Kommentar"));
        if (username.equals(redacteur)) {
            throw new SQLException("Gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO NutzerBewertetKommentar(Nutzer, Kommentar, Bewertung) VALUES (?, ?, ?)");
        preparedStatement.setObject(1, username);
        preparedStatement.setObject(2, data.get("NutzerBewertetKommentar.Kommentar"));
        preparedStatement.setObject(3, data.get("NutzerBewertetKommentar.Bewertung"));
        preparedStatement.executeUpdate();
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
