package funktion;

import datenbank.DatenbankService;
import javafx.scene.control.Alert;

import java.util.List;

/**
 * Created by VaniR on 09.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 *
 * Klasse als Schnittstelle zwischen Userinterface und Datenbank. Verwaltet alle Funtionen die nötig sind um die
 * objekte der Funktionsschicht zu verwalten.
 */

public class KontoService {
    /**
     * Diese Methode lädt ein Kontoobjekt aus der Datenbank mithilfe einer übergebenen
     * Kontonummer. Wirf eine spezielle KontoNichtVorhandenException, wenn das Konto
     * nicht vorhanden ist.
     * @param kontonummer Das ist die Kontonummer Kontoobjeks welches gesucht wird
     * @return Kontoobjekt aus der DB geladen
     * @throws Exception
     */
    public static Konto getKonto(Integer kontonummer) throws Exception{
        DatenbankService datenbank = new DatenbankService();
        return datenbank.datenLadenKonto(kontonummer);
    }

    /**
     * Diese Methode speichert ein Kontoobjekt in der DB, wenn ein gültiges Kontoobjekt übergeben wurde.
     * @param konto Das ist das Kontoobjekt welches gespeichert wird
     * @throws Exception
     */
    public static void safeKonto(Konto konto) throws Exception{
        DatenbankService datenbank = new DatenbankService();
        datenbank.datenSpeichernKonto(konto);
    }

    /**
     * Diese Methode Löscht ein Konto aus der DB, wenn ein gültiges Kontoobjekt übergeben wurde.
     * @param konto Das ist das Kontoobjekt welches gelöscht wird
     * @throws Exception
     */
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
