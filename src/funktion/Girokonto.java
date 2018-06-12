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

    public Boolean aufDispoAenderung(BigDecimal aufDispoAenderung) {
        if (super.getKontostand().multiply(BigDecimal.valueOf(-1)).compareTo(aufDispoAenderung) <= 0) {
            dispoLimit = aufDispoAenderung;
            return true;
        } else {
            return false;
        }
    }

    public boolean ueberweisen(BigDecimal betrag, Girokonto empfaengerKonto) {
        if (getKontostand().compareTo(betrag) >= 0 ) {
            setKontostand(getKontostand().subtract(betrag));
            empfaengerKonto.setKontostand(empfaengerKonto.getKontostand().add(betrag));
            return true;
        } else {
            return false;
        }
    }
}
