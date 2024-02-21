package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.User;

import java.sql.*;

public class UserDAO {
    /**
     * UserAlreadyExists
     * Controlla che la matricola inserita non esista gia nel db.
     * @param Matricola
     * @return true or false
     */
    public boolean UserAlreadyExists(String Matricola) {
        Connection connect = null;
        String query = "SELECT 1 FROM utente WHERE Matricola = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, Matricola);
            resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return false;
    }

    /**
     * addNewUser
     * Inserisce un nuovo utente nel db
     * @param Matricola dell'utente
     * @param name Nome dell'utente
     * @param surname Cognome dell'utente
     * @param birthDate Data di nascita dell'utente
     * @param registrationDate Data di registrazione dell'utente
     */
    public void addNewUser(String Matricola, String name, String surname, Date birthDate, Date registrationDate){
        Connection connect = null;
        String query = "INSERT INTO utente (Matricola, Nome, Cognome, Data_di_Nascita, Data_di_Registrazione) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psInsert = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            psInsert = connect.prepareStatement(query);
            psInsert.setString(1, Matricola);
            psInsert.setString(2, name);
            psInsert.setString(3, surname);
            psInsert.setDate(4, birthDate);
            psInsert.setDate(5, registrationDate);
            psInsert.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User not added!");
        }
    }

    /**
     * getMatricolaByEmail
     * Restituisce la matricola dell'utente a partire dalla sua email
     * @param email
     * @return matricola
     */
    public String getMatricolaByEmail(String email){
        String matricola = null;
        Connection connect = null;
        String query = "SELECT Matricola FROM Utente WHERE Matricola IN (" +
                            "SELECT U.Matricola FROM Utente U JOIN Autenticazione A "+
                                "ON U.ID_Autenticazione = A.ID_Autenticazione "+
                                "WHERE A.Email = ?)";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
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
        Connection connect = null;
        String query = "SELECT * FROM Utente WHERE Matricola = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
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
     * getFullNameByEmail
     * Restituisce il nome e congome di un utente a partire dalla sua email
     * @param email
     * @return user
     */
    public User getFullNameByEmail(String email){
        User user = null;
        Connection connect = null;
        String query = "SELECT Nome, Cognome " +
                       "FROM Utente U JOIN Autenticazione A " +
                       "ON U.ID_Autenticazione = A.ID_Autenticazione " +
                       "WHERE A.Email = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, email);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("Nome");
                String surname = resultSet.getString("Cognome");
                user = new User(name,surname);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return user;
    }
}