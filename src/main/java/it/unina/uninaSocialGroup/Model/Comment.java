package it.unina.uninaSocialGroup.Model;

public class Comment {
    private String ID_Commento;
    private String ID_Post;
    private String Matricola;
    private String Testo;
    private String DataOra;

    public Comment(String ID_Commento, String ID_Post, String Matricola, String Testo, String DataOra) {
        this.ID_Commento = ID_Commento;
        this.ID_Post = ID_Post;
        this.Matricola = Matricola;
        this.Testo = Testo;
        this.DataOra = DataOra;
    }

    public String getID_Commento() {
        return ID_Commento;
    }

    public void setID_Commento(String ID_Commento) {
        this.ID_Commento = ID_Commento;
    }

    public String getID_Post() {
        return ID_Post;
    }

    public void setID_Post(String ID_Post) {
        this.ID_Post = ID_Post;
    }

    public String getMatricola() {
        return Matricola;
    }

    public void setMatricola(String matricola) {
        Matricola = matricola;
    }

    public String getTesto() {
        return Testo;
    }

    public void setTesto(String testo) {
        Testo = testo;
    }

    public String getDataOra() {
        return DataOra;
    }

    public void setDataOra(String dataOra) {
        DataOra = dataOra;
    }
}
