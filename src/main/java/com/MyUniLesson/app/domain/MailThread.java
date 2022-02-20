package com.MyUniLesson.app.domain;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailThread extends Thread {
    private String mail;
    private String oggetto;
    private String testo;

    public MailThread(String mail, String oggetto, String testo) {
        this.mail = mail;
        this.oggetto = oggetto;
        this.testo = testo;
    }

    @Override
    public void run() {
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

        try {
            Message message = prepareMessage(session, myAccount, mail, oggetto, testo);
            Transport.send(message);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private Message prepareMessage(Session session, String myAccount, String recipient, String oggetto, String testo) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccount));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(oggetto);
        message.setText(testo);
        return message;
    }

}
