package de.hhu.cs.dbs.project.table.account;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if ( permission ==  2) {
            return "SELECT Benutzername , EMail, Passwort, Geburtsdatum, Geschlecht  FROM Nutzer WHERE Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
        else if ( permission ==  1){
            return "SELECT N.Benutzername , N.EMail, N.Passwort , N.Geburtsdatum, N.Geschlecht, P.Vorname , P.Nachname, P.Biographie FROM Nutzer N, Redakteur P WHERE N.Benutzername = P.Benutzername AND N.Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
        else {
            return "SELECT N.Benutzername , N.EMail, N.Passwort , N.Geburtsdatum, N.Geschlecht, P.Vorname , P.Nachname, P.Biographie, substr(C.Telefonnummer,2,length(C.Telefonnummer) - 2) AS Telefonnummer FROM Nutzer N, Redakteur P, Chefredakteur C WHERE N.Benutzername = P.Benutzername AND P.Benutzername = C.Benutzername AND N.Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        Integer permission = (Integer) Application.getInstance().getData().get("permission");
        if ( permission ==  2) {
            return "SELECT Benutzername , EMail, Passwort, Geburtsdatum, Geschlecht  FROM Nutzer WHERE Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
        else if ( permission ==  1){
            return "SELECT N.Benutzername , N.EMail, N.Passwort , N.Geburtsdatum, N.Geschlecht, P.Vorname , P.Nachname, P.Biographie FROM Nutzer N, Redakteur P WHERE N.Benutzername = P.Benutzername AND N.Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
        else {
            return "SELECT N.Benutzername , N.EMail, N.Passwort , N.Geburtsdatum, N.Geschlecht, P.Vorname , P.Nachname, P.Biographie, substr(C.Telefonnummer,2,length(C.Telefonnummer) - 2) AS Telefonnummer FROM Nutzer N, Redakteur P, Chefredakteur C WHERE N.Benutzername = P.Benutzername AND P.Benutzername = C.Benutzername AND N.Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".insertRowWithData(Data) nicht implementiert.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if(!isValidEmail((String)newData.get("Nutzer.EMail"))) {
            throw new SQLException("Invalide Email");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Nutzer SET EMail = ?,Passwort = ?, Geburtsdatum = ?, Geschlecht = ?  WHERE Benutzername = ?");
        preparedStatement.setObject(1, newData.get("Nutzer.EMail"));
        preparedStatement.setObject(2, newData.get("Nutzer.Passwort"));
        preparedStatement.setObject(3, newData.get("Nutzer.Geburtsdatum"));
        preparedStatement.setObject(4, newData.get("Nutzer.Geschlecht"));
        preparedStatement.setObject(5, Application.getInstance().getData().get("username"));
        preparedStatement.executeUpdate();

        Integer permission = (Integer) Application.getInstance().getData().get("permission");

        if (permission == 1  || permission == 0) {

            PreparedStatement pstm = Application.getInstance().getConnection().prepareStatement("UPDATE Redakteur SET Vorname = ?,Nachname = ? ,Biographie = ? WHERE Benutzername = ?");
            pstm.setObject(1, newData.get("Redakteur.Vorname"));
            pstm.setObject(2, newData.get("Redakteur.Nachname"));
            pstm.setObject(3, newData.get("Redakteur.Biographie"));
            pstm.setObject(4, Application.getInstance().getData().get("username"));
            pstm.executeUpdate();
        }
        if (permission == 0) {
            PreparedStatement pstm = Application.getInstance().getConnection().prepareStatement("UPDATE Chefredakteur SET Telefonnummer = ? WHERE Benutzername = ?");
            pstm.setObject(1, "'" + newData.get(".Telefonnummer") + "'");
            pstm.setObject(2, Application.getInstance().getData().get("username"));
            pstm.executeUpdate();

        }
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".deleteRowWithData(Data) nicht implementiert.");
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
