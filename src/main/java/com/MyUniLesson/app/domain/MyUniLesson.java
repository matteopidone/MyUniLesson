package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.*;

import java.io.*;
import java.util.*;

public class MyUniLesson {
    private static MyUniLesson myUniLesson;
    private Map<Integer, CorsoDiLaurea> elencoCdl;
    private List<Lezione> elencoLezioni;
    private CorsoDiLaurea cdlSelezionato;
    private Lezione lezioneSelezionata;


    public MyUniLesson() { //Singleton
        this.elencoLezioni = new LinkedList<Lezione>();
        this.elencoCdl = new HashMap<Integer, CorsoDiLaurea>();
        loadCdl();
    }

    public List<Lezione> getLezCorrente() {
        return cdlSelezionato.getLezCorrente();
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
            elencoCdl = mapCdl;

        } catch (Exception e) {
            System.err.println("errore: " + e);
        }
    }

    //UC1

    public Map<Integer, CorsoDiLaurea> mostraCdl() {
        return elencoCdl;
    }

    public Map<Integer, Insegnamento> mostraInsegnamenti(int codiceCdl) throws CdlException {
        cdlSelezionato = elencoCdl.get(codiceCdl);
        if (cdlSelezionato == null) {
            throw new CdlException("Errore nel corso di laurea inserito");
        } else {
            return cdlSelezionato.getInsegnamenti();
        }
    }

    public void selezionaInsegnamento(int codiceInsegnamento) throws InsegnamentoException {
        cdlSelezionato.cercaInsegnamenti(codiceInsegnamento);

    }

    public void creaLezione(Date data, int durata, boolean ricorrenza) {
        cdlSelezionato.creaLezione(data, durata, ricorrenza);

    }

    public void confermaInserimento() throws LezioneException {
        elencoLezioni.addAll(cdlSelezionato.confermaLezioni());
        //deseleziona();

    }

    //UC2

    public boolean identificaStudente(String matricola, int codiceCdl) throws StudenteException {
        cdlSelezionato = elencoCdl.get(codiceCdl);
        return cdlSelezionato.cercaStudente(matricola);
    }

    public List<Insegnamento> mostraLezioniPrenotabili() throws LezioneException {
        //Non consideriamo lo scenario alternativo in cui non sono presenti lezioni prenotabili
        return cdlSelezionato.cercaLezioni();
    }

    public void creaPartecipazione(int codiceLezione) throws PartecipazioneException {
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
        //deseleziona();
    }


    // Getters and Setters

    public List<Lezione> getElencoLezioni() {
        return elencoLezioni;
    }

    // Others

    /*public void deseleziona() {
        cdlSelezionato.deseleziona();
        cdlSelezionato = null;
        lezioneSelezionata = null;
    }*/
}
