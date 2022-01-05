package com.MyUniLesson.app.domain;
import java.util.*;


public class Insegnamento {
    private int codice;
    private String nome;
    private int CFU;
    private List<Lezione> lezioniInsegnamento;

    public Insegnamento(int codice, String nome, int CFU) {
        this.codice = codice;
        this.nome = nome;
        this.CFU = CFU;
        this.lezioniInsegnamento = new LinkedList<Lezione>();
    }

    public boolean verificaDisponibilita(Date d){
    for(Lezione l : lezioniInsegnamento){
        if(l.getData().getDate() == d.getDate()
                && l.getData().getMonth() == d.getMonth()
                && l.getData().getYear() == d.getYear()){
            return false;
        }
    }
    return true;

    }

    public void aggiungiLezione(Lezione l){
        this.lezioniInsegnamento.add(l);
    }

    @Override
    public String toString() {
        return "Insegnamento{" +
                "codice=" + codice +
                ", nome='" + nome + '\'' +
                ", CFU=" + CFU +
                ", lezioniInsegnamento=" + lezioniInsegnamento +
                '}';
    }
}
