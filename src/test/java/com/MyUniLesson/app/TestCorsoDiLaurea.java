package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.CorsoDiLaurea;
import com.MyUniLesson.app.domain.Studente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestCorsoDiLaurea {

    //UC2 Test
    @Test
    public void getCercaStudente(){
        //test che verifica se viene selezionato uno studente che non esiste nel corso di laurea
        try {
            CorsoDiLaurea c1 = new CorsoDiLaurea(1, "Ingegneria Informatica");
            Studente s1 = new Studente("O46002170", "Matteo", "Pidone");
            Studente s2 = new Studente("O46002020", "Elio", "Vinciguerra");
            c1.inserisciStudente(s1.getMatricola(), s1);
            c1.cercaStudente(s2.getMatricola());
            fail("Unexpected fail");
            
        }catch (Exception e){
            assertEquals(e.getMessage(), "Studente non trovato");
        }

    }

    @Test
    public void cercaLezioni(){
        //test che verifica se viene sollevata l'eccezione quando non ci sono lezioni prenotabili
        try{
            CorsoDiLaurea c1 = new CorsoDiLaurea(1, "Ingegneria Informatica");
            Studente s = new Studente("O46002170", "Matteo", "Pidone");
            c1.inserisciStudente(s.getMatricola(), s);
            c1.cercaStudente(s.getMatricola());
            c1.cercaLezioni();
            fail("Error Test");

        }catch (Exception e){
            assertEquals(e.getMessage(), "Non ci sono lezioni prenotabili");
        }
    }

}
