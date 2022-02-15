package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.MyUniLessonException;
import com.MyUniLesson.app.exception.PartecipazioneException;
import static java.lang.Math.abs;

import java.util.*;

public class Lezione {
    private int codice;
    private Date data;
    private int durata;
    private boolean appello;
    private Map<String, Partecipazione> elencoPartecipazioni;
    private Partecipazione pCorrente;
    private Map<String, Partecipazione> elencoAssenze;
    private Map<String, Partecipazione> elencoPresenze;
    private PresenzeObserver presenzeObserver;

    public Lezione(Date data, int durata) {
        this.codice = abs((int) System.currentTimeMillis());
        this.data = data;
        this.durata = durata;
        this.elencoPartecipazioni = new HashMap<String, Partecipazione>();
        this.appello=false;
        this.presenzeObserver= new PresenzeObserver(this);
    }

    public Lezione(int codice, Date data, int durata) {         //Overload degli operatori, per caricare da file impostando il codice corretto (che serve necessariamente per il caricamento delle Partecipazioni)
        this.codice = codice;
        this.data = data;
        this.durata = durata;
        this.elencoPartecipazioni = new HashMap<String, Partecipazione>();
        this.appello=false;
        this.presenzeObserver= new PresenzeObserver(this);
    }

    public boolean nonDisponibile(Date data) {
        if (this.data.getDate() == data.getDate()
                && this.data.getMonth() == data.getMonth()
                && this.data.getYear() == data.getYear()) {
            return true;
        }
        return false;
    }

    public boolean verificaPartecipazione(String matricola) {
        return elencoPartecipazioni.get(matricola) != null;
    }

    public void generaPartecipazione(Studente studenteSelezionato) throws PartecipazioneException {
        pCorrente = new Partecipazione(studenteSelezionato);
        if (pCorrente == null) throw new PartecipazioneException("Partecipazione non creata");
        pCorrente.addObserver(presenzeObserver);
    }

    public void aggiungiPartecipazione(String matricola) throws Exception {
        if (pCorrente != null) {
            if (elencoPartecipazioni.get(matricola) == null) {
                elencoPartecipazioni.put(matricola, pCorrente);
                pCorrente = null;
            } else throw new PartecipazioneException("Lo studente sta già partecipando alla lezione");
        } else throw new MyUniLessonException("Invocazione metodi non rispettata");

    }

    public void registraPresenza(String matricola, Partecipazione partecipazione){
        elencoPresenze.put(matricola, partecipazione);
        elencoPartecipazioni.remove(matricola);
    }

    public void registraAssenza(String matricola, Partecipazione partecipazione){
        elencoAssenze.put(matricola, partecipazione);
        elencoPartecipazioni.remove(matricola);
    }

    public void creaElenchiAppello(){
        elencoAssenze= new HashMap<String, Partecipazione>();
        elencoPresenze= new HashMap<String, Partecipazione>();
    }

    public List<Studente> cercaStudenti(){
        List<Studente> elencoStudenti= new LinkedList<Studente>(){};
        for(Map.Entry<String, Partecipazione> entry : elencoPartecipazioni.entrySet()){
            elencoStudenti.add(entry.getValue().getStudente());
        }
        return elencoStudenti;
    }

    public void inserisciPresenza (Studente studente, boolean presenza) throws Exception{
        String matricola=studente.getMatricola();
        Partecipazione partecipazione= elencoPartecipazioni.get(matricola);
        partecipazione.aggiornaPartecipazione(presenza);
    }

    //Getters and Setters

    public Partecipazione getpCorrente() {
        return pCorrente;
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

    public Map<String, Partecipazione> getElencoAssenze() {
        return elencoAssenze;
    }

    public Map<String, Partecipazione> getElencoPresenze() {
        return elencoPresenze;
    }

    public boolean isAppello() {
        return appello;
    }


    public Map<String, Partecipazione> getElencoPartecipazioni() {
        return elencoPartecipazioni;
    }

    public void setElencoPartecipazioni(Map<String, Partecipazione> elencoPartecipazioni) {
        this.elencoPartecipazioni = elencoPartecipazioni;
    }

    public void setAppello(boolean appello) {
        this.appello = appello;
    }

    @Override
    public String toString() {
        return "Lezione{" +
                "codice=" + codice +
                ", data=" + data +
                ", durata=" + durata +
                "}" +
                "}";
    }
}
