package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.*;
import it.unina.uninaSocialGroup.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class LogicalController {
    private static String userEmail;
    private static User user;
    private static Group group;

    //TODO: Al momento provo con questi sopra, poi casomai implemento ulteriormente;

    //    private static Post post;
    //    private static Comment comment;
    AuthenticationDAO authenticateDAO;
    UserDAO userDAO;
    GroupDAO groupDAO;
    ReportDAO reportDAO;
    PostDAO postDAO;

    public LogicalController() {
        this.authenticateDAO = new AuthenticationDAO();
        this.userDAO = new UserDAO();
        this.groupDAO = new GroupDAO();
        this.reportDAO = new ReportDAO();
        this.postDAO = new PostDAO();
    }

    public static Group getGroup() {
        return group;
    }

    public boolean checkCredentials(String email, String password) {
        return authenticateDAO.CheckCredentials(email, password);
    }

    public void setUser(String email) {
        this.userEmail = email;
        user = userDAO.getUserByEmail(email);
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

    public List<Group> getGroupsBySF(String searchField) {
        List<Group> groups = groupDAO.getGroupsBySearchField(searchField);
        return groups;
    }

    public List<Group> getUserGroups() {
        groupDAO.getUserGroups(user);
        List<Group> dati = user.getGruppiUtente();
        return dati;
    }

    public List<Group> getAdminGroups() {
        groupDAO.getAdminGroups(user);
        List<Group> dati = user.getGruppiCreati();
        return dati;
    }

    public List<Report> getReportGroups(Integer Month) {
        reportDAO.getGroupsReport(user, Month);
        List<Report> dati = user.getReportMensili();
        return dati;
    }

    public void NewGroup(String NameGroup, String category) {
        groupDAO.addNewGroup(NameGroup, category, getMatricolaUser());
    }

    public void removeMember() {
        groupDAO.RemoveMemberToGroup(group, user.getMatricola());
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setGroup(Group group, String searchField) {
        this.group = group;
    }

    public String getGroupID() {
        return group.getIDGruppo();
    }

    public String getGroupName() {
        return group.getNomeGruppo();
    }

    public List<User> getGroupMembers() {
        groupDAO.getGroupMembers(group);
        List<User> members = group.getListaPartecipanti();
        return members;
    }

    public void createPost(String text) {
        String category = group.getCategoriaGruppo();
        String matricola = getMatricolaUser();
        String groupId = group.getIDGruppo();
        postDAO.CreateNewPost(category, text, matricola, groupId);
    }

    public int numberOfMembers() {
        return groupDAO.getNumberOfMemberGroup(group.getIDGruppo());
    }

    public List<Post> ListPosts() {
        List<Post> posts;
        postDAO.getAllPosts(group);
        posts = group.getPostPubblicati();
        return posts;
    }

    public boolean isUserMemberOfGroup() {
        boolean isUserInMembers = false;
        isUserInMembers = groupDAO.isUserMemberOfGroup(group, getMatricolaUser());
        return isUserInMembers;
    }

    public void JoinGroup() {
        groupDAO.addNewMemberToGroup(group, getMatricolaUser());
    }

    public String getGroupCategory() {
        return group.getCategoriaGruppo();
    }

    public ObservableList<Group> getGroupsBySearchField(String searchFieldText) {
        //Prende i gruppi che corrispondono alla stringa di ricerca.
        return FXCollections.observableArrayList(groupDAO.getGroupsBySearchField(searchFieldText));
    }
}