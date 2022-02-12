package com.MyUniLesson.app.domain;
import java.util.*;

public class Docente {
    private int codice;
    private String nome;
    private String cognome;
    private Map<Integer, Insegnamento> elencoInsErogati;

    public List<Lezione> cercaLezioni(int codiceInsegnamento){
        return null;
    }

    public Docente(int codice, String nome, String cognome) {
        this.codice = codice;
        this.nome = nome;
        this.cognome = cognome;
    }
}
