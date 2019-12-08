package de.travelbuddy.finance;

public class CurrencyConverterFactory {
    public static ICurrencyConverter create()
    {
        return new CurrencyConverter();
    }
}
