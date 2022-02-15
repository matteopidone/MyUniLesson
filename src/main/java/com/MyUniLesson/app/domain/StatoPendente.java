package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.*;

public class StatoPendente extends StatoPartecipazione{
    @Override
    public void aggiornaPartecipazione (boolean presenza) throws Exception{
        if(presenza){
            partecipazione.setStato(new StatoPresente(partecipazione));
        }else if(presenza == false){
            partecipazione.setStato(new StatoAssente(partecipazione));
        }else{
            throw new PartecipazioneException("Stato partecipazione non valido.");
        }
    }

    public StatoPendente(Partecipazione partecipazione) {
        super(partecipazione);
    }
}
