package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.Partecipazione;
import com.MyUniLesson.app.domain.Studente;
import com.MyUniLesson.app.exception.LezioneException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;


public class TestPartecipazione {

    //UC2 Test
    @Test
    public void testInserisciPartecipazione(){
        Studente s = new Studente("O46002170", "Matteo", "Pidone");
        Partecipazione p = new Partecipazione(s);
        assertEquals(s, p.getStudente());
    }
}
