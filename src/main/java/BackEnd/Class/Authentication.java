package BackEnd.Class;

public class Authentication {
    public String Email;
    public String Password;
    public String ConfermaPassword;
    public String NumeroDiTelefono;
    private User Matricola;
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

    public String getConfermaPassword() {
        return ConfermaPassword;
    }

    public void setConfermaPassword(String confermaPassword) {
        ConfermaPassword = confermaPassword;
    }

    public String getNumeroDiTelefono() {
        return NumeroDiTelefono;
    }

    public void setNumeroDiTelefono(String numeroDiTelefono) {
        NumeroDiTelefono = numeroDiTelefono;
    }

    public Authentication(String email, String password, String confermaPassword, String numeroDiTelefono) {
        Email = email;
        Password = password;
        ConfermaPassword = confermaPassword;
        NumeroDiTelefono = numeroDiTelefono;
    }
}
