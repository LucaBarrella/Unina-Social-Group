package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.Comment;
import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.Reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private static Connection connect = DatabaseConnectionManager.createDatabaseConnection();
    public List<Comment> getCommentByPost(String ID_Post) {
        List<Comment> commentList = new ArrayList<>();
        PreparedStatement ps = null;
        String query = "SELECT * FROM Commento NATURAL JOIN utente WHERE ID_Post = ? ORDER BY data_di_pubblicazione DESC, ora_di_pubblicazione DESC";
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, ID_Post);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getString("id_commento"),
                        resultSet.getString("id_post"),
                        resultSet.getString("nome") + " " + resultSet.getString("cognome"),
                        resultSet.getString("testo_scritto"),
                        resultSet.getString("ora_di_pubblicazione"));
                commentList.add(comment);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return commentList;
    }

    //TODO: Implementare la funzione per risposte a un commento di un post (status: Aborted)
    public List<Reply> getReplyByComment(String ID_Comment) {
        List<Reply> replyList = new ArrayList<>();
        PreparedStatement ps = null;
        String query = "SELECT * FROM risposta WHERE ID_commento = ?";
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, ID_Comment);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Reply reply = new Reply(
                        resultSet.getString("id_commento"),
                        resultSet.getString("matricola"),
                        resultSet.getString("messaggio"));
                replyList.add(reply);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return replyList;
    }

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
}