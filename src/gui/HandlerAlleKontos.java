package gui;

import funktion.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by VaniR on 11.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerAlleKontos {
    private ObservableList<KontoProperties> konten = FXCollections.observableArrayList();
    private List<Kontoinhaber> alleKunden;
    @FXML public void initialize() {
        alleKunden = KontoService.getAlleKunden();
        for (Kontoinhaber kontoinhaber: alleKunden) {
            cbKunde.getItems().add(kontoinhaber);
        }
        cbKunde.setValue(alleKunden.get(0));
        cbKunde.setEditable(false);
        tblKonten.setEditable(false);
    }

    @FXML private ComboBox cbKunde;
    @FXML private TableView<Kontoinhaber> tblKonten;
    @FXML private TextField tfGesamtsumme;
    @FXML private TableColumn<KontoProperties, Integer> tcKontonummer;
    @FXML private TableColumn<Kontoinhaber, String>  tcKontostand;
    @FXML private TableColumn<Kontoinhaber, String>  tcDispo;

    @FXML protected void kundeLadenPressed(ActionEvent event) {
        Kontoinhaber kunde = (Kontoinhaber) cbKunde.getValue();
        for ( Konto kontenInKunde: kunde.getKontos()) {
            if (kontenInKunde instanceof Sparkonto) {
                konten.add(new KontoProperties(kontenInKunde.getKontoNummer(), kontenInKunde.getEroeffnungsdatum(), null,
                        kontenInKunde.getKontostand()));
            } else {
                Girokonto gkonto = (Girokonto) kontenInKunde;
                konten.add(new KontoProperties(kontenInKunde.getKontoNummer(), kontenInKunde.getEroeffnungsdatum(), gkonto.getDispoLimit(),
                        kontenInKunde.getKontostand()));
            }
        }
        tcKontonummer.setCellValueFactory(cellData -> cellData.getValue().kontonummerProperty().asObject());
    }
}
