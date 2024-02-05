package it.unina.uninaSocialGroup.Model;

import java.util.Date;
import java.util.List;
// Generate getters and setters for all fields in a class:

public class User {
    private String Nome;
    private String Cognome;
    private String Matricola;
    private Date DataDiNascita;
    private Date DataDiRegistazione;
    private List<Group> OwnerGroups;
    private List<Group> UserGroups;
    private List<Post> PostPubblicati;
    private List<User> Amici;


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

    public Date getDataDiRegistazione() {
        return DataDiRegistazione;
    }

    public void setDataDiRegistazione(Date dataDiRegistazione) {
        DataDiRegistazione = dataDiRegistazione;
    }

    public User(String nome, String cognome, String matricola, Date dataDiNascita, Date dataDiRegistazione) {
        Nome = nome;
        Cognome = cognome;
        Matricola = matricola;
        DataDiNascita = dataDiNascita;
        DataDiRegistazione = dataDiRegistazione;
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
                ", DataDiRegistazione=" + DataDiRegistazione +
                '}';
    }
}
