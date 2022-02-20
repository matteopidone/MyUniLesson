package com.MyUniLesson.app;

import static org.junit.jupiter.api.Assertions.*;

import com.MyUniLesson.app.domain.*;
import com.MyUniLesson.app.exception.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class TestMyUniLesson {

    static MyUniLesson myUniLesson;
    static CorsoDiLaurea cdl;
    static Docente d;
    static Insegnamento ins;
    static Studente s1;
    static Studente s2;


    @BeforeAll
    public static void initTest() {

        myUniLesson = MyUniLesson.getInstance();
        d = myUniLesson.getElencoDocenti().get(10003);
        cdl = myUniLesson.getElencoCdl().get(1);
        ins = cdl.getInsegnamenti().get(20005);
        s1 = cdl.getElencoStudenti().get("O46002170");
        s2 = cdl.getElencoStudenti().get("O46002200");
    }

    //UC1 Test
    @Test
    public void testInserimentoNuovaLezione() {

        //Test che verifica il non inserimento di più lezioni nello stesso giorno
        try {

            Date d1 = new Date(2022 - 1900, 1 - 1, 25, 10, 0);
            Date d2 = new Date(2022 - 1900, 1 - 1, 25, 15, 0);

            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            myUniLesson.selezionaInsegnamento(ins.getCodice());
            myUniLesson.creaLezione(d1, 2, false);
            myUniLesson.confermaInserimento();
            myUniLesson.creaLezione(d2, 2, false);
            myUniLesson.confermaInserimento();
            myUniLesson.terminaInserimento();

            int count = 0;
            boolean bool = false;
            for (Lezione lezione : myUniLesson.getElencoLezioni()) {

                if (lezione.nonDisponibile(d2)) {
                    count++;
                }

            }
            assertEquals(1, count);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testVerificaLezioniPrenotabili() {
        Lezione l1;
        Lezione l2;
        Date d1 = new Date(2022 - 1900, 2 - 1, 14, 13, 00);
        Date d2 = new Date(2022 - 1900, 2 - 1, 21, 13, 00);
        try {
            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            myUniLesson.selezionaInsegnamento(ins.getCodice());
            myUniLesson.creaLezione(d1, 2, false);
            l1 = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();
            myUniLesson.creaLezione(d2, 2, false);
            l2 = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();
            myUniLesson.terminaInserimento();

            myUniLesson.identificaStudente(s1.getMatricola(), cdl.getCodice());
            for (Insegnamento i : myUniLesson.mostraLezioniPrenotabili()) {

                if (i.getCodice() == 3) {
                    assertFalse(i.getLezioniInsegnamento().contains(l1));
                }
            }

        } catch (Exception e) {
            fail("Error Test");
        }

    }

    @Test
    public void getLezioniRicorrenti() {
        //testiamo l'inserimento delle lezioni nel caso siano ricorrenti

        //supponiamo che si conosca la data di fine
        Date end;
        Date data = new Date(2022 - 1900, 4 - 1, 4, 13, 00);
        int count = 0;
        if (data.getMonth() > 5) {
            end = new Date(data.getYear(), Calendar.DECEMBER, 31);
        } else {
            end = new Date(data.getYear(), Calendar.JUNE, 30);
        }
        try {
            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            myUniLesson.selezionaInsegnamento(ins.getCodice());
            myUniLesson.creaLezione(data, 2, true);
            while (data.before(end)) {
                count++;

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                calendar.add(Calendar.DATE, 7);
                data = calendar.getTime();

            }
            assertEquals(count, myUniLesson.getLezCorrente().size());

        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    //UC2 Test
    @Test
    public void getPartecipazioniDisponibili() {
        try {
            //test che verifica se, dopo aver manifestato una partecipazione, la lezione non compare tra quelle disponibili
            //inserisco almeno due lezioni per evitare la propagazione di un'altra eccezione

            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            myUniLesson.selezionaInsegnamento(ins.getCodice());
            myUniLesson.creaLezione(new Date(2022 - 1900, 2 - 1, 15, 10, 00), 2, false);
            Lezione lezione = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();
            myUniLesson.creaLezione(new Date(2022 - 1900, 2 - 1, 22, 14, 00), 2, false);
            myUniLesson.confermaInserimento();
            myUniLesson.terminaInserimento();

            myUniLesson.identificaStudente(s1.getMatricola(), cdl.getCodice());
            System.out.println(myUniLesson.mostraLezioniPrenotabili());
            myUniLesson.creaPartecipazione(lezione.getCodice());
            myUniLesson.confermaPartecipazione();


            myUniLesson.identificaStudente(s1.getMatricola(), cdl.getCodice());
            for (Insegnamento i : myUniLesson.mostraLezioniPrenotabili()) {

                if (i.getCodice() == 1) {
                    System.out.println("Insegnamento trovato");
                    assertFalse(i.getLezioniInsegnamento().contains(lezione));
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //UC6 Test
    @Test
    public void testModificaStatoPartecipazione() {
        try {

            Date d1 = new Date(2022 - 1900, 2 - 1, 23, 10, 0);

            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            myUniLesson.selezionaInsegnamento(ins.getCodice());
            myUniLesson.creaLezione(d1, 2, false);
            Lezione lezione = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();
            myUniLesson.terminaInserimento();

            myUniLesson.identificaStudente(s1.getMatricola(), cdl.getCodice());
            System.out.println(myUniLesson.mostraLezioniPrenotabili());
            myUniLesson.creaPartecipazione(lezione.getCodice());
            myUniLesson.confermaPartecipazione();

            myUniLesson.identificaStudente(s2.getMatricola(), cdl.getCodice());
            System.out.println(myUniLesson.mostraLezioniPrenotabili());
            myUniLesson.creaPartecipazione(lezione.getCodice());
            myUniLesson.confermaPartecipazione();

            myUniLesson.identificaDocente(d.getCodice());
            Map<Integer, Insegnamento> elencoInsegnamenti = myUniLesson.cercaInsegnamenti();
            List<Lezione> elencoLezioni = myUniLesson.cercaLezioni(ins.getCodice());
            List<Studente> elencoStudenti = myUniLesson.iniziaAppello(lezione.getCodice());

            myUniLesson.inserisciPresenza(elencoStudenti.get(0), true);
            myUniLesson.inserisciPresenza(elencoStudenti.get(1), false);
            myUniLesson.terminaAppello();

            Thread.sleep(5000); //Aspetto che finiscano i Threads

            assertEquals(1, lezione.getElencoPresenze().size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void TestEsistenzaDocenti() {
        try {
            myUniLesson.identificaDocente(1); //docente che non esiste nel sistema
            fail("Docente trovato.");
        } catch (DocentiException m) {
            assertNotNull(m);
        } catch (Exception e) {
            fail("Error Test");
        }
    }

    @Test
    public void TestEsistenzaLezione() {
        try {
            myUniLesson.identificaDocente(d.getCodice());
            myUniLesson.cercaLezioni(ins.getCodice());
            myUniLesson.iniziaAppello(1); //lezione che non esiste nell'insegnamento

            fail("Lezione trovata.");
        } catch (LezioneException l) {
            assertNotNull(l);
        } catch (Exception e) {
            fail("Error Test");
        }
    }

    @Test
    public void testSetElencoPartecipazioniNull() {
        try {
            Lezione l = null;
            myUniLesson.identificaDocente(d.getCodice());
            List<Lezione> elencoLezioni = myUniLesson.cercaLezioni(20005);

            for (Lezione l1 : elencoLezioni) {
                if (l1.getCodice() == 207465137) { //lezione che è presente nel sistema (vedi file Lezioni.txt)
                    l = l1;
                    break;
                }

            }
            List<Studente> elencoStudenti = myUniLesson.iniziaAppello(l.getCodice());
            for (Studente s : elencoStudenti) {
                myUniLesson.inserisciPresenza(s, true);
            }
            myUniLesson.terminaAppello();
            assertNull(l.getElencoPartecipazioni());

        } catch (Exception e) {
            fail("Test fail ");
        }
    }

    @Test
    public void getLezioniAnnullabili() {
        //test che verifica la comparsa di una lezione successiva alla data di oggi tra quelle annullabili
        try {
            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            myUniLesson.selezionaInsegnamento(ins.getCodice());
            myUniLesson.creaLezione(new Date(2022 - 1900, 4 - 1, 12, 12, 00), 2, false);
            Lezione l = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();
            myUniLesson.terminaInserimento();

            myUniLesson.identificaDocente(d.getCodice());
            List<Lezione> lez = myUniLesson.cercaProssimeLezioni(ins.getCodice());
            assertTrue(lez.contains(l));

        } catch (Exception e) {
            fail("error test");
        }

    }

    @Test
    public void testLezioneAnnullabile() {
        //test che dopo l'annullamento di una lezione, essa non sia più presente tra quelle annullabili
        try {
            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            myUniLesson.selezionaInsegnamento(ins.getCodice());
            myUniLesson.creaLezione(new Date(2022 - 1900, 4 - 1, 13, 12, 00), 2, false);
            Lezione l = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();
            myUniLesson.terminaInserimento();

            myUniLesson.identificaDocente(d.getCodice());
            myUniLesson.cercaProssimeLezioni(ins.getCodice());
            myUniLesson.annullaLezione(l.getCodice());

            myUniLesson.identificaDocente(d.getCodice());
            List<Lezione> lez = myUniLesson.cercaProssimeLezioni(ins.getCodice());

            Thread.sleep(5000); //Aspetto che finiscano i Threads

            assertFalse(lez.contains(l));

        } catch (Exception e) {
            fail("error test");
        }

    }

    private Date addDay(Date start, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }
}