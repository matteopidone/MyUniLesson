package com.MyUniLesson.app.domain;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class AnnullamentoStrategy implements FormatoMail {
    @Override
    public void inviaMail(Partecipazione p) throws MessagingException {
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
        String oggetto = "MyUniLesson: Annullamento lezione di " + p.getLezione().getInsegnamento().getNome();
        String testo ="Il docente ti comunica l'annullamento della lezione di giorno " + p.getLezione().getData() + "\n\nBuono studio.";
        Message message = prepareMessage(session, myAccount, p.getStudente().getEmail(), oggetto, testo);

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
