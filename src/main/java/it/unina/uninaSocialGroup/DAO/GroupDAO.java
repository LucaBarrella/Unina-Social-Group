package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupDAO {
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
                Date dataCreazione = new java.util.Date(resultSet.getDate("data_Di_Creazione").getTime());
                String idGruppo = resultSet.getString("ID_Gruppo");
                Group group = new Group(idGruppo, nomeGruppo, dataCreazione, categoriaGruppo);
                listGroups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listGroups;
    }

    public List<Group> getUserGroups(String matricola){
        List<Group> dataList = new ArrayList<>();
        PreparedStatement ps = null;
        String query = "SELECT G.* FROM Gruppo G LEFT JOIN Partecipa P ON G.ID_Gruppo = P.ID_Gruppo " +
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
        return dataList;
    }
    public List<Group> getAdminGroups(String matricola){
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
        return dataList;
    }

    public void CreateNewGroup(String Nome, String Categoria, String Matricola){
        Connection connect = null;
        String query = "INSERT INTO Gruppo (ID_Gruppo,Nome_Gruppo,Data_di_Creazione,Categoria_Gruppo,GestoreGruppo) VALUES (NULL,?,current_date,?,?)";
        PreparedStatement psInsert = null;
        try {
            connect = DatabaseConnectionManager.createDatabaseConnection();
            psInsert = connect.prepareStatement(query);
            psInsert.setString(1, Nome);
            psInsert.setString(2, Categoria);
            psInsert.setString(3, Matricola);
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
