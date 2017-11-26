package de.hhu.cs.dbs.project.table.blog;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Videoblogs extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT V.ID, B.Titel, V.Spiellaenge, V.Link720p, V.Link1080p, B.Redakteur FROM Videoblog V, Blogeintrag B WHERE B.ID = V.ID ";

        if ( filter != null && ! filter .isEmpty() )
        {
            String[] filters = filter.split(",");
            if(filters.length == 1)
            {
                filters = filter.split(" ");
            }
            String hd = null;
            List<String> hqFilters = new ArrayList<>();
            for(int i = 0; i < filters.length; i++) {
                String value = filters[i].trim();
                if(value.contains("1080")){
                    hd = filters[i];
                }
                else {
                    hqFilters.add(filters[i]);
                }
            }
            for(int i = 0; i < hqFilters.size() ; i++) {
                String value = hqFilters.get(i).trim();
                selectQuery += " AND B.Titel LIKE '%" + value + "%' ";
            }

            if(hd != null)
            {
                selectQuery += " AND V.Link1080p NOT NULL ";
            }

        }
        selectQuery = selectQuery + " ORDER BY B.Aenderungsdatum ";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        Integer id = (Integer) data.get("Videoblog.ID");
        String selectQuery = String.format("SELECT V.* FROM Videoblog V WHERE V.ID = '%s' ", id);
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        String redakteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("Videoblog.ID"));
        if (!username.equals(redakteur)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Videoblog(ID,Spiellaenge,Link720p,Link1080p) VALUES (?, ?, ?, ?)");
        preparedStatement.setObject(1, data.get("Videoblog.ID"));
        preparedStatement.setObject(2, data.get("Videoblog.Spiellaenge"));
        preparedStatement.setObject(3, data.get("Videoblog.Link720p"));
        preparedStatement.setObject(4, data.get("Videoblog.Link1080p"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String kommentator = SqlUtil.getRedacteurByBlogeintrag((Integer) oldData.get("Videoblog.ID"));
        if (!username.equals(kommentator)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Videoblog SET Spiellaenge = ?,Link720p = ?,Link1080p = ?   WHERE ID = ? ");
        preparedStatement.setObject(1, newData.get("Videoblog.Spiellaenge"));
        preparedStatement.setObject(1, newData.get("Videoblog.Link720p"));
        preparedStatement.setObject(1, newData.get("Videoblog.Link1080p"));
        preparedStatement.setObject(2, oldData.get("Videoblog.ID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        String username = (String) Application.getInstance().getData().get("username");
        String redakteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("Videoblog.ID"));
        if (!username.equals(redakteur)) {
            throw new SQLException("Keine Berechtigungen.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Videoblog WHERE ID = ?");
        preparedStatement.setObject(1, data.get("Videoblog.ID"));

        preparedStatement.executeUpdate();
    }
}
