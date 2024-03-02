package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.User;

import java.sql.*;

public class UserDAO {
    Connection connect = DatabaseConnectionManager.createDatabaseConnection();

    /**
     * getMatricolaByEmail
     * Restituisce la matricola dell'utente a partire dalla sua email
     * @param email
     * @return matricola
     */
    public String getMatricolaByEmail(String email){
        String matricola = null;
        String query = "SELECT Matricola FROM Utente WHERE Matricola IN (" +
                            "SELECT U.Matricola FROM Utente U JOIN Autenticazione A "+
                                "ON U.ID_Autenticazione = A.ID_Autenticazione "+
                                "WHERE A.Email = ?)";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, email);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                matricola = resultSet.getString("Matricola");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return matricola;
    }

    /**
     * getUserData
     * Restituisce i dati di un utente a partire dalla sua matricola
     * @param matricola dell'utente
     * @return user
     */
    public User getUserData(String matricola){
        User user = null;
        String query = "SELECT * FROM Utente WHERE Matricola = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, matricola);
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