package com.MyUniLesson.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.MyUniLesson.app.domain.Partecipazione;
import com.MyUniLesson.app.domain.Studente;
import org.junit.jupiter.api.Test;


public class TestPartecipazione {

    //UC2 Test
    @Test
    public void testInserisciPartecipazione(){
        Studente s = new Studente("O46002170", "Matteo", "Pidone");
        Partecipazione p = new Partecipazione(s);
        assertEquals(s, p.getStudente());

    }
}
