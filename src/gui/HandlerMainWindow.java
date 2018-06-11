package gui;

import funktion.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by VaniR on 09.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerMainWindow extends Stage {

    @FXML private Text actiontarget;

    @FXML protected void kontoAnzeigenPressed(ActionEvent event) {
        actiontarget.setText("anzeigen");
        try {
            new WindowLoader("../gui/kontoAnzeigen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void kontoTransaktionPressed(ActionEvent event) {
        actiontarget.setText("transaktion");
        try {
            new WindowLoader("../gui/kontoTransaktion.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void kontoÜbersichtPressed(ActionEvent event) {
        actiontarget.setText("übersicht");
        try {
            new WindowLoader("../gui/alleKontos.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void kontoPflegePressed(ActionEvent event) {
        actiontarget.setText("pflege");
        try {
            new WindowLoader("../gui/kontoPflegen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void kontoAnlegenPressed(ActionEvent event) {
        actiontarget.setText("anlegen");
        try {
            new WindowLoader("../gui/kontoAnlegen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
