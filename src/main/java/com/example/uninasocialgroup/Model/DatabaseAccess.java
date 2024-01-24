package com.example.uninasocialgroup.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DatabaseAccess {
    public ResultSet executeQuery(String query, List<String> parameters, String closeDataBase) {
        ResultSet rs = null;
        try {
            // 1. Load the JDBC driver
            Class.forName("manny.db.elephantsql.com");

            // 2. Establish a connection
            Connection conn = DriverManager.getConnection("jdbc:postgresql://manny.db.elephantsql.com:5432/", "rhwjlmoo", "qRli1Cj50UXsPpSaCn4cG1u5cTgoqjZF");

            // 3. Create a statement
            Statement stmt = conn.createStatement();

            // 4. Execute a SQL query
            rs = stmt.executeQuery(query);

            // 5. Close the connection
            if (!closeDataBase.startsWith("n")) {
            } else {
                conn.close();
            }
            // conn.close(); // You might want to close this connection in the finally block or use try-with-resources to ensure it gets closed.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}

/*
String dbUrl = System.getenv("DB_URL");
String username = System.getenv("DB_USERNAME");
String password = System.getenv("DB_PASSWORD");

Connection conn = DriverManager.getConnection(dbUrl, username, password);



String query = "SELECT * FROM users WHERE username = ?";
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setString(1, "username");
ResultSet rs = pstmt.executeQuery();


public String createDynamicSql(String table, String column, String condition) {
    return "SELECT * FROM " + table + " WHERE " + column + " = " + condition;
}



String table = "users";
String column = "username";
String condition = "'username'";

String query = createDynamicSql(table, column, condition);

try (PreparedStatement pstmt = conn.prepareStatement(query)) {
    ResultSet rs = pstmt.executeQuery();
    // process the result set
} catch (SQLException e) {
    e.printStackTrace();
}


* */