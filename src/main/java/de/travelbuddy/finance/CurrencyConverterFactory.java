package de.travelbuddy.finance;
// TODO JAVADOC
public class CurrencyConverterFactory {
    public static ICurrencyConverter create()
    {
        return new CurrencyConverter();
    }
}
