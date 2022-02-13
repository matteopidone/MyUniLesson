package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.*;

import java.io.*;
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


        do {
            System.out.println("Benvenuto:\n" +
                    "Seleziona l'operazione:\n" +
                    "1. Inserisci Lezione\n" +
                    "2. Inserisci Partecipazione dello studente\n" +
                    "3. Fai l'Appello\n" +
                    "0. Esci");
            try {
                scelta = Integer.parseInt(tastiera.readLine());
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
                        for (Map.Entry<Integer, CorsoDiLaurea> entry : cdlMap.entrySet()) {
                            System.out.println(entry.getValue().getCodice() + " - " + entry.getValue().getNome());
                        }

                        System.out.println("Inserisci il codice del CdL: ");
                        codCdl = Integer.parseInt(tastiera.readLine());
                        insMap = myUniLesson.mostraInsegnamenti(codCdl);
                        for (Map.Entry<Integer, Insegnamento> entry : insMap.entrySet()) {
                            System.out.println(entry.getValue().getCodice() + " - " + entry.getValue().getNome() + " - CFU: " + entry.getValue().getCFU());
                        }

                        System.out.println("Inserisci il codice dell'insegnamento: ");
                        int codIns = Integer.parseInt(tastiera.readLine());
                        myUniLesson.selezionaInsegnamento(codIns);
                        do {
                            System.out.println("Inserisci data e ora (aaaa 'invio' mm 'invio' gg 'invio'  ora 'invio'): ");
                            //Bisogna premere invio dopo l'inserimento di ciascun parametro
                            Date d = new Date(Integer.parseInt(tastiera.readLine()) - 1900, Integer.parseInt(tastiera.readLine()) - 1,
                                    Integer.parseInt(tastiera.readLine()), Integer.parseInt(tastiera.readLine()), 0);

                            System.out.println("Inserisci durata: ");
                            int durata = Integer.parseInt(tastiera.readLine());

                            System.out.println("La lezione deve essere ricorrente? (true/false)");
                            boolean ricorrenza = Boolean.parseBoolean(tastiera.readLine());

                            myUniLesson.creaLezione(d, durata, ricorrenza);
                            myUniLesson.confermaInserimento();

                            System.out.println("Vuoi inserire altre lezioni? (s/n)");
                            continua = tastiera.readLine().charAt(0);

                        } while (continua == 's');

                        System.out.println(myUniLesson.getElencoLezioni()); //inserito per confermare l'inserimento
                    } catch (Exception e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    } finally {
                        break;
                    }

                case 2:
                    try {
                        System.out.println("Inserisci la matricola dello studente: ");
                        String matricola = tastiera.readLine();
                        System.out.println("Inserisci il codice del CdL: ");
                        codCdl = Integer.parseInt(tastiera.readLine());
                        myUniLesson.identificaStudente(matricola, codCdl);
                        insegnamentoList = myUniLesson.mostraLezioniPrenotabili();
                        for (Insegnamento i : insegnamentoList) {
                            System.out.println(i.getCodice() + " - " + i.getNome() + " - CFU: " + i.getCFU());
                            if (i.getLezioniInsegnamento().isEmpty()) {
                                System.out.println("Nessuna lezione prenotabile.\n");
                            } else {
                                System.out.println(i.getLezioniInsegnamento() + "\n");
                            }
                        }

                        System.out.println("Inserisci il codice della Lezione da prenotare: ");
                        int codice = Integer.parseInt(tastiera.readLine());
                        myUniLesson.creaPartecipazione(codice);
                        myUniLesson.confermaPartecipazione();
                    } catch (Exception e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    } finally {
                        break;
                    }

                case 3:
                    try{
                        System.out.println("Inserisci il codice del docente: ");
                        int codiceDocente = Integer.parseInt(tastiera.readLine());
                        myUniLesson.identificaDocente(codiceDocente);
                        Map<Integer, Insegnamento> elencoInsegnamenti= myUniLesson.cercaInsegnamenti();
                        for(Map.Entry<Integer, Insegnamento> entry : elencoInsegnamenti.entrySet()){
                            System.out.println(entry.getValue() + "\n");
                        }
                        System.out.println("Inserisci il codice dell'insegnamento: ");
                        int codiceInsegnamento = Integer.parseInt(tastiera.readLine());
                        List<Lezione> elencoLezioni= myUniLesson.cercaLezioni(codiceInsegnamento);
                        for(Lezione l : elencoLezioni){
                            System.out.println(l + "\n");
                        }
                        System.out.println("Inserisci il codice della lezione: ");
                        int codiceLezione = Integer.parseInt(tastiera.readLine());
                        List<Studente> elencoStudenti= myUniLesson.iniziaAppello(codiceLezione);
                        for(Studente s: elencoStudenti){
                            System.out.println("Lo studente "+s+" e' presente? (true/false) ");
                            boolean presenza = Boolean.parseBoolean(tastiera.readLine());
                            if(presenza==true)
                                myUniLesson.inserisciPresenza(s, true);
                            else
                                myUniLesson.inserisciPresenza(s, false);
                        }
                        myUniLesson.terminaAppello();

                        System.out.println("Ci sono "+myUniLesson.getPresenti()+" studenti presenti e "+myUniLesson.getAssenti()+" studenti assenti.");     //Per vedere quanti alunni presenti o assenti ha inserito nelle apposite liste.

                    } catch (Exception e) {
                        System.out.println("ERRORE: " + e.getMessage());
                    } finally {
                        break;
                    }

                default:
                    System.out.println("Scelta non valida");
                    break;
            }
        } while (scelta != 0);


    }
}
