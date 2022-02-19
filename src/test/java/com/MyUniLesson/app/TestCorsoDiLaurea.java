package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.*;
import com.MyUniLesson.app.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCorsoDiLaurea {

     Studente s1;
     Studente s2;
     CorsoDiLaurea cdl1;


    @BeforeEach
    public  void initTest() {
        s1 = new Studente("O46002170", "Matteo", "Pidone", null);
        s2 = new Studente("O46002020", "Elio", "Vinciguerra", null);
        cdl1 = new CorsoDiLaurea(1, "Ingegneria Informatica");
    }

    @AfterEach
    public  void clearTest(){
        s1 = null;
        s2 = null;
        cdl1 = null;
    }

    //UC2 Test
    @Test
    public void getCercaStudente(){
        //test che verifica se viene selezionato uno studente che non esiste nel corso di laurea
        try {

            cdl1.inserisciStudente(s1.getMatricola(), s1);
            cdl1.cercaStudente(s2.getMatricola());
            fail("Unexpected fail");
            
        }catch (StudenteException s){
            assertNotNull(s);
        }

    }

    @Test
    public void cercaLezioni(){
        //test che verifica se viene sollevata l'eccezione quando non ci sono lezioni prenotabili
        try{

            cdl1.inserisciStudente(s1.getMatricola(), s1);
            cdl1.cercaStudente(s1.getMatricola());
            cdl1.cercaLezioni();
            fail("Error Test");

        }catch (LezioneException l){
            assertNotNull(l);
        }
        catch (StudenteException s){
            fail("Error Test");
        }
    }

}
