package it.unina.uninaSocialGroup.esperimenti;

import it.unina.uninaSocialGroup.Model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class Notifica {
    private String titolo;
    private String messaggio;
    private LocalDateTime dataOra;
    private User destinatario;

    public static void main(String[] args) {
        Notifica notifica = new Notifica("Fan di Boris", "Un nuovo post è stato publicato!", java.time.LocalDateTime.now());
    try {
        notifica.displayTray();
    } catch (AWTException e) {
        e.printStackTrace();
    }
}
    public void displayTray() throws AWTException {
        if (SystemTray.isSupported() &&  !System.getProperty("os.name").toLowerCase().contains("mac")) {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/it/unina/uninaSocialGroup/images/icon.png"));
            TrayIcon trayIcon = new TrayIcon(image,
                    "Forza Napoli!");

            trayIcon.setImageAutoSize(true);

            tray.add(trayIcon);

            trayIcon.displayMessage(titolo, messaggio, MessageType.NONE);
        } else {
            sendMacNotification(titolo, messaggio);
        }
    }
    public void sendMacNotification(String title, String message) {
        String script = "display notification \""+ message + "\" with title \"" + title + "\" sound name \"default\"";
        String[] cmd = { "osascript", "-e", script };
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Notifica(String titolo, String messaggio, LocalDateTime dataOra/*, User destinatario*/) {
        this.titolo = titolo;
        this.messaggio = messaggio;
        this.dataOra = dataOra;
        //this.destinatario = destinatario;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public User getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(User destinatario) {
        this.destinatario = destinatario;
    }
}