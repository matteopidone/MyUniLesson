package com.MyUniLesson.app;


import com.MyUniLesson.app.domain.Lezione;
import com.MyUniLesson.app.domain.Partecipazione;
import com.MyUniLesson.app.domain.Studente;
import com.MyUniLesson.app.exception.MyUniLessonException;
import com.MyUniLesson.app.exception.PartecipazioneException;

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
            Lezione l = new Lezione(d, 1, null);
            l.aggiungiPartecipazione("O46002170");
            fail("Error test");
        }catch (MyUniLessonException m){
            assertNotNull(m);
        }
        catch (Exception e){
            fail("Error Test");
        }
    }

    @Test
    public void testPrenotazionePresente(){
        //test che verifica se viene inserita due volte la partecipazione di uno studente
        try {
            Studente s = new Studente("O46002170", "Matteo", "Pidone", null);
            Date d = new Date();
            Lezione l = new Lezione(d, 1, null);
            l.generaPartecipazione(s);
            l.aggiungiPartecipazione(s.getMatricola());
            l.generaPartecipazione(s);
            l.aggiungiPartecipazione(s.getMatricola());
            fail("Error test");
        }catch (PartecipazioneException p){
            assertNotNull(p);
        }
        catch (Exception e){
            fail("Error Test");

        }
    }

    @Test
    public void testSetElencoPresenti(){
        Lezione l = new Lezione(new Date(), 1, null);
        Studente s = new Studente("O46002170", "Matteo", "Pidone", "pidonematteo@hotmail.it");
        try {
            l.generaPartecipazione(s);
            l.aggiungiPartecipazione(s.getMatricola());
            Partecipazione p = l.getElencoPartecipazioni().get(s.getMatricola());

            l.creaElenchiAppello();
            l.inserisciPresenza(s, true);
            assertTrue(l.getElencoPresenze().containsValue(p));

        }catch(Exception e){
            fail("Test fail ");
        }
    }


    //cercaLezioni
    @Test
    public void testGetpCorrente(){
        //test che verifica il corretto funzionamento del metodo generaPartecipazione
        try {
            Lezione l = new Lezione(new Date(), 1, null);
            Studente s1 = new Studente("O46002170", "Matteo", "Pidone", null);
            l.generaPartecipazione(s1);
            assertNotNull(l.getpCorrente());
        }catch (Exception e){
           fail("Unexpected Exception");
        }
    }
}
