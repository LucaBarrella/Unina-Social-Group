package it.unina.uninaSocialGroup.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static final String JDBCDriver = "org.postgresql.Driver";
    private static final String DatabaseURL = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/UninaSocialGroup";
    private static final String DatabaseUser = "postgres";
    private static final String DatabasePassword = "nuvxiz-vuvSy2-tajxuk";

    public static Connection createDatabaseConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBCDriver);
            connection = DriverManager.getConnection(DatabaseURL, DatabaseUser, DatabasePassword);
            printConnectionStatus(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            //Fatto bene?
            //closeDatabaseConnection(connection);
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