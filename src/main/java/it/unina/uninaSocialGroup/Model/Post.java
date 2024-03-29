package it.unina.uninaSocialGroup.Model;

import java.util.List;

public class Post {
    private String IDPost;
    private String Categoria;
    private String MessaggioTestuale;
    private String CreatorePost;
    private List<Comment> Commenti;

    public String getIDPost() {
        return IDPost;
    }

    public void setIDPost(String IDPost) {
        this.IDPost = IDPost;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getMessaggioTestuale() {
        return MessaggioTestuale;
    }

    public void setMessaggioTestuale(String messaggioTestuale) {
        MessaggioTestuale = messaggioTestuale;
    }

    public String getCreatorePost() {
        return CreatorePost;
    }

    public void setCreatorePost(String creatorePost) {
        CreatorePost = creatorePost;
    }

    public List<Comment> getCommenti() {
        return Commenti;
    }

    public void setCommenti(List<Comment> commenti) {
        Commenti = commenti;
    }

    public Post(String IDPost, String categoria, String messaggioTestuale, String creatorePost) {
        this.IDPost = IDPost;
        Categoria = categoria;
        MessaggioTestuale = messaggioTestuale;
        CreatorePost = creatorePost;
    }

    @Override
    public String toString() {
        return "Post{" +
                "IDPost='" + IDPost + '\'' +
                ", Categoria='" + Categoria + '\'' +
                ", MessaggioTestuale='" + MessaggioTestuale + '\'' +
                ", CreatorePost='" + CreatorePost + '\'' +
                '}';
    }
}
