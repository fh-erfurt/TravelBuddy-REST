package de.travelbuddy.finance;

/**
 *  Create a Currency converter
 */
public class CurrencyConverterFactory {
    public static ICurrencyConverter create()
    {
        return new CurrencyConverter();
    }
}
