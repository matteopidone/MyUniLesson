package com.MyUniLesson.app.domain;

public class Partecipazione {
    private Studente studente;

    public Partecipazione(Studente studente) {
        this.studente = studente;
    }

    @Override
    public String toString() {
        return "Partecipazione{" +
                "studente=" + studente +
                '}';
    }
}
