package it.unina.uninaSocialGroup.Model;

public class Authentication {
    private String Email;
    private String Password;

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

    public Authentication(String email, String password) {
        Email = email;
        Password = password;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "Email='" + Email + '\'' +
                ", Password='" + Password + '}';
    }
}
