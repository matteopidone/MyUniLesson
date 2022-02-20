package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.Insegnamento;
import com.MyUniLesson.app.domain.Lezione;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestInsegnamento {

    Insegnamento ins1;
    Date d = new Date();
    Lezione l1;

    @BeforeEach
    public void initTest() {
        ins1 = new Insegnamento(1, "Ingegneria del Software", 9);
        l1 = new Lezione(d, 2, ins1);
    }

    @AfterEach
    public void clearTest() {
        ins1 = null;
        l1 = null;
    }

    //UC2 Test
    @Test
    public void testVerificaDisponibilita() {
        //test che verifica il corretto funzionamento del metodo verificaDisponibilità

        List<Lezione> lezioneList = new LinkedList<Lezione>();
        lezioneList.add(l1);
        ins1.aggiungiLezione(lezioneList);
        assertFalse(ins1.verificaDisponibilita(d));
    }

    @Test
    public void testAggiungiLezione() {
        //test che verifica l'inserimento della lezione

        List<Lezione> lezioneList = new LinkedList<Lezione>();
        lezioneList.add(l1);
        assertNotNull(ins1.getLezioniInsegnamento().contains(l1));
    }
}
