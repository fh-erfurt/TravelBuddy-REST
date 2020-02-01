package de.travelbuddy.finance;

/**
 *  Should be thrown if
 */
public class CurrencyConverterFactory {
    public static ICurrencyConverter create()
    {
        return new CurrencyConverter();
    }
}
