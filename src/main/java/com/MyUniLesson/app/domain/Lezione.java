package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.MyUniLessonException;
import com.MyUniLesson.app.exception.PartecipazioneException;


import java.util.*;

public class Lezione {
    private int codice;
    private Date data;
    private int durata;
    private Map<String, Partecipazione> elencoPartecipazioni;
    private Partecipazione pCorrente;

    public Lezione(Date data, int durata) {
        this.codice = (int) System.currentTimeMillis();
        this.data = data;
        this.durata = durata;
        this.elencoPartecipazioni = new HashMap<String, Partecipazione>();
    }

    public boolean nonDisponibile(Date data) {
        if (this.data.getDate() == data.getDate()
                && this.data.getMonth() == data.getMonth()
                && this.data.getYear() == data.getYear()) {
            return true;
        }
        return false;
    }

    public boolean verificaPartecipazione(String matricola) {
        return elencoPartecipazioni.get(matricola) != null;
    }

    public void generaPartecipazione(Studente studenteSelezionato) throws PartecipazioneException {
        pCorrente = new Partecipazione(studenteSelezionato);
        if (pCorrente == null) throw new PartecipazioneException("Partecipazione non creata");
    }

    public void aggiungiPartecipazione(String matricola) throws Exception {
        if (pCorrente != null) {
            if (elencoPartecipazioni.get(matricola) == null) {
                elencoPartecipazioni.put(matricola, pCorrente);
                pCorrente = null;
            } else throw new PartecipazioneException("Lo studente sta già partecipando alla lezione");
        } else throw new MyUniLessonException("Invocazione metodi non rispettata");

    }

    //Getters and Setters

    public Partecipazione getpCorrente() {
        return pCorrente;
    }

    public Date getData() {
        return data;
    }

    public int getDurata() {
        return durata;
    }

    public int getCodice() {
        return codice;
    }

    @Override
    public String toString() {
        return "Lezione{" +
                "codice=" + codice +
                ", data=" + data +
                ", durata=" + durata +
                ", elencoPartecipazioni{" + elencoPartecipazioni +
                "}" +
                "}";
    }
}
