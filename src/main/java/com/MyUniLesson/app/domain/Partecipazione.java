package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.*;

import java.util.Observable;

public class Partecipazione extends Observable {
    private Studente studente;
    private StatoPartecipazione statoPartecipazione;
    private Lezione lezione;

    public Partecipazione(Studente studente, Lezione lezione) {
        this.studente = studente;
        this.statoPartecipazione= new StatoPendente(this);
        this.lezione = lezione;
    }

    public void aggiornaPartecipazione(boolean presenza) throws Exception{
        statoPartecipazione.aggiornaPartecipazione(presenza);
        if(statoPartecipazione.getClass() == StatoPendente.class){
            throw new PartecipazioneException("Stato non aggiornato.");
        }
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

    public Lezione getLezione() {
        return lezione;
    }

    @Override
    public String toString() {
        return "Partecipazione{" +
                "studente=" + studente +
                '}';
    }
}
