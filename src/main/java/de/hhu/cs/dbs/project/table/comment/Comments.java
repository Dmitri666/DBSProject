package de.hhu.cs.dbs.project.table.comment;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class Comments extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        throw new SQLException(getClass().getName() + ".getSelectQueryForTableWithFilter(String) nicht implementiert.");
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
