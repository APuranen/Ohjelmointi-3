package tests;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.junit.Assert.assertEquals;

import java.lang.ModuleLayer.Controller;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import fi.tuni.prog3.projekti.Sisu;

public class SisuTest extends ApplicationTest{

    private static Sisu sisu = new Sisu();

    private static Pane mainroot;
    private static Stage mainStage;
    private static Controller controller;
    //private static Parent rootNode;

     @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Sisu.class.getResource("primary.fxml"));
        fxmlLoader.load();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        //new Sisu().start(stage);
        /*
        rootNode = FXMLLoader.load(Sisu.class.getResource("primary.fxml"));
        mainStage.setScene(new Scene(rootNode));
        mainStage.show();
        mainStage.toFront();

        /*
        stage.setScene(sisu.getScene());
        stage.show();
        stage.toFront();
        /*
        this.mainStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("projekti/main/resources/fi/tuni/prog3/projekti/primary.fxml"));
        this.mainroot = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(mainroot));
        stage.show();
        stage.toFront();
        
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Sisu.class);   
        */   
    }

    @Before public void setUp() throws Exception{}
    @After public void cleanup() throws Exception{
        FxToolkit.hideStage();
    }

    @Test
    public void nameLabelTest() {
        Label nameLabel = fromAll().lookup("#nameField").queryFirst();
        assertEquals(nameLabel.getText(), "Nimi:");
        //FxAssert.verifyThat(".nameLabel", LabeledMatchers.hasText("Name:"));
    }
    
    @Test
    public void languageButtonClicked() {
        // when:
        clickOn("#langE");

        // then:
        FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Name:"));
    }
}

