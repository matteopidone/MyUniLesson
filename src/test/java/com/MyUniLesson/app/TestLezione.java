package com.MyUniLesson.app;


import com.MyUniLesson.app.domain.Lezione;
import com.MyUniLesson.app.domain.Studente;
import org.junit.jupiter.api.Test;

import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

public class TestLezione {

    //UC2 Test
    @Test
    public void testAggiungiPartecipazione(){
        //test che verifica la corretta chiamata ai metodi
        try {
            Date d = new Date();
            Lezione l = new Lezione(d, 1);
            l.aggiungiPartecipazione("O46002170");
            fail("Error test");
        }catch (Exception e){
            assertEquals(e.getMessage(),"Invocazione metodi non rispettata");
        }
    }

    @Test
    public void testPrenotazionePresente(){
        //test che verifica se viene inserita due volte la partecipazione di uno studente
        try {
            Studente s = new Studente("O46002170", "Matteo", "Pidone");
            Date d = new Date();
            Lezione l = new Lezione(d, 1);
            l.generaPartecipazione(s);
            l.aggiungiPartecipazione(s.getMatricola());
            l.generaPartecipazione(s);
            l.aggiungiPartecipazione(s.getMatricola());
            fail("Error test");
        }catch (Exception e){
            assertEquals(e.getMessage(),"Lo studente sta già partecipando alla lezione");
        }
    }


    //cercaLezioni
    @Test
    public void testGetpCorrente(){
        //test che verifica il corretto funzionamento del metodo generaPartecipazione
        try {
            Lezione l = new Lezione(new Date(), 1);
            Studente s1 = new Studente("O46002170", "Matteo", "Pidone");
            l.generaPartecipazione(s1);
            assertNotNull(l.getpCorrente());
        }catch (Exception e){
           fail("Unexpected Exception");
        }
    }
}
