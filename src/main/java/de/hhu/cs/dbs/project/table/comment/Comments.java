package de.hhu.cs.dbs.project.table.comment;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comments extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT K.ID, B.ID AS Blogeintrag, K.Nutzer, K.Text, K.Erstelldatum, SUM(B.Bewertung) AS Bewertung  FROM Blogeintrag B INNER JOIN Kommentar K ON B.ID = K.Blogeintrag LEFT OUTER JOIN NutzerBewertetKommentar B ON K.ID = B.Kommentar\n" +
                "GROUP BY K.ID, K.Nutzer, K.Text, K.Erstelldatum ";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE K.Nutzer LIKE '%" + filter + "%'"  ;
        }
        selectQuery = selectQuery + " ORDER BY K.Nutzer";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = String.format("SELECT K.ID, K.Text, K.Blogeintrag  FROM Kommentar K WHERE K.ID ='%s' ",data.get("Kommentar.ID"));
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Kommentar(Text, Erstelldatum, Nutzer,Blogeintrag) VALUES (?, ?, ?, ?)");
        preparedStatement.setObject(1, data.get("Kommentar.Text"));
        preparedStatement.setObject(2, format.format(new Date()));
        preparedStatement.setObject(3, username);
        preparedStatement.setObject(4, data.get("Kommentar.Blogeintrag"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String kommentator = SqlUtil.getNutzerByKommentar((Integer) oldData.get("Kommentar.ID"));
        if (!username.equals(kommentator)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Kommentar SET Text = ? WHERE ID = ? ");
        preparedStatement.setObject(1, newData.get("Kommentar.Text"));
        preparedStatement.setObject(2, oldData.get("Kommentar.ID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");

        if (permission != 0) {
            String username = (String) Application.getInstance().getData().get("username");
            String kommentator = SqlUtil.getNutzerByKommentar((Integer) data.get("Kommentar.ID"));
            if (!username.equals(kommentator)) {
                throw new SQLException("Keine Berechtigungen.");
            }
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Kommentar WHERE ID = ?");
        preparedStatement.setObject(1, data.get("Kommentar.ID"));
        preparedStatement.executeUpdate();
    }


}
