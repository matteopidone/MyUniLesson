package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.MyUniLesson;

import java.io.*;
import java.util.Date;

import static java.lang.System.in;

public class App {

    public static void main(String[] args) {
        MyUniLesson myUniLesson = MyUniLesson.getInstance();
        Menu(myUniLesson);
    }

    public static void Menu(MyUniLesson myUniLesson) {
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
        int scelta;
        int codCdl;

        try {
            do {
                System.out.println("Benvenuto:\n" +
                        "Seleziona l'operazione:\n" +
                        "1. Inserisci Lezione\n" +
                        "2. Inserisci Partecipazione dello studente\n" +
                        "0. Esci");
                scelta = Integer.parseInt(tastiera.readLine());

                switch (scelta) {
                    case 0:
                        System.out.println("Uscita");
                        break; //esci
                    case 1:
                        char continua;
                        myUniLesson.mostraCdl();

                        System.out.println("Inserisci il codice del CdL");
                        codCdl = Integer.parseInt(tastiera.readLine());
                        myUniLesson.mostraInsegnamenti(codCdl);

                        System.out.println("Inserisci il codice dell'insegnamento");
                        int codIns = Integer.parseInt(tastiera.readLine());
                        myUniLesson.selezionaInsegnamento(codIns);
                        do {
                            System.out.println("Inserisci data e ora (aaaa 'invio' mm 'invio' gg 'invio'  ora 'invio')");
                            //Bisogna premere invio dopo l'inserimento di ciascun parametro
                            Date d = new Date(Integer.parseInt(tastiera.readLine()) - 1900, Integer.parseInt(tastiera.readLine()) - 1,
                                    Integer.parseInt(tastiera.readLine()), Integer.parseInt(tastiera.readLine()), 0);

                            System.out.println("Inserisci durata");
                            int durata = Integer.parseInt(tastiera.readLine());

                            System.out.println("La lezione deve essere riccorrente? (true/false)");
                            boolean ricorrenza = Boolean.parseBoolean(tastiera.readLine());

                            myUniLesson.creaLezione(d, durata, ricorrenza);
                            myUniLesson.confermaInserimento();
                            System.out.println("Lezioni inserite!");

                            System.out.println("Vuoi inserire altre lezioni ? (s/n)");
                            continua = tastiera.readLine().charAt(0);

                        } while (continua == 's');

                        System.out.println(myUniLesson.getElencoLezioni()); //inserito per confermare l'inserimento

                        break;
                    case 2:
                        System.out.println("Inserisci la matricola dello studente");
                        String matricola = tastiera.readLine();
                        System.out.println("Inserisci il codice del CdL");
                        codCdl = Integer.parseInt(tastiera.readLine());
                        myUniLesson.identificaStudente(matricola, codCdl);
                        myUniLesson.mostraLezioniPrenotabili();

                        System.out.println("Inserisci il codice della Lezione da prenotare");
                        int codice = Integer.parseInt(tastiera.readLine());
                        myUniLesson.creaPartecipazione(codice);
                        myUniLesson.confermaPartecipazione();

                        break;
                    default:
                        System.out.println("Scelta non valida");
                        break;
                }
            } while (scelta != 0);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
