package de.travelbuddy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class MoneyConverter {

    enum ConversionRate
    {
        EUR (new BigDecimal(1)), USD (new BigDecimal(1.1)), CNY (new BigDecimal(7.82)), RUB (new BigDecimal(70.8));

        private final BigDecimal conversionRate;

        ConversionRate(BigDecimal conversionRate) { this.conversionRate = conversionRate; }

        public BigDecimal getConversionRate() { return conversionRate; }
    }

    public Money ConvertFromTo(Money startMoney, Currency resultCurrnecy){

        BigDecimal toEUR = ConversionRate.valueOf(startMoney.getCurrency().getCurrencyCode()).getConversionRate();
        BigDecimal toResultCurrency = ConversionRate.valueOf(resultCurrnecy.getCurrencyCode()).getConversionRate();

        BigDecimal resultValue = (startMoney.getValue().divide(toEUR, RoundingMode.UNNECESSARY)).multiply(toResultCurrency);

        return new Money(resultCurrnecy,resultValue);
    }
}
