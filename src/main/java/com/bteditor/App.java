package com.bteditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for the Behaviour Tree Editor application.
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
    	Parent xml = new FXMLLoader(App.class.getResource("bteditor.fxml")).load();
    	scene = new Scene(xml, 540, 385);
        stage.setScene(scene);
        stage.show();
    }
    
    /** 
     * Launches the JavaFX application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
