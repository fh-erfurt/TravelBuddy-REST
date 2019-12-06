package de.travelbuddy;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {

    private Currency currency;
    private BigDecimal value;

    public Money (Currency currency, BigDecimal value){
        this.currency = currency;
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Money ConvertTo(Currency resultCurrency) {

        MoneyConverter converter = new MoneyConverter();

        return converter.ConvertFromTo(this,resultCurrency);
    }
}
