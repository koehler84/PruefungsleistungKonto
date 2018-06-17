package funktion;

import datenbank.DatenbankService;

import java.util.List;

/**
 * Created by VaniR on 09.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class KontoService {
    public static Konto getKonto(Integer kontonummer) throws Exception{
        DatenbankService datenbank = new DatenbankService();
        return datenbank.datenLadenKonto(kontonummer);
    }

    public static void safeKonto(Konto konto) throws Exception{
        DatenbankService datenbank = new DatenbankService();
        datenbank.datenSpeichernKonto(konto);
    }

    public static void deleteKonto(Konto konto) throws Exception{
        DatenbankService datenbank = new DatenbankService();
        datenbank.datenLoeschenKonto(konto);
    }

    public static Kontoinhaber getKunde(Integer kundennummer) throws Exception{
        DatenbankService datenbank = new DatenbankService();
        return datenbank.datenLadenKontoinhaber(kundennummer);
    }

    public static List<Kontoinhaber> getAlleKunden() throws Exception{
        DatenbankService datenbank = new DatenbankService();
        return datenbank.alleKundenLaden();
    }

    public static Integer getNewKontonummer() throws Exception{
        DatenbankService datenbank = new DatenbankService();
        return datenbank.neueKontonummer() + 1;
    }

    public static void safeNewKunde(Kontoinhaber kontoinhaber, Konto konto) throws Exception{
        DatenbankService datenbank = new DatenbankService();
        datenbank.datenSpeichernKontoinhaber(kontoinhaber);
        datenbank.datenSpeichernKonto(konto);
    }
}
