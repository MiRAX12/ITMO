package org.exampleMirax;//import Commands.Add;

import gui.loginAndRegister.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import utility.Handler;

import java.io.IOException;

import static javafx.application.Application.launch;

/**
 * Main class which creates a handler and runs it to handle
 * all following input
 *
 * @author Mirax
 * @see Handler
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        LoginWindow loginWindow = new LoginWindow(stage);
        loginWindow.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
//        Handler handler = new Handler();
//        handler.run();
    }
}


