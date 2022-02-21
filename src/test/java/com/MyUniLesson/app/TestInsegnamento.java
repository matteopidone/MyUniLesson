package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.Insegnamento;
import com.MyUniLesson.app.domain.Lezione;
import com.MyUniLesson.app.domain.Studente;
import com.MyUniLesson.app.exception.LezioneException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestInsegnamento {

    Insegnamento ins1;
    Studente s;
    Date d = new Date();


    @BeforeEach
    public void initTest() {
        ins1 = new Insegnamento(1, "Ingegneria del Software", 9);
        s = new Studente("O46002170", "Matteo", "Pidone", null);

    }

    @AfterEach
    public void clearTest() {
        ins1 = null;

    }

    //UC1 Test
    @Test
    public void testInserimentoNuovaLezione() {

        //Test che verifica il non inserimento di più lezioni nello stesso giorno
        try {
            Date d1 = new Date(2022 - 1900, 3 - 1, 16, 13, 00);
            ins1.creaLezione(d, 2,false);
            ins1.confermaLezioni();
            ins1.creaLezione(d, 1,false);
            ins1.confermaLezioni();

            assertEquals(1, ins1.getLezioniInsegnamento().size());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
    @Test
    public void getLezioniRicorrenti() {
        //testiamo l'inserimento delle lezioni nel caso siano ricorrenti
        Date data = new Date(2022 - 1900, 3 - 1, 16, 13, 00);
        Date end;
        int count = 0;
        if (data.getMonth() > 5) {
            end = new Date(data.getYear(), Calendar.DECEMBER, 31);
        } else {
            end = new Date(data.getYear(), Calendar.JUNE, 30);
        }

        try {
            ins1.creaLezione(data, 2, true);
            ins1.confermaLezioni();
            while (data.before(end)) {
                count++;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                calendar.add(Calendar.DATE, 7);
                data = calendar.getTime();
            }
            assertEquals(count, ins1.getLezioniInsegnamento().size());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void testVerificaLezioniPrenotabili() {
        //test che verifica la non prenotabilità di una lezione di una lezione di questa settimana
        Lezione l1;
        Date d1 = new Date(2022 - 1900, 2 - 1, 22, 13, 00);
        try {


            ins1.creaLezione(d1, 2, false);
            ins1.confermaLezioni();

            l1 = ins1.getLezioniInsegnamento().get(0);
            assertFalse(ins1.cercaLezioniPrenotabili(s.getMatricola()).contains(l1));

        } catch (Exception e) {
            fail("Error Test");
        }

    }


    //UC2 Test
    @Test
    public void getPartecipazioniDisponibili() {
        try {
            Date d = new Date(2022 - 1900, 2 - 1, 28, 13, 00);
            //test che verifica se, dopo aver manifestato una partecipazione, la lezione non compare più tra quelle disponibili

            ins1.creaLezione(d, 2,false);
            ins1.confermaLezioni();

            Lezione l = ins1.getLezioniInsegnamento().get(0);
            assertTrue(ins1.cercaLezioniPrenotabili(s.getMatricola()).contains(l), "La lezione doveva essere ritornata");

            l.generaPartecipazione(s);
            l.aggiungiPartecipazione(s.getMatricola());

            assertFalse(ins1.cercaLezioniPrenotabili(s.getMatricola()).contains(l),"La lezione non doveva essere ritornata");

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAggiungiLezione() {
        //test che verifica l'inserimento della lezione
        try {
            ins1.creaLezione(d, 2, false);
            ins1.confermaLezioni();
            Lezione l = ins1.getLezioniInsegnamento().get(0);
            assertNotNull(ins1.getLezioniInsegnamento().contains(l));
        }catch (LezioneException e){
            fail("Error Test");
        }
    }

    @Test
    public void testLezioneAnnullabile() {
        //test che dopo l'annullamento di una lezione, essa non sia più presente tra quelle annullabili
        try {
            Date d = new Date();
            d = addDay(d,1);
            //Suppongo d diverso da  Sabato e Domenica

            ins1.creaLezione(d, 2,false);
            ins1.confermaLezioni();

            Lezione l = ins1.getLezioniInsegnamento().get(0);

            assertTrue(ins1.cercaProssimeLezioni().contains(l), "La lezione non appare tra quelle annullabili");

            l.setAnnullata(true);

            assertFalse(ins1.cercaProssimeLezioni().contains(l), "La lezione non è stata annullata");


        } catch (Exception e) {
            fail("error test");
        }
    }

    @Test
    public void getLezioniAnnullabili() {
        //test che verifica la comparsa di una lezione successiva alla data di oggi tra quelle annullabili
        try {

            Date d1 = new Date();
            d1 = addDay(d, 1);
            //Suppongo d1 diverso da Sabato e Domenica

            Date d2 = new Date(2022-1900,2-1,16, 13, 00);

            ins1.creaLezione(d1, 1, false);
            ins1.confermaLezioni();
            ins1.creaLezione(d2, 1, false);
            ins1.confermaLezioni();

            Lezione l1 = ins1.getLezioniInsegnamento().get(0);
            Lezione l2 = ins1.getLezioniInsegnamento().get(1);

            List<Lezione> lezioni = ins1.cercaProssimeLezioni();
            assertTrue(lezioni.contains(l1),"La lezione di domani non è tra quelle annullabili");
            assertFalse(lezioni.contains(l2),"La lezione passata è tra quelle annullabili");

        } catch (Exception e) {
            fail("error test");
        }

    }

    //Others

    private Date addDay(Date start, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }
}
