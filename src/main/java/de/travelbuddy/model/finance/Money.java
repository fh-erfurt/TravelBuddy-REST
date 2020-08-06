package de.travelbuddy.model.finance;

import com.sun.istack.NotNull;
import de.travelbuddy.model.finance.exception.NotSupportedCurrencyException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;


/**
 * Class which represents Money
 */
@Getter @Setter
@NoArgsConstructor
public class Money {

    private Currency currency;
    private BigDecimal value = new BigDecimal(0);

    /**
     * Convert the money value in the right currency
     * @param resultCurrency the currency we want to have
     * @return the money value with the currency
     * @throws NotSupportedCurrencyException if the currency does not exist in CurrencyConverter
     */
    public Money convert(Currency resultCurrency) throws NotSupportedCurrencyException {
        ICurrencyConverter converter = CurrencyConverterFactory.create();
        return converter.convert(this, resultCurrency);
    }

    /**
     * Add Money
     * @param money to add
     * @return the new value of money
     */
    public Money add(Money money)
    {
        Money newMoney = null;

        if (money.getCurrency() != this.getCurrency()) {
            try {
                newMoney = money.convert(this.getCurrency());
            } catch (NotSupportedCurrencyException e) {
                e.printStackTrace();
            }
        }
        else
            newMoney = money;

        assert newMoney != null;
        this.setValue(this.getValue().add(newMoney.getValue()));
        return this;
    }

    /**
     * Add money
     * @param currency Currency of the money to add
     * @param value Value of the money to add
     * @return The new value of the money
     */
    public Money add(Currency currency, BigDecimal value)
    {
        Money newMoney = new Money();
        newMoney.setCurrency(currency);
        newMoney.setValue(value);

        return add(newMoney);
    }

    /**
     * subtract money
     * @param money to subtract
     * @return the new value of money
     */
    public Money subtract(Money money)
    {
        Money newMoney = null;

        if (money.getCurrency() != this.getCurrency()) {
            try {
                newMoney = money.convert(this.getCurrency());
            } catch (NotSupportedCurrencyException e) {
                e.printStackTrace();
            }
        }
        else
            newMoney = money;

        assert newMoney != null;
        this.setValue(this.getValue().subtract(newMoney.getValue()));
        return this;
    }

    /**
     * Subtract money
     * @param currency Currency of the money to subtract
     * @param value Value of the money to subtract
     * @return The new value of the money
     */
    public Money subtract(Currency currency, BigDecimal value)
    {
        Money newMoney = new Money();
        newMoney.setCurrency(currency);
        newMoney.setValue(value);

        return subtract(newMoney);
    }
}
