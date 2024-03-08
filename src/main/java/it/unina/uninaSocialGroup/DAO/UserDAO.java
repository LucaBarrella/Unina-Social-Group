package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.User;

import java.sql.*;

public class UserDAO {
    private static Connection connect = DatabaseConnectionManager.createDatabaseConnection();

    /**
     * getUserByEmail
     * Restituisce i dati di un utente a partire dalla sua email
     * @param email
     * @return user
     */
    public User getUserByEmail(String email){
        User user = null;
        String query = "SELECT * " +
                "FROM Utente U JOIN Autenticazione A " +
                "ON U.ID_Autenticazione = A.ID_Autenticazione " +
                "WHERE A.Email = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, email);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("Nome"),
                        resultSet.getString("Cognome"),
                        resultSet.getString("Matricola"),
                        resultSet.getDate("Data_di_Nascita"),
                        resultSet.getDate("Data_di_Registrazione"));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return user;
    }
}