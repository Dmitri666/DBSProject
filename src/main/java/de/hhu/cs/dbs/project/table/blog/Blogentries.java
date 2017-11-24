package de.hhu.cs.dbs.project.table.blog;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Blogentries extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT B.*  FROM Blogeintrag B ";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE B.Redakteur LIKE '%" + filter + "%' OR B.Titel LIKE '%" + filter + "%'"  ;
        }
        selectQuery = selectQuery + " ORDER BY B.Aenderungsdatum DESC";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = String.format("SELECT B.ID, B.Titel, B.Text  FROM Blogeintrag B WHERE B.ID ='%s' ",data.get("Blogeintrag.ID"));
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum,Redakteur) VALUES (?, ?, ?, ?)");
        preparedStatement.setObject(1, data.get("Blogeintrag.Titel"));
        preparedStatement.setObject(2, data.get("Blogeintrag.Text"));
        preparedStatement.setObject(3, format.format(new Date()));
        preparedStatement.setObject(4, username);
        preparedStatement.executeUpdate();

    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        if (permission == 1) {
            String username = (String) Application.getInstance().getData().get("username");
            String redacteur = getRedacteur((Integer) oldData.get("Blogeintrag.ID"));
            if (!username.equals(redacteur)) {
                throw new SQLException("Keine Berechtigungen.");
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Blogeintrag SET Titel = ?, Text = ?, Aenderungsdatum = ? WHERE ID = ? ");
        preparedStatement.setObject(1, newData.get("Blogeintrag.Titel"));
        preparedStatement.setObject(2, newData.get("Blogeintrag.Text"));
        preparedStatement.setObject(3, format.format(new Date()));
        preparedStatement.setObject(4, oldData.get("Blogeintrag.ID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        if (permission == 1) {
            String username = (String) Application.getInstance().getData().get("username");
            String redacteur = getRedacteur((Integer) data.get("Blogeintrag.ID"));
            if (!username.equals(redacteur)) {
                throw new SQLException("Keine Berechtigungen.");
            }
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Blogeintrag WHERE ID = ?");
        preparedStatement.setObject(1, data.get("Blogeintrag.ID"));
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
