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
        String query = "SELECT ID_Post " +
                            "FROM (" +
                            "SELECT ID_Post, COUNT(*) AS Num_Likes " +
                            "FROM Like " +
                            "WHERE ID_Gruppo = ? " +
                            "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                            "GROUP BY ID_Post " +
                            "ORDER BY Num_Likes DESC " +
                            "LIMIT 1)";
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
        String query = "SELECT ID_Post " +
                            "FROM (" +
                            "SELECT ID_Post, COUNT(*) AS Num_Likes " +
                            "FROM Like " +
                            "WHERE ID_Gruppo = ? " +
                            "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                            "GROUP BY ID_Post " +
                            "ORDER BY Num_Likes ASC " +
                            "LIMIT 1)";
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
        String query = "SELECT ID_Post " +
                            "FROM (" +
                            "SELECT ID_Post, COUNT(*) AS Num_Commenti " +
                            "FROM Commento " +
                            "WHERE ID_Gruppo = ? " +
                            "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                            "GROUP BY ID_Post " +
                            "ORDER BY Num_Commenti DESC " +
                            "LIMIT 1)";
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
        String query = "SELECT ID_Post " +
                            "FROM (" +
                            "SELECT ID_Post, COUNT(*) AS Num_Commenti " +
                            "FROM Commento " +
                            "WHERE ID_Gruppo = ? " +
                            "AND EXTRACT(MONTH FROM Data_Pubblicazione) = ? " +
                            "GROUP BY ID_Post " +
                            "ORDER BY Num_Commenti ASC " +
                            "LIMIT 1)";
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
                            "GROUP BY DATE_TRUNC('day', Data_Pubblicazione))";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("MediaPostPubblicati");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return result;
    }
}
