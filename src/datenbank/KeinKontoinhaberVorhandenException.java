package datenbank;

/**
 * Created by VaniR on 12.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class KeinKontoinhaberVorhandenException extends Exception{
    public KeinKontoinhaberVorhandenException() {
    }
    public KeinKontoinhaberVorhandenException(String s) {
        super(s);
    }
}
