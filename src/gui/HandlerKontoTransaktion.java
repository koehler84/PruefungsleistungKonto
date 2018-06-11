package gui;

import funktion.Girokonto;
import funktion.Konto;
import funktion.KontoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/**
 * Created by VaniR on 10.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerKontoTransaktion {
    Girokonto kontoSender;
    Girokonto kontoEmpfaenger;
    @FXML private NumberTextField tfEmpfaengerKontonummer;
    @FXML private NumberTextField tfSenderKontonummer;
    @FXML private TextField tfEmpfaengerKontostand;
    @FXML private TextField tfSenderKontostand;
    @FXML private TextField tfUeberweisung;
    @FXML private Button btnUeberweisung;

    @FXML protected void kontoLadenPressed(ActionEvent event) {
        btnUeberweisung.setDisable(true);
        Konto kontoSender = KontoService.getKonto(Integer.parseInt(tfSenderKontonummer.getText()));
        Konto kontoEmpfaenger = KontoService.getKonto(Integer.parseInt(tfEmpfaengerKontonummer.getText()));
        if (kontoSender.getClass().getSimpleName().equals("Sparkonto")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Senderkonto kein Girokonto");
            alert.showAndWait();
            btnUeberweisung.setDisable(true);
        } else if (kontoEmpfaenger.getClass().getSimpleName().equals("Sparkonto")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Empfängerkonto kein Girokonto");
            alert.showAndWait();
            btnUeberweisung.setDisable(true);
        } else {
            this.kontoSender = (Girokonto)kontoSender;
            this.kontoEmpfaenger = (Girokonto)kontoEmpfaenger;
            tfSenderKontostand.setText(String.format("%.2f €",kontoSender.getKontostand()));
            tfEmpfaengerKontostand.setText(String.format("%.2f €",kontoEmpfaenger.getKontostand()));
            btnUeberweisung.setDisable(false);
        }
    }

    @FXML protected void ueberweisungPressed(ActionEvent event) {
        if (!kontoSender.ueberweisen(Float.parseFloat(tfUeberweisung.getText()), kontoEmpfaenger)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Senderkonto verfügt nicht über ausreichend Geld");
            alert.showAndWait();
            btnUeberweisung.setDisable(true);
        } else {
            KontoService.safeKonto(kontoSender);
            KontoService.safeKonto(kontoEmpfaenger);
        }

    }

}
