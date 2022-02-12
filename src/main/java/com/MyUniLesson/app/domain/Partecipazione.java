package com.MyUniLesson.app.domain;

import java.util.Observable;

public class Partecipazione extends Observable {
    private Studente studente;
    private StatoPartecipazione statoPartecipazione;

    public Partecipazione(Studente studente) {
        this.studente = studente;
        //Setta la partecipazione a pendente!
    }

    private void aggiornaPartecipazione(boolean presenza){

    }

    //Getters and Setters

    public String getMatricolaStudente() {

        return studente.getMatricola();
    }

    //public void setStato(StatoPartecipazione statoPartecipazione) {
      //  this.statoPartecipazione = statoPartecipazione;
    //}

    public Studente getStudente() {
        return studente;
    }

    @Override
    public String toString() {
        return "Partecipazione{" +
                "studente=" + studente +
                '}';
    }
}
