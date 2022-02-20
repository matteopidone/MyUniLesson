package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.*;
import com.MyUniLesson.app.exception.CdlException;
import com.MyUniLesson.app.exception.DocentiException;
import com.MyUniLesson.app.exception.LezioneException;
import com.MyUniLesson.app.exception.StudenteException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {

    public static void main(String[] args) {
        MyUniLesson myUniLesson = MyUniLesson.getInstance();
        Menu(myUniLesson);
    }

    public static void Menu(MyUniLesson myUniLesson) {
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
        int scelta = 0;
        int codCdl;
        List<Insegnamento> insegnamentoList;
        Map<Integer, CorsoDiLaurea> cdlMap;
        Map<Integer, Insegnamento> insMap;
        Map<Integer, String> giorniSettimana = new HashMap<Integer, String>();

        giorniSettimana.put(0, "Domenica");
        giorniSettimana.put(1, "Lunedi");
        giorniSettimana.put(2, "Martedi");
        giorniSettimana.put(3, "Mercoledi");
        giorniSettimana.put(4, "Giovedi");
        giorniSettimana.put(5, "Venerdi");
        giorniSettimana.put(6, "Sabato");

        System.out.println("Benvenuto in MyUniLesson !!");
        do {
            System.out.print("\nMenu:\n" + "\t1. Inserisci una Lezione\n" + "\t2. Aggiungi la Partecipazione di uno Studente\n" + "\t3. Esegui l'appello di una Lezione\n" + "\t4. Annulla una Lezione\n" + "\n\t8. Visualizza i Corsi di Laurea, Studenti e Insegnamenti\n" + "\t9. Visualizza i Docenti e Insegnamenti\n" + "\t0. Esci" + "\n\nSeleziona l'operazione: ");
            try {
                scelta = Integer.parseInt(tastiera.readLine());
            } catch (NumberFormatException n) {
                scelta = 0;
            } catch (Exception e) {
                System.out.println(e);
            }

            switch (scelta) {
                case 0:
                    System.out.println("Uscita");
                    break; //esci
                case 1:
                    try {
                        char continua;

                        cdlMap = myUniLesson.mostraCdl();
                        System.out.println("\nI Corsi di Laurea:");
                        for (Map.Entry<Integer, CorsoDiLaurea> entry : cdlMap.entrySet()) {
                            System.out.println(entry.getValue().getCodice() + " - " + entry.getValue().getNome());
                        }
                        System.out.print("\nInserisci il codice del CdL: ");

                        codCdl = Integer.parseInt(tastiera.readLine());
                        insMap = myUniLesson.mostraInsegnamenti(codCdl);
                        System.out.println("\nGli Insegnamenti di: " + myUniLesson.getElencoCdl().get(codCdl).getNome());
                        for (Map.Entry<Integer, Insegnamento> entry : insMap.entrySet()) {
                            System.out.println(entry.getValue().getCodice() + " - " + entry.getValue().getNome() + " - CFU: " + entry.getValue().getCFU());
                        }
                        System.out.print("\nInserisci il codice dell'insegnamento: ");

                        int codIns = Integer.parseInt(tastiera.readLine());
                        myUniLesson.selezionaInsegnamento(codIns);
                        do {
                            boolean bool;
                            do {
                                bool = false;

                                System.out.println("\nInserisci i dati della Lezione");
                                System.out.print("Inserisci l'anno: ");
                                int year = Integer.parseInt(tastiera.readLine());
                                System.out.print("Inserisci il mese: (1-12) -> ");
                                int month = Integer.parseInt(tastiera.readLine());
                                System.out.print("Inserisci il giorno: ");
                                int day = Integer.parseInt(tastiera.readLine());
                                System.out.print("Inserisci l'ora: ");
                                int hour = Integer.parseInt(tastiera.readLine());
                                System.out.print("Inserisci i minuti: ");
                                int minutes = Integer.parseInt(tastiera.readLine());

                                Date d = new Date(year - 1900, month - 1, day, hour, minutes);

                                System.out.print("\nInserisci la durata della Lezione: ");
                                int durata = Integer.parseInt(tastiera.readLine());

                                System.out.print("Vuoi inserire la Lezione tutti i " + giorniSettimana.get(d.getDay()) + " successivi alla stessa ora ? (true o false) -> ");
                                boolean ricorrenza = Boolean.parseBoolean(tastiera.readLine());

                                try {
                                    myUniLesson.creaLezione(d, durata, ricorrenza);
                                    myUniLesson.confermaInserimento();
                                } catch (LezioneException l) {
                                    bool = true;
                                    System.out.println("Errore: " + l.getMessage() + "\nReinserisci i dati\n");
                                }
                            } while (bool);

                            System.out.print("\nVuoi inserire altre lezioni? (s/n) -> ");
                            continua = tastiera.readLine().charAt(0);

                        } while (continua == 's');
                        myUniLesson.terminaInserimento();
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    } finally {
                        break;
                    }

                case 2:
                    try {
                        boolean bool;
                        do {
                            bool = false;

                            System.out.print("\nInserisci la matricola dello studente: ");
                            String matricola = tastiera.readLine();

                            System.out.print("Inserisci il codice del CdL: ");
                            codCdl = Integer.parseInt(tastiera.readLine());

                            try {
                                myUniLesson.identificaStudente(matricola, codCdl);
                            } catch (StudenteException s) {
                                bool = true;
                                System.out.println("\nErrore: " + s.getMessage() + "\nReinserisci i dati");
                            } catch (CdlException c) {
                                bool = true;
                                System.out.println("\nErrore: " + c.getMessage() + "\nReinserisci i dati");
                            } catch (Exception e) {
                                bool = true;
                                System.out.println("\nErrore: " + e.getMessage() + "\nReinserisci i dati");
                            }
                        } while (bool);

                        insegnamentoList = myUniLesson.mostraLezioniPrenotabili();

                        Studente s = myUniLesson.getElencoCdl().get(codCdl).getStudenteSelezionato();
                        System.out.println("\nLe Lezioni prenotabili per " + s.getNome() + " " + s.getCognome() + " - " + s.getMatricola() + ":\n");
                        for (Insegnamento i : insegnamentoList) {
                            System.out.println(i.getCodice() + " - " + i.getNome() + " - CFU: " + i.getCFU());

                            List<Lezione> lezioni = i.getLezioniInsegnamento();
                            Collections.sort(lezioni);

                            if (lezioni.isEmpty()) {
                                System.out.println("\tNessuna lezione prenotabile.\n");
                            } else {
                                for (Lezione l : lezioni) {
                                    System.out.println("\tCodice: " + l.getCodice() + " - " + LocalDate.of(l.getData().getYear() + 1900, l.getData().getMonth() + 1, l.getData().getDate()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "   " + LocalTime.of(l.getData().getHours(), l.getData().getMinutes()) + " - " + LocalTime.of(l.getData().getHours() + l.getDurata(), l.getData().getMinutes()) + "   " + giorniSettimana.get(l.getData().getDay()));
                                }
                                System.out.println("");
                            }
                        }

                        System.out.print("Inserisci il codice della Lezione da prenotare: ");
                        int codice = Integer.parseInt(tastiera.readLine());

                        myUniLesson.creaPartecipazione(codice);
                        myUniLesson.confermaPartecipazione();
                        System.out.println("\nOperazione completata !");

                    } catch (LezioneException l) {
                        System.out.println("\nNon ci sono lezioni prenotabili");
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    } finally {
                        break;
                    }

                case 3:
                    try {
                        boolean bool;
                        int codiceDocente;

                        do {
                            bool = false;

                            System.out.print("Inserisci il codice del docente: ");
                            codiceDocente = Integer.parseInt(tastiera.readLine());

                            try {
                                myUniLesson.identificaDocente(codiceDocente);
                            } catch (DocentiException d) {
                                bool = true;
                                System.out.println("\nErrore: " + d.getMessage() + "\nReinserisci i dati");
                            }
                        } while (bool);

                        Map<Integer, Insegnamento> elencoInsegnamenti = myUniLesson.cercaInsegnamenti();

                        System.out.println("\nGli Insegnamenti di " + myUniLesson.getElencoDocenti().get(codiceDocente).getNome() + " " + myUniLesson.getElencoDocenti().get(codiceDocente).getCognome() + ":");
                        for (Map.Entry<Integer, Insegnamento> entry : elencoInsegnamenti.entrySet()) {
                            System.out.println("\t" + entry.getValue().getCodice() + " - " + entry.getValue().getNome() + " - CFU: " + entry.getValue().getCFU());
                        }

                        System.out.print("\nInserisci il codice dell'insegnamento: ");
                        int codiceInsegnamento = Integer.parseInt(tastiera.readLine());

                        List<Lezione> elencoLezioni = myUniLesson.cercaLezioni(codiceInsegnamento);
                        Collections.sort(elencoLezioni);

                        System.out.println("\nLe Lezioni di " + elencoInsegnamenti.get(codiceInsegnamento).getNome() + ":");
                        for (Lezione l : elencoLezioni) {
                            System.out.println("\tCodice: " + l.getCodice() + " - " + LocalDate.of(l.getData().getYear() + 1900, l.getData().getMonth() + 1, l.getData().getDate()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "   " + LocalTime.of(l.getData().getHours(), l.getData().getMinutes()) + " - " + LocalTime.of(l.getData().getHours() + l.getDurata(), l.getData().getMinutes()) + "   " + giorniSettimana.get(l.getData().getDay()));
                        }

                        System.out.print("\nInserisci il codice della Lezione: ");
                        int codiceLezione = Integer.parseInt(tastiera.readLine());
                        List<Studente> elencoStudenti = null;
                        try {
                            elencoStudenti = myUniLesson.iniziaAppello(codiceLezione);
                        } catch (StudenteException s) {
                            System.out.println(s.getMessage());
                            myUniLesson.terminaAppello();
                            System.out.println("\nOperazione completata !");
                            break;
                        }

                        System.out.println("\nElenco degli Studenti: (true o false)");
                        for (Studente s : elencoStudenti) {
                            System.out.print("\t" + s.getMatricola() + " - " + s.getNome() + " " + s.getCognome() + ": ");
                            boolean presenza = Boolean.parseBoolean(tastiera.readLine());
                            if (presenza == true) myUniLesson.inserisciPresenza(s, true);
                            else myUniLesson.inserisciPresenza(s, false);
                        }
                        myUniLesson.terminaAppello();

                        System.out.println("\nGli Studenti Assenti verranno avvisati tramite mail\nOperazione completata !");
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    } finally {
                        break;
                    }
                case 4: {
                    try {
                        boolean bool;
                        int codiceDocente;

                        do {
                            bool = false;

                            System.out.print("Inserisci il codice del docente: ");
                            codiceDocente = Integer.parseInt(tastiera.readLine());

                            try {
                                myUniLesson.identificaDocente(codiceDocente);
                            } catch (DocentiException d) {
                                bool = true;
                                System.out.println("\nErrore: " + d.getMessage() + "\nReinserisci i dati");
                            }
                        } while (bool);

                        Map<Integer, Insegnamento> elencoInsegnamenti = myUniLesson.cercaInsegnamenti();

                        System.out.println("\nGli Insegnamenti di " + myUniLesson.getElencoDocenti().get(codiceDocente).getNome() + " " + myUniLesson.getElencoDocenti().get(codiceDocente).getCognome() + ":");
                        for (Map.Entry<Integer, Insegnamento> entry : elencoInsegnamenti.entrySet()) {
                            System.out.println("\t" + entry.getValue().getCodice() + " - " + entry.getValue().getNome() + " - CFU: " + entry.getValue().getCFU());
                        }

                        System.out.print("\nInserisci il codice dell'insegnamento: ");
                        int codiceInsegnamento = Integer.parseInt(tastiera.readLine());

                        List<Lezione> elencoLezioni = myUniLesson.cercaProssimeLezioni(codiceInsegnamento);
                        Collections.sort(elencoLezioni);
                        System.out.println("\nLe Lezioni successive di " + elencoInsegnamenti.get(codiceInsegnamento).getNome() + ":");
                        if (elencoLezioni.isEmpty()) {
                            System.out.println("Non ci sono lezioni da annullare\n");
                            break;
                        }
                        for (Lezione l : elencoLezioni) {
                            System.out.println("\tCodice: " + l.getCodice() + " - " + LocalDate.of(l.getData().getYear() + 1900, l.getData().getMonth() + 1, l.getData().getDate()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "   " + LocalTime.of(l.getData().getHours(), l.getData().getMinutes()) + " - " + LocalTime.of(l.getData().getHours() + l.getDurata(), l.getData().getMinutes()) + "   " + giorniSettimana.get(l.getData().getDay()));
                        }

                        System.out.print("\nInserisci il codice della Lezione da annullare: ");
                        int codiceLezione = Integer.parseInt(tastiera.readLine());

                        myUniLesson.annullaLezione(codiceLezione);
                        System.out.println("\nLezione annullata con successo, gli studenti prenotati sono stati avvisati tramite mail\nOperazione completata !");

                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    } finally {
                        break;
                    }

                }
                case 8:
                    CorsoDiLaurea cdl;
                    Insegnamento insegnamento;
                    Studente studente;

                    System.out.println("\nI Corsi di Laurea:");
                    for (Map.Entry<Integer, CorsoDiLaurea> entryCdl : myUniLesson.getElencoCdl().entrySet()) {
                        cdl = entryCdl.getValue();
                        System.out.println("\n" + cdl.getCodice() + " - " + cdl.getNome());

                        System.out.println("\n\tGli Studenti:");
                        for (Map.Entry<String, Studente> entryStudente : cdl.getElencoStudenti().entrySet()) {
                            studente = entryStudente.getValue();
                            System.out.println("\t\t" + studente.getMatricola() + " - " + studente.getNome() + " " + studente.getCognome());
                        }

                        System.out.println("\n\tGli Insegnamenti:");
                        for (Map.Entry<Integer, Insegnamento> entryInsegnamento : cdl.getInsegnamenti().entrySet()) {
                            insegnamento = entryInsegnamento.getValue();
                            System.out.println("\t\t" + insegnamento.getCodice() + " - " + insegnamento.getNome() + " - CFU: " + insegnamento.getCFU());
                        }
                    }
                    break;
                case 9:
                    Docente docente;

                    System.out.println("\nI Docenti:");
                    for (Map.Entry<Integer, Docente> entryDocente : myUniLesson.getElencoDocenti().entrySet()) {
                        docente = entryDocente.getValue();
                        System.out.println("\n" + docente.getCodice() + " - " + docente.getNome() + " " + docente.getCognome());

                        System.out.println("\tGli Insegnamenti:");
                        for (Map.Entry<Integer, Insegnamento> entryInsegnamento : docente.getInsegnamenti().entrySet()) {
                            insegnamento = entryInsegnamento.getValue();
                            System.out.println("\t\t" + insegnamento.getCodice() + " - " + insegnamento.getNome() + " - CFU: " + insegnamento.getCFU());
                        }
                    }
                    break;
                default:
                    System.out.println("\nScelta non valida");
                    break;
            }
        } while (scelta != 0);

    }
}
