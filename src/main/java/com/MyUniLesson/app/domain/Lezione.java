package com.MyUniLesson.app.domain;

import java.util.Calendar;
import java.util.Date;

public class Lezione {
    private int codice;
    private Date data;
    private int durata;


    public Date creaData(int anno, int mese, int giorno, int ora, int minuti){
        return new Date();
    }

    public Lezione(Date data, int durata) {
        this.codice = (int)System.currentTimeMillis(); //provvisorio
        this.data = data;
        this.durata = durata;
        //new Date(2022-1900, Calendar.JANUARY, 4, 20,0,0); //anno-1900
    }

    public Date getData() {
        return data;
    }

    public int getDurata() {
        return durata;
    }

    public int getCodice() {
        return codice;
    }

    @Override
    public String toString() {
        return "Lezione{" +
                "codice=" + codice +
                ", data=" + data +
                ", durata=" + durata +
                '}';
    }
}
