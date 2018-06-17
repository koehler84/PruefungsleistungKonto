package funktion;

import java.math.BigDecimal;

/**
 * Created by VaniR on 15.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Currency {
    public BigDecimal parseTextbox (String valueCurrency) throws Exception {
        BigDecimal value;
        //Nur für deutsche Notation 1.000,00
        valueCurrency = valueCurrency.replaceAll("\\.", "");
        valueCurrency = valueCurrency.replaceAll(" ", "");
        valueCurrency = valueCurrency.replaceAll(",", ".");
        valueCurrency = valueCurrency.replaceAll("€", "");

        value = new BigDecimal(valueCurrency.substring(0,valueCurrency.length()-2));

        return value;
    }
}
