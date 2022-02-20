package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.Partecipazione;
import com.MyUniLesson.app.domain.Studente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPartecipazione {

    //UC2 Test
    @Test
    public void testInserisciPartecipazione() {
        Studente s = new Studente("O46002170", "Matteo", "Pidone", null);
        Partecipazione p = new Partecipazione(s, null);
        assertEquals(s, p.getStudente());
    }
}
