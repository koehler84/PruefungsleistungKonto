package funktion;

import datenbank.DatenbankService;

/**
 * Created by VaniR on 09.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class KontoService {
    public static Konto getKonto(Integer kontonummer) {
        DatenbankService datenbank = new DatenbankService();
        return datenbank.datenLadenKonto(kontonummer);
    }

    public static void safeKonto(Konto konto) {
        DatenbankService datenbank = new DatenbankService();
        datenbank.datenSpeichernKonto(konto);
    }

    public static void deleteKonto(Konto konto) {
        DatenbankService datenbank = new DatenbankService();
        datenbank.datenLoeschenKonto(konto);
    }

    public static Kontoinhaber getKunde(Integer kundennummer) {
        DatenbankService datenbank = new DatenbankService();
        return datenbank.datenLadenKontoinhaber(kundennummer);
    }
}
