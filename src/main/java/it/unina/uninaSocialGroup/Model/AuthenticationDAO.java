package it.unina.uninaSocialGroup.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthenticationDAO extends Authentication {
    private Connection connect = DatabaseConnectionManager.createDatabaseConnection();
    public AuthenticationDAO(String email, String password, String confermaPassword, String numeroDiTelefono) {
        super(email, password, confermaPassword, numeroDiTelefono);
    }

    public boolean CheckEmailPassword(String email,String password) throws SQLException {
        Authentication credentials = null;
        String query = "SELECT * FROM \"Autenticazione\" WHERE Email = ? AND Password = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try{
            ps = connect.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2,password);
            resultSet = ps.executeQuery();
            while (!resultSet.next()){
                return false;
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }finally{
            resultSet.close();
            ps.close();
            return true;
        }
    }
}
