package com.MyUniLesson.app.domain;

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

    public void creaLezione(Date data, int durata, boolean ricorrenza) {
        lezCorrente = new LinkedList<Lezione>();
        Date end;

        if (data.getMonth() > 5) {
            end = new Date(data.getYear(), Calendar.DECEMBER, 31);
        } else {
            end = new Date(data.getYear(), Calendar.JUNE, 30);
        }
        do {
            if (verificaDisponibilita(data)) {
                lezCorrente.add(new Lezione(data, durata));
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

    public List<Lezione> confermaLezioni() throws Exception {
        if (lezCorrente != null) {
            aggiungiLezione(lezCorrente);
        } else {
            throw new Exception("Errore: lezione non inserita");
        }
        return lezCorrente;
    }

    public List<Lezione> cercaLezioniPrenotabili(String matricola) {
        List<Lezione> elencoLezioniPrenotabili = new LinkedList<Lezione>();
        for (Lezione l : lezioniInsegnamento) {
            if (!l.verificaPartecipazione(matricola)) {
                elencoLezioniPrenotabili.add(l);
            }
        }
        return elencoLezioniPrenotabili;
    }

    //Getters and Setters

    public int getCodice() {
        return codice;
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

    // Others

    public void deseleziona() {
        lezCorrente = null;
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
