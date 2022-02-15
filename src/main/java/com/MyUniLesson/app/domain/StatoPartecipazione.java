package com.MyUniLesson.app.domain;

public abstract class StatoPartecipazione {
    protected Partecipazione partecipazione;

    public void aggiornaPartecipazione (boolean presenza) throws Exception{
    }

    public StatoPartecipazione(Partecipazione partecipazione) {
        this.partecipazione = partecipazione;
    }
}
