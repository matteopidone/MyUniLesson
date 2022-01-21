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

    public int getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public int getCFU() {
        return CFU;
    }

    public boolean verificaDisponibilita(Date data) {
        for(Lezione l : lezioniInsegnamento){
           if(l.nonDisponibile(data)){ return false; }
        }
        return true;
    }

    public void aggiungiLezione(List lezCorrente) {
        this.lezioniInsegnamento.addAll(lezCorrente);
    }
    public List<Lezione> cercaLezioniPrenotabili(String matricola){
        List<Lezione> elencoLezioniPrenotabili = new LinkedList<Lezione>();
        for(Lezione l: lezioniInsegnamento){
            if(!l.verificaPartecipazione(matricola)){
                elencoLezioniPrenotabili.add(l);
            }
        }

        return elencoLezioniPrenotabili;
    }


    @Override
    public String toString() {
        return "Insegnamento{" +
                "codice=" + codice +
                ", nome='" + nome + '\'' +
                ", CFU=" + CFU +
                ", lezioni{" + lezioniInsegnamento + "}" +
                "}";
    }
}
