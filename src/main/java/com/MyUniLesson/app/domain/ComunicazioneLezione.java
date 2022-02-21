package com.MyUniLesson.app.domain;

import javax.mail.MessagingException;

import static java.lang.Math.abs;

public class ComunicazioneLezione {
    private int codice;
    private FormatoMail formato;
    private Partecipazione partecipazione;

    public ComunicazioneLezione(Partecipazione partecipazione) {
        this.codice = abs((int) System.currentTimeMillis()) + (int)(Math.random()*(10000));
        this.partecipazione = partecipazione;

    }

    public void setFormatoMail(FormatoMail formato) {
        this.formato = formato;
    }

    public void invia() {
        formato.inviaMail(partecipazione);
    }

    public Partecipazione getPartecipazione() {
        return partecipazione;
    }

    public FormatoMail getFormato() {
        return formato;
    }
}