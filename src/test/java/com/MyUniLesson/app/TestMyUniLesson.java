package com.MyUniLesson.app;

import static org.junit.Assert.*;

import com.MyUniLesson.app.domain.*;
import org.junit.Test;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class TestMyUniLesson
{

    @Test
    public void testInserimentoNuovaLezione() {
        //Testiamo l'inserimento di più lezioni nello stesso giorno (non deve accadere)

        MyUniLesson myUniLesson = MyUniLesson.getInstance();

        myUniLesson.mostraInsegnamenti(1);
        myUniLesson.selezionaInsegnamento(1);

        myUniLesson.creaLezione(new Date(2022-1900,1 - 1,1,10, 0), 2);
        myUniLesson.confermaInserimento();

        myUniLesson.creaLezione(new Date(2022-1900,1 - 1,1,15, 0), 2);
        myUniLesson.confermaInserimento();

        System.out.println(myUniLesson.getElencoLezioni()); //Per la visualizzazione

        int count = 0;
        for(Lezione lezione : myUniLesson.getElencoLezioni()) {
            if(lezione.nonDisponibile(new Date(2022-1900,1 - 1,1))) {
                count++;
            }
        }

        assertEquals("Test non superato", 1, count);
    }
}
