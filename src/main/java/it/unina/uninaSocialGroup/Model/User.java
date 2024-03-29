package it.unina.uninaSocialGroup.Model;

import java.util.Date;
import java.util.List;

public class User {
    private String Nome;
    private String Cognome;
    private String Matricola;
    private Date DataDiNascita;
    private Date DataDiRegistrazione;
    private List<Group> GruppiCreati;
    private List<Group> GruppiUtente;
    private List<Post> PostPubblicati;
    private List<Report> ReportMensili;


    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getMatricola() {
        return Matricola;
    }

    public void setMatricola(String matricola) {
        Matricola = matricola;
    }

    public Date getDataDiNascita() {
        return DataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        DataDiNascita = dataDiNascita;
    }

    public Date getDataDiRegistrazione() {
        return DataDiRegistrazione;
    }

    public void setDataDiRegistrazione(Date dataDiRegistrazione) {
        DataDiRegistrazione = dataDiRegistrazione;
    }

    public List<Group> getGruppiCreati() {
        return GruppiCreati;
    }

    public void setGruppiCreati(List<Group> gruppiCreati) {
        GruppiCreati = gruppiCreati;
    }

    public List<Group> getGruppiUtente() {
        return GruppiUtente;
    }

    public void setGruppiUtente(List<Group> gruppiUtente) {
        GruppiUtente = gruppiUtente;
    }

    public List<Post> getPostPubblicati() {
        return PostPubblicati;
    }

    public void setPostPubblicati(List<Post> postPubblicati) {
        PostPubblicati = postPubblicati;
    }

    public List<Report> getReportMensili() {
        return ReportMensili;
    }

    public void setReportMensili(List<Report> reportMensili) {
        ReportMensili = reportMensili;
    }

    public User(String nome, String cognome, String matricola, Date dataDiNascita, Date dataDiRegistrazione) {
        Nome = nome;
        Cognome = cognome;
        Matricola = matricola;
        DataDiNascita = dataDiNascita;
        DataDiRegistrazione = dataDiRegistrazione;
    }

    public User(String nome, String cognome) {
        Nome = nome;
        Cognome = cognome;
    }

    @Override
    public String toString() {
        return "" + Nome + " " + Cognome;
    }

}
