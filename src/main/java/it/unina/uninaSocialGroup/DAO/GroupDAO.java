package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    /**
     * getGroupsBySearchField
     * Restituisce una lista di gruppi filtrati per la barra di ricerca
     * @param searchBarFieldText testo scritto nella barra di ricerca
     * @return List<Group> listGroups
     */
    public List<Group> getGroupsBySearchField(String searchBarFieldText) {
        List<Group> listGroups = new ArrayList<>();

        try (Connection connection = DatabaseConnectionManager.createDatabaseConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM gruppo WHERE nome_Gruppo LIKE ? OR categoria_Gruppo LIKE ?");
            statement.setString(1, "%" + searchBarFieldText + "%");
            statement.setString(2, "%" + searchBarFieldText + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nomeGruppo = resultSet.getString("nome_Gruppo");
                String categoriaGruppo = resultSet.getString("categoria_Gruppo");
                Date dataCreazione = resultSet.getDate("data_Di_Creazione");
                String idGruppo = resultSet.getString("ID_Gruppo");
                Group group = new Group(idGruppo, nomeGruppo, dataCreazione, categoriaGruppo);
                listGroups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listGroups;
    }

    /**
     * getUserGroups
     * Restituisce la lista di gruppi di un utente di cui fa parte o è creatore
     * @param user
     * @return List<Group> dataList
     */
    public void getUserGroups(User user){
        String matricola = user.getMatricola();
        List<Group> dataList = new ArrayList<>();
        PreparedStatement ps = null;
        String query = "SELECT DISTINCT G.* FROM Gruppo G LEFT JOIN Partecipa P ON G.ID_Gruppo = P.ID_Gruppo " +
                       "WHERE P.Matricola = ? OR G.GestoreGruppo = ?";
        try{
            Connection db = DatabaseConnectionManager.createDatabaseConnection();
            ps = db.prepareStatement(query);
            ps.setString(1, matricola);
            ps.setString(2, matricola);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Group gruppo = new Group(resultSet.getString("ID_Gruppo"),resultSet.getString("Nome_Gruppo"),
                                         resultSet.getDate("Data_di_Creazione"),resultSet.getString("Categoria_Gruppo"));
                dataList.add(gruppo);
            }
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        user.setGruppiUtente(dataList);
    }

    /**
     * getAdminGroups
     * Restituisce la lista di gruppi di un utente di cui è creatore
     * @param user
     * @return List<Group> dataList
     */
    public void getAdminGroups(User user){
        String matricola = user.getMatricola();
        List<Group> dataList = new ArrayList<>();
        PreparedStatement ps = null;
        String query = "SELECT G.* FROM Gruppo G WHERE G.GestoreGruppo = ?";
        try{
            Connection db = DatabaseConnectionManager.createDatabaseConnection();
            ps = db.prepareStatement(query);
            ps.setString(1, matricola);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Group gruppo = new Group(resultSet.getString("ID_Gruppo"),resultSet.getString("Nome_Gruppo"),
                        resultSet.getDate("Data_di_Creazione"),resultSet.getString("Categoria_Gruppo"));
                dataList.add(gruppo);
            }
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        user.setGruppiCreati(dataList);
    }

    /**
     * addNewGroup
     * Inserisce un nuovo gruppo nel db
     * @param GroupName nome del gruppo
     * @param GroupCategory categoria del gruppo
     * @param GroupAdmin matricola del creatore del gruppo
     */
    public void addNewGroup(String GroupName, String GroupCategory, String GroupAdmin) {
        PreparedStatement ps = null;
        String query = "INSERT INTO Gruppo (Nome_Gruppo, Data_di_Creazione, Categoria_Gruppo, GestoreGruppo) VALUES (?, ?, ?, ?)";
        try {
            Connection db = DatabaseConnectionManager.createDatabaseConnection();
            ps = db.prepareStatement(query);
            ps.setString(1, GroupName);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setString(3, GroupCategory);
            ps.setString(4, GroupAdmin);
            ps.executeUpdate();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }
}
