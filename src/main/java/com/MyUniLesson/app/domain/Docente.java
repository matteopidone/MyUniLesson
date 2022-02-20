package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.*;

import java.util.*;

public class Docente {
    private int codice;
    private String nome;
    private String cognome;
    private Map<Integer, Insegnamento> elencoInsErogati;

    public List<Lezione> cercaLezioni(int codiceInsegnamento) throws Exception {
        Insegnamento ins = elencoInsErogati.get(codiceInsegnamento);
        if (ins == null) {
            throw new InsegnamentoException("Insegnamento non trovato.");
        }
        return ins.cercaLezioni();
    }

    public List<Lezione> cercaProssimeLezioni(int codiceInsegnamento) throws Exception {
        Insegnamento ins = elencoInsErogati.get(codiceInsegnamento);
        if (ins == null) {
            throw new InsegnamentoException("Insegnamento non trovato.");
        }
        return ins.cercaProssimeLezioni();
    }


    public Docente(int codice, String nome, String cognome) {
        this.codice = codice;
        this.nome = nome;
        this.cognome = cognome;
        this.elencoInsErogati = new HashMap<Integer, Insegnamento>();
    }

    public void aggiungiInsegnamento(Insegnamento i) {
        this.elencoInsErogati.put(i.getCodice(), i);
    }

    //Getters and Setters

    public int getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Map<Integer, Insegnamento> getInsegnamenti() {
        return elencoInsErogati;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "codice=" + codice +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", elencoInsErogati=" + elencoInsErogati +
                '}';
    }
}
