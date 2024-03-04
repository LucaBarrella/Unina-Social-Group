package it.unina.uninaSocialGroup.Model;

import java.sql.Time;

public class Comment {
    private String ID_Commento;
    private String ID_Post;
    private String AutoreCommento;
    private String Testo;
    private Time OraPubblicazione;

    public Comment(String ID_Commento, String ID_Post, String AutoreCommento, String Testo, Time Ora) {
        this.ID_Commento = ID_Commento;
        this.ID_Post = ID_Post;
        this.AutoreCommento = AutoreCommento;
        this.Testo = Testo;
        this.OraPubblicazione = Ora;
    }

    public String getIDCommento() {
        return ID_Commento;
    }

    public void setIDCommento(String ID_Commento) {
        this.ID_Commento = ID_Commento;
    }

    public String getIDPost() {
        return ID_Post;
    }

    public void setIDPost(String ID_Post) {
        this.ID_Post = ID_Post;
    }

    public String getAutoreCommento() {
        return AutoreCommento;
    }

    public void setAutoreCommento(String autoreCommento) {
        AutoreCommento = autoreCommento;
    }

    public String getTesto() {
        return Testo;
    }

    public void setTesto(String testo) {
        Testo = testo;
    }

    public Time getOraPubblicazione() {
        return OraPubblicazione;
    }

    public void setOraPubblicazione(Time ora) {
        OraPubblicazione = ora;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "ID_Commento='" + ID_Commento + '\'' +
                ", ID_Post='" + ID_Post + '\'' +
                ", AutoreCommento='" + AutoreCommento + '\'' +
                ", Testo='" + Testo + '\'' +
                ", Ora='" + OraPubblicazione + '\'' +
                '}';
    }
}
