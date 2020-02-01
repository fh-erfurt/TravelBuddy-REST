package de.travelbuddy.finance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;


public class CurrencyConverter implements ICurrencyConverter {

    enum ConversionRate
    {
        // nach ISO 4217
        EUR (BigDecimal.valueOf(1)),//Euro
        CHF (BigDecimal.valueOf(1.07)),//Schweizer Franken
        CNY (BigDecimal.valueOf(7.82)),//Yuan
        CZK (BigDecimal.valueOf(25.15)),//Tschechische Krone
        PLN (BigDecimal.valueOf(4.24)),//Polnischer Zloty
        RUB (BigDecimal.valueOf(70.00)),//Rubel
        USD (BigDecimal.valueOf(1.10));//US Dollar
        
    
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



    /**
     * Get the Rate, between currency source and currency target
     * @param currencySource is the currency we have
     * @param currencyTarget is the currency we want to get
     * @return the currency
     */

    public BigDecimal getRate(Currency currencySource, Currency currencyTarget) {

        BigDecimal toEUR = ConversionRate.valueOf(currencySource.getCurrencyCode()).getConversionRate();
        BigDecimal toResultCurrency = ConversionRate.valueOf(currencyTarget.getCurrencyCode()).getConversionRate();

        toEUR = toEUR.multiply(BigDecimal.valueOf(100));
        toResultCurrency = toResultCurrency.multiply(BigDecimal.valueOf(100));

        return toResultCurrency.setScale(10,RoundingMode.HALF_UP).divide(toEUR,RoundingMode.HALF_UP);
    }
    // TODO JAVADOC
    public Money convert(Money money, Currency currencyTarget) {

        BigDecimal rate = getRate(money.getCurrency(),currencyTarget);

        return new Money(currencyTarget,money.getValue().multiply(rate).setScale(2,RoundingMode.HALF_UP));
    }
}
