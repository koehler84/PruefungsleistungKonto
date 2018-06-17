package gui;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

/**
 * Created by VaniR on 12.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class KontoProperties {
    private final IntegerProperty kontonummer;
    private final ObjectProperty<LocalDate> eroeffnungsdatum;
    private final StringProperty dispo;
    private final StringProperty kontostand;

    public KontoProperties(Integer kontonummer , LocalDate eroeffnungsdatum, BigDecimal dispo, BigDecimal kontostand) {
        this.kontonummer = new SimpleIntegerProperty(kontonummer);
        this.eroeffnungsdatum = new SimpleObjectProperty<>(eroeffnungsdatum);
        if (dispo == null) {
            this.dispo = new SimpleStringProperty("Kein Girokonto");
        } else {
            this.dispo = new SimpleStringProperty(new DecimalFormat("#0.00 €").format(dispo));
        }
        this.kontostand = new SimpleStringProperty(new DecimalFormat("#0.00 €").format(kontostand));
    }

    public int getKontonummer() {
        return kontonummer.get();
    }

    public IntegerProperty kontonummerProperty() {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer) {
        this.kontonummer.set(kontonummer);
    }

    public LocalDate getEroeffnungsdatum() {
        return eroeffnungsdatum.get();
    }

    public ObjectProperty<LocalDate> eroeffnungsdatumProperty() {
        return eroeffnungsdatum;
    }

    public void setEroeffnungsdatum(LocalDate eroeffnungsdatum) {
        this.eroeffnungsdatum.set(eroeffnungsdatum);
    }

    public String getDispo() {
        return dispo.get();
    }

    public StringProperty dispoProperty() {
        return dispo;
    }

    public void setDispo(String dispo) {
        this.dispo.set(dispo);
    }

    public String getKontostand() {
        return kontostand.get();
    }

    public StringProperty kontostandProperty() {
        return kontostand;
    }

    public void setKontostand(String kontostand) {
        this.kontostand.set(kontostand);
    }
}
