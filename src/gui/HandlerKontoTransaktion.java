package gui;

import funktion.Currency;
import funktion.Girokonto;
import funktion.KontoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * Created by VaniR on 10.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerKontoTransaktion {
    Girokonto kontoSender;
    Girokonto kontoEmpfaenger;

    @FXML public void initialize() {
        tfUeberweisung.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);

                tfUeberweisung.setText(currencyFormatter.format(new BigDecimal(tfUeberweisung.getText())));
            }
            if (newPropertyValue) {
                tfUeberweisung.setText("");
            }
        });
        btnUeberweisung.setDisable(true);
    }
    @FXML private NumberTextField tfEmpfaengerKontonummer;
    @FXML private NumberTextField tfSenderKontonummer;
    @FXML private TextField tfEmpfaengerKontostand;
    @FXML private TextField tfSenderKontostand;
    @FXML private TextField tfUeberweisung;
    @FXML private Button btnUeberweisung;

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
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    @FXML protected void ueberweisungPressed(ActionEvent event) {
        try {
            kontoSender.ueberweisen(new Currency().parseTextbox(tfUeberweisung.getText()), kontoEmpfaenger);

            KontoService.safeKonto(kontoSender);
            KontoService.safeKonto(kontoEmpfaenger);
            tfSenderKontostand.setText(String.format("%.2f €", kontoSender.getKontostand()));
            tfEmpfaengerKontostand.setText(String.format("%.2f €", kontoEmpfaenger.getKontostand()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
            btnUeberweisung.setDisable(true);
            tfSenderKontostand.setText(String.format("%.2f €", kontoSender.getKontostand()));
            tfEmpfaengerKontostand.setText(String.format("%.2f €", kontoEmpfaenger.getKontostand()));
        }
    //todo Polish: konto laden mit Listener instand btn
    }

}
