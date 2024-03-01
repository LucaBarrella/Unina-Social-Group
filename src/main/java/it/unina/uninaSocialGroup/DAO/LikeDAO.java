package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDAO {

    /**
     * addLike
     * Aggiunge un nuovo like messo da un utente ad un post
     * @param matricola
     * @param IDPost
     */
    public void addLike(String matricola, String IDPost){
        Connection connect = null;
        String query = "INSERT INTO Likes (matricola, id_post, data_like, ora_like) VALUES (?,?, current_date, current_time)";
        PreparedStatement ps = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
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
        Connection connect = null;
        String query = "DELETE FROM Likes WHERE Matricola = ? AND ID_Post = ?";
        PreparedStatement ps = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
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
            Connection db = DatabaseConnectionManager.createDatabaseConnection();
            ps = db.prepareStatement(query);
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
        Connection connect = null;
        String query = "SELECT 1 FROM Likes WHERE Matricola = ? AND ID_Post = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
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



