package it.unina.uninaSocialGroup.ViewModel;

import it.unina.uninaSocialGroup.Model.User;
import it.unina.uninaSocialGroup.DAO.AuthenticationDAO;
public class LoginViewModel {
    private AuthenticationDAO authenticationDAO;

    public LoginViewModel() {
        this.authenticationDAO = new AuthenticationDAO();
    }

    public User authenticate(String email, String password) {
        return this.authenticationDAO.authenticate(email, password);
    }
}