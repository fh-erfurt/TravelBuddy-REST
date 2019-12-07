package de.travelbuddy.finance;

public class CurrencyConverterFactory {
    public static ICurrencyConverter Create()
    {
        return new CurrencyConverter();
    }
}
