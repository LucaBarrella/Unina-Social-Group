package it.unina.uninaSocialGroup;

import it.unina.uninaSocialGroup.DAO.*;
import it.unina.uninaSocialGroup.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LogicalController {
    private static String userEmail;
    private static User user;
    private static Group group;
    private static Post post;
    private static Comment comment;

    AuthenticationDAO authenticateDAO;
    UserDAO userDAO;
    GroupDAO groupDAO;
    ReportDAO reportDAO;
    PostDAO postDAO;
    CommentDAO commentDAO;

    public LogicalController() {
        this.authenticateDAO = new AuthenticationDAO();
        this.userDAO = new UserDAO();
        this.groupDAO = new GroupDAO();
        this.reportDAO = new ReportDAO();
        this.postDAO = new PostDAO();
        this.commentDAO = new CommentDAO();
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

    public void setPost(Post post) {
        this.post = post;
    }

    public String getNumberOfLike() {
        return String.valueOf(postDAO.getNumberOfLike(post.getIDPost()));
    }

    public String getNumberOfComments() {
        return String.valueOf(commentDAO.getNumberOfComment(post.getIDPost()));
    }

    public String getAuthor() {
        return post.getCreatorePost();
    }

    public String getPostContent() {
        return post.getMessaggioTestuale();
    }

    public boolean isLikeAlreadyAdd() {
        return postDAO.isLikeAlreadyAdd(user.getMatricola(), post.getIDPost());
    }

    public void removeLike() {
        postDAO.removeLike(user.getMatricola(), post.getIDPost());
    }

    public void addLike() {
        postDAO.addLike(user.getMatricola(), post.getIDPost());
    }

    public List<Comment> ListComments() {
        commentDAO.getCommentByPost(post);
        return post.getCommenti();
    }

    public String getAuthorOfComment() {
        commentDAO.getCommentByPost(post);
        return comment.getAutoreCommento();
    }

    public String getCommentContent() {
        commentDAO.getCommentByPost(post);
        return comment.getTesto();
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void createNewComment(String text) {
        commentDAO.createNewComment(text, user.getMatricola(), post.getIDPost());
    }
}