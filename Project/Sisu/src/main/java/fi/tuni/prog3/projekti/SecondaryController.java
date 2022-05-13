/**
 * Ohjelmointi-3 Harjoitustyö: Sisu-projekti, SecondaryController.
 * @author Antti-Jussi Isoviita, H283435
 * @author Aleksi Puranen, H283752
 */
package fi.tuni.prog3.projekti;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for secondary window.
 */
public class SecondaryController extends Sisu {
    @FXML protected Text shownName;
    @FXML protected Text shownNumber;
    @FXML private Button informationButton;
    @FXML private Button structureButton;
    @FXML private Button logOutButton;
    @FXML private GridPane content;

    private boolean english = false;

    @FXML private TreeView treeView;

    /**
     * Initializes primary window ComboBox element with needed information. 
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException{

        

        if(logged){
            Gson gson = new Gson();
            Reader reader = new FileReader(record);

            User[] users = gson.fromJson(reader, User[].class);
            User u = users[0];

            String degree = u.studentDegree;

            TreeItem<String> rootItem = new TreeItem<>(degree);

            treeView.setRoot(rootItem);

            CourseHandler.getModuleTree(degree, rootItem);
            reader.close();

            treeView.setVisible(true);
            content.setVisible(false);
        }
        else{
            CourseHandler degreeProgrammes = new CourseHandler();
            ArrayList<String> programmeNames = degreeProgrammes.getDegreeProgrammes();
            for (int i = 0; i < programmeNames.size() ; i++){
                Text text = new Text(programmeNames.get(i));
                content.add(text, 0, i);
            }
            content.setVisible(true);
            treeView.setVisible(false);
        }

        

        //CourseHandler degreeProgrammes = new CourseHandler();
        //ArrayList<String> submodules = degreeProgrammes.getStudyModulesOfProgramme(degree);

        
        /*
        CourseHandler degreeProgrammes = new CourseHandler();
        ArrayList<String> programmeNames = degreeProgrammes.getDegreeProgrammes();

        for (int i = 0; i < programmeNames.size() ; i++){
            Text text = new Text(programmeNames.get(i));
            
            //content.add(label, 0, i);
            content.add(text, 0, i);
        }
        */
    }

    /**
     * Switches to primary window.
     * @throws IOException Throws error if primary window does not exist.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        Sisu.setRoot("primary");
    }

    /**
     * Event handler for user information button.
     * @param event Mouseclick opens pop-up window to display user information.
     * @throws IOException Throws error if student record file doesnt exist.
     */
    @FXML
    protected void handleInformation(ActionEvent event) throws IOException{

        Gson gson = new Gson();
        Reader reader = new FileReader(record);

        User[] users = gson.fromJson(reader, User[].class);
        User u = users[0];

        String name = u.studentName;
        String number = u.studentNumber;
        String degree = u.studentDegree;

        final Stage stats = new Stage();
        stats.initModality(Modality.NONE);

        // To create pop-up window for user information
        BorderPane pane = new BorderPane();
        HBox infoBar = new HBox();

        Text tName = new Text();
        tName.setFont(new Font(20));
        tName.setText(name + ": ");

        Text tNum = new Text();
        tNum.setFont(new Font(20));
        tNum.setText(number + ": ");

        Text tDegree = new Text();
        tDegree.setFont(new Font(20));
        tDegree.setText(degree);

        infoBar.setSpacing(20);

        if(name.length() == 0 || number.length() == 0 || degree.length() == 0){
            Text guestInfo = new Text();
            guestInfo.setFont(new Font(20));
            if(english) guestInfo.setText("You are logged in as a guest, log in with your information to see details.");
            else guestInfo.setText("Olet kirjautuneena vieraana, kirjaudu sisään tunnuksilla nähdäksesi tiedot.");
            infoBar.getChildren().add(guestInfo);
        }
        else infoBar.getChildren().addAll(tName, tNum, tDegree);

        pane.setTop(infoBar);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(57, 179, 219), null, null)));

        Scene statScene = new Scene(pane, 800, 400);
        stats.setScene(statScene);
        stats.show();

        reader.close();
    }

    /**
     * Event handler for language: Finnish button.
     * @param event Event mouse click switches language to Finnish.
     */
    @FXML
    protected void handleLanguageButtonF2(ActionEvent event){
        english = false;
        informationButton.setText("Opiskelijan tiedot.");
        logOutButton.setText("Kirjaudu ulos.");
    }

    /**
     * Event handler for language: English button.
     * @param event Event mouse click switches language to English.
     */
    @FXML
    protected void handleLanguageButtonE2(ActionEvent event){
        // Change second window language to English
        english = true;
        informationButton.setText("Student information.");
        logOutButton.setText("Log out.");
    }
}