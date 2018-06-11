package funktion;

import java.time.LocalDate;

/**
 * Created by VaniR on 06.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Konto {
    private Integer kontoNummer;
    private LocalDate eroeffnungsdatum;
    private Float kontostand;
    private Integer kundennummerInhaber;


    public Konto(Integer kontoNummer, LocalDate eroeffnungsdatum, Float kontostand, Integer kundennummerInhaber) {
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

    public Float getKontostand() {
        return kontostand;
    }

    public void setKontostand(Float kontostand) {
        this.kontostand = kontostand;
    }

    public Integer getKundennummerInhaber() {
        return kundennummerInhaber;
    }

    public void setKundennummerInhaber(Integer kundennummerInhaber) {
        this.kundennummerInhaber = kundennummerInhaber;
    }
}
