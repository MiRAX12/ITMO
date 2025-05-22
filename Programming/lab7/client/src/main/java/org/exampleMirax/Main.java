package org.exampleMirax;//import Commands.Add;

import Network.User;
import Network.UserBuilder;
import utility.Handler;

import java.io.IOException;

/**
 * Main class which creates a handler and runs it to handle
 * all following input
 *
 * @author Mirax
 * @see Handler
 */
public class Main {

    public static void main(String[] args) throws IOException {
        User user = new UserBuilder().setUserName(null).setPassword(null).createUser();

        Handler handler = new Handler();
        handler.run();
    }
}


