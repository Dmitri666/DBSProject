package de.hhu.cs.dbs.project.table.account;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class Favorites extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT B.*  FROM NutzerFavorisiertBlogeintrag B WHERE B.Nutzer = '%" + Application.getInstance().getData().get("username") + "%'";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE B.Redakteur LIKE '%" + filter + "%'";
        }
        selectQuery = selectQuery + " ORDER BY B.Aenderungsdatum";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".getSelectQueryForRowWithData(Data) nicht implementiert.");
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".insertRowWithData(Data) nicht implementiert.");
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
