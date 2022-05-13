/**
 * Ohjelmointi-3 Harjoitustyö: Sisu-projekti, PrimaryController.
 * @author Antti-Jussi Isoviita, H283435
 * @author Aleksi Puranen, H283752
 */

package fi.tuni.prog3.projekti;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Controller class for primary window.
 */
public class PrimaryController extends Sisu{

    @FXML private TextField nameField;
    @FXML private TextField numberField;
    @FXML private Button loginButton;
    @FXML private Button guestButton;
    @FXML private Label nameLabel;
    @FXML private Label numLabel;
    @FXML private Label degreeLabel;
    @FXML private Label infoLabel;
    @FXML private Label infoLabel2;
    @FXML private ComboBox<String> major;
    @FXML private Label errorMsg;

    private String name;
    private String number;
    private String degree;
    private Boolean isEmpty = true;
    private Boolean english = false;
    private ArrayList<User> userList;
    private JsonArray uList;

    /**
     * Writes student information into student record file in Json format.
     * @param u The new user information to be written.
     * @throws IOException Throws error if student record file does not exist.
     */
    protected void writeJson(User u) throws IOException{
        
        this.userList = new ArrayList<>();
        this.uList = new JsonArray();
        // If the file is empty, create new structure or add to existing structure
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if(isEmpty){
            Writer writer = new FileWriter(record);
            userList.add(u);
            
            uList = gson.toJsonTree(userList).getAsJsonArray();

            gson.toJson(uList, writer);
            writer.flush();
        }
        else{
            
            Reader reader = new FileReader(record);
            User[] users = gson.fromJson(reader, User[].class);

            userList.add(u);
            for(User e : users){
                if ( ! u.studentNumber.equals(e.studentNumber) ) userList.add(e);
                else {
                    u.studentName = e.studentName;
                    u.studentNumber = e.studentNumber;
                    u.studentDegree = e.studentDegree;
                }
            }

            Writer writer = new FileWriter(record);
            uList = gson.toJsonTree(userList).getAsJsonArray();
            gson.toJson(uList, writer);

            writer.flush();
            reader.close();
        }
    }

    /**
     * Initializes primary window ComboBox element with needed information. 
     */
    @FXML
    private void initialize(){

        CourseHandler degreeProgrammes = new CourseHandler();
        ArrayList<String> programmeNames = degreeProgrammes.getDegreeProgrammes();

        major.getItems().addAll(programmeNames);
    }

    /**
     * Event handler for log in button.
     * @param event Event mouse click saves student information and switches to secondary window.
     * @throws IOException Throws error if student record file, or secondary window does not exist.
     */
    @FXML 
    protected void handleLoginButton(ActionEvent event) throws IOException{

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        logged = true;
        Boolean falseName = false;
        Boolean falseDegree = false;

        try{
            this.name = nameField.getText();
            this.number = numberField.getText();
            this.degree = major.getValue().toString();
        }
        catch(Exception e){
            this.name = "";
            this.number = "";
            this.degree = "";
        }
        
        User u = new User(this.name, this.number, this.degree);

        // Check if studentrecord(.json) exists
        try{
            Reader reader = new FileReader(record);
            File recordFile = new File(record);
            if(recordFile.length() != 0) {
                isEmpty = false;
                User[] users = gson.fromJson(reader, User[].class);
        
                for (User e : users){
                    if (e.studentNumber.equals(u.studentNumber)){
                        if(!e.studentName.equals(u.studentName)) falseName = true;
                        if(!e.studentDegree.equals(u.studentDegree)) falseDegree = true;
                    }
                }
            }
        }
        catch(Exception e){System.out.println("Error: No previous student data, creating new student record.");}

        // Checking for user errors in log in
        if(this.name.length() == 0 || this.number.length() == 0){
            if(english) errorMsg.setText("Fill out the fields or log in as guest.");
            else errorMsg.setText("Täytä kenttiin tietosi tai kirjaudu vieraana.");
        }
        // JOS NIMI LÖYTYY -> ilmoita että valitse oikea tutkinto-ohjelma
        else if (falseName){
            if(english) errorMsg.setText("User with different name exists.");
            else errorMsg.setText("Käyttäjä eri nimellä on olemassa.");
        }
        else if (falseDegree){
            if(english) errorMsg.setText("User with different degree exists.");
            else errorMsg.setText("Käyttäjä eri tutkinnolla on olemassa.");
        }
        else{
            writeJson(u);
            Sisu.setRoot("secondary");
        } 
    }

    /**
     * Event handler for guest log in button.
     * @param event Event mouse click saves empty student information and switches to secondary window.
     * @throws IOException Throws error if student record file, or secondary window does not exist.
     */
    @FXML
    protected void handleGuestButton(ActionEvent event) throws IOException {
        // Guest Login
        logged = false;
        User u = new User("", "", "");
        writeJson(u);

        Sisu.setRoot("secondary");
    }

    /**
     * Event handler for language: English button.
     * @param event Event mouse click switches language to English.
     */
    @FXML
    protected void handleLanguageButtonE(ActionEvent event){
        // Switch language to English
        english = true;
        errorMsg.setText("");
        loginButton.setText("Log in.");
        guestButton.setText("Log in as guest");
        nameLabel.setText("Name: ");
        numLabel.setText("Student number: ");
        degreeLabel.setText("Degree: ");
        infoLabel.setText("Welcome to Sisu add-on!");
        infoLabel2.setText("Fill in your information, or create new profile.");
    }

    /**
     * Event handler for language: Finnish button.
     * @param event Event mouse click switches language to Finnish.
     */
    @FXML
    protected void handleLanguageButtonF(ActionEvent event){
        // Switch language to Finnish
        english = false;
        errorMsg.setText("");
        loginButton.setText("Kirjaudu sisään.");
        guestButton.setText("Kirjaudu vieraana.");
        nameLabel.setText("Nimi: ");
        numLabel.setText("Opiskelijanumero: ");
        degreeLabel.setText("Tutkinto: ");
        infoLabel.setText("Tervetuloa Sisun liitännäiseen!");
        infoLabel2.setText("Syötä tietosi niin etsimme ne, tai luo uusi profiili.");
    }
}
