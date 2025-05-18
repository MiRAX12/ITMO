package org.exampleMirax;//import Commands.Add;

import utility.Handler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.sql.Date;

/**
 * Main class which creates a handler and runs it to handle
 * all following input
 *
 * @author Mirax
 * @see Handler
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(LocalDate.now().toString());
        Handler handler = new Handler();
        handler.run();
    }
}


