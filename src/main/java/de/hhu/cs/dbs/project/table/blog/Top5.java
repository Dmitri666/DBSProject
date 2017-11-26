package de.hhu.cs.dbs.project.table.blog;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class Top5 extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        return "SELECT B.ID,B.Redakteur,B.Titel,avg(BW.Bewertungsskala) AS Bewertung from Videoblog V,Blogeintrag B, BewertungBlogeintrag BW WHERE V.ID = BW.Blogeintrag AND B.ID = BW.Blogeintrag GROUP BY B.ID,B.Redakteur,B.Titel order by Bewertung desc limit 5";
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
