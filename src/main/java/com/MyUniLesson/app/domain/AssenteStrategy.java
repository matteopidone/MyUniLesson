package com.MyUniLesson.app.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AssenteStrategy implements FormatoMail {

    Map<Integer, String> giorniSettimana = new HashMap<Integer, String>();

    @Override
    public void inviaMail(Partecipazione p) {

        giorniSettimana.put(0, "Domenica");
        giorniSettimana.put(1, "Lunedi");
        giorniSettimana.put(2, "Martedi");
        giorniSettimana.put(3, "Mercoledi");
        giorniSettimana.put(4, "Giovedi");
        giorniSettimana.put(5, "Venerdi");
        giorniSettimana.put(6, "Sabato");

        String mail = p.getStudente().getEmail();
        String oggetto = "MyUniLesson: Comunicazione Lezione " + p.getLezione().getCodice();
        String testo = "Il giorno " + myData(p) + " sei risultato ASSENTE alla lezione ( Codice " + p.getLezione().getCodice() + " ) di " + p.getLezione().getInsegnamento().getNome() + "\n\nBuono Studio";

        Thread myThread = new MailThread(mail, oggetto, testo);
        myThread.start();
    }

    private String myData(Partecipazione p) {
        Lezione l = p.getLezione();
        return giorniSettimana.get(p.getLezione().getData().getDay()) + " - " + LocalDate.of(l.getData().getYear() + 1900, l.getData().getMonth() + 1, l.getData().getDate()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "   " + LocalTime.of(l.getData().getHours(), l.getData().getMinutes()) + " - " + LocalTime.of(l.getData().getHours() + l.getDurata(), l.getData().getMinutes());
    }
}
