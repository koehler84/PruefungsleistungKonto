package gui;

import funktion.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by VaniR on 11.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerAlleKontos {
    private ObservableList<KontoProperties> konten = FXCollections.observableArrayList();
    private List<Kontoinhaber> alleKunden;
    @FXML public void initialize() {
        try {
            alleKunden = KontoService.getAlleKunden();
            for (Kontoinhaber kontoinhaber : alleKunden) {
                cbKunde.getItems().add(kontoinhaber);
            }
            cbKunde.setValue(alleKunden.get(0));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
        cbKunde.setEditable(false);
        tblKonten.setEditable(false);
    }

    @FXML private ComboBox cbKunde;
    @FXML private TableView<KontoProperties> tblKonten;
    @FXML private TextField tfGesamtsumme;
    @FXML private TableColumn<KontoProperties, Integer> tcKontonummer;
    @FXML private TableColumn<KontoProperties, String>  tcKontostand;
    @FXML private TableColumn<KontoProperties, String>  tcDispo;
    @FXML private TableColumn<KontoProperties, LocalDate> tcEroeffnungsdatum;

    @FXML protected void kundeLadenPressed(ActionEvent event) {
        BigDecimal kumulierterKontostand = new BigDecimal(0);
        tblKonten.getItems().clear();
        Kontoinhaber kunde = (Kontoinhaber) cbKunde.getValue();
        for ( Konto kontenInKunde: kunde.getKontos()) {
            if (kontenInKunde instanceof Sparkonto) {
                konten.add(new KontoProperties(kontenInKunde.getKontoNummer(), kontenInKunde.getEroeffnungsdatum(), null,
                        kontenInKunde.getKontostand()));
                kumulierterKontostand = kumulierterKontostand.add(kontenInKunde.getKontostand());
            } else {
                Girokonto gkonto = (Girokonto) kontenInKunde;
                konten.add(new KontoProperties(kontenInKunde.getKontoNummer(), kontenInKunde.getEroeffnungsdatum(), gkonto.getDispoLimit(),
                        kontenInKunde.getKontostand()));
                kumulierterKontostand = kumulierterKontostand.add(kontenInKunde.getKontostand());
            }
        }
        tcKontonummer.setCellValueFactory(cellData -> cellData.getValue().kontonummerProperty().asObject());
        tcDispo.setCellValueFactory(cellData -> cellData.getValue().dispoProperty());
        tcKontostand.setCellValueFactory(cellData -> cellData.getValue().kontostandProperty());
        tcEroeffnungsdatum.setCellValueFactory(cellData -> cellData.getValue().eroeffnungsdatumProperty());
        tblKonten.setItems(konten);

        tfGesamtsumme.setText(kumulierterKontostand.toString());
    }
}
