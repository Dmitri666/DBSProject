package de.hhu.cs.dbs.project.gui;

import com.alexanderthelen.applicationkit.Application;
import com.alexanderthelen.applicationkit.database.Data;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class AuthenticationViewController extends com.alexanderthelen.applicationkit.gui.AuthenticationViewController {
    protected AuthenticationViewController(String name) {
        super(name);
    }

    public static AuthenticationViewController createWithName(String name) throws IOException {
        AuthenticationViewController viewController = new AuthenticationViewController(name);
        viewController.loadView();
        return viewController;
    }

    @Override
    public void loginUser(Data data) throws SQLException {
        String selectQuery = "SELECT Benutzername,EMail,Geburtsdatum, Passwort,Geschlecht FROM Nutzer WHERE Benutzername ='"  + data.get("username") + "' AND Passwort = '" + data.get("password") + "'";
        ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
        if(!result.next()) {
            throw new SQLException("Falsche Benutzername oder Passwort.");
        }

        Application.getInstance().getData().put("email",result.getString("EMail"));
        Application.getInstance().getData().put("username",result.getString("Benutzername"));
        Application.getInstance().getData().put("geburtsdatum",result.getString("Geburtsdatum"));
        Application.getInstance().getData().put("sex",result.getString("Geschlecht"));
        Application.getInstance().getData().put("permission",2);

        result = Application.getInstance().getConnection().executeQuery("SELECT Nachname, Vorname , Biographie FROM Redakteur WHERE Benutzername ='"  + data.get("username") + "'");
        if(result.next()) {
            Application.getInstance().getData().put("nachname",result.getString("Nachname"));
            Application.getInstance().getData().put("vorname",result.getString("Vorname"));
            Application.getInstance().getData().put("biographie",result.getString("Biographie"));
            Application.getInstance().getData().replace("permission",1);
        }

        result = Application.getInstance().getConnection().executeQuery("SELECT Telefonnummer FROM Chefredakteur WHERE Benutzername ='"  + data.get("username") + "'");
        if(result.next()) {
            Application.getInstance().getData().put("telefonnummer",result.getString("Telefonnummer"));
            Application.getInstance().getData().replace("permission",0);
        }
    }

    @Override
    public void registerUser(Data data) throws SQLException {
        String benutzernameQuery = "SELECT Benutzername FROM Nutzer WHERE Benutzername ='" + data.get("username") + "'";
        ResultSet result = Application.getInstance().getConnection().executeQuery(benutzernameQuery);
        if (result.next()) {
            throw new SQLException("Benutzername schon vergeben.");
        }

        String eMailQuery = "SELECT EMail FROM Nutzer WHERE EMail ='" + data.get("email") + "'";
        result = Application.getInstance().getConnection().executeQuery(eMailQuery);
        if (result.next()) {
            throw new SQLException("EMail schon vergeben.");
        }

        com.alexanderthelen.applicationkit.database.Connection conn = Application.getInstance().getConnection();
        conn.getRawConnection().setAutoCommit(false);

        try {
            String sql = "INSERT INTO Nutzer(Benutzername,EMail,Geburtsdatum,Passwort, Geschlecht) VALUES(?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, data.get("username").toString());
            pstmt.setString(2, data.get("email").toString());
            pstmt.setString(3, data.get("birthday").toString());
            pstmt.setString(4, data.get("password").toString());
            pstmt.setString(5, data.get("sex").toString());
            pstmt.executeUpdate();

            if ((boolean) data.get("isRedakteur")) {
                String premiumSql = "INSERT INTO Redakteur(Benutzername,Nachname,Vorname,Biographie) VALUES(?,?,?,?)";
                PreparedStatement pstmt1 = Application.getInstance().getConnection().prepareStatement(premiumSql);

                pstmt1.setString(1, data.get("username").toString());
                pstmt1.setString(2, data.get("nachname").toString());
                pstmt1.setString(3, data.get("vorname").toString());
                if (data.containsKey("biographie")) {
                    pstmt1.setString(4, data.get("biographie").toString());
                }

                pstmt1.executeUpdate();
            }


            if ((boolean) data.get("isChefredakteur")) {
                String actorSql = "INSERT INTO Chefredakteur(Benutzername,Telefonnummer) VALUES(?,?)";
                PreparedStatement pstmt2 = Application.getInstance().getConnection().prepareStatement(actorSql);

                pstmt2.setString(1, data.get("username").toString());
                pstmt2.setString(2, data.get("telefonnummer").toString());
                pstmt2.executeUpdate();
            }

            conn.getRawConnection().commit();
            conn.getRawConnection().setAutoCommit(true);
        }
        catch (Exception ex) {
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
}
