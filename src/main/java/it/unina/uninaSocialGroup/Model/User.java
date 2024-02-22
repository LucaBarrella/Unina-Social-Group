package it.unina.uninaSocialGroup.Model;

import java.util.Date;
import java.util.List;
// Generate getters and setters for all fields in a class:

public class User {
    private String Nome;
    private String Cognome;
    private String Matricola;
    private Date DataDiNascita;
    private Date DataDiRegistrazione;
    private List<Group> OwnerGroups;
    private List<Group> UserGroups;
    private List<Post> PostPubblicati;


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

    public List<Group> getOwnerGroups() {
        return OwnerGroups;
    }

    public void setOwnerGroups(List<Group> ownerGroups) {
        OwnerGroups = ownerGroups;
    }

    public List<Group> getUserGroups() {
        return UserGroups;
    }

    public void setUserGroups(List<Group> userGroups) {
        UserGroups = userGroups;
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
        return "User{" +
                "Nome='" + Nome + '\'' +
                ", Cognome='" + Cognome + '\'' +
                ", Matricola='" + Matricola + '\'' +
                ", DataDiNascita=" + DataDiNascita +
                ", DataDiRegistazione=" + DataDiRegistrazione +
                '}';
    }
}
