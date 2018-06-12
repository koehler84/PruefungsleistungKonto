package funktion;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by VaniR on 06.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Girokonto extends Konto{
    private BigDecimal dispoLimit;

    public Girokonto(Integer kontoNummer, LocalDate eroeffnungsdatum, BigDecimal kontostand, BigDecimal dispoLimit, Integer kundennummerInhaber) {
        super(kontoNummer, eroeffnungsdatum, kontostand, kundennummerInhaber);
        this.dispoLimit = dispoLimit;
    }

    public BigDecimal getDispoLimit() {
        return dispoLimit;
    }

    public void einzahlen(BigDecimal betrag) {
        super.setKontostand(super.getKontostand().add(betrag));
    }

    public void auszahlen(BigDecimal betrag) {
        if (super.getKontostand().add(dispoLimit).compareTo(betrag) >= 0 ) {
            super.setKontostand(super.getKontostand().subtract(betrag));
        }
    }

    public void aufDispoAenderung(BigDecimal aufDispoAenderung) throws Exception{
        if (super.getKontostand().multiply(BigDecimal.valueOf(-1)).compareTo(aufDispoAenderung) <= 0) {
            dispoLimit = aufDispoAenderung;
        } else {
            throw new DispoAenderungUngueltungException("Kontostand nicht ausreichend für Dispoänderung");
        }
    }

    public void ueberweisen(BigDecimal betrag, Girokonto empfaengerKonto) throws Exception {
        if (getKontostand().add(dispoLimit).compareTo(betrag) >= 0 ) {
            setKontostand(getKontostand().subtract(betrag));
            empfaengerKonto.setKontostand(empfaengerKonto.getKontostand().add(betrag));
        } else {
            throw new NichtGenuegendGeldException("Das Senderkonto verfügt nicht über ausreichend Mittel");
        }
    }
}
