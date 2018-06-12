package funktion;


import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by VaniR on 06.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Sparkonto extends Konto {

    public Sparkonto(Integer kontoNummer, LocalDate eroeffnungsdatum, BigDecimal kontostand, Integer kundennummerInhaber) {
        super(kontoNummer, eroeffnungsdatum, kontostand, kundennummerInhaber);
    }

    public void einzahlen(BigDecimal betrag) {
        super.setKontostand(super.getKontostand().add(betrag));
    }

    public void auszahlen(BigDecimal betrag) {
        if (super.getKontostand().compareTo(betrag) >= 0) {
            super.setKontostand(super.getKontostand().subtract(betrag));
        }
    }

}
