package it.unina.uninaSocialGroup.esperimenti;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    /**
     * registerNewUser
     * Registra un nuovo utente nel database
     * @param  name dall'utente
     * @param studentID inserita dall'utente
     * @return true se la registrazione è andata a buon fine, false altrimenti
     */
    public void addNewUser(String studentID, String name, String surname, String birthDate, String registrationDate){
        Connection connect = null;
        String query = "INSERT INTO \"UninaSocialGroup\".\"utente\" (matricola, nome, cognome, DataDiNascita, DataDiRegistrazione) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psCheck = null;
        PreparedStatement psInsert = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            psInsert = connect.prepareStatement(query);
            psInsert.setString(1, studentID);
            psInsert.setString(2, name);
            psInsert.setString(3, surname);
            psInsert.setString(4, birthDate);
            psInsert.setString(5, registrationDate);
            psInsert.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User not added!");
        }
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