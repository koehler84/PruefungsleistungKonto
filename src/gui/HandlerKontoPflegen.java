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
        konto = KontoService.getKonto(Integer.parseInt(tfKontonummer.getText()));

        tfKontostand.setText(String.format("%.2f €",konto.getKontostand()));
        dpEroeffnungsdatum.setValue(konto.getEroeffnungsdatum());
        if (konto.getClass().getSimpleName().equals("Girokonto")) {
            Girokonto gkonto = (Girokonto) konto;
            tfDispo.setText(String.format("%.2f €",gkonto.getDispoLimit()));
            btnDispo.setDisable(false);
            txtDispoaenderungText.setVisible(true);
            tfDispoaenderung.setVisible(true);
        }

        btnLoeschen.setDisable(false);


    }
    @FXML protected void dispoPressed(ActionEvent event) {
        Girokonto gkonto = (Girokonto) konto;
        if (gkonto.aufDispoAenderung(new BigDecimal(tfDispoaenderung.getText()))) {
            KontoService.safeKonto(gkonto);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Dispoänderung ungültig");
            alert.showAndWait();
        }
        btnDispo.setDisable(true);
        btnLoeschen.setDisable(true);
        txtDispoaenderungText.setVisible(false);
        tfDispoaenderung.setVisible(false);
    }

    @FXML protected void loeschenPressed(ActionEvent event) {
        KontoService.deleteKonto(konto);
        btnDispo.setDisable(true);
        btnLoeschen.setDisable(true);
        txtDispoaenderungText.setVisible(false);
        tfDispoaenderung.setVisible(false);
    }
}
