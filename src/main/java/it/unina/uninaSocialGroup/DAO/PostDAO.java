package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.Post;
import it.unina.uninaSocialGroup.Model.User;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.time.LocalDate;

public class PostDAO {
    Connection connect = DatabaseConnectionManager.createDatabaseConnection();

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
            ps = connect.prepareStatement(query);
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
        String query = "SELECT p.Messaggio_Scritto, COUNT(l.ID_Post) AS Numero_Like " +
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
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post = resultSet.getString("Messaggio_Scritto");
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
        String query = "SELECT p.Messaggio_Scritto, COUNT(l.ID_Post) AS Numero_Like " +
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
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post = resultSet.getString("Messaggio_Scritto");
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
        String query = "SELECT p.Messaggio_Scritto " +
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
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post = resultSet.getString("Messaggio_Scritto");
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
        String query = "SELECT p.Messaggio_Scritto " +
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
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setInt(2, Month);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post = resultSet.getString("Messaggio_Scritto");
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


    /**
     * getAllPosts
     * Restituisce tutti i post pubblicati su un gruppo
     * @param group
     */
    public void getAllPosts(Group group) {
        List<Post> dataList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        String IDGroup = group.getIDGruppo();
        String query = "SELECT * FROM post NATURAL JOIN utente WHERE id_gruppo = ?";
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Post post = new Post(
                        resultSet.getString("ID_Post"),
                        resultSet.getString("Categoria"),
                        resultSet.getString("Messaggio_Scritto"),
                        resultSet.getString("nome") + " " + resultSet.getString("cognome"));
                dataList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        group.setPostPubblicati(dataList);
    }

    /**
     * addLike
     * Aggiunge un nuovo like messo da un utente ad un post
     * @param matricola
     * @param IDPost
     */
    public void addLike(String matricola, String IDPost){
        String query = "INSERT INTO Likes (matricola, id_post, data_like, ora_like) VALUES (?,?, current_date, current_time)";
        PreparedStatement ps = null;
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, matricola);
            ps.setString(2, IDPost);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RemoveLike
     * Rimuove un like messo da un utente ad un post
     * @param matricola
     * @param IDPost
     */
    public void RemoveLike(String matricola, String IDPost){
        String query = "DELETE FROM Likes WHERE Matricola = ? AND ID_Post = ?";
        PreparedStatement ps = null;
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, matricola);
            ps.setString(2, IDPost);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getNumberOfLike
     * Restituisce il numero di like messi ad un post
     * @param IDPost
     * @return numberOfLike
     */
    public int getNumberOfLike(String IDPost){
        int numberOfLike = 0;
        PreparedStatement ps = null;
        String query = "SELECT COUNT(*) AS NumeroLike " +
                        "FROM Likes " +
                        "WHERE ID_Post = ?";
        try{
            ps = connect.prepareStatement(query);
            ps.setString(1, IDPost);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                numberOfLike = resultSet.getInt("NumeroLike");
            }
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        return numberOfLike;
    }

    /**
     * isLikeAlreadyAdd
     * Controlla se è stato gia messo like ad un post da parte di un utente
     * @param matricola
     * @param IDPost
     * @return true or false
     */
    public boolean isLikeAlreadyAdd(String matricola, String IDPost){
        String query = "SELECT 1 FROM Likes WHERE Matricola = ? AND ID_Post = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, matricola);
            ps.setString(2, IDPost);
            resultSet = ps.executeQuery();
            while (!resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException sql) {
            sql.printStackTrace();
            return false;
        }
    }
}
