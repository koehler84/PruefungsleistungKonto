package datenbank;

/**
 * Created by VaniR on 12.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class KeineKontosVorhandenException extends Exception {
    public KeineKontosVorhandenException() {}
    public KeineKontosVorhandenException(String s) {
        super(s);
    }
}
