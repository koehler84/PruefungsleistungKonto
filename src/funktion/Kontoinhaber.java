package funktion;

import java.util.LinkedList;
import java.util.List;
/**
 * Created by VaniR on 06.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Kontoinhaber {

    private Integer kundenNummer;
    private String name;
    private String adresse;
    private List<Konto> kontos;

    public Kontoinhaber(Integer kundenNummer, String name, String adresse) {
        this.kundenNummer = kundenNummer;
        this.name = name;
        this.adresse = adresse;
        this.kontos = new LinkedList<>();
    }

    public Integer getKundenNummer() {
        return kundenNummer;
    }

    // public void setKundenNummer(Integer kundenNummer) {
    // this.kundenNummer = kundenNummer;
    // }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Konto> getKontos() {
        return kontos;
    }

    public void setKontos(List<Konto> kontos) {
        this.kontos = kontos;
    }

    public void addKonto(Konto konto) {
        this.kontos.add(konto);
    }

}
