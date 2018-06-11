package funktion;

import java.time.LocalDate;

/**
 * Created by VaniR on 06.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Girokonto extends Konto{
    private Float dispoLimit;

    public Girokonto(Integer kontoNummer, LocalDate eroeffnungsdatum, Float kontostand, Float dispoLimit, Integer kundennummerInhaber) {
        super(kontoNummer, eroeffnungsdatum, kontostand, kundennummerInhaber);
        this.dispoLimit = dispoLimit;
    }

    public Float getDispoLimit() {
        return dispoLimit;
    }

    public void einzahlen(Float betrag) {
        super.setKontostand(super.getKontostand() + betrag);
    }

    public void auszahlen(Float betrag) {
        if (super.getKontostand() + dispoLimit >= betrag) {
            super.setKontostand(super.getKontostand() - betrag);
        }
    }

    public Boolean aufDispoAenderung(Float aufDispoAenderung) {
        if (super.getKontostand() * -1 < aufDispoAenderung ) {
            dispoLimit = aufDispoAenderung;
            return true;
        } else {
            return false;
        }
    }

    public boolean ueberweisen(Float betrag, Girokonto empfaengerKonto) {
        if (getKontostand() >= betrag ) {
            setKontostand(getKontostand() - betrag);
            empfaengerKonto.setKontostand(empfaengerKonto.getKontostand() + betrag);
            return true;
        } else {
            return false;
        }
    }
}
