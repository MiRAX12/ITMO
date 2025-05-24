package org.exampleMirax;//import Commands.Add;

import Network.User;
import database.Database;
import managers.CollectionManager;
import model.Worker;
import server.Server;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main class which creates a handler and runs it to handle
 * all following input
 *
 * @author Mirax
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Database.establishConnection();

        Server server = new Server(5505);
        server.run();
    }
}





