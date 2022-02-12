package com.MyUniLesson.app.domain;

public class StatoPendente extends StatoPartecipazione{
    @Override
    public void aggiornaPartecipazione (boolean presenza){
        if(presenza){
            partecipazione.setStato(new StatoPresente(partecipazione));
        }else{
            partecipazione.setStato(new StatoAssente(partecipazione));
        }
    }

    public StatoPendente(Partecipazione partecipazione) {
        super(partecipazione);
    }
}
