package gui;

import datenbank.KeinKontoinhaberVorhandenException;
import funktion.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Created by VaniR on 09.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class HandlerKontoAnlegen {
    @FXML
    public void initialize() {
        tfKundennummer.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue) {
                    try {
                        Kontoinhaber kunde = KontoService.getKunde(Integer.parseInt(tfKundennummer.getText()));
                        if (kunde != null) {
                            tfAdresse.setText(kunde.getAdresse());
                            tfName.setText(kunde.getName());
                        }
                        btnSpeichern.setDisable(false);
                    } catch (KeinKontoinhaberVorhandenException e) {
                        System.out.println("Neuer Kunde");
                        tfAdresse.setText("");
                        tfName.setText("");
                    }catch (NumberFormatException e) {
                        System.out.println("Keine gültige Nummer");
                        tfAdresse.setText("");
                        tfName.setText("");
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setContentText(e.getLocalizedMessage());
                        alert.showAndWait();
                        tfAdresse.setText("");
                        tfName.setText("");
                    }
                }
            }
        });

        dpEroeffnungsDatum.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue) {
                    if (dpEroeffnungsDatum.getValue().isBefore(LocalDate.now())) {
                        dpEroeffnungsDatum.setValue(LocalDate.now());
                    }
                }
            }
        });

        tfDispo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
        {
            if (!newPropertyValue) {
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);

                tfDispo.setText(currencyFormatter.format(new BigDecimal(tfDispo.getText())));
                btnSpeichern.setDisable(false);
            }
            if (newPropertyValue) {
                tfDispo.setText("");
            }
        }
    });

        dpEroeffnungsDatum.setValue(LocalDate.now());
        btnSpeichern.setDisable(true);
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
        tfDispo.setText("0,00 €");
    }

    @FXML protected void speichernPressed(ActionEvent event) {
        try {
            BigDecimal dispo = null;
            Integer kontonummer = KontoService.getNewKontonummer();
            if (cbGiro.isSelected()) {
                dispo = new Currency().parseTextbox(tfDispo.getText());

                KontoService.safeNewKunde(new Kontoinhaber( Integer.parseInt(tfKundennummer.getText()), tfName.getText(),
                        tfAdresse.getText()
                ), new Girokonto(kontonummer, LocalDate.parse(dpEroeffnungsDatum.getValue().toString()), new BigDecimal(0), dispo, Integer.parseInt(tfKundennummer.getText())));
            } else {
                KontoService.safeNewKunde(new Kontoinhaber( Integer.parseInt(tfKundennummer.getText()), tfName.getText(),
                        tfAdresse.getText()
                ), new Sparkonto(kontonummer, LocalDate.parse(dpEroeffnungsDatum.getValue().toString()), new BigDecimal(0), Integer.parseInt(tfKundennummer.getText())));
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }

    }
}
