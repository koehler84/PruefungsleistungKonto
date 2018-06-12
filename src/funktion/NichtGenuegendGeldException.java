package funktion;

/**
 * Created by VaniR on 12.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class NichtGenuegendGeldException extends Exception {
    public NichtGenuegendGeldException() {
    }
    public NichtGenuegendGeldException(String s) {
        super(s);
    }
}
