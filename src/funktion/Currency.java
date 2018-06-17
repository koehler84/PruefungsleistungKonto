package funktion;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * Created by VaniR on 15.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class Currency {
    public BigDecimal parseTextbox (String valueCurrency) throws Exception {
        BigDecimal value = null;
        //Nur für deutsche Notation 1.000,00
        valueCurrency = valueCurrency.replaceAll("\\.", "");
        valueCurrency = valueCurrency.replaceAll(" ", "");
        valueCurrency = valueCurrency.replaceAll(",", ".");
        valueCurrency = valueCurrency.replaceAll("€", "");

        value = new BigDecimal(valueCurrency.substring(0,valueCurrency.length()-2));

        return value;
    }
}
