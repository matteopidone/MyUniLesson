package com.MyUniLesson.app;

import static org.junit.jupiter.api.Assertions.*;

import com.MyUniLesson.app.domain.*;
import com.MyUniLesson.app.exception.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @Test
    public void testUC1() {
        try {
            Date d = new Date(2022 - 1900, 6 - 1, 2, 14, 00);

            myUniLesson.mostraCdl();

            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            assertEquals(myUniLesson.getCdlSelezionato(), cdl);

            myUniLesson.selezionaInsegnamento(ins.getCodice());
            assertEquals(cdl.getInsSelezionato(), ins);

            myUniLesson.creaLezione(d, 2, false);
            assertEquals(ins.getLezCorrente().size(), 1);
            Lezione lez = ins.getLezCorrente().get(0);

            myUniLesson.confermaInserimento();
            assertTrue(ins.getLezioniInsegnamento().contains(lez));

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    @Test
    public void testUC2() {

        try {
            myUniLesson.identificaStudente(s1.getMatricola(), cdl.getCodice());
            assertNotNull(myUniLesson.getCdlSelezionato());
            assertNotNull(cdl.getStudenteSelezionato());

            myUniLesson.mostraLezioniPrenotabili();

            Date data = new Date();
            data = addDay(data, 7);
            Lezione l = inserisciLezioni(data, false).get(0);

            myUniLesson.creaPartecipazione(l.getCodice());
            assertNotNull(myUniLesson.getLezioneSelezionata());
            Partecipazione p = l.getpCorrente();
            assertNotNull(p);

            myUniLesson.confermaPartecipazione();
            assertTrue(l.getElencoPartecipazioni().containsValue(p));
            assertEquals(1, l.getElencoPartecipazioni().size());
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    @Test
    public void testUC5() {
        try {
            Lezione lezione = inserisciLezioni(new Date(2022, 9 - 1, 6, 13, 00), false).get(0);
            myUniLesson.identificaDocente(d.getCodice());
            assertNotNull(myUniLesson.getDocenteSelezionato());

            myUniLesson.cercaInsegnamenti();
            myUniLesson.cercaProssimeLezioni(ins.getCodice());
            myUniLesson.annullaLezione(lezione.getCodice());
            assertTrue(lezione.isAnnullata());

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

    }

    @Test
    public void testUC6() {
        Lezione lezione = inserisciLezioni(new Date(2022 - 1900, 2 - 1, 18, 14, 00), false).get(0);
        Partecipazione p1 = inserisciPartecipazione(lezione, s1);
        Partecipazione p2 = inserisciPartecipazione(lezione, s2);

        try {
            myUniLesson.identificaDocente(d.getCodice());
            assertNotNull(myUniLesson.getDocenteSelezionato());

            myUniLesson.cercaInsegnamenti();

            myUniLesson.cercaLezioni(ins.getCodice());

            myUniLesson.iniziaAppello(lezione.getCodice());
            assertNotNull(myUniLesson.getLezioneSelezionata());

            myUniLesson.inserisciPresenza(s1, true);
            myUniLesson.inserisciPresenza(s2, false);

            myUniLesson.terminaAppello();
            assertNull(myUniLesson.getLezioneSelezionata());
            assertNull(myUniLesson.getDocenteSelezionato());

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    @Test
    public void testIdentificaStudente() {
        try {
            myUniLesson.identificaStudente(s1.getMatricola(), 1111);
            fail("Error test");
        } catch (CdlException e) {
            assertNotNull(e);

        } catch (Exception e) {
            fail("Error Test");
        }
    }

    @Test
    public void testIdentificaDocente() {
        try {
            myUniLesson.identificaDocente(1111111111);
            fail("Error test");
        } catch (DocentiException e) {
            assertNotNull(e);

        } catch (Exception e) {
            fail("Error Test");
        }
    }

    @Test
    public void testMostraInsegnamenti() {
        try {
            myUniLesson.mostraInsegnamenti(1111111111);
            fail("Error test");
        } catch (CdlException e) {
            assertNotNull(e);

        } catch (Exception e) {
            fail("Error Test");
        }
    }

    //Others
    private Date addDay(Date start, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    public Partecipazione inserisciPartecipazione(Lezione l, Studente s) {
        Partecipazione p = null;
        try {
            myUniLesson.identificaStudente(s1.getMatricola(), cdl.getCodice());
            myUniLesson.creaPartecipazione(l.getCodice());
            p = myUniLesson.getLezioneSelezionata().getpCorrente();
            myUniLesson.confermaPartecipazione();

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return p;
    }


    private List<Lezione> inserisciLezioni(Date d, boolean ricorrenza) {
        List<Lezione> lez = null;
        try {

            myUniLesson.mostraInsegnamenti(cdl.getCodice());
            myUniLesson.selezionaInsegnamento(ins.getCodice());

            myUniLesson.creaLezione(d, 2, ricorrenza);
            lez = ins.getLezCorrente();
            myUniLesson.confermaInserimento();

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return lez;

    }


}