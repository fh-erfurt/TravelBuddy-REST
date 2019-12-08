package de.travelbuddy.finance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class CurrencyConverter implements ICurrencyConverter {

    enum ConversionRate
    {
        // nach ISO 4217
        EUR (new BigDecimal(1)),
        USD (new BigDecimal(1.1)),
        CNY (new BigDecimal(7.82)),
        RUB (new BigDecimal(70.8));

        private final BigDecimal conversionRate;

        ConversionRate(BigDecimal conversionRate) { this.conversionRate = conversionRate; }

        public BigDecimal getConversionRate() { return conversionRate; }
    }



    public Money convert(Money money, Currency currency) {
        BigDecimal toEUR = ConversionRate.valueOf(money.getCurrency().getCurrencyCode()).getConversionRate();
        BigDecimal toResultCurrency = ConversionRate.valueOf(currency.getCurrencyCode()).getConversionRate();

        BigDecimal resultValue = (money.getValue().divide(toEUR, RoundingMode.UP)).multiply(toResultCurrency);

        return new Money(currency,resultValue);
    }

    public BigDecimal getRate(Currency currency_source, Currency currency_target) {
        return new BigDecimal(0); //Todo implement!
    }
}
