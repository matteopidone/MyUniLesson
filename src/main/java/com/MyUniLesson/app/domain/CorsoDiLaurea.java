package com.MyUniLesson.app.domain;

import java.util.*;

public class CorsoDiLaurea {
    private int codice;
    private String nome;
    private Map<Integer, Insegnamento> elencoInsegnamenti;
    private Studente studenteSelezionato;
    private Map<String, Studente> elencoStudenti;


    public CorsoDiLaurea(int codice, String nome) {
        this.codice = codice;
        this.nome = nome;
        this.elencoInsegnamenti = new HashMap<Integer, Insegnamento>();
        this.elencoStudenti = new HashMap<String, Studente>();
    }

    public Map<Integer, Insegnamento> getInsegnamenti(){
        return elencoInsegnamenti;
    }

    public void inserisciInsegnamento(int codice, Insegnamento i){
        elencoInsegnamenti.put(codice, i);
    }

    public void inserisciStudente(String matricola, Studente s){elencoStudenti.put(matricola, s); }

    public Insegnamento cercaInsegnamenti(int codiceInsegnamento){
        return elencoInsegnamenti.get(codiceInsegnamento);
    }

    public void cercaStudente(String matricola){
        studenteSelezionato = elencoStudenti.get(matricola);

    }

    public List<Insegnamento> cercaLezioni(){
        List<Lezione> elencoLezioni;
        Insegnamento i;
        List<Insegnamento> insLezioni = new LinkedList<Insegnamento>();
        String matricola = studenteSelezionato.getMatricola();

        for (Map.Entry<Integer, Insegnamento> entry : elencoInsegnamenti.entrySet()){
            elencoLezioni = entry.getValue().cercaLezioniPrenotabili(matricola);
            i = new Insegnamento(entry.getValue().getCodice(), entry.getValue().getNome(), entry.getValue().getCFU());
            i.aggiungiLezione(elencoLezioni);
            insLezioni.add(i);
        }

        return insLezioni;
    }

    public Studente getStudenteSelezionato() {
        return studenteSelezionato;
    }

    @Override
    public String toString() {
        return "CorsoDiLaurea{" +
                "codice=" + codice +
                ", nome=" + nome +
                ", elencoStudenti=" + elencoStudenti +
                "}";
    }


}
