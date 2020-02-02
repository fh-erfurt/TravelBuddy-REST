package de.travelbuddy.finance;

/**
 * Creates an instance of the default class for currency conversion
 */
public class CurrencyConverterFactory {
    public static ICurrencyConverter create()
    {
        return new CurrencyConverter();
    }
}
