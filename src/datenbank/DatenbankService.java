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
        ds.setServerName("192.168.83.150");
        ds.setPortNumber(1433);
        ds.setDatabaseName("Anwend1");
        ds.setUser("lg064801");
        ds.setPassword("lg064801");
        ds.setIntegratedSecurity(false);
    }

    public Konto datenLadenKonto(Integer kontonummer) throws Exception{
        Konto konto = null;
        Statement stmt = null;
        ResultSet rs;

        con = ds.getConnection();


        PreparedStatement queryKonto = con.prepareStatement(
                "SELECT * FROM Konto WHERE Kontonummer = ?" );
        queryKonto.setInt( 1, kontonummer );
        rs = queryKonto.executeQuery();


        /*String queryKontoinhaber = "SELECT * FROM Konto WHERE kontonummer = " + kontonummer;
        stmt = con.createStatement();
        rs = stmt.executeQuery(queryKonto);*/


        while ( rs.next() ) {
            if (rs.getString("Dispo") == null){
                konto = datenLadenSparkonto(rs);
            } else {
                konto = datenLadenGirokonto(rs);
            }
        }

        if (konto == null) {
            throw new KontoNichtVorhandenException("Kontonummer " + kontonummer + " nicht vergeben.");
            // Handle any errors that may have occurred.
        }


        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (con != null) con.close();

        return konto;
    }

    private Sparkonto datenLadenSparkonto(ResultSet rs) throws SQLException{

        Sparkonto konto = new Sparkonto(rs.getInt("Kontonummer"),
                rs.getDate("Eroeffnungsdatum").toLocalDate(),
                rs.getBigDecimal("Kontostand"),
                rs.getInt("Kontoinhaber_Kundennummer"));

        return konto;
    }

    private Girokonto datenLadenGirokonto(ResultSet rs) throws SQLException{

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
        ResultSet rs;

        con = ds.getConnection();

        PreparedStatement queryKunde = con.prepareStatement(
                "SELECT * FROM Kontoinhaber WHERE Kundennummer = ?" );
        queryKunde.setInt( 1, kundennummer );
        rs = queryKunde.executeQuery();

        /*String queryKontoinhaber = "SELECT * FROM Kontoinhaber WHERE kundennummer = " + kundennummer;

        stmt = con.createStatement();
        rs = stmt.executeQuery(queryKontoinhaber);*/

        while ( rs.next() ) {
            kontoinhaber = new Kontoinhaber(rs.getInt("Kundennummer"),
                    rs.getString("Name"),
                    rs.getString("Adresse"));
        }

        if (kontoinhaber == null) {
            throw new KeinKontoinhaberVorhandenException("Es existieren kein Kontoinhaber");
        }

        PreparedStatement queryKonto = con.prepareStatement("SELECT * FROM konto WHERE Kontoinhaber_Kundennummer = ?");
        queryKonto.setInt(1, kundennummer);
        rs = queryKonto.executeQuery();

        /*String queryKontos = "SELECT * FROM konto WHERE Kontoinhaber_Kundennummer = " + kundennummer;
        rs = stmt.executeQuery(queryKontos);*/

        while ( rs.next() ) {
            if (rs.getString("Dispo") == null){
                kontoinhaber.addKonto(datenLadenSparkonto(rs));
            } else {
                kontoinhaber.addKonto(datenLadenGirokonto(rs));
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
        con = ds.getConnection();


        try {

            if (datenLadenKontoinhaber(kontoinhaber.getKundenNummer()) != null) {
                con = ds.getConnection();

                PreparedStatement querySaveKunde = con.prepareStatement("UPDATE kontoinhaber SET name = ?, adresse = ? WHERE kundennummer = ?");
                querySaveKunde.setString(1, kontoinhaber.getName());
                querySaveKunde.setString(2, kontoinhaber.getAdresse());
                querySaveKunde.setInt(3, kontoinhaber.getKundenNummer());

                querySaveKunde.executeUpdate();
            }
        } catch (KeinKontoinhaberVorhandenException e) {
            con = ds.getConnection();

            PreparedStatement querySaveKunde = con.prepareStatement("INSERT INTO kontoinhaber(kundennummer, name, adresse) VALUES (?, ? , ?)");
            querySaveKunde.setInt(1, kontoinhaber.getKundenNummer());
            querySaveKunde.setString(2, kontoinhaber.getName());
            querySaveKunde.setString(3, kontoinhaber.getAdresse());

            querySaveKunde.executeUpdate();

        }

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
        if (konto instanceof Girokonto) {
            Girokonto gkonto = (Girokonto) konto;
            dispo = gkonto.getDispoLimit();
        }

         try {
             if (datenLadenKonto(konto.getKontoNummer())!=null) {
                 con = ds.getConnection();

                 PreparedStatement querySaveKonto = con.prepareStatement("UPDATE Konto SET Eroeffnungsdatum = ?, " +
                         "Kontostand = ?, Dispo = ?, Kontoinhaber_Kundennummer = ? WHERE Kontonummer = ?");
                 querySaveKonto.setInt(5, konto.getKontoNummer());
                 querySaveKonto.setObject(1, konto.getEroeffnungsdatum());
                 querySaveKonto.setBigDecimal(2, konto.getKontostand());
                 querySaveKonto.setBigDecimal(3, dispo);
                 querySaveKonto.setInt(4, konto.getKundennummerInhaber());

                 querySaveKonto.executeUpdate();
             }
         } catch (KontoNichtVorhandenException e) {
             con = ds.getConnection();

             PreparedStatement querySaveKonto = con.prepareStatement("INSERT INTO konto(Kontonummer, Eroeffnungsdatum, Kontostand, Dispo, Kontoinhaber_Kundennummer) " +
                     "VALUES (?, ?, ?, ?, ?)");
             querySaveKonto.setInt(1, konto.getKontoNummer());
             querySaveKonto.setObject(2, konto.getEroeffnungsdatum());
             querySaveKonto.setBigDecimal(3, konto.getKontostand());
             querySaveKonto.setBigDecimal(4, dispo);
             querySaveKonto.setInt(5, konto.getKundennummerInhaber());

             querySaveKonto.executeUpdate();
         }



        if (stmt != null) stmt.close();
        if (con != null) con.close();
    }

    public void datenLoeschenKonto(Konto konto) throws Exception{
        Statement stmt = null;

        con = ds.getConnection();

        PreparedStatement queryDeleteKonto = con.prepareStatement("DELETE FROM Konto WHERE Kontonummer = ?");
        queryDeleteKonto.setInt(1, konto.getKontoNummer());

        queryDeleteKonto.executeUpdate();

        if (stmt != null) stmt.close();
        if (con != null) con.close();

    }

    public List<Kontoinhaber> alleKundenLaden() throws Exception{
        Statement stmt;
        ResultSet rs;
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

    public Integer neueKontonummer() throws Exception{
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT Kontonummer FROM Konto ORDER BY Kontonummer";
        Integer kontonummer = 0;

        con = ds.getConnection();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        while ( rs.next() ) {
            kontonummer = rs.getInt("Kontonummer");
        }
        return kontonummer;
    }

}
