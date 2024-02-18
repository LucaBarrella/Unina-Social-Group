package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostDAO {
    public String getPostPlusLike(int Month, String IDGroup) {
        String post = null;
        Connection connect = null;
        String query = "SELECT p.ID_Post, COUNT(l.ID_Post) AS Numero_Like " +
                        "FROM Post p " +
                        "JOIN Likes l ON p.ID_Post = l.ID_Post " +
                        "WHERE p.ID_Gruppo = ? " +
                        "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                        "GROUP BY p.ID_Post " +
                        "ORDER BY Numero_Like DESC " +
                        "LIMIT 1";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post = resultSet.getString("ID_Post");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return post;
    }

    public String getPostMinusLike(int Month, String IDGroup) {
        String post = null;
        Connection connect = null;
        String query = "SELECT p.ID_Post, COUNT(l.ID_Post) AS Numero_Like " +
                        "FROM Post p " +
                        "JOIN Likes l ON p.ID_Post = l.ID_Post " +
                        "WHERE p.ID_Gruppo = ? " +
                        "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                        "GROUP BY p.ID_Post " +
                        "ORDER BY Numero_Like ASC " +
                        "LIMIT 1";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post = resultSet.getString("ID_Post");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return post;
    }

    public String getPostPlusComments(int Month, String IDGroup) {
        String post = null;
        Connection connect = null;
        String query = "SELECT p.ID_Post " +
                        "FROM Post p " +
                        "LEFT JOIN Commento c ON p.ID_Post = c.ID_Post " +
                        "WHERE p.ID_Gruppo = ? " +
                        "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                        "GROUP BY p.ID_Post " +
                        "ORDER BY COUNT(c.ID_Commento) DESC " +
                        "LIMIT 1";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post = resultSet.getString("ID_Post");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return post;
    }

    public String getPostMinusComments(int Month, String IDGroup) {
        String post = null;
        Connection connect = null;
        String query = "SELECT p.ID_Post " +
                        "FROM Post p " +
                        "LEFT JOIN Commento c ON p.ID_Post = c.ID_Post " +
                        "WHERE p.ID_Gruppo = ? " +
                        "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                        "GROUP BY p.ID_Post " +
                        "ORDER BY COUNT(c.ID_Commento) " +
                        "LIMIT 1";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post = resultSet.getString("ID_Post");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return post;
    }

    public int getAveragePost(int Month, String IDGroup) {
        int result = 0;
        Connection connect = null;
        String query = "SELECT AVG(num_post) AS Numero_Medio_Post_Pubblicati " +
                            "FROM (" +
                            "SELECT COUNT(*) AS num_post " +
                            "FROM Post " +
                            "WHERE ID_Gruppo = ? " +
                            "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                            "GROUP BY DATE_TRUNC('day', Data_Pubblicazione)) AS subquery";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("Numero_Medio_Post_Pubblicati");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return result;
    }
}
