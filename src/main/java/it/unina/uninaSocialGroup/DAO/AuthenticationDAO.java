package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.Authentication;
import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDAO {
      private static Connection connect = DatabaseConnectionManager.createDatabaseConnection();

      /**
       * CheckCredentials
       * Controlla se sono presenti nel database le credenziali inserite
       * @param email inserita dall'utente
       * @param password inserita dall'utente
       * @return true or false
       */
      public boolean CheckCredentials(String email, String password) {
            String query = "SELECT 1 FROM autenticazione WHERE Email = ? AND Password = ?";
            PreparedStatement ps = null;
            ResultSet resultSet = null;
            try {
                  ps = connect.prepareStatement(query);
                  ps.setString(1, email);
                  ps.setString(2, password);
                  resultSet = ps.executeQuery();
                  while (!resultSet.next()) {
                        return false;
                  }
                  return true;
            } catch (SQLException sql) {
                  sql.printStackTrace();
                  return false;
            }
      }
}
