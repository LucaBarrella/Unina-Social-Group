package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.User;

import java.sql.*;

public class UserDAO {
    /**
     * registerNewUser
     * Registra un nuovo utente nel database
     * @param name dall'utente
     * @param studentID inserita dall'utente
     * @return true se la registrazione è andata a buon fine, false altrimenti
     */

    public boolean UserAlreadyExists(String studentID) {
        Connection connect = null;
        String query = "SELECT 1 FROM utente WHERE Matricola = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, studentID);
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
    public void addNewUser(String studentID, String name, String surname, Date birthDate, Date registrationDate){
        Connection connect = null;
        String query = "INSERT INTO utente (Matricola, Nome, Cognome, Data_di_Nascita, Data_di_Registrazione) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psInsert = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            psInsert = connect.prepareStatement(query);
            psInsert.setString(1, studentID);
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

    public User getFullNameByEmail(String email){
        User user = null;
        Connection connect = null;
        String query = "SELECT Nome, Cognome FROM Utente U JOIN Autenticazione A ON U.ID_Autenticazione = A.ID_Autenticazione WHERE A.Email = ?";
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




/*
* @FXML
private TextField nomeField;
@FXML
private TextField cognomeField;
@FXML
private TextField emailField;
@FXML
private TextField passwordField;

public void registerUser() {
    String nome = nomeField.getText();
    String cognome = cognomeField.getText();
    String email = emailField.getText();
    String password = passwordField.getText();

    // Qui puoi utilizzare i dati raccolti per creare un nuovo utente
    // Ad esempio, potresti chiamare il metodo registerNewUser del tuo DAO
    RegistrationDAO registrationDAO = new RegistrationDAO();
    boolean registrationResult = registrationDAO.registerNewUser(email, password);

    if (registrationResult) {
        System.out.println("Utente registrato con successo");
    } else {
        System.out.println("Registrazione fallita");
    }
}
*
*
*
*
*
*
*
*
*
*
*
*
*
* @FXML
private TextField nomeField;
@FXML
private TextField cognomeField;
@FXML
private TextField matricolaField;
@FXML
private TextField emailField;
@FXML
private PasswordField passwordField;
@FXML
private PasswordField confermaPasswordField;
@FXML
private TextField numeroTelefonoField;

public void registerUser() {
    String nome = nomeField.getText();
    String cognome = cognomeField.getText();
    String matricola = matricolaField.getText();
    String email = emailField.getText();
    String password = passwordField.getText();
    String confermaPassword = confermaPasswordField.getText();
    String numeroTelefono = numeroTelefonoField.getText();

    // Qui puoi utilizzare i dati raccolti per creare un nuovo utente
    // Ad esempio, potresti chiamare il metodo registerNewUser del tuo DAO
    RegistrationDAO registrationDAO = new RegistrationDAO();
    boolean registrationResult = registrationDAO.registerNewUser(nome, cognome, matricola, email, password, confermaPassword, numeroTelefono);

    if (registrationResult) {
        System.out.println("Utente registrato con successo");
    } else {
        System.out.println("Registrazione fallita");
    }
}*/