package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.AuthenticationDAO;
import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.ReportDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.Report;
import it.unina.uninaSocialGroup.Model.User;

import java.util.Date;
import java.util.List;

public class LogicalController {
    private String userEmail;
    AuthenticationDAO authenticateDAO;
    UserDAO userDAO;
    GroupDAO groupDAO;
    ReportDAO reportDAO;
    Group group;
    private static User user;
    public LogicalController() {
        this.authenticateDAO = new AuthenticationDAO();
        this.userDAO = new UserDAO();
        this.groupDAO = new GroupDAO();
        this.reportDAO = new ReportDAO();
    }
    public boolean checkCredentials(String email, String password) {
        return authenticateDAO.CheckCredentials(email, password);
    }

    public boolean signIn(String email, String password){
        //Se non viene scritto nulla nel campo Email o Password, si mostra un messaggio di avvertenza
        if(email.isEmpty() || password.isEmpty()){
            return false;
        }
        return checkCredentials(email, password);
    }

    public void setUserEmail(String email){
        this.userEmail = email;
    }


    public String getMatricolaUser() {
        return user.getMatricola();
    }


    public String getEmailUser() {
        return userEmail;
    }

    public String getNameUser() {
        return user.getNome();
    }

    public String getSurnameUser() {
        return user.getCognome();
    }

    public String getBirthDateUser() {
        return user.getDataDiNascita().toString();
    }

    public String getRegistrationDateUser() {
        return user.getDataDiRegistrazione().toString();
    }

    public List<Group> getUserGroups(){
        groupDAO.getUserGroups(user);
        List<Group> dati = user.getGruppiUtente();
        return dati;
    }

    public List<Group> getAdminGroups(){
        groupDAO.getAdminGroups(user);
        List<Group> dati = user.getGruppiCreati();
        return dati;
    }

    public List<Report> getReportGroups(Integer Month){
        reportDAO.getGroupsReport(user, Month);
        List<Report> dati = user.getReportMensili();
        return dati;
    }

    public String getGroupName(){
        return group.getNomeGruppo();
    }

    public String getGroupCategory(){
        return group.getCategoriaGruppo();
    }
}
