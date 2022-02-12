package com.MyUniLesson.app.domain;

import java.util.Observable;
import java.util.Observer;

public class PresenzeObserver implements Observer {
    private Lezione lezione;

    public PresenzeObserver(Lezione lezione) {  // DA VEDERE!
        this.lezione = lezione;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
