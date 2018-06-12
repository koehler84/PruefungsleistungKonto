package datenbank;

/**
 * Created by VaniR on 12.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class KontoNichtVorhandenException extends Exception {
    public KontoNichtVorhandenException() {
    }

    public KontoNichtVorhandenException(String s) {
        super( s );
    }
}
