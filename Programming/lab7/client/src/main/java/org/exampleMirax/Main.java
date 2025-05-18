package org.exampleMirax;//import Commands.Add;

import Network.User;
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
        User user = new User(null, null);

        Handler handler = new Handler();
        handler.run();
    }
}


