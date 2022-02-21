package com.MyUniLesson.app;


import com.MyUniLesson.app.domain.*;
import com.MyUniLesson.app.exception.MyUniLessonException;
import com.MyUniLesson.app.exception.PartecipazioneException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestLezione {

    Insegnamento ins1;
    Date d = new Date();
    Lezione l1;
    Studente s1;
    Studente s2;

    @BeforeEach
    public void initTest() {
        ins1 = new Insegnamento(1, "Ingegneria del Software", 9); //l'insegnamento nei test serve per la comunicazione via mail
        l1 = new Lezione(d, 1, ins1);
        s1 = new Studente("O46002191", "Tomas", "Prifti", "tomasprifti99@gmail.com");
        s2 = new Studente("O46002170", "Matteo", "Pidone", "pidonematteo@hotmail.it");
    }

    @AfterEach
    public void clearTest() {
        ins1 = null;
        l1 = null;
        s1 = null;
        s2 = null;
    }



    //UC2 Test
    @Test
    public void testAggiungiPartecipazione() {
        //test che verifica la corretta chiamata ai metodi
        try {
            l1.aggiungiPartecipazione(s1.getMatricola());
            fail("Error test");
        } catch (MyUniLessonException m) {
            assertNotNull(m);
        } catch (Exception e) {
            fail("Error Test");
        }
    }

    @Test
    public void testPrenotazionePresente() {
        //test che verifica se viene inserita due volte la partecipazione di uno studente
        try {

            l1.generaPartecipazione(s1);
            l1.aggiungiPartecipazione(s1.getMatricola());
            l1.generaPartecipazione(s1);
            l1.aggiungiPartecipazione(s1.getMatricola());
            fail("Error test");
        } catch (PartecipazioneException p) {
            assertNotNull(p);
        } catch (Exception e) {
            fail("Error Test");

        }
    }

    @Test
    public void testSetElencoPresenti() {
        try {
            l1.generaPartecipazione(s1);
            l1.aggiungiPartecipazione(s1.getMatricola());
            Partecipazione p = l1.getElencoPartecipazioni().get(s1.getMatricola());

            l1.creaElenchiAppello();
            l1.inserisciPresenza(s1, true);
            assertTrue(l1.getElencoPresenze().containsValue(p));

        } catch (Exception e) {
            fail("Test fail ");
        }
    }


    //cercaLezioni
    @Test
    public void testGetpCorrente() {
        //test che verifica il corretto funzionamento del metodo generaPartecipazione
        try {
            l1.generaPartecipazione(s1);
            assertNotNull(l1.getpCorrente());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testSetElencoPartecipazioniNull() {
        try {
            l1.generaPartecipazione(s1);
            l1.aggiungiPartecipazione(s1.getMatricola());
            l1.creaElenchiAppello();

            assertEquals(1, l1.cercaStudenti().size(), "Non coincide il numero di studenti ritornati");

            l1.inserisciPresenza(s1, true);
            l1.setElencoPartecipazioni(null);
            assertNull(l1.getElencoPartecipazioni(), "elencoPartecipazioni not null ");


        } catch (Exception e) {
            fail("Test fail ");
        }
    }

    @Test
    public void testRegistraAssenza() {
        //con questo test viene verificato sia la creazione della comunicazione, sia il corretto formato della mail
        try {
            l1.generaPartecipazione(s1);
            Partecipazione p = l1.getpCorrente();
            l1.aggiungiPartecipazione(s1.getMatricola());
            l1.creaElenchiAppello();
            l1.registraAssenza(s1.getMatricola(), p);

            Thread.sleep(5000); //Aspetto che finiscano i Threads

            for (ComunicazioneLezione c : l1.getElencoComunicazioni()) {
                if (c.getPartecipazione() == p) {
                    assertEquals(c.getFormato().getClass(), AssenteStrategy.class);
                    break;
                }
            }
        } catch (Exception e) {
            fail("Error Test " + e.getMessage());
        }
    }

    @Test
    public void testRegistraAnnullamento() {
        //con questo test viene verificato sia la creazione della comunicazione, sia il corretto formato della mail
        try {
            l1.generaPartecipazione(s1);
            Partecipazione p = l1.getpCorrente();
            l1.aggiungiPartecipazione(s1.getMatricola());

            l1.comunicaAnnullamento();

            Thread.sleep(5000); //Aspetto che finiscano i Threads

            for (ComunicazioneLezione c : l1.getElencoComunicazioni()) {
                if (c.getPartecipazione() == p) {
                    assertEquals(c.getFormato().getClass(), AnnullamentoStrategy.class);
                    break;
                }
            }
        } catch (Exception e) {
            fail("Error Test " + e.getMessage());
        }
    }

    //UC6 Test
    @Test
    public void testModificaStatoPartecipazione() {
        //testiamo l'aggiornamento degli elenchi dei presenti e degli assenti
        //testiamo l'invio della comunicazione in caso di assenza
        //testiamo il corretto formato della comunicazione
        try {

            Date d1 = new Date(2022 - 1900, 2 - 1, 15, 10, 0);
            Lezione l = new Lezione(d1, 2, ins1);
            ComunicazioneLezione co;

            l.generaPartecipazione(s1);
            l.aggiungiPartecipazione(s1.getMatricola());
            l.generaPartecipazione(s2);
            l.aggiungiPartecipazione(s2.getMatricola());

            l.creaElenchiAppello();
            l.inserisciPresenza(s1, true);
            l.inserisciPresenza(s2, false);

            Thread.sleep(5000); //Aspetto che finiscano i Threads

            co = l.getElencoComunicazioni().get(0);


            assertEquals(1, l.getElencoPresenze().size(), "Non coincide il numero dei presenti");
            assertEquals(1, l.getElencoAssenze().size(), "Non coincide il numero degli assenti");


            assertNotNull(co, "Non è stata inviata la comunicazione");
            assertEquals(co.getFormato().getClass(), AssenteStrategy.class, "Formato not equals");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
