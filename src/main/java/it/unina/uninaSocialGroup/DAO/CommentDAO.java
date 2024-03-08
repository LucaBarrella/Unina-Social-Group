package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.Comment;
import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private static Connection connect = DatabaseConnectionManager.createDatabaseConnection();

    /**
     * getCommentByPost
     * Restituisce i commenti di un determinato post
     * @param post
     * @return commentList
     */
    public void getCommentByPost(Post post) {
        List<Comment> commentList = new ArrayList<>();
        PreparedStatement ps = null;
        String query = "SELECT * FROM Commento NATURAL JOIN utente WHERE ID_Post = ? ORDER BY data_di_pubblicazione DESC, ora_di_pubblicazione DESC";
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, post.getIDPost());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getString("id_commento"),
                        resultSet.getString("id_post"),
                        resultSet.getString("nome") + " " + resultSet.getString("cognome"),
                        resultSet.getString("testo_scritto"),
                        resultSet.getTime("ora_di_pubblicazione"));
                commentList.add(comment);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        post.setCommenti(commentList);
    }

    /**
     * createNewComment
     * Crea un nuovo commento nel database
     * @param text testo del commento
     * @param matricola
     * @param postId
     */
    public void createNewComment(String text, String matricola, String postId) {
        PreparedStatement ps = null;
        String query = "INSERT INTO Commento (testo_scritto, data_di_pubblicazione, ora_di_pubblicazione, id_post, matricola) VALUES (?, CURRENT_DATE, CURRENT_TIME, ?, ?)";
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, text);
            ps.setString(2, postId);
            ps.setString(3, matricola);
            ps.executeUpdate();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    /**
     * getNumberOfComment
     * Restituisce il numero di commenti messi ad un post
     * @param IDPost
     * @return numberOfComment
     */
    public int getNumberOfComment(String IDPost) {
        int numberOfComment = 0;
        PreparedStatement ps = null;
        String query = "SELECT COUNT(*) AS NumeroCommenti " +
                "FROM Commento " +
                "WHERE ID_Post = ?";
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, IDPost);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                numberOfComment = resultSet.getInt("NumeroCommenti");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return numberOfComment;
    }
}