package BackEnd.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static final String DatabaseURL = "jdbc:postgresql://manny.db.elephantsql.com:5432/";
    private static final String DatabaseUser = "rhwjlmoo";
    private static final String DatabasePassword = "qRli1Cj50UXsPpSaCn4cG1u5cTgoqjZF";

    public static Connection createDatabaseConnection() {
        Connection connection = null;
        try {
            Class.forName("manny.db.elephantsql.com");
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

    private static void closeDatabaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connessione chiusa!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}