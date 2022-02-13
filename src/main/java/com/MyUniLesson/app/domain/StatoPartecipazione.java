package com.MyUniLesson.app.domain;

public abstract class StatoPartecipazione {
    protected Partecipazione partecipazione;

    public void aggiornaPartecipazione (boolean presenza){
    }

    public StatoPartecipazione(Partecipazione partecipazione) {
        this.partecipazione = partecipazione;
    }
}
