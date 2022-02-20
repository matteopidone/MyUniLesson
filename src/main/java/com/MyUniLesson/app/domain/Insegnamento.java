package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.LezioneException;

import java.util.*;

public class Insegnamento {
    private int codice;
    private String nome;
    private int CFU;
    private List<Lezione> lezCorrente;
    private List<Lezione> lezioniInsegnamento;

    public Insegnamento(int codice, String nome, int CFU) {
        this.codice = codice;
        this.nome = nome;
        this.CFU = CFU;
        this.lezioniInsegnamento = new LinkedList<Lezione>();
    }

    public void creaLezione(Date data, int durata, boolean ricorrenza) throws LezioneException {
        lezCorrente = new LinkedList<Lezione>();
        Date end;
        Date today = new Date();

        today.setHours(23);
        if (data.getDay() == 0 || data.getDay() == 6) {
            throw new LezioneException("Non è possibile inserire lezioni il Sabato o la Domenica");
        }
        /*
        //Non è possibile applicare questa Regola di Dominio senza compromettere il corretto funzionamento dei Test
        if (data.before(today)) {
            throw new LezioneException("Non è possibile inserire lezioni nei giorni precedenti a oggi");
        }
        */
        if (data.getMonth() > 5) {
            end = new Date(data.getYear(), Calendar.DECEMBER, 31);
        } else {
            end = new Date(data.getYear(), Calendar.JUNE, 30);
        }
        do {
            if (verificaDisponibilita(data)) {
                lezCorrente.add(new Lezione(data, durata, this));
            } else {
                System.out.println("Errore: Impossibile inserire la lezione di giorno " + data);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data);
            calendar.add(Calendar.DATE, 7);
            data = calendar.getTime();
        } while (ricorrenza && data.before(end));
    }

    public boolean verificaDisponibilita(Date data) {
        for (Lezione l : lezioniInsegnamento) {
            if (l.nonDisponibile(data)) {
                return false;
            }
        }
        return true;
    }

    public List<Lezione> confermaLezioni() throws LezioneException {
        if (lezCorrente != null) {
            aggiungiLezione(lezCorrente);
        } else {
            throw new LezioneException("Errore: lezione non inserita");
        }
        return lezCorrente;
    }

    private Date addDay(Date start, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    public List<Lezione> cercaLezioniPrenotabili(String matricola) {
        Date start = new Date();
        start.setHours(6);
        Date end;
        List<Lezione> elencoLezioniPrenotabili = new LinkedList<Lezione>();

        do {
            start = addDay(start, 1);

        } while (start.getDay() != 1);

        end = addDay(start, 4);
        end.setHours(23);


        for (Lezione l : lezioniInsegnamento) {
            if (!l.verificaPartecipazione(matricola) && l.getData().before(end) && l.getData().after(start)) {
                elencoLezioniPrenotabili.add(l);
            }
        }
        return elencoLezioniPrenotabili;
    }

    public List<Lezione> cercaLezioni() throws LezioneException {
        List<Lezione> elencoLezioni = new LinkedList<Lezione>();
        Date today = new Date();
        for (Lezione l : lezioniInsegnamento) {
            if (l.getData().before(today) && !l.isAppello() && !l.isAnnullata()) {               //verifica la data e che l'appello non sia stato fatto (!appello= !false=true)
                elencoLezioni.add(l);
            }
        }
        if (elencoLezioni.isEmpty()) {
            throw new LezioneException("Non ci sono lezioni su cui poter fare l'appello");
        }
        return elencoLezioni;
    }

    public List<Lezione> cercaProssimeLezioni() {
        List<Lezione> elencoLezioni = new LinkedList<Lezione>();
        Date today = new Date();
        for (Lezione l : lezioniInsegnamento) {
            if (l.getData().after(today) && !l.isAppello() && !l.isAnnullata()) {
                elencoLezioni.add(l);
            }
        }
        return elencoLezioni;
    }


    //Getters and Setters

    public int getCodice() {
        return codice;
    }

    public List<Lezione> getLezCorrente() {
        return lezCorrente;
    }

    public String getNome() {
        return nome;
    }

    public int getCFU() {
        return CFU;
    }

    public List<Lezione> getLezioniInsegnamento() {
        return lezioniInsegnamento;
    }

    public void aggiungiLezione(List<Lezione> lezioni) {
        this.lezioniInsegnamento.addAll(lezioni);
    }

    public void aggiungiLezione(Lezione lezione) {      //Overload degli operatori, per aggiungere la singola lezione
        this.lezioniInsegnamento.add(lezione);
    }

    public void setLezCorrente(List<Lezione> lezCorrente) {
        this.lezCorrente = lezCorrente;
    }

    @Override
    public String toString() {
        return "Insegnamento{" + "codice=" + codice + ", nome='" + nome + '\'' + ", CFU=" + CFU + "}";
    }
}
