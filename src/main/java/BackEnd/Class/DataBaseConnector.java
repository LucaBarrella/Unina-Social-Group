package BackEnd.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
      public static Connection ConnectToDatabase(){
          Connection connectDB = null;
          try{
              //Carica il driver JDBC
              Class.forName("manny.db.elephantsql.com");
              //Creo la connessione al database
              connectDB = DriverManager.getConnection("jdbc:postgresql://manny.db.elephantsql.com:5432/","rhwjlmoo","qRli1Cj50UXsPpSaCn4cG1u5cTgoqjZF");
              if(connectDB != null){
                  System.out.println("Connessione al database riuscita");
              }else{
                  System.out.println("Impossibile connettersi al database");
              }
          }catch(ClassNotFoundException | SQLException e){
              e.printStackTrace();
          }finally{
              //Chiusura della connessione
              try {
                  if (connectDB != null && !connectDB.isClosed()) {
                      connectDB.close();
                      System.out.println("Connessione chiusa!");
                  }
              }catch(SQLException e){
                  e.printStackTrace();
              }
          }
          return connectDB;
      }
}
