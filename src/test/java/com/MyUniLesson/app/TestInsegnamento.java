package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.Insegnamento;
import com.MyUniLesson.app.domain.Lezione;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestInsegnamento {

    //UC2 Test
    @Test
    public void testVerificaDisponiblilità(){
        //test che verifica il corretto funzionamento del metodo verificaDisponibilità
        Insegnamento i = new Insegnamento(1, "Ingegneria del Software", 9);
        Date d = new Date();
        Lezione l1 = new Lezione(d, 2);
        List<Lezione> lezioneList = new LinkedList<Lezione>();
        lezioneList.add(l1);
        i.aggiungiLezione(lezioneList);
        assertFalse(i.verificaDisponibilita(d));

    }

    @Test
    public void testAggiungiLezione(){
        //test che verifica l'inserimento della lezione
        Insegnamento i = new Insegnamento(1, "Ingegneria del Software", 9);
        Date d = new Date();
        Lezione l1 = new Lezione(d, 2);
        List<Lezione> lezioneList = new LinkedList<Lezione>();
        lezioneList.add(l1);
        assertNotNull(i.getLezioniInsegnamento().contains(l1));

    }
}
