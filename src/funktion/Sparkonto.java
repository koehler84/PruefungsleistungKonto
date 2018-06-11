package funktion;


import java.time.LocalDate;

/**
 * Created by VaniR on 06.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Sparkonto extends Konto {

    public Sparkonto(Integer kontoNummer, LocalDate eroeffnungsdatum, Float kontostand, Integer kundennummerInhaber) {
        super(kontoNummer, eroeffnungsdatum, kontostand, kundennummerInhaber);
    }

    public void einzahlen(Float betrag) {
        super.setKontostand(super.getKontostand() + betrag);
    }

    public void auszahlen(Float betrag) {
        if (super.getKontostand() >= betrag) {
            super.setKontostand(super.getKontostand() - betrag);
        }
    }

}
