package com.MyUniLesson.app.domain;

import java.util.Observable;
import java.util.Observer;

public class PresenzeObserver implements Observer {         //VAI A REGISTRARE L'OBSERVER ALLA LEZIONE.
    private Lezione lezione;

    public PresenzeObserver(Lezione lezione) {  // DA VEDERE!
        this.lezione = lezione;
    }

    @Override
    public void update(Observable o, Object arg) {
        Partecipazione p=(Partecipazione) o;
        String matricola= p.getMatricolaStudente();
        if(p.getStatoPartecipazione().getClass()==StatoPresente.class){
            lezione.registraPresenza(matricola, p);
        }else if(p.getStatoPartecipazione().getClass()==StatoAssente.class){
            lezione.registraAssenza(matricola, p);
        }
    }
}
