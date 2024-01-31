package it.unina.uninaSocialGroup.DAO;

import Model.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDAO {
      /**
       * CheckCredentials
       * Controlla se sono presenti nel database nel credenziali inserite
       * @param email inserita dall'utente
       * @param password inserita dall'utente
       * @return true or false
       */
      public boolean CheckCredentials(String email, String password){
            Connection connect;
            String query = "SELECT * FROM \"Autenticazione\" WHERE Email = ? AND Password = ?";
            PreparedStatement ps = null;
            ResultSet resultSet = null;
            try{
                  connect = DatabaseConnectionManager.createDatabaseConnection();
                  ps = connect.prepareStatement(query);
                  ps.setString(1,email);
                  ps.setString(2,password);
                  resultSet = ps.executeQuery();
                  while (!resultSet.next()){
                        return false;
                  }
            }catch (SQLException sql){
                  sql.printStackTrace();
            }finally{
                  return true;
            }
      }

}
