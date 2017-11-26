package de.hhu.cs.dbs.project.table.blog;

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
        String selectQuery = String.format(" SELECT BW.Blogeintrag, B.Redakteur, B.Titel, B.Text, BW.Bewertungsskala AS Bewertung FROM Blogeintrag B, BewertungBlogeintrag BW WHERE B.ID = BW.Blogeintrag  AND BW.Nutzer = '%s' ",username);
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE B.Titel LIKE '%" + filter + "%'";
        }
        selectQuery = selectQuery + " ORDER BY B.Erstellungsdatum desc";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String username = (String) Application.getInstance().getData().get("username");
        String selectQuery = String.format("SELECT BW.Blogeintrag, BW.Bewertungsskala  FROM BewertungBlogeintrag BW  WHERE BW.Nutzer = '%s' AND BW.Blogeintrag  = '%s'",username,data.get("BewertungBlogeintrag.Blogeintrag"));
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        String username = (String)Application.getInstance().getData().get("username");
        String redacteur = SqlUtil.getRedacteurByBlogeintrag((Integer) data.get("BewertungBlogeintrag.Blogeintrag"));
        if (username.equals(redacteur)) {
            throw new SQLException("Gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO BewertungBlogeintrag(Bewertungsskala,Nutzer, Blogeintrag) VALUES (?, ?, ?)");
        preparedStatement.setObject(1, data.get("BewertungBlogeintrag.Bewertungsskala"));
        preparedStatement.setObject(2, username);
        preparedStatement.setObject(3, data.get("BewertungBlogeintrag.Blogeintrag"));

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
