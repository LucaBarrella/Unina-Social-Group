package it.unina.uninaSocialGroup.Boundary;

import it.unina.uninaSocialGroup.DAO.*;
import it.unina.uninaSocialGroup.Model.*;
import javafx.collections.ObservableList;

import java.util.List;

public class LogicalController {
    private static String userEmail;
    private static String groupID;
    AuthenticationDAO authenticateDAO;
    UserDAO userDAO;
    GroupDAO groupDAO;
    ReportDAO reportDAO;
    PostDAO postDAO;
    CommentDAO commentDAO;
    private static Post post;
    private static Group group;
    private static User user;
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

    public void setUser(String email){
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

    public String getGroupID(Group group){
        return group.getIDGruppo();
    }

    public String getGroupName(){
        return group.getNomeGruppo();
    }

    public String getGroupCategory(){
        return group.getCategoriaGruppo();
    }

    public void setGroup(Group group){
        this.group = group;
    }

    public List<Group> getGroupsBySF(String searchField){
        List<Group> groups = groupDAO.getGroupsBySearchField(searchField);
        return groups;
    }

    public boolean isUserMemberOfGroup(List<User> members){
        boolean isUserInMembers = false;
        String currentUserName = getNameUser() + " " + getSurnameUser();
        for (User member : members) {
            String memberName = member.getNome() + " " + member.getCognome();
            if (memberName.equals(currentUserName)) {
                isUserInMembers = true;
                break;
            }
        }
        return isUserInMembers;
    }

    public void removeMember(){
        groupDAO.RemoveMemberToGroup(group, user.getMatricola());
    }

    public List<User> getGroupMembers(){
        groupDAO.getGroupMembers(group);
        List<User> members = group.getListaPartecipanti();
        return members;
    }

    public void createPost(String text){
        String category = group.getCategoriaGruppo();
        String matricola = getMatricolaUser();
        String groupId = group.getIDGruppo();
        postDAO.CreateNewPost(category,text,matricola,groupId);
    }

    public int numberOfMembers(){
        return groupDAO.getNumberOfMemberGroup(group.getIDGruppo());
    }

    public List<Post> ListPosts(){
        List<Post> posts;
        postDAO.getAllPosts(group);
        posts = group.getPostPubblicati();
        return posts;
    }

    public void NewGroup(String NameGroup, String category){
        groupDAO.addNewGroup(NameGroup,category,getMatricolaUser());
    }

    public void setPost(Post post){
        this.post = post;
    }

    public String getCommentAuthor(Comment comment){
        return comment.getAutoreCommento();
    }

    public String getCommentContent(Comment comment){
        return comment.getTesto();
    }

    public void createComment(String commentText){
        commentDAO.createNewComment(commentText,getMatricolaUser(),post.getIDPost());
    }

    public String getPostAuthor(){
        return post.getCreatorePost();
    }

    public String getPostContent(){
        return post.getMessaggioTestuale();
    }

    public String getPostID(){
        return post.getIDPost();
    }

    public int numberOfLike(){
        return postDAO.getNumberOfLike(getPostID());
    }

    public int numberOfComment(){
        return postDAO.getNumberOfComment(getPostID());
    }

    public boolean CheckStatusLike(){
        return postDAO.isLikeAlreadyAdd(getMatricolaUser(),getPostID());
    }

    public void addLike(){
        postDAO.addLike(getMatricolaUser(),getPostID());
    }

    public void removeLike(){
        postDAO.removeLike(getMatricolaUser(), getPostID());
    }

    public List<Comment> ListComments(){
        List<Comment> comments;
        commentDAO.getCommentByPost(post);
        comments = post.getCommenti();
        return comments;
    }

    public void JoinGroup(){
        groupDAO.addNewMemberToGroup(group, getMatricolaUser());
    }
}
