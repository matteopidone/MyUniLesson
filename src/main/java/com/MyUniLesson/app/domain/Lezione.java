package com.MyUniLesson.app.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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

    public Date getData() {
        return data;
    }

    public int getDurata() {
        return durata;
    }

    public int getCodice() {
        return codice;
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

    public void generaPartecipazione(Studente studenteSelezionato) {
        pCorrente = new Partecipazione(studenteSelezionato);
    }

    public void aggiungiPartecipazione(String matricola) {
        elencoPartecipazioni.put(matricola, pCorrente);
        pCorrente = null; //rimangone le cose selezionate da deselezionare
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
