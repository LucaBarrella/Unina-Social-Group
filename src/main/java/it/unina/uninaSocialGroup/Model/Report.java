package it.unina.uninaSocialGroup.Model;

public class Report {
    private String NomeGruppo;
    private String PostPiuLike;
    private String PostMenoLike;
    private String PostPiuCommenti;
    private String PostMenoCommenti;
    private int NumMedioPost;

    public String getNomeGruppo() {
        return NomeGruppo;
    }

    public void setNomeGruppo(String nomeGruppo) {
        NomeGruppo = nomeGruppo;
    }

    public String getPostPiuLike() {
        return PostPiuLike;
    }

    public void setPostPiuLike(String postPiuLike) {
        PostPiuLike = postPiuLike;
    }

    public String getPostMenoLike() {
        return PostMenoLike;
    }

    public void setPostMenoLike(String postMenoLike) {
        PostMenoLike = postMenoLike;
    }

    public String getPostPiuCommenti() {
        return PostPiuCommenti;
    }

    public void setPostPiuCommenti(String postPiuCommenti) {
        PostPiuCommenti = postPiuCommenti;
    }

    public String getPostMenoCommenti() {
        return PostMenoCommenti;
    }

    public void setPostMenoCommenti(String postMenoCommenti) {
        PostMenoCommenti = postMenoCommenti;
    }

    public int getNumMedioPost() {
        return NumMedioPost;
    }

    public void setNumMedioPost(int numMedioPost) {
        NumMedioPost = numMedioPost;
    }

    public Report(String nomeGruppo, String postPiuLike, String postMenoLike, String postPiuCommenti, String postMenoCommenti, int numMedioPost) {
        NomeGruppo = nomeGruppo;
        PostPiuLike = postPiuLike;
        PostMenoLike = postMenoLike;
        PostPiuCommenti = postPiuCommenti;
        PostMenoCommenti = postMenoCommenti;
        NumMedioPost = numMedioPost;
    }

    public Report() {
    }
}
