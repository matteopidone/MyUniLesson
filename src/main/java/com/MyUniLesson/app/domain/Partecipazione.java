package com.MyUniLesson.app.domain;

import java.util.Observable;

public class Partecipazione extends Observable {
    private Studente studente;
    private StatoPartecipazione statoPartecipazione;

    public Partecipazione(Studente studente) {
        this.studente = studente;
        this.statoPartecipazione= new StatoPendente(this);
    }

    public void aggiornaPartecipazione(boolean presenza){
        statoPartecipazione.aggiornaPartecipazione(presenza);
        this.setChanged();
        this.notifyObservers();
    }

    //Getters and Setters

    public String getMatricolaStudente() {

        return studente.getMatricola();
    }

    public void setStato(StatoPartecipazione statoPartecipazione) {
      this.statoPartecipazione = statoPartecipazione;
    }

    public Studente getStudente() {
        return studente;
    }

    public StatoPartecipazione getStatoPartecipazione() {
        return statoPartecipazione;
    }

    @Override
    public String toString() {
        return "Partecipazione{" +
                "studente=" + studente +
                '}';
    }
}
