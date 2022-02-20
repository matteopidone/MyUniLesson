package com.MyUniLesson.app;

import static org.junit.jupiter.api.Assertions.*;

import com.MyUniLesson.app.domain.*;
import org.junit.jupiter.api.*;

public class TestStatoPartecipazione {
    //UC6
    @Test
    public void testVerificaStatoPartecipazione() {
        Partecipazione p = new Partecipazione(new Studente("O46002200", "Elio", "Vinciguerra", null), null);

        assertEquals(p.getStatoPartecipazione().getClass(), StatoPendente.class, "Test Failed!");
    }

    @Test
    public void testVerificaAggiornaStato() {
        try {
            Partecipazione p = new Partecipazione(new Studente("O46002200", "Elio", "Vinciguerra", null), null);
            p.aggiornaPartecipazione(true);         //Idem per StatoAssente, ma con presenza=false.

            assertEquals(p.getStatoPartecipazione().getClass(), StatoPresente.class, "Test Failed!");
        } catch (Exception e) {
            fail("Error test.");
        }
    }

}