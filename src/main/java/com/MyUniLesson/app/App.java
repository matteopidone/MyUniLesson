package com.MyUniLesson.app;

import com.MyUniLesson.app.domain.MyUniLesson;
import java.io.*;
import java.util.Date;

import static java.lang.System.in;

public class App
{

    public static void main( String[] args )
    {
        MyUniLesson myUniLesson = MyUniLesson.getInstance();
        Menu(myUniLesson);
    }

    public static void Menu(MyUniLesson myUniLesson){
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
        int scelta;

        try{
            do {
                System.out.println("Benvenuto:\nSeleziona l'operazione:\n1. Inserisci lezione");
                scelta = Integer.parseInt(tastiera.readLine());

            }while (scelta > 1 || scelta < 0);

            switch (scelta){
                case 0 :
                    System.out.println("Uscita");
                    break; //esci

                case 1 :
                    char concludi;
                    myUniLesson.mostraCdl();

                    System.out.println("Inserisci il codice del CdL");
                    int codCdl = Integer.parseInt(tastiera.readLine());
                    myUniLesson.mostraInsegnamenti(codCdl);

                    System.out.println("Inserisci il codice dell'insegnamento");
                    int codIns = Integer.parseInt(tastiera.readLine());
                    myUniLesson.selezionaInsegnamento(codIns);
                    do{
                        System.out.println("Inserisci data e ora (aaaa 'invio' mm 'invio' gg 'invio')");
                        //Bisogna premere invio dopo l'inserimento di ciascun parametro
                        Date d = new Date(Integer.parseInt(tastiera.readLine())-1900, Integer.parseInt(tastiera.readLine()) - 1,
                                Integer.parseInt(tastiera.readLine()), Integer.parseInt(tastiera.readLine()), 0);

                        System.out.println("Inserisci durata");
                        int durata = Integer.parseInt(tastiera.readLine());

                        System.out.println("La lezione deve essere riccorrente? (true/false)");
                        boolean ricorrenza =Boolean.parseBoolean(tastiera.readLine());

                        myUniLesson.creaLezione(d, durata, ricorrenza);
                        myUniLesson.confermaInserimento();
                        System.out.println("Lezioni inserite!");

                        System.out.println("Vuoi concludere? (s/n)");
                        concludi = tastiera.readLine().charAt(0);


                    }while(concludi != 's');

                    //System.out.println(myUniLesson.getElencoLezioni()); //inserito per confermare l'inserimento
                    //myUniLesson.mostraCdl();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
