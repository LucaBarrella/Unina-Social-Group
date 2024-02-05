package it.unina.uninaSocialGroup.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static final String JDBCDriver = "org.postgresql.Driver";
    private static final String DatabaseURL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=UninaSocialGroup";
    private static final String DatabaseUser = "postgres";
    private static final String DatabasePassword = "lucasimone";

    /**
     * createDatabaseConnection
     * Effettua la connessione al database
     * @return connection
     */
    public static Connection createDatabaseConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBCDriver);
            connection = DriverManager.getConnection(DatabaseURL, DatabaseUser, DatabasePassword);
            printConnectionStatus(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void printConnectionStatus(Connection connection) {
        String connectionStatus = (connection != null) ? "Connessione al database riuscita" : "Impossibile connettersi al database";
        System.out.println(connectionStatus);
    }

}