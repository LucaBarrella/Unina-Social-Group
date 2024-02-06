package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    public List<Group> getUserGroups(String matricola){
        List<Group> dataList = new ArrayList<>();
        PreparedStatement ps = null;
        String query = "SELECT G.* FROM Gruppo G LEFT JOIN Partecipa P ON G.ID_Gruppo = P.ID_Gruppo" +
                       "WHERE P.Matricola = ? OR G.GestoreGruppo = ?";
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
}
