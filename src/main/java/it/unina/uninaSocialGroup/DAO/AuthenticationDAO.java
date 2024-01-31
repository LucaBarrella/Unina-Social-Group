package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.User;
import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDAO {
    public User authenticate(String email, String password) {
        User user = null;
        String query = "SELECT * FROM User WHERE Email = ? AND Password = ?";

        try (Connection connection = DatabaseConnectionManager.createDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getString("Nome"),
                            resultSet.getString("Cognome"),
                            resultSet.getString("Matricola"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getDate("DataDiNascita"),
                            resultSet.getDate("DataDiRegistazione")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}