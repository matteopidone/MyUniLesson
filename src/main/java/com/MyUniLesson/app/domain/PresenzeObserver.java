package com.MyUniLesson.app.domain;

import javax.mail.MessagingException;
import java.util.Observable;
import java.util.Observer;

public class PresenzeObserver implements Observer {
    private Lezione lezione;

    public PresenzeObserver(Lezione lezione) {
        this.lezione = lezione;
    }

    @Override
    public void update(Observable o, Object arg) {
        Partecipazione p = (Partecipazione) o;
        String matricola = p.getMatricolaStudente();
        if (p.getStatoPartecipazione().getClass() == StatoPresente.class) {
            lezione.registraPresenza(matricola, p);
        } else if (p.getStatoPartecipazione().getClass() == StatoAssente.class) {
            lezione.registraAssenza(matricola, p);
        }
    }
}
