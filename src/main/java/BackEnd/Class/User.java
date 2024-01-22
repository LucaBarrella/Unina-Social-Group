package BackEnd.Class;

import java.util.Date;
import java.util.List;
// Generate getters and setters for all fields in a class:

public class User {
    public String Nome;
    public String Cognome;
    public String Matricola;
    public String Email;
    private String Password;
    public Date DataDiNascita;
    public Date DataDiRegistazione;
    private List<Group> OwnerGroups;
    private List<Group> UserGroups;
    private List<Post> PostPubblicati;
    private List<User> Amici;
    public Report report;

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public User(String nome, String cognome, String matricola, String email, String password, Date dataDiNascita, Date dataDiRegistazione) {
        Nome = nome;
        Cognome = cognome;
        Matricola = matricola;
        Email = email;
        Password = password;
        DataDiNascita = dataDiNascita;
        DataDiRegistazione = dataDiRegistazione;
    }
}
