package gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by VaniR on 12.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class KontoProperties {
    private final IntegerProperty kontonummer;
    private final ObjectProperty<LocalDate> eroeffnungsdatum;
    private final ObjectProperty<BigDecimal> dispo;
    private final ObjectProperty<BigDecimal> kontostand;

    public KontoProperties(Integer kontonummer , LocalDate eroeffnungsdatum, BigDecimal dispo, BigDecimal kontostand) {
        this.kontonummer = new SimpleIntegerProperty(kontonummer);
        this.eroeffnungsdatum = new SimpleObjectProperty<LocalDate>(eroeffnungsdatum);
        this.dispo = new SimpleObjectProperty<BigDecimal>(dispo);
        this.kontostand = new SimpleObjectProperty<BigDecimal>(kontostand);
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

    public BigDecimal getDispo() {
        return dispo.get();
    }

    public ObjectProperty<BigDecimal> dispoProperty() {
        return dispo;
    }

    public void setDispo(BigDecimal dispo) {
        this.dispo.set(dispo);
    }

    public BigDecimal getKontostand() {
        return kontostand.get();
    }

    public ObjectProperty<BigDecimal> kontostandProperty() {
        return kontostand;
    }

    public void setKontostand(BigDecimal kontostand) {
        this.kontostand.set(kontostand);
    }
}
