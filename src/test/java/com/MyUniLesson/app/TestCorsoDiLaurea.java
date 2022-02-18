package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.*;
import com.MyUniLesson.app.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCorsoDiLaurea {

    //UC2 Test
    @Test
    public void getCercaStudente(){
        //test che verifica se viene selezionato uno studente che non esiste nel corso di laurea
        try {
            CorsoDiLaurea c1 = new CorsoDiLaurea(1, "Ingegneria Informatica");
            Studente s1 = new Studente("O46002170", "Matteo", "Pidone", null);
            Studente s2 = new Studente("O46002020", "Elio", "Vinciguerra", null);
            c1.inserisciStudente(s1.getMatricola(), s1);
            c1.cercaStudente(s2.getMatricola());
            fail("Unexpected fail");
            
        }catch (StudenteException s){
            assertNotNull(s);
        }

    }

    @Test
    public void cercaLezioni(){
        //test che verifica se viene sollevata l'eccezione quando non ci sono lezioni prenotabili
        try{
            CorsoDiLaurea c1 = new CorsoDiLaurea(1, "Ingegneria Informatica");
            Studente s = new Studente("O46002170", "Matteo", "Pidone", null);
            c1.inserisciStudente(s.getMatricola(), s);
            c1.cercaStudente(s.getMatricola());
            c1.cercaLezioni();
            fail("Error Test");

        }catch (LezioneException l){
            assertNotNull(l);
        }
        catch (StudenteException s){
            fail("Error Test");
        }
    }

}
