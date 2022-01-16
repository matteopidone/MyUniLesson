package com.MyUniLesson.app.domain;

import java.util.Date;

public class Lezione {
    private int codice;
    private Date data;
    private int durata;

    public Lezione(Date data, int durata) {
        this.codice = (int)System.currentTimeMillis();
        this.data = data;
        this.durata = durata;
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

    public boolean nonDisponibile(Date data){
        if(this.data.getDate() == data.getDate()
                && this.data.getMonth() == data.getMonth()
                && this.data.getYear() == data.getYear()){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Lezione{" +
                "codice=" + codice +
                ", data=" + data +
                ", durata=" + durata +
                "}";
    }
}
