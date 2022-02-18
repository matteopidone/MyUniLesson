package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.Studente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStudente {

    //UC2 Test
    @Test
    public void testGetMatricola(){
        String matricola = "O46002170";
        Studente s = new Studente(matricola, "Matteo", "Pidone", null);
        assertEquals(matricola, s.getMatricola());
    }
}
