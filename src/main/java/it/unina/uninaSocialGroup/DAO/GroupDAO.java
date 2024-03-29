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
    private static Connection connect = DatabaseConnectionManager.createDatabaseConnection();

    /**
     * getGroupsBySearchField
     * Restituisce una lista di gruppi filtrati per la barra di ricerca
     * @param searchBarFieldText testo scritto nella barra di ricerca
     * @return List<Group> listGroups
     */
    public List<Group> getGroupsBySearchField(String searchBarFieldText) {
        List<Group> listGroups = new ArrayList<>();

        try{
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM gruppo WHERE LOWER(TRIM(categoria_Gruppo)) LIKE LOWER(?) OR LOWER(TRIM(nome_Gruppo)) LIKE LOWER(?)");
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
            ps = connect.prepareStatement(query);
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
            ps = connect.prepareStatement(query);
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
            ps = connect.prepareStatement(query);
            ps.setString(1, GroupName);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setString(3, GroupCategory);
            ps.setString(4, GroupAdmin);
            ps.executeUpdate();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    /**
     * getNumberOfMemberGroup
     * Restituisce il numero di membri di un gruppo
     * @param IDGroup id del gruppo
     * @return numberOfMember
     */
    public int getNumberOfMemberGroup(String IDGroup){
        int numberOfMember = 0;
        PreparedStatement ps = null;
        String query = "SELECT COUNT(*) AS NumeroMembri " +
                            "FROM (" +
                            "SELECT P.Matricola " +
                            "FROM Partecipa P " +
                            "WHERE P.ID_Gruppo = ? " +
                            "UNION " +
                            "SELECT G.GestoreGruppo " +
                            "FROM Gruppo G " +
                            "WHERE G.ID_Gruppo = ? " +
                            ") AS Membri;";
        try{
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setString(2, IDGroup);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                numberOfMember = resultSet.getInt("NumeroMembri");
            }
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        return numberOfMember;
    }

    /**
     * getGroupMembers
     * Restituisce i membri di un gruppo
     * @param group
     */
    public void getGroupMembers(Group group){
        String IDGroup = group.getIDGruppo();
        List<User> dataList = new ArrayList<>();
        PreparedStatement ps = null;
        String query = "SELECT U.Nome, U.Cognome " +
                        "FROM Utente U " +
                        "WHERE U.Matricola IN (" +
                        "SELECT P.Matricola " +
                        "FROM Partecipa P " +
                        "WHERE P.ID_Gruppo = ?) " +
                        "OR U.Matricola IN (" +
                        "SELECT G.GestoreGruppo " +
                        "FROM Gruppo G " +
                        "WHERE G.ID_Gruppo = ? " +
                        ");";
        try{
            ps = connect.prepareStatement(query);
            ps.setString(1, IDGroup);
            ps.setString(2, IDGroup);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                User user = new User(resultSet.getString("Nome"),resultSet.getString("Cognome"));
                dataList.add(user);
            }
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        group.setListaPartecipanti(dataList);
    }

    /**
     * addNewMemberToGroup
     * Aggiunge un nuovo membro ad un gruppo
     * @param group
     * @param Matricola
     */
    public void addNewMemberToGroup(Group group, String Matricola){
        String query = "INSERT INTO Partecipa (Matricola, ID_Gruppo) VALUES (?, ?)";
        PreparedStatement psInsert = null;
        try {
            psInsert = connect.prepareStatement(query);
            psInsert.setString(1, Matricola);
            psInsert.setString(2, group.getIDGruppo());
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * isUserMemberOfGroup
     * Controlla se l'utente fa parte del gruppo
     * @param group
     * @param matricola
     * @return true or false
     */
    public boolean isUserMemberOfGroup(Group group, String matricola){
        String query = "SELECT U.Matricola, U.Nome, U.Cognome " +
                "FROM Utente U " +
                "WHERE U.Matricola = ? AND " +
                "(EXISTS (SELECT 1 FROM Partecipa P WHERE P.Matricola = U.Matricola AND P.ID_Gruppo = ?) OR " +
                "EXISTS (SELECT 1 FROM Gruppo G WHERE G.GestoreGruppo = U.Matricola AND G.ID_Gruppo = ?))";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        String idgroup = group.getIDGruppo();
        try {
            ps = connect.prepareStatement(query);
            ps.setString(1, matricola);
            ps.setString(2, idgroup);
            ps.setString(3, idgroup);
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

    /**
     * RemoveMemberToGroup
     * Rimuove un membro da un gruppo
     * @param group
     * @param Matricola
     */
    public void RemoveMemberToGroup(Group group, String Matricola){
        String query = "DELETE FROM Partecipa WHERE Matricola = ? AND ID_Gruppo = ?";
        PreparedStatement psInsert = null;
        try {
            psInsert = connect.prepareStatement(query);
            psInsert.setString(1, Matricola);
            psInsert.setString(2, group.getIDGruppo());
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
