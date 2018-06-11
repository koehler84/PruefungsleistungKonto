package datenbank;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import funktion.Girokonto;
import funktion.Konto;
import funktion.Kontoinhaber;
import funktion.Sparkonto;

import java.sql.*;


/**
 * Created by VaniR on 08.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class DatenbankService {
    // Declare the JDBC objects.
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    SQLServerDataSource ds = null;

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

    public Konto datenLadenKonto(Integer kontonummer) {
        Konto konto = null;
        try {

            con = ds.getConnection();

            String queryKonto = "SELECT * FROM Konto WHERE Kontonummer = " + kontonummer;

            stmt = con.createStatement();
            rs = stmt.executeQuery(queryKonto);

            while ( rs.next() ) {
                if (rs.getString(4) == null){
                    konto = datenLadenSparkonto(kontonummer, rs);
                } else {
                    konto = datenLadenGirokonto(kontonummer, rs);
                }
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
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
        return konto;
    }

    private Sparkonto datenLadenSparkonto(Integer kontonummer, ResultSet rs) throws SQLException{

        Sparkonto konto = new Sparkonto(this.rs.getInt(1), this.rs.getDate(2).toLocalDate(), this.rs.getFloat(3), this.rs.getInt(5));

        return konto;
    }

    private Girokonto datenLadenGirokonto(Integer kontonummer, ResultSet rs) throws SQLException{

        Girokonto konto = new Girokonto(this.rs.getInt(1), this.rs.getDate(2).toLocalDate(), this.rs.getFloat(3), this.rs.getFloat(4), this.rs.getInt(5));

        return konto;
    }

    public Kontoinhaber datenLadenKontoinhaber(Integer kundennummer) {
        Kontoinhaber kontoinhaber = null;
        try {

            con = ds.getConnection();

            String queryKontoinhaber = "SELECT * FROM Kontoinhaber WHERE kundennummer = " + kundennummer;

            stmt = con.createStatement();
            rs = stmt.executeQuery(queryKontoinhaber);

            while ( rs.next() ) {
                kontoinhaber = new Kontoinhaber(rs.getInt(1), rs.getString(2), rs.getString(3));
            }

            String queryKontos = "SELECT * FROM konto WHERE Kontoinhaber_Kundennummer = " + kundennummer;
            rs = stmt.executeQuery(queryKontos);

            while ( rs.next() ) {
                if (rs.getString(4) == null){
                    kontoinhaber.addKonto(datenLadenSparkonto(rs.getInt(1), rs));
                } else {
                    kontoinhaber.addKonto(datenLadenGirokonto(rs.getInt(1), rs));
                }
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
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
        return kontoinhaber;
    }

    public void datenSpeichernKontoinhaber(Kontoinhaber kontoinhaber) {
        try {
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


        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
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
    }

    public void datenSpeichernKonto(Konto konto) {
        try {
            Float dispo = null;
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
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
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
    }

    public void datenLoeschenKonto(Konto konto) {
        try {
            String sql = "DELETE FROM Konto WHERE Kontonummer = " + konto.getKontoNummer();

            con = ds.getConnection();
            stmt = con.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
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
    }
}
