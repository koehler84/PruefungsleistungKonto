package datenbank;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import funktion.Girokonto;
import funktion.Konto;
import funktion.Kontoinhaber;
import funktion.Sparkonto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by VaniR on 08.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class DatenbankService {
    // Declare the JDBC objects.
    Connection con = null;
    SQLServerDataSource ds = null;

    //todo preparedStatements
    public DatenbankService() {
        // Establish the connection.
        ds = new SQLServerDataSource();
        ds.setIntegratedSecurity(true);
        //todo: wirkliche Werte eintragen
        ds.setServerName("localhost");
        ds.setPortNumber(1433);
        ds.setDatabaseName("KontoApp");
        ds.setUser("skoehler");
        ds.setPassword("skoehler");
        ds.setIntegratedSecurity(false);
    }

    public Konto datenLadenKonto(Integer kontonummer) throws Exception{
        Konto konto = null;
        Statement stmt = null;
        ResultSet rs = null;

        con = ds.getConnection();

        String queryKonto = "SELECT * FROM Konto WHERE Kontonummer = " + kontonummer;

        stmt = con.createStatement();
        rs = stmt.executeQuery(queryKonto);


        while ( rs.next() ) {
            if (rs.getString("Dispo") == null){
                konto = datenLadenSparkonto(kontonummer, rs);
            } else {
                konto = datenLadenGirokonto(kontonummer, rs);
            }
        }

        if (konto == null) {
            throw new KontoNichtVorhandenException("Kontonummer " + kontonummer + " nicht vergeben.");
        }
        // Handle any errors that may have occurred.


        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (con != null) con.close();

        return konto;
    }

    private Sparkonto datenLadenSparkonto(Integer kontonummer, ResultSet rs) throws SQLException{

        Sparkonto konto = new Sparkonto(rs.getInt("Kontonummer"),
                rs.getDate("Eroeffnungsdatum").toLocalDate(),
                rs.getBigDecimal("Kontostand"),
                rs.getInt("Kontoinhaber_Kundennummer"));

        return konto;
    }

    private Girokonto datenLadenGirokonto(Integer kontonummer, ResultSet rs) throws SQLException{

        Girokonto konto = new Girokonto(rs.getInt("Kontonummer"),
                rs.getDate("Eroeffnungsdatum").toLocalDate(),
                rs.getBigDecimal("Kontostand"),
                rs.getBigDecimal("Dispo"),
                rs.getInt("Kontoinhaber_Kundennummer"));

        return konto;
    }

    public Kontoinhaber datenLadenKontoinhaber(Integer kundennummer) throws Exception{
        Kontoinhaber kontoinhaber = null;
        Statement stmt = null;
        ResultSet rs = null;

        con = ds.getConnection();

        String queryKontoinhaber = "SELECT * FROM Kontoinhaber WHERE kundennummer = " + kundennummer;

        stmt = con.createStatement();
        rs = stmt.executeQuery(queryKontoinhaber);

        while ( rs.next() ) {
            kontoinhaber = new Kontoinhaber(rs.getInt("Kundennummer"),
                    rs.getString("Name"),
                    rs.getString("Adresse"));
        }

        if (kontoinhaber == null) {
            throw new KeinKontoinhaberVorhandenException("Es existieren kein Kontoinhaber");
        }

        String queryKontos = "SELECT * FROM konto WHERE Kontoinhaber_Kundennummer = " + kundennummer;
        rs = stmt.executeQuery(queryKontos);

        while ( rs.next() ) {
            if (rs.getString("Dispo") == null){
                kontoinhaber.addKonto(datenLadenSparkonto(rs.getInt("Kontonummer"), rs));
            } else {
                kontoinhaber.addKonto(datenLadenGirokonto(rs.getInt("Kontonummer"), rs));
            }
        }




        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (con != null) con.close();
        return kontoinhaber;
    }

    public void datenSpeichernKontoinhaber(Kontoinhaber kontoinhaber) throws Exception {
        Statement stmt = null;

        String sql;

        if (datenLadenKontoinhaber(kontoinhaber.getKundenNummer()) == null) {
            sql = "INSERT INTO kontoinhaber(kundennummer, name, adresse) VALUES ("
                    + kontoinhaber.getKundenNummer() + ", '" + kontoinhaber.getName()
                    + "' , '" + kontoinhaber.getAdresse() + "')";
        } else {
            sql = "UPDATE kontoinhaber SET name = '" + kontoinhaber.getName()  + "', adresse = '"
                    + kontoinhaber.getAdresse() + "' WHERE kundennummer = " + kontoinhaber.getKundenNummer();
        }
        con = ds.getConnection();

        stmt = con.createStatement();
        stmt.execute(sql);


        if (stmt != null) try {
            stmt.close();
        } catch (Exception e) {
        }
        if (con != null) try {
            con.close();
        } catch (Exception e) {
        }

    }

    public void datenSpeichernKonto(Konto konto) throws Exception{
        Statement stmt = null;

        BigDecimal dispo = null;
        String sql;
        if (konto.getClass().getSimpleName().equals("Girokonto")) {
            Girokonto gkonto = (Girokonto) konto;
            dispo = gkonto.getDispoLimit();
        }
        if (datenLadenKonto(konto.getKontoNummer()) == null) {
            sql = "INSERT INTO konto(Kontonummer, Eroeffnungsdatum, Kontostand, Dispo, Kontoinhaber_Kundennummer) VALUES ("
                    + konto.getKontoNummer() + ", '" + konto.getEroeffnungsdatum()+ "', "+ konto.getKontostand()
                    + ", " + dispo + ", " + konto.getKundennummerInhaber() +")";
        } else {
            sql = "UPDATE Konto SET Eroeffnungsdatum = '" + konto.getEroeffnungsdatum()  + "', Kontostand = "
                    + konto.getKontostand() + ", Dispo = "
                    + dispo +", Kontoinhaber_Kundennummer = "
                    + konto.getKundennummerInhaber() +" WHERE Kontonummer = " + konto.getKontoNummer();
        }

        con = ds.getConnection();
        stmt = con.createStatement();
        stmt.execute(sql);

        if (stmt != null) stmt.close();
        if (con != null) con.close();
    }

    public void datenLoeschenKonto(Konto konto) throws Exception{
        Statement stmt = null;

        String sql = "DELETE FROM Konto WHERE Kontonummer = " + konto.getKontoNummer();

        con = ds.getConnection();
        stmt = con.createStatement();
        stmt.execute(sql);

        if (stmt != null) stmt.close();
        if (con != null) con.close();

    }

    public List<Kontoinhaber> alleKundenLaden() throws Exception{
        Statement stmt = null;
        ResultSet rs = null;
        List<Kontoinhaber> kontoinhaberList = new LinkedList<>();

        String sql = "SELECT Kundennummer FROM Kontoinhaber";

        con = ds.getConnection();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        while ( rs.next() ) {
            kontoinhaberList.add(datenLadenKontoinhaber(rs.getInt("Kundennummer")));
        }

        if (kontoinhaberList == null) {
                throw new KeineKontosVorhandenException("Es existieren keine Konten");
        }


        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (con != null) con.close();

        return kontoinhaberList;
    }
}
