package com.MyUniLesson.app.domain;
import java.util.*;

public class Docente {
    private int codice;
    private String nome;
    private String cognome;
    private Map<Integer, Insegnamento> elencoInsErogati;

    public List<Lezione> cercaLezioni(int codiceInsegnamento){
        Insegnamento ins= elencoInsErogati.get(codiceInsegnamento);
        return ins.cercaLezioni();
    }

    public Docente(int codice, String nome, String cognome) {
        this.codice = codice;
        this.nome = nome;
        this.cognome = cognome;
        this.elencoInsErogati=new HashMap<Integer, Insegnamento>();
    }

    public Map<Integer, Insegnamento> getInsegnamenti(){
        return elencoInsErogati;
    }

    public void aggiungiInsegnamento(Insegnamento i){
        this.elencoInsErogati.put(i.getCodice(), i);
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
