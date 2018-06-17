package gui;

import funktion.Girokonto;
import funktion.Konto;
import funktion.KontoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by VaniR on 09.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerKontoAnzeigen {

    @FXML private DatePicker dpEroeffnungsDatum;

    @FXML private TextField tfKontonummer;

    @FXML private TextField tfDispo;

    @FXML private TextField tfKontostand;

    @FXML private Text dispoText;

    @FXML protected void oeffnenPressed(ActionEvent event) {
        tfDispo.setVisible(false);
        dispoText.setVisible(false);
        try {
            Konto konto = KontoService.getKonto(Integer.parseInt(tfKontonummer.getText()));
            dpEroeffnungsDatum.setValue(konto.getEroeffnungsdatum());
            tfKontostand.setText(String.format("%.2f €",konto.getKontostand()));
            if (konto instanceof Girokonto) {
                Girokonto gkonto = (Girokonto) konto;
                tfDispo.setVisible(true);
                dispoText.setVisible(true);
                tfDispo.setText(String.format("%.2f €", gkonto.getDispoLimit()));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }


    }




}
