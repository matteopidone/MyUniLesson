package com.MyUniLesson.app;


import com.MyUniLesson.app.domain.*;
import com.MyUniLesson.app.exception.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDocente {
    //UC6 Test

    static MyUniLesson myUniLesson;

    @BeforeAll
    public static void initTest() {

        myUniLesson = MyUniLesson.getInstance();
    }

    @Test
    public void TestTrovaInsegnamento() {
        Docente d;
        try {
            d = myUniLesson.getElencoDocenti().get(1);
            d.cercaLezioni(9999);
            fail("Insegnamento trovato.");
        } catch (InsegnamentoException l) {
            assertNotNull(l);
        } catch (Exception e) {
            fail("Error Test");
        }
    }

}
