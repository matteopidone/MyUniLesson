package com.MyUniLesson.app.domain;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static java.lang.Math.abs;

public class ComunicazioneLezione {
    private int codice;
    private String oggetto;
    private String testo;
    private Partecipazione partecipazione;

    public ComunicazioneLezione(Partecipazione partecipazione) throws MessagingException {
        this.codice = abs((int) System.currentTimeMillis());
        this.oggetto = "MyUniLesson: Comunicazione Lezione " + partecipazione.getLezione().getCodice();
        this.testo = "Nel giorno " + partecipazione.getLezione().getData() +
                " sei risultato assente alla lezione di " + partecipazione.getLezione().getInsegnamento().getNome() +
                " avente codice " + partecipazione.getLezione().getCodice() + "\n\nCordiali Saluti";

        this.partecipazione = partecipazione;
        inviaMail(partecipazione.getStudente().getEmail());
    }


    public void inviaMail(String recipient) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        final String myAccount = "noreplymyunilesson@gmail.com";
        final String psw = "S!P4cZf-6!CzeCe";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, psw);
            }
        });

        Message message = prepareMessage(session, myAccount, partecipazione.getStudente().getEmail(), oggetto, testo);

        Transport.send(message);
    }

    private Message prepareMessage(Session session, String myAccount, String recipient, String oggetto, String testo) throws MessagingException{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccount));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(oggetto);
        message.setText(testo);
        return message;

    }
}