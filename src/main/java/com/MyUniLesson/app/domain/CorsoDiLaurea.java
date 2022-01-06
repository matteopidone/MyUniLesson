package com.MyUniLesson.app.domain;
import java.util.*;

public class CorsoDiLaurea {
    private int codice;
    private String nome;
    private Map<Integer, Insegnamento> elencoInsegnamenti; //scegliere se mettere string

    public CorsoDiLaurea(int codice, String nome) {
        this.codice = codice;
        this.nome = nome;
        this.elencoInsegnamenti = new HashMap<Integer, Insegnamento>();
    }

    public Map<Integer, Insegnamento> getInsegnamenti(){
        return elencoInsegnamenti;
    }

    public void inserisciInsegnamento(int codice, Insegnamento i){
        elencoInsegnamenti.put(codice, i);
    }

    public Insegnamento cercaInsegnamenti(int codiceInsegnamento){  //era void nell'sd
        return elencoInsegnamenti.get(codiceInsegnamento);
    }

    @Override
    public String toString() {
        return "CorsoDiLaurea{" +
                "codice=" + codice +
                ", nome='" + nome + '\'' +
                ", elencoInsegnamenti=" + elencoInsegnamenti +
                '}';
    }
}
