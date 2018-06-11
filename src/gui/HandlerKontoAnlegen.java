package gui;

import funktion.KontoService;
import funktion.Kontoinhaber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.time.LocalDate;

/**
 * Created by VaniR on 09.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerKontoAnlegen {
    private Integer kundennummer = 0;

    @FXML
    public void initialize() {
        dpEroeffnungsDatum.setValue(LocalDate.now());
    }

    @FXML private DatePicker dpEroeffnungsDatum;

    @FXML private TextField tfKundennummer;

    @FXML private TextField tfName;

    @FXML private TextField tfAdresse;

    @FXML private CheckBox cbGiro;

    @FXML private Text dispoText;

    @FXML private TextField tfDispo;

    @FXML private Button btnSpeichern;

    @FXML protected void giroPressed(ActionEvent event) {
        if (cbGiro.isSelected()) {
            dispoText.setVisible(true);
            tfDispo.setVisible(true);

        } else {
            dispoText.setVisible(false);
            tfDispo.setVisible(false);

        }
    }

    @FXML protected void kundennummerPressed(KeyEvent event) {
        //todo how the fuck do i make this? lost focus funktion exist?
        Kontoinhaber kunde = KontoService.getKunde(Integer.parseInt(tfKundennummer.getText()));
        if (kunde != null) {
            tfAdresse.setText(kunde.getAdresse());
            tfName.setText(kunde.getName());
        }
        event.consume();
    }

    @FXML protected void speichernPressed(ActionEvent event) {

    }
}
