package DAO;

import java.sql.SQLException;

public interface AuthenticationDAO {
      /**
       * CheckCredentials
       * Controlla se sono presenti nel database nel credenziali inserite
       * @param email inserita dall'utente
       * @param password inserita dall'utente
       * @return true or false
       */
      boolean CheckCredentials(String email, String password);

}
