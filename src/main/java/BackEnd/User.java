package BackEnd;

import java.util.Date;
// Generate getters and setters for all fields in a class:

public class User {
    String Nome;
    String Cognome;
    String Matricola;
    String Email;
    String Password;
    Date DataDiNascita;
    Enum Interessi;
    public String getMatricola() {
        return Matricola;
    }

    public void setMatricola(String matricola) {
        Matricola = matricola;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public User(String matricola) {
        Matricola = matricola;
    }

}
