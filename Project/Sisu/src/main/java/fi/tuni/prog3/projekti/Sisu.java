/**
 * Ohjelmointi-3 Harjoitustyö: Sisu-projekti.
 * @author Antti-Jussi Isoviita, H283435
 * @author Aleksi Puranen, H283752
 */

package fi.tuni.prog3.projekti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A class for representing Sisu add-on JavaFX app.
 */
public class Sisu extends Application {

    protected static Scene scene;
    protected static String record = "studentrecord.json";
    protected static Boolean logged = false;

    /**
     * For initializing application and loading visual guidelines.
     */
    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("primary"), 1000, 600);
        
        stage.setTitle("Sisu");
        stage.setScene(scene);

        scene.getStylesheets().add(Sisu.class.getResource("outlook.css").toExternalForm());

        stage.show();
    }

    /**
     * Switches current window to next one.
     * @param fxml Visual guideline document for current window.
     * @throws IOException Throws error if visual representation document is missing.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads different windows using document for visual representation.
     * @param fxml Document for visual representation.
     * @throws IOException Throws error if visual representation document is missing.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Sisu.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    /**
     * Initializes the app.
     * @param args Arguments given in command-line execution.
     */
    public static void main(String[] args) {    
        launch();
    }

}
