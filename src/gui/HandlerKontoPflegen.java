package gui;

import funktion.Girokonto;
import funktion.Konto;
import funktion.KontoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.math.BigDecimal;


/**
 * Created by VaniR on 10.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerKontoPflegen {
    Konto konto;
    @FXML public void initialize() {
        btnDispo.setDisable(true);
        btnLoeschen.setDisable(true);
    }
    @FXML private NumberTextField tfKontonummer;
    @FXML private TextField tfDispo;
    @FXML private TextField tfKontostand;
    @FXML private Text txtDispoaenderungText;
    @FXML private TextField tfDispoaenderung;
    @FXML private DatePicker dpEroeffnungsdatum;
    @FXML private Button btnLoeschen;
    @FXML private Button btnDispo;

    @FXML protected void kontoLadenPressed(ActionEvent event) {
        btnDispo.setDisable(true);
        btnLoeschen.setDisable(true);
        txtDispoaenderungText.setVisible(false);
        tfDispoaenderung.setVisible(false);

        try {
            konto = KontoService.getKonto(Integer.parseInt(tfKontonummer.getText()));

            tfKontostand.setText(String.format("%.2f €", konto.getKontostand()));
            dpEroeffnungsdatum.setValue(konto.getEroeffnungsdatum());
            if (konto instanceof Girokonto) {
                Girokonto gkonto = (Girokonto) konto;
                tfDispo.setText(String.format("%.2f €", gkonto.getDispoLimit()));
                btnDispo.setDisable(false);
                txtDispoaenderungText.setVisible(true);
                tfDispoaenderung.setVisible(true);
            }
            btnLoeschen.setDisable(false);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }


    }
    @FXML protected void dispoPressed(ActionEvent event) {
        try {
            Girokonto gkonto = (Girokonto) konto;
            gkonto.aufDispoAenderung(new BigDecimal(tfDispoaenderung.getText()));
            KontoService.safeKonto(gkonto);
            tfDispo.setText(String.format("%.2f €", gkonto.getDispoLimit()));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
        btnDispo.setDisable(true);
        btnLoeschen.setDisable(true);
        txtDispoaenderungText.setVisible(false);
        tfDispoaenderung.setVisible(false);
    }

    @FXML protected void loeschenPressed(ActionEvent event) {
        try {
            KontoService.deleteKonto(konto);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
        btnDispo.setDisable(true);
        btnLoeschen.setDisable(true);
        txtDispoaenderungText.setVisible(false);
        tfDispoaenderung.setVisible(false);

    }
}
