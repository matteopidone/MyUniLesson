package com.MyUniLesson.app;

import static org.junit.jupiter.api.Assertions.*;

import com.MyUniLesson.app.domain.*;
import com.MyUniLesson.app.exception.*;
import org.junit.jupiter.api.*;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class TestMyUniLesson {

    static MyUniLesson myUniLesson;

    @BeforeAll
    public static void initTest() {

        myUniLesson = MyUniLesson.getInstance();
    }

    @AfterEach
    public void clearTest() {

        //myUniLesson.deseleziona();
    }

    //UC1 Test
    @Test
    public void testInserimentoNuovaLezione() {

        //Test che verifica il non inserimento di più lezioni nello stesso giorno
        try {

            Date d1 = new Date(2022 - 1900, 1 - 1, 25, 10, 0);
            Date d2 = new Date(2022 - 1900, 1 - 1, 25, 15, 0);

            myUniLesson.mostraInsegnamenti(1);
            myUniLesson.selezionaInsegnamento(1);
            myUniLesson.creaLezione(d1, 2, false);
            myUniLesson.confermaInserimento();

            myUniLesson.mostraInsegnamenti(1);
            myUniLesson.selezionaInsegnamento(1);
            myUniLesson.creaLezione(d2, 2, false);
            myUniLesson.confermaInserimento();

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
    public void testVerificaLezioniPrenotabili(){
        Lezione l1;
        Lezione l2;
        Date d1 = new Date();
        Date d2 = addDay(d1,7);
        try{
            myUniLesson.mostraInsegnamenti(1);
            myUniLesson.selezionaInsegnamento(1);
            myUniLesson.creaLezione(d1, 2, false);
            l1 = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();

            myUniLesson.mostraInsegnamenti(1);
            myUniLesson.selezionaInsegnamento(1);
            myUniLesson.creaLezione(d2, 2, false);
            l2 = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();

            myUniLesson.identificaStudente("O46002170",1);
            for(Insegnamento i: myUniLesson.mostraLezioniPrenotabili()){

                if(i.getCodice() == 1){
                    System.out.println("Insegnamento trovato");
                    assertFalse(i.getLezioniInsegnamento().contains(l1));
                }
            }

        }catch(Exception e){
            fail("Error Test");
        }

    }

    @Test
    public void getLezioniRicorrenti(){
        //testiamo l'inserimento delle lezioni nel caso siano ricorrenti

        //supponiamo che si conosca la data di fine
        Date end;
        Date data = new Date();
        int count = 0;
        if (data.getMonth() > 5) {
            end = new Date(data.getYear(), Calendar.DECEMBER, 31);
        } else {
            end = new Date(data.getYear(), Calendar.JUNE, 30);
        }
        try {
            myUniLesson.mostraInsegnamenti(1);
            myUniLesson.selezionaInsegnamento(1);
            myUniLesson.creaLezione(data, 2, true);
            while (data.before(end)){
                count++;

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                calendar.add(Calendar.DATE, 7);
                data = calendar.getTime();

            }
            assertEquals(count, myUniLesson.getLezCorrente().size());

        }catch(Exception e){
            fail("Unexpected exception");
        }
    }

    //UC2 Test
    @Test
    public void getPartecipazioniDisponibili() {
        try {
            //test che verifica se, dopo aver manifestato una partecipazione, la lezione non compare tra quelle disponibili
            //inserisco almeno due lezioni per evitare la propagazione di un'altra eccezione

            myUniLesson.mostraInsegnamenti(1);
            myUniLesson.selezionaInsegnamento(1);
            myUniLesson.creaLezione(new Date(2022-1900, 2-1, 26, 10, 00), 2, false);
            Lezione lezione = myUniLesson.getLezCorrente().get(0);
            myUniLesson.confermaInserimento();

            myUniLesson.mostraInsegnamenti(1);
            myUniLesson.selezionaInsegnamento(1);
            myUniLesson.creaLezione(new Date(2022-1900, 2-1, 24, 14,00), 2, false);
            myUniLesson.confermaInserimento();

            myUniLesson.identificaStudente("O46002170", 1);
            System.out.println(myUniLesson.mostraLezioniPrenotabili());
            myUniLesson.creaPartecipazione(lezione.getCodice());
            myUniLesson.confermaPartecipazione();


            myUniLesson.identificaStudente("O46002170", 1);
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
    public void testModificaStatoPartecipazione(){
        try {

            Date d1 = new Date(2022 - 1900, 2 - 1, 22, 10, 0);

            myUniLesson.mostraInsegnamenti(1);
            myUniLesson.selezionaInsegnamento(1);
            myUniLesson.creaLezione(d1, 2, false);
            myUniLesson.confermaInserimento();
            Lezione lezione = myUniLesson.getLezCorrente().get(0);

            myUniLesson.identificaStudente("O46002170", 1);
            System.out.println(myUniLesson.mostraLezioniPrenotabili());
            myUniLesson.creaPartecipazione(lezione.getCodice());
            myUniLesson.confermaPartecipazione();

            myUniLesson.identificaStudente("O46002200", 1);
            System.out.println(myUniLesson.mostraLezioniPrenotabili());
            myUniLesson.creaPartecipazione(lezione.getCodice());
            myUniLesson.confermaPartecipazione();

            myUniLesson.identificaDocente(1);
            Map<Integer, Insegnamento> elencoInsegnamenti= myUniLesson.cercaInsegnamenti();
            List<Lezione> elencoLezioni= myUniLesson.cercaLezioni(1);
            List<Studente> elencoStudenti= myUniLesson.iniziaAppello(lezione.getCodice());

            myUniLesson.inserisciPresenza(elencoStudenti.get(0), true);
            myUniLesson.inserisciPresenza(elencoStudenti.get(1), false);
            myUniLesson.terminaAppello();

            System.out.println("Ci sono "+myUniLesson.getPresenti()+" studenti presenti e "+myUniLesson.getAssenti()+" studenti assenti.");     //Per vedere quanti alunni presenti o assenti ha inserito nelle apposite liste.

            assertEquals(1, myUniLesson.getPresenti());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void TestEsistenzaDocenti() {
        try {
            myUniLesson.identificaDocente(9999); //docente che non esiste nel sistema
            fail("Docente trovato.");
        }catch (DocentiException m){
            assertNotNull(m);
        }
        catch (Exception e){
            fail("Error Test");
        }
    }

    @Test
    public void TestEsistenzaLezione() {
        try {
            myUniLesson.identificaDocente(1);
            myUniLesson.cercaLezioni(1);
            myUniLesson.iniziaAppello(1); //lezione che non esiste nell'insegnamento

            fail("Lezione trovata.");
        }catch (LezioneException l){
            assertNotNull(l);
        }
        catch (Exception e){
            fail("Error Test");
        }
    }

    @Test
    public void testSetElencoPartecipazioniNull(){
        try {
            Lezione l = null;
            myUniLesson.identificaDocente(1);
            List<Lezione> elencoLezioni = myUniLesson.cercaLezioni(1);

            for(Lezione l1: elencoLezioni) {
                if(l1.getCodice() == 207465127){ //lezione che è presente nel sistema (vedi file Lezioni.txt)
                    l = l1;
                    break;
                }

            }
            List<Studente> elencoStudenti = myUniLesson.iniziaAppello(l.getCodice());
            for(Studente s: elencoStudenti){
                myUniLesson.inserisciPresenza(s, true);

            }
            myUniLesson.terminaAppello();
            assertNull(l.getElencoPartecipazioni());

        }catch(Exception e){
            fail("Test fail ");
        }
    }
    private Date addDay(Date start, int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }
}



