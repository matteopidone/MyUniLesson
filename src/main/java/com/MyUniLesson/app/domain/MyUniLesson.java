package com.MyUniLesson.app.domain;

import com.MyUniLesson.app.exception.*;

import javax.mail.MessagingException;
import java.io.*;
import java.util.*;

public class MyUniLesson {
    private static MyUniLesson myUniLesson;
    private Map<Integer, CorsoDiLaurea> elencoCdl;
    private List<Lezione> elencoLezioni;
    private CorsoDiLaurea cdlSelezionato;
    private Lezione lezioneSelezionata;
    private Docente docenteSelezionato;
    private Map<Integer, Docente> elencoDocenti;


    public MyUniLesson() { //Singleton
        this.elencoLezioni = new LinkedList<Lezione>();
        this.elencoCdl = new HashMap<Integer, CorsoDiLaurea>();
        this.elencoDocenti = new HashMap<Integer, Docente>();
        loadCdl();
        loadDocenti();
        loadLezioni();
        loadPartecipazioni();
    }

    public List<Lezione> getLezCorrente() {
        return cdlSelezionato.getLezCorrente();
    }

    public static MyUniLesson getInstance() {
        if (myUniLesson == null) myUniLesson = new MyUniLesson();
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
                cdl.inserisciStudente(strings[1], new Studente(strings[1], strings[2], strings[3], strings[4]));

            }
            elencoCdl = mapCdl;

        } catch (Exception e) {
            System.err.println("errore: " + e.getMessage());
        }
    }

    public void loadDocenti() {
        try {
            BufferedReader bfDocenti = new BufferedReader(new FileReader("Docenti.txt"));
            BufferedReader bfInsErogati = new BufferedReader(new FileReader("InsegnamentiErogati.txt"));
            String[] strings;
            String str;
            while ((str = bfDocenti.readLine()) != null) {
                strings = str.split("-");
                elencoDocenti.put(Integer.parseInt(strings[0]), new Docente(Integer.parseInt(strings[0]), strings[1], strings[2]));
            }

            while ((str = bfInsErogati.readLine()) != null) {

                strings = str.split("-");
                CorsoDiLaurea cdl = elencoCdl.get(Integer.parseInt(strings[1]));
                Insegnamento i = cdl.getInsegnamenti().get(Integer.parseInt(strings[2]));
                Docente d = elencoDocenti.get(Integer.parseInt(strings[0]));
                d.aggiungiInsegnamento(i);
            }


        } catch (Exception e) {
            System.err.println("errore: " + e.getMessage());
        }
    }

    public void loadLezioni() {
        CorsoDiLaurea cdl;
        Insegnamento i;
        Lezione l;

        try {
            BufferedReader bfLezioni = new BufferedReader(new FileReader("Lezioni.txt"));
            String[] strings;
            String str;
            while ((str = bfLezioni.readLine()) != null) {
                strings = str.split("-");
                cdl = elencoCdl.get(Integer.parseInt(strings[0]));
                i = cdl.getInsegnamenti().get(Integer.parseInt(strings[1]));
                l = new Lezione(Integer.parseInt(strings[2]), new Date(Integer.parseInt(strings[3]) - 1900, Integer.parseInt(strings[4]) - 1, Integer.parseInt(strings[5]), Integer.parseInt(strings[6]), 0), Integer.parseInt(strings[7]), i);
                i.aggiungiLezione(l);
                elencoLezioni.add(l);
            }
        } catch (Exception e) {
            System.err.println("errore: " + e.getMessage());
        }
    }

    public void loadPartecipazioni() {
        Studente s;
        try {
            BufferedReader bfPartecipazioni = new BufferedReader(new FileReader("Partecipazioni.txt"));
            String[] strings;
            String str;
            while ((str = bfPartecipazioni.readLine()) != null) {
                strings = str.split("-");
                s = elencoCdl.get(Integer.parseInt(strings[1])).getElencoStudenti().get(strings[2]);            //Preleva lo studente

                for (Lezione l : elencoLezioni) {
                    if (l.getCodice() == Integer.parseInt(strings[0])) {
                        l.generaPartecipazione(s);
                        l.aggiungiPartecipazione(s.getMatricola());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("errore: " + e.getMessage());
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

    public void creaLezione(Date data, int durata, boolean ricorrenza) throws LezioneException {
        cdlSelezionato.creaLezione(data, durata, ricorrenza);
    }

    public void confermaInserimento() throws LezioneException {
        elencoLezioni.addAll(cdlSelezionato.confermaLezioni());
        cdlSelezionato.getInsSelezionato().setLezCorrente(null);

    }

    public void terminaInserimento() {
        cdlSelezionato.setInsSelezionato(null);
        cdlSelezionato = null;

    }

    //UC2

    public boolean identificaStudente(String matricola, int codiceCdl) throws Exception {
        cdlSelezionato = elencoCdl.get(codiceCdl);
        if (cdlSelezionato == null) {
            throw new CdlException("Corso di Laurea inserito non valido");
        }
        return cdlSelezionato.cercaStudente(matricola);
    }

    public List<Insegnamento> mostraLezioniPrenotabili() throws LezioneException {
        //Non consideriamo lo scenario alternativo in cui non sono presenti lezioni prenotabili.
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
        }
    }

    public void confermaPartecipazione() throws Exception {
        String matricola = cdlSelezionato.getMatricolaStudenteSelezionato();
        lezioneSelezionata.aggiungiPartecipazione(matricola);

        cdlSelezionato.setStudenteSelezionato(null);
        lezioneSelezionata = null;
        cdlSelezionato = null;
    }

    //UC5

    public Map<Integer, Insegnamento> cercaInsegnamenti() {
        return docenteSelezionato.getInsegnamenti();
    }

    public List<Lezione> cercaProssimeLezioni(int codiceInsegnamento) throws Exception {
        return docenteSelezionato.cercaProssimeLezioni(codiceInsegnamento);
    }

    public void annullaLezione(int codiceLezione) {
        for (Lezione l : elencoLezioni) {
            if (l.getCodice() == codiceLezione) {
                lezioneSelezionata = l;
                break;
            }
        }
        lezioneSelezionata.comunicaAnnullamento();
        lezioneSelezionata.setAnnullata(true);

        docenteSelezionato = null;
        lezioneSelezionata = null;
    }

    // UC6

    public void identificaDocente(int codiceDocente) throws Exception {
        docenteSelezionato = elencoDocenti.get(codiceDocente);
        if (docenteSelezionato == null) {
            throw new DocentiException("Docente non trovato.");
        }
    }

    public List<Lezione> cercaLezioni(int codiceInsegnamento) throws Exception {
        return docenteSelezionato.cercaLezioni(codiceInsegnamento);
    }

    public List<Studente> iniziaAppello(int codiceLezione) throws Exception {
        for (Lezione l : elencoLezioni) {
            if (l.getCodice() == codiceLezione) {
                lezioneSelezionata = l;
                break;
            }
        }

        if (lezioneSelezionata == null) {
            throw new LezioneException("Lezione non trovata.");
        }
        lezioneSelezionata.creaElenchiAppello();

        List<Studente> elencoStudenti = lezioneSelezionata.cercaStudenti();
        if (elencoStudenti.isEmpty()) {
            throw new StudenteException("Non ci sono studenti prenotati a questa Lezione");
        }
        return elencoStudenti;
    }

    public void inserisciPresenza(Studente studente, boolean presenza) throws Exception {
        lezioneSelezionata.inserisciPresenza(studente, presenza);
    }

    public void terminaAppello() {
        lezioneSelezionata.setElencoPartecipazioni(null);
        lezioneSelezionata.setAppello(true);

        docenteSelezionato = null;
        lezioneSelezionata = null;
    }

    // Getters and Setters

    public List<Lezione> getElencoLezioni() {
        return elencoLezioni;
    }

    public Map<Integer, Docente> getElencoDocenti() {
        return elencoDocenti;
    }

    public Map<Integer, CorsoDiLaurea> getElencoCdl() {
        return elencoCdl;
    }

    public CorsoDiLaurea getCdlSelezionato() {
        return cdlSelezionato;
    }

    public Lezione getLezioneSelezionata() {
        return lezioneSelezionata;
    }

    public Docente getDocenteSelezionato() {
        return docenteSelezionato;
    }
}
