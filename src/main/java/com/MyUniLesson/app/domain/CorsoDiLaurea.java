package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.InsegnamentoException;
import com.MyUniLesson.app.exception.LezioneException;
import com.MyUniLesson.app.exception.StudenteException;

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

    public void cercaInsegnamenti(int codiceInsegnamento) throws InsegnamentoException {
        insSelezionato = elencoInsegnamenti.get(codiceInsegnamento);
        if (insSelezionato == null) throw new InsegnamentoException("Errore nell'insegnamento inserito");

    }

    public void creaLezione(Date data, int durata, boolean ricorrenza) throws LezioneException {
        insSelezionato.creaLezione(data, durata, ricorrenza);
    }

    public List<Lezione> confermaLezioni() throws LezioneException {
        return insSelezionato.confermaLezioni();
    }

    public boolean cercaStudente(String matricola) throws StudenteException {
        studenteSelezionato = elencoStudenti.get(matricola);
        if (studenteSelezionato != null) {
            return true;
        } else {
            throw new StudenteException("Studente non trovato");
        }
    }

    public List<Insegnamento> cercaLezioni() throws LezioneException {
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
            throw new LezioneException("Non ci sono lezioni prenotabili");
        } else {
            return insLezioni;
        }
    }

    //Getters and Setters

    public int getCodice() {
        return codice;
    }
    public List<Lezione> getLezCorrente(){
        return insSelezionato.getLezCorrente();
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

    public Map<String, Studente> getElencoStudenti() {
        return elencoStudenti;
    }

    public Insegnamento getInsSelezionato() {
        return insSelezionato;
    }

    public void setInsSelezionato(Insegnamento insSelezionato) {
        this.insSelezionato = insSelezionato;
    }

    public void setStudenteSelezionato(Studente studenteSelezionato) {
        this.studenteSelezionato = studenteSelezionato;
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
