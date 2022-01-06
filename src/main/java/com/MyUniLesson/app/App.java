package com.MyUniLesson.app;


import com.MyUniLesson.app.domain.MyUniLesson;

import java.io.*;
import java.util.Date;

import static java.lang.System.in;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main( String[] args )
    {
        MyUniLesson myUniLesson = MyUniLesson.getInstance();

        myUniLesson.mostraCdl();
        myUniLesson.mostraInsegnamenti(1);
        myUniLesson.selezionaInsegnamento(2);

        myUniLesson.creaLezione(new Date(), 2);
        myUniLesson.confermaInserimento();
        myUniLesson.mostraCdl();



    }
}
