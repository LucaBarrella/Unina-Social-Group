package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;

import java.sql.*;
import java.time.LocalDate;

public class PostDAO {
    /**
     * CreateNewPost
     * Inserisce un nuovo post nel db.
     * @param Category categoria del post
     * @param Message messaggio del post
     * @param Matricola matricola del creatore del post
     * @param IDGroup ID del gruppo in cui il post è pubblicato
     */
    public void CreateNewPost(String Category, String Message, String Matricola, String IDGroup){
        PreparedStatement ps = null;
        String query = "INSERT INTO Post (Categoria, Data_Pubblicazione, Messaggio_Scritto, Percorso_File, Estensione, Tipo_Post, Matricola, ID_Gruppo) VALUES (?,current_date,?,NULL,NULL,'Post_Testuale',?,?)";
        try {
            Connection db = DatabaseConnectionManager.createDatabaseConnection();
            ps = db.prepareStatement(query);
            ps.setString(1, Category);
            ps.setString(2, Message);
            ps.setString(3, Matricola);
            ps.setString(4, IDGroup);
            ps.executeUpdate();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    /**
     * getPostPlusLike
     * Restituisce il post con piu like pubblicato su un gruppo in un determinato mese
     * @param Month mese di riferimento
     * @param IDGroup ID del gruppo
     * @return post
     */
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

    /**
     * getPostMinusLike
     * Restituisce il post con meno like pubblicato su un gruppo in un determinato mese
     * @param Month mese di riferimento
     * @param IDGroup ID del gruppo
     * @return post
     */
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

    /**
     * getPostPlusComments
     * Restituisce il post con piu commenti pubblicato su un gruppo in un determinato mese
     * @param Month mese di riferimento
     * @param IDGroup ID del gruppo
     * @return post
     */
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

    /**
     * getPostMinusComments
     * Restituisce il post con meno commenti pubblicato su un gruppo in un determinato mese
     * @param Month mese di riferimento
     * @param IDGroup ID del gruppo
     * @return post
     */
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

    /**
     * getAveragePost
     * Restituisce il numero medio di post pubblicati su un gruppo in un determinato mese
     * @param Month mese di riferimento
     * @param IDGroup ID del gruppo
     * @return result
     */
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
