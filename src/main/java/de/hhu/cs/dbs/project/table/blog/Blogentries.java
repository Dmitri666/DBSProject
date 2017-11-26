package de.hhu.cs.dbs.project.table.blog;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import de.hhu.cs.dbs.project.table.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Blogentries extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT B.ID, B.Redakteur, B.Titel, B.Text, B.Erstellungsdatum, B.Aenderungsdatum, group_concat(T.Schlagwort) AS Schlagworte" +
                "  FROM Blogeintrag B LEFT OUTER JOIN BlogeintragHatSchlagwort T ON B.ID = T.Blogeintrag" +
                "     GROUP BY  B.ID, B.Redakteur, B.Titel, B.Text, B.Erstellungsdatum, B.Aenderungsdatum\n";

        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " HAVING ";
            String[] filters = filter.split(",");
            if(filters.length == 1)
            {
                filters = filter.split(" ");
            }
            for(int i = 0; i < filters.length; i++)
            {
                String value = filters[i].trim();
                selectQuery += " (B.Redakteur LIKE '%" + value + "%' OR B.Titel LIKE '%" + value + "%' OR Schlagworte LIKE '%" + value + "%') ";
                if(filters.length > 1 && i < filters.length - 1)
                {
                    selectQuery += " AND ";
                }
            }

        }

        selectQuery = selectQuery + " ORDER BY B.Aenderungsdatum DESC";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = String.format("SELECT B.ID, B.Titel, B.Text, group_concat(T.Schlagwort) AS Schlagwort  FROM Blogeintrag B LEFT OUTER JOIN BlogeintragHatSchlagwort T ON B.ID = T.Blogeintrag WHERE B.ID ='%s' GROUP BY  B.ID, B.Titel, B.Text  ",data.get("Blogeintrag.ID"));
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        if (data.get(".Schlagwort") == null) {
            throw new SQLException("Schlagwort.");
        }

        String schlagwort = (String) data.get(".Schlagwort");
        String[] slagwoerte = schlagwort.split(",");
        if(slagwoerte.length == 1)
        {
            slagwoerte = schlagwort.split(" ");
        }
        String username = (String) Application.getInstance().getData().get("username");

        com.alexanderthelen.applicationkit.database.Connection conn = Application.getInstance().getConnection();
        conn.getRawConnection().setAutoCommit(false);


        try {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum,Redakteur) VALUES (?, ?, ?, ?)");
        preparedStatement.setObject(1, data.get("Blogeintrag.Titel"));
        preparedStatement.setObject(2, data.get("Blogeintrag.Text"));
        preparedStatement.setObject(3, format.format(new Date()));
        preparedStatement.setObject(4, username);
        preparedStatement.executeUpdate();

        ResultSet set = Application.getInstance().getConnection().executeQuery(" SELECT last_insert_rowid() AS ID");
        set.next();
        Integer id = set.getInt("ID");

        for(int i = 0; i < slagwoerte.length; i++) {
            PreparedStatement preparedStatement1 = Application.getInstance().getConnection().prepareStatement("INSERT INTO BlogeintragHatSchlagwort(Blogeintrag, Schlagwort) VALUES (?, ?)");
            preparedStatement1.setObject(1, id);
            preparedStatement1.setObject(2, slagwoerte[i]);

            preparedStatement1.executeUpdate();
        }
            conn.getRawConnection().commit();
            conn.getRawConnection().setAutoCommit(true);

        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.getRawConnection().rollback();
                }
            } catch (SQLException e2) {
                throw e2;
            }
            throw ex;
        }


    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        if (permission == 1) {
            String username = (String) Application.getInstance().getData().get("username");
            String redacteur = SqlUtil.getRedacteurByBlogeintrag((Integer) oldData.get("Blogeintrag.ID"));
            if (!username.equals(redacteur)) {
                throw new SQLException("Keine Berechtigungen.");
            }
        }

        if (newData.get(".Schlagwort") == null) {
            throw new SQLException("Schlagwort.");
        }

        String schlagwort = (String) newData.get(".Schlagwort");
        String[] slagwoerte = schlagwort.split(",");
        if(slagwoerte.length == 1)
        {
            slagwoerte = schlagwort.split(" ");
        }
        com.alexanderthelen.applicationkit.database.Connection conn = Application.getInstance().getConnection();
        conn.getRawConnection().setAutoCommit(false);


        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Blogeintrag SET Titel = ?, Text = ?, Aenderungsdatum = ? WHERE ID = ? ");
            preparedStatement.setObject(1, newData.get("Blogeintrag.Titel"));
            preparedStatement.setObject(2, newData.get("Blogeintrag.Text"));
            preparedStatement.setObject(3, format.format(new Date()));
            preparedStatement.setObject(4, oldData.get("Blogeintrag.ID"));
            preparedStatement.executeUpdate();

            {
                PreparedStatement preparedStatement1 = Application.getInstance().getConnection().prepareStatement("DELETE FROM BlogeintragHatSchlagwort WHERE Blogeintrag = ? ");
                preparedStatement1.setObject(1, oldData.get("Blogeintrag.ID"));

                preparedStatement1.executeUpdate();
            }

            for(int i = 0; i < slagwoerte.length; i++) {
                PreparedStatement preparedStatement1 = Application.getInstance().getConnection().prepareStatement("INSERT INTO BlogeintragHatSchlagwort(Blogeintrag, Schlagwort) VALUES (?, ?)");
                preparedStatement1.setObject(1, oldData.get("Blogeintrag.ID"));
                preparedStatement1.setObject(2, slagwoerte[i]);

                preparedStatement1.executeUpdate();
            }

            conn.getRawConnection().commit();
            conn.getRawConnection().setAutoCommit(true);

        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.getRawConnection().rollback();
                }
            } catch (SQLException e2) {
                throw e2;
            }
            throw ex;
        }

    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if (permission > 1) {
            throw new SQLException("Keine Berechtigungen.");
        }
        if (permission == 1) {
            String username = (String) Application.getInstance().getData().get("username");
            String redacteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("Blogeintrag.ID"));
            if (!username.equals(redacteur)) {
                throw new SQLException("Keine Berechtigungen.");
            }
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Blogeintrag WHERE ID = ?");
        preparedStatement.setObject(1, data.get("Blogeintrag.ID"));
        preparedStatement.executeUpdate();
    }


}
