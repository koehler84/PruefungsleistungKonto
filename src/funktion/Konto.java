package funktion;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by VaniR on 06.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Konto {
    private Integer kontoNummer;
    private LocalDate eroeffnungsdatum;
    private BigDecimal kontostand;
    private Integer kundennummerInhaber;


    public Konto(Integer kontoNummer, LocalDate eroeffnungsdatum, BigDecimal kontostand, Integer kundennummerInhaber) {
        this.kontoNummer = kontoNummer;
        this.eroeffnungsdatum = eroeffnungsdatum;
        this.kontostand = kontostand;
        this.kundennummerInhaber = kundennummerInhaber;
    }

    public Integer getKontoNummer() {
        return kontoNummer;
    }

    public void setKontoNummer(Integer kontoNummer) {
        this.kontoNummer = kontoNummer;
    }

    public LocalDate getEroeffnungsdatum() {
        return eroeffnungsdatum;
    }

    public void setEroeffnungsdatum(LocalDate eroeffnungsdatum) {
        this.eroeffnungsdatum = eroeffnungsdatum;
    }

    public BigDecimal getKontostand() {
        return kontostand;
    }

    public void setKontostand(BigDecimal kontostand) {
        this.kontostand = kontostand;
    }

    public Integer getKundennummerInhaber() {
        return kundennummerInhaber;
    }

    public void setKundennummerInhaber(Integer kundennummerInhaber) {
        this.kundennummerInhaber = kundennummerInhaber;
    }

    public Girokonto parseToGirokonto() throws Exception {
        if (this instanceof Girokonto) {
            return (Girokonto) this;
        }
        throw new KeinGirokontoException(this.getKontoNummer() + " ist kein Girokonto");
    }
}
