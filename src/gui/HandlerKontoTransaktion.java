package gui;

import funktion.Girokonto;
import funktion.Konto;
import funktion.KontoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;


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

    //todo checks Empfänger Sender per Exception + überweisung genug geld?
    @FXML protected void kontoLadenPressed(ActionEvent event) {
        btnUeberweisung.setDisable(true);
        try {
            kontoSender = KontoService.getKonto(Integer.parseInt(tfSenderKontonummer.getText())).parseToGirokonto();
            kontoEmpfaenger = KontoService.getKonto(Integer.parseInt(tfEmpfaengerKontonummer.getText())).parseToGirokonto();

                tfSenderKontostand.setText(String.format("%.2f €", kontoSender.getKontostand()));
                tfEmpfaengerKontostand.setText(String.format("%.2f €", kontoEmpfaenger.getKontostand()));
                btnUeberweisung.setDisable(false);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    @FXML protected void ueberweisungPressed(ActionEvent event) {
        try {
            kontoSender.ueberweisen(new BigDecimal(tfUeberweisung.getText()), kontoEmpfaenger);

            KontoService.safeKonto(kontoSender);
            KontoService.safeKonto(kontoEmpfaenger);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
            btnUeberweisung.setDisable(true);
            tfSenderKontostand.setText(String.format("%.2f €", kontoSender.getKontostand()));
            tfEmpfaengerKontostand.setText(String.format("%.2f €", kontoEmpfaenger.getKontostand()));
        }

    }

}
