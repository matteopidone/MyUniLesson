package com.MyUniLesson.app.domain;

import java.io.*;
import java.util.*;

public class MyUniLesson {
    private static MyUniLesson myUniLesson;
    private Map<Integer, CorsoDiLaurea> elencoCdl;
    private List<Lezione> elencoLezioni;

    private CorsoDiLaurea cdlCorrente;
    private Insegnamento insCorrente;
    private Lezione lezCorrente;

    public MyUniLesson(){ //Singleton
        this.elencoLezioni = new LinkedList<Lezione>();
        this.elencoCdl = new HashMap<Integer, CorsoDiLaurea>();
        loadCdl();
    }

    public static MyUniLesson getInstance(){
        if(myUniLesson == null)
            myUniLesson = new MyUniLesson();
        return myUniLesson;
    }

    public void loadCdl(){

        try{

            BufferedReader bfCdl = new BufferedReader(new FileReader("CorsiDiLaurea.txt"));
            BufferedReader bfIns = new BufferedReader(new FileReader("Insegnamenti.txt"));

            String[] strings;
            String str;
            Map<Integer, CorsoDiLaurea> mapCdl = new HashMap<Integer, CorsoDiLaurea>();

            while((str = bfCdl.readLine()) != null){
                strings = str.split("-");
                mapCdl.put(Integer.parseInt(strings[0]),new CorsoDiLaurea(Integer.parseInt(strings[0]), strings[1]));
            }

            while((str = bfIns.readLine()) != null){
                strings = str.split("-");
                CorsoDiLaurea cdl = mapCdl.get(Integer.parseInt(strings[0]));
                cdl.inserisciInsegnamento(Integer.parseInt(strings[1]),new Insegnamento(Integer.parseInt(strings[1]), strings[2], Integer.parseInt(strings[3])));
            }
            elencoCdl = mapCdl; //salvo tutti i corsi di laurea dentro in elencoCdl

        }catch (Exception e) {
            System.err.println("errore: " + e);
        }
    }

    public void mostraCdl(){
            System.out.println(elencoCdl);
    }

    public void mostraInsegnamenti(int codiceCdl){
     cdlCorrente = elencoCdl.get(codiceCdl);
     System.out.println(cdlCorrente.getInsegnamenti());
    }

    public void selezionaInsegnamento(int codiceInsegnamento){
        insCorrente = cdlCorrente.cercaInsegnamenti(codiceInsegnamento);
    }

    public void creaLezione(Date data, int durata){
        if(insCorrente.verificaDisponibilita(data)){
            lezCorrente = new Lezione(data, durata);
        } else {
            lezCorrente = null;
            System.out.println("Errore: lezione già presente nello stesso giorno");
        }
    }

    public void confermaInserimento(){
        if(lezCorrente != null) {
            insCorrente.aggiungiLezione(lezCorrente);
            elencoLezioni.add(lezCorrente);
        } else {
            System.out.println("Errore: lezione non inserita");
        }
    }

    // Getters and Setters

    public List<Lezione> getElencoLezioni() {
        return elencoLezioni;
    }
}
