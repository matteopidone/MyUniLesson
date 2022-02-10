package com.MyUniLesson.app.domain;

import java.util.*;

public class CorsoDiLaurea {
    private int codice;
    private String nome;
    private Map<Integer, Insegnamento> elencoInsegnamenti;
    private Insegnamento insSelezionato;
    private Studente studenteSelezionato;
    private Map<String, Studente> elencoStudenti;


    public CorsoDiLaurea(int codice, String nome) {
        this.codice = codice;
        this.nome = nome;
        this.elencoInsegnamenti = new HashMap<Integer, Insegnamento>();
        this.elencoStudenti = new HashMap<String, Studente>();
    }

    // Avviamento

    public void inserisciInsegnamento(int codice, Insegnamento i) {
        elencoInsegnamenti.put(codice, i);
    }

    public void inserisciStudente(String matricola, Studente s) {
        elencoStudenti.put(matricola, s);
    }

    // UC

    public void cercaInsegnamenti(int codiceInsegnamento) throws Exception {
        insSelezionato = elencoInsegnamenti.get(codiceInsegnamento);
        if (insSelezionato == null) throw new Exception("Errore nell'insegnamento inserito");

        //return elencoInsegnamenti.get(codiceInsegnamento);
    }

    public void creaLezione(Date data, int durata, boolean ricorrenza) {
        insSelezionato.creaLezione(data, durata, ricorrenza);
    }

    public List<Lezione> confermaLezioni() throws Exception {
        return insSelezionato.confermaLezioni();
    }

    public boolean cercaStudente(String matricola) throws Exception {
        studenteSelezionato = elencoStudenti.get(matricola);
        if (studenteSelezionato != null) {
            return true;
        } else {
            throw new Exception("Studente non trovato");
        }
    }

    public List<Insegnamento> cercaLezioni() throws Exception {
        List<Lezione> elencoLezioni;
        Insegnamento i;
        List<Insegnamento> insLezioni = new LinkedList<Insegnamento>();
        String matricola = studenteSelezionato.getMatricola();
        int count = 0;

        for (Map.Entry<Integer, Insegnamento> entry : elencoInsegnamenti.entrySet()) {
            elencoLezioni = entry.getValue().cercaLezioniPrenotabili(matricola);
            i = new Insegnamento(entry.getValue().getCodice(), entry.getValue().getNome(), entry.getValue().getCFU());
            if (!elencoLezioni.isEmpty()) {
                i.aggiungiLezione(elencoLezioni);
                count++;
            }
            insLezioni.add(i);
        }
        if (count == 0) {
            throw new Exception("Non ci sono lezioni prenotabili");
        } else {
            return insLezioni;
        }
    }

    //Getters and Setters

    public int getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public Studente getStudenteSelezionato() {
        return studenteSelezionato;
    }

    public Map<Integer, Insegnamento> getInsegnamenti() {
        return elencoInsegnamenti;
    }

    public String getMatricolaStudenteSelezionato() {
        return studenteSelezionato.getMatricola();
    }

    // Others

    public void deseleziona() {
        insSelezionato.deseleziona();
        insSelezionato = null;
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
