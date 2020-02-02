package de.travelbuddy.finance;

import de.travelbuddy.finance.exception.NotSupportedCurrencyException;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Interface for currency conversion classes.
 * Handles the conversion for one currency in the other.
 */
public interface ICurrencyConverter {

    /**
     * Convert the money in the right currency
     * @param money is the
     * @param currency is the currency we want to get
     * @return The Money with the target currency
     */
    Money convert(Money money, Currency currency) throws NotSupportedCurrencyException;

    /**
     * Get the Rate, between currency source and currency target
     * @param currencySource is the currency we have
     * @param currencyTarget is the currency we want to get
     * @return the currency
     */
    BigDecimal getRate(Currency currencySource, Currency currencyTarget) throws NotSupportedCurrencyException;

    /**
     * Checks if the given currency is supported in the class
     * @param currency The currency to check
     * @return True, if the currency is supported, otherwise false
     */
    boolean isSupportedCurrency(Currency currency);
}
