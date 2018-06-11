package gui;

import funktion.KontoService;
import funktion.Kontoinhaber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by VaniR on 11.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerAlleKontos {
    private List<Kontoinhaber> alleKunden;
    @FXML public void initialize() {
        alleKunden = KontoService.getAlleKunden();
        for (Kontoinhaber kontoinhaber: alleKunden) {
            cbKunde.getItems().add(kontoinhaber);
        }
        cbKunde.setValue(alleKunden.get(0));
        cbKunde.setEditable(false);
    }

    @FXML private ComboBox cbKunde;
    @FXML private TableView tblKonten;
    @FXML private TextField tfGesamtsumme;

    @FXML protected void kundeLadenPressed(ActionEvent event) {
        Kontoinhaber kunde = (Kontoinhaber) cbKunde.getValue();

    }
}
