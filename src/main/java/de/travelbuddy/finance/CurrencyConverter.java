package de.travelbuddy.finance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class CurrencyConverter implements ICurrencyConverter {

    enum ConversionRate
    {
        // nach ISO 4217
        EUR (new BigDecimal(1)),//Euro
        USD (new BigDecimal(1.1)),//US Dollar
        CNY (new BigDecimal(7.82)),//Yuan
        RUB (new BigDecimal(70.8)),//Rubel
        CZK (new BigDecimal()),//Krone
        CHF (new BigDecimal()),//Franken
        PLN (new BigDecimal());//Zloty

        private final BigDecimal conversionRate;

        ConversionRate(BigDecimal conversionRate) { this.conversionRate = conversionRate; }

        public BigDecimal getConversionRate() { return conversionRate; }
    }


    public Money convert(Money money, Currency currencyTarget) { //Todo Test bauen

        BigDecimal rate = getRate(money.getCurrency(),currencyTarget);

        return new Money(currencyTarget,money.getValue().multiply(rate));
    }

    public BigDecimal getRate(Currency currencySource, Currency currencyTarget) {

        BigDecimal toEUR = ConversionRate.valueOf(currencySource.getCurrencyCode()).getConversionRate();
        BigDecimal toResultCurrency = ConversionRate.valueOf(currencyTarget.getCurrencyCode()).getConversionRate();

        return toResultCurrency.divide(toEUR,RoundingMode.UP);

    }
}
