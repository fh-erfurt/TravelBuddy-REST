package de.travelbuddy.finance;

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

    public Money convert(Currency resultCurrency) {
        ICurrencyConverter converter = CurrencyConverterFactory.create();
        return converter.convert(this, resultCurrency);
    }

    public void add(Money money)
    {
        Money newMoney = null;

        if (money.getCurrency() != this.getCurrency())
            newMoney = money.convert(this.getCurrency());
        else
            newMoney = money;

        this.setValue(this.getValue().add(newMoney.getValue()));
    }

    public void subtract(Money money)
    {
        Money newMoney = null;

        if (money.getCurrency() != this.getCurrency())
            newMoney = money.convert(this.getCurrency());
        else
            newMoney = money;

        this.setValue(this.getValue().subtract(newMoney.getValue()));
    }
}
