package it.unina.uninaSocialGroup.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static final String JDBCDriver = "org.postgresql.Driver";
    private static final String DatabaseURL = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?currentSchema=UninaSocialGroup";
    private static final String DatabaseUser = "postgres.cpsqkfuqoizfrhisstzx";
    private static final String DatabasePassword = "nuvxiz-vuvSy2-tajxuk";

    /**
     * createDatabaseConnection
     * Effettua la connessione al database
     * @return connection
     */
    public static Connection createDatabaseConnection(){
        Connection connection = null;
        try {
            Class.forName(JDBCDriver);
            connection = DriverManager.getConnection(DatabaseURL, DatabaseUser, DatabasePassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}