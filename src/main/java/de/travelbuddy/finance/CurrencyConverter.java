package de.travelbuddy.finance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class CurrencyConverter implements ICurrencyConverter {

    enum ConversionRate
    {
        // nach ISO 4217
        EUR (new BigDecimal(1)),//Euro
        CHF (new BigDecimal(1.07)),//Schweizer Franken
        CNY (new BigDecimal(7.82)),//Yuan
        CZK (new BigDecimal(25.15)),//Tschechische Krone
        PLN (new BigDecimal(4.24)),//Polnischer Zloty
        RUB (new BigDecimal(70.8)),//Rubel
        USD (new BigDecimal(1.1));//US Dollar
        
    
        private final BigDecimal conversionRate;

        ConversionRate(BigDecimal conversionRate) { this.conversionRate = conversionRate; }

        public BigDecimal getConversionRate() { return conversionRate; }
    }

    /**
     * Convert the money in the right currency
     * @param money is the
     * @param currencyTarget is the currency we want to get
     * @return The Money with the target currency
     */

    public Money convert(Money money, Currency currencyTarget) { //Todo Test bauen

        BigDecimal rate = getRate(money.getCurrency(),currencyTarget);

        return new Money(currencyTarget,money.getValue().multiply(rate));
    }

    /**
     * Get the Rate, between currency source and currency target
     * @param currencySource is the currency we have
     * @param currencyTarget is the currency we want to get
     * @return the currency
     */

    public BigDecimal getRate(Currency currencySource, Currency currencyTarget) {

        BigDecimal toEUR = ConversionRate.valueOf(currencySource.getCurrencyCode()).getConversionRate();
        BigDecimal toResultCurrency = ConversionRate.valueOf(currencyTarget.getCurrencyCode()).getConversionRate();

        return toResultCurrency.divide(toEUR,RoundingMode.UP);

    }
}
