package BackEnd.Class;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Notification {
    public String Messaggio;
    public LocalDate DataInvio;
    public LocalTime OraInvio;
    private List<User> UserNotificati;

    public String getMessaggio() {
        return Messaggio;
    }

    public void setMessaggio(String messaggio) {
        Messaggio = messaggio;
    }

    public LocalDate getDataInvio() {
        return DataInvio;
    }

    public void setDataInvio(LocalDate dataInvio) {
        DataInvio = dataInvio;
    }

    public LocalTime getOraInvio() {
        return OraInvio;
    }

    public void setOraInvio(LocalTime oraInvio) {
        OraInvio = oraInvio;
    }

    public Notification(String messaggio, LocalDate dataInvio, LocalTime oraInvio) {
        Messaggio = messaggio;
        DataInvio = dataInvio;
        OraInvio = oraInvio;
    }
}
