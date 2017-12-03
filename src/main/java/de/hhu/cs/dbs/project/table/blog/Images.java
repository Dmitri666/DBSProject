package de.hhu.cs.dbs.project.table.blog;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Images extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT B.ID,  BL.Redakteur, BL.Titel AS Album , B.Bezeichnung, B.Aufnahmeort, B.Bild  FROM Bild B , Album A , Blogeintrag BL WHERE B.Album = A.ID AND BL.ID = A.ID AND (BL.Redakteur = '%s' OR A.Sichtbarkeit = 'oeffentlich') ", username);

        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " AND B.Aufnahmeort LIKE '%" + filter + "%' ";
        }
        //selectQuery = selectQuery + " ORDER BY B.Aenderungsdatum DESC";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = String.format("SELECT B.*  FROM Bild B WHERE B.ID = '%s' ", data.get("Bild.ID"));
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        String redakteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("Bild.Album"));
        if (!username.equals(redakteur)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        Integer count = SqlUtil.getBildCount((Integer) data.get("Bild.Album"));
        if (count >= 15 ) {
            throw new SQLException("Maximale Anzahl gleich 15.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Bild(Bezeichnung, Aufnahmeort, Bild,Album) VALUES (?, ?, ?, ?)");
        preparedStatement.setObject(1, data.get("Bild.Bezeichnung"));
        preparedStatement.setObject(2, data.get("Bild.Aufnahmeort"));
        preparedStatement.setObject(3, data.get("Bild.Bild"));
        preparedStatement.setObject(4, data.get("Bild.Album"));

        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String kommentator = SqlUtil.getRedacteurByBlogeintrag((Integer) oldData.get("Bild.Album"));
        if (!username.equals(kommentator)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        Integer count = SqlUtil.getBildCount((Integer)newData.get("Bild.Album"));
        if (count >= 15 ) {
            throw new SQLException("Maximale Bilder Anzahl gleich 15.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Bild SET Bezeichnung = ?, Aufnahmeort = ?, Bild = ?, Album = ? WHERE ID = ? ");
        preparedStatement.setObject(1, newData.get("Bild.Bezeichnung"));
        preparedStatement.setObject(2, newData.get("Bild.Aufnahmeort"));
        preparedStatement.setObject(3, newData.get("Bild.Bild"));
        preparedStatement.setObject(4, newData.get("Bild.Album"));
        preparedStatement.setObject(5, oldData.get("Bild.ID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        String redakteur = SqlUtil.getRedakteurByBild((Integer) data.get("Bild.ID"));
        if (!username.equals(redakteur)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Bild WHERE ID = ?");
        preparedStatement.setObject(1, data.get("Bild.ID"));

        preparedStatement.executeUpdate();
    }




}
