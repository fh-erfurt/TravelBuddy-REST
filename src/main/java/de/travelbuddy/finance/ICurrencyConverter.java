package de.travelbuddy.finance;

import java.math.BigDecimal;
import java.util.Currency;

public interface ICurrencyConverter {
    Money convert(Money money, Currency currency);
    BigDecimal getRate(Currency currency_source, Currency currency_target);
}
