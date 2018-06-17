package test;

import datenbank.DatenbankService;
import datenbank.KeinKontoinhaberVorhandenException;
import datenbank.KontoNichtVorhandenException;
import funktion.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by VaniR on 17.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
class ProjektTests {

    private static DatenbankService datenbankVerbindung = new DatenbankService();
    private static Kontoinhaber kontoinhaber1;
    private static Konto gkonto1;
    private static Konto gkonto2;
    private static Konto skonto1;

    @BeforeAll
    public static void setUp() throws Exception {
        kontoinhaber1 = new Kontoinhaber(20, "Tester", "Teststraße");
        gkonto1 = new Girokonto(1, LocalDate.now(), new BigDecimal(-1000), new BigDecimal(2000),kontoinhaber1.getKundenNummer());
        gkonto2 = new Girokonto(2, LocalDate.now(), new BigDecimal(-1000), new BigDecimal(2000),kontoinhaber1.getKundenNummer());
        skonto1 = new Sparkonto(1, LocalDate.now(), new BigDecimal(2000),kontoinhaber1.getKundenNummer());
    }

    @AfterAll
    public static void throwDown() throws Exception {
        kontoinhaber1 = null;
        gkonto1 = null;
        gkonto2 = null;
        skonto1 = null;
        datenbankVerbindung = null;
    }

    @org.junit.jupiter.api.Test
    void KontoNichtVorhandenExceptionTest() {
        try {
            datenbankVerbindung.datenLadenKonto(1000);
            fail ("Kontonummer 1000 sollte nicht vergeben sein");
        } catch (KontoNichtVorhandenException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail (e.getLocalizedMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void KeinKontoinhaberVorhandenExceptionTest() {
        try {
            datenbankVerbindung.datenLadenKontoinhaber(1000);
            fail ("Kundennummer 1000 sollte nicht vergeben sein");
        } catch (KeinKontoinhaberVorhandenException e){
            assertTrue(true);
        } catch (Exception e){
            fail (e.getLocalizedMessage());
        }

    }

    @org.junit.jupiter.api.Test
    void DispoAenderungUngueltungExceptionTest() {
        try {
            gkonto1.parseToGirokonto().aufDispoAenderung(new BigDecimal(0));
            fail("Dispo 0, sollte Kontostand -1000 nicht decken");
        } catch (DispoAenderungUngueltungException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail (e.getLocalizedMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void NichtGenuegendGeldExceptionTest() {
        try {
            gkonto1.parseToGirokonto().ueberweisen(new BigDecimal(2000), gkonto2.parseToGirokonto());
            fail("Dispo und Vermögen sollten nicht reichen um den Geldbetrag zu überweisen");
        } catch (NichtGenuegendGeldException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail (e.getLocalizedMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void KeinGirokontoExceptionTest() {
        try {
            skonto1.parseToGirokonto();
            fail("Das Konto sollte kein Girokonto sein.");
        } catch (KeinGirokontoException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail (e.getLocalizedMessage());
        }
    }

}






