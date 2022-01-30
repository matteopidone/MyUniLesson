package com.MyUniLesson.app.domain;

import java.io.*;
import java.util.*;

public class MyUniLesson {
    private static MyUniLesson myUniLesson;
    private Map<Integer, CorsoDiLaurea> elencoCdl;
    private List<Lezione> elencoLezioni;

    private CorsoDiLaurea cdlSelezionato;
    private Insegnamento insSelezionato;
    private Lezione lezioneSelezionata;
    private List<Lezione> lezCorrente;

    public MyUniLesson() { //Singleton
        this.elencoLezioni = new LinkedList<Lezione>();
        this.elencoCdl = new HashMap<Integer, CorsoDiLaurea>();
        loadCdl();
    }

    public List<Lezione> getLezCorrente() {
        return lezCorrente;
    }

    public static MyUniLesson getInstance() {
        if (myUniLesson == null)
            myUniLesson = new MyUniLesson();
        return myUniLesson;
    }

    public void loadCdl() {

        try {

            BufferedReader bfCdl = new BufferedReader(new FileReader("CorsiDiLaurea.txt"));
            BufferedReader bfIns = new BufferedReader(new FileReader("Insegnamenti.txt"));
            BufferedReader bfStud = new BufferedReader(new FileReader("Studenti.txt"));

            String[] strings;
            String str;
            Map<Integer, CorsoDiLaurea> mapCdl = new HashMap<Integer, CorsoDiLaurea>();

            while ((str = bfCdl.readLine()) != null) {
                strings = str.split("-");
                mapCdl.put(Integer.parseInt(strings[0]), new CorsoDiLaurea(Integer.parseInt(strings[0]), strings[1]));
            }

            while ((str = bfIns.readLine()) != null) {
                strings = str.split("-");
                CorsoDiLaurea cdl = mapCdl.get(Integer.parseInt(strings[0]));
                cdl.inserisciInsegnamento(Integer.parseInt(strings[1]), new Insegnamento(Integer.parseInt(strings[1]), strings[2], Integer.parseInt(strings[3])));
            }

            while ((str = bfStud.readLine()) != null) {
                strings = str.split("-");
                CorsoDiLaurea cdl = mapCdl.get(Integer.parseInt(strings[0]));
                cdl.inserisciStudente(strings[1], new Studente(strings[1], strings[2], strings[3]));

            }
            elencoCdl = mapCdl; //salvo tutti i corsi di laurea dentro in elencoCdl

        } catch (Exception e) {
            System.err.println("errore: " + e);
        }
    }

    //UC1

    public Map<Integer, CorsoDiLaurea> mostraCdl() {
        return elencoCdl;
    }

    public Map<Integer, Insegnamento> mostraInsegnamenti(int codiceCdl) throws Exception {
        cdlSelezionato = elencoCdl.get(codiceCdl);
        if (cdlSelezionato == null) {
            throw new Exception("Errore nel corso di laurea inserito");
        } else {
            return cdlSelezionato.getInsegnamenti();
        }
    }

    public void selezionaInsegnamento(int codiceInsegnamento) throws Exception {
        insSelezionato = cdlSelezionato.cercaInsegnamenti(codiceInsegnamento);
        if(insSelezionato == null) throw new Exception("Errore nell'insegnamento inserito");
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
            if (insSelezionato.verificaDisponibilita(data)) {
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

    public void confermaInserimento() throws Exception {

        if (lezCorrente != null) {
            insSelezionato.aggiungiLezione(lezCorrente);
            elencoLezioni.addAll(lezCorrente);
        } else {
            throw new Exception("Errore: lezione non inserita");
        }
        deseleziona();
    }

    //UC2

    public boolean identificaStudente(String matricola, int codiceCdl) throws Exception {
        cdlSelezionato = elencoCdl.get(codiceCdl);
        return cdlSelezionato.cercaStudente(matricola);
    }

    public List<Insegnamento> mostraLezioniPrenotabili() throws Exception {
        //Non consideriamo lo scenario alternativo in cui non sono presenti lezioni prenotabili
        return cdlSelezionato.cercaLezioni();
    }

    public void creaPartecipazione(int codiceLezione) throws Exception {
        for (Lezione l : elencoLezioni) {
            if (l.getCodice() == codiceLezione) {
                lezioneSelezionata = l;
                break;
            }

        }
        if (lezioneSelezionata != null) {
            lezioneSelezionata.generaPartecipazione(cdlSelezionato.getStudenteSelezionato());
            System.out.println("Partecipazione Creata ! ");
        }
    }

    public void confermaPartecipazione() throws Exception {
        String matricola = cdlSelezionato.getMatricolaStudenteSelezionato();
        lezioneSelezionata.aggiungiPartecipazione(matricola);
        System.out.println("Partecipazione Confermata ! ");
        deseleziona();
    }


    // Getters and Setters

    public List<Lezione> getElencoLezioni() {
        return elencoLezioni;
    }

    // Others

    public void deseleziona() {
        cdlSelezionato = null;
        insSelezionato = null;
        lezioneSelezionata = null;
        lezCorrente = null;
    }
}
