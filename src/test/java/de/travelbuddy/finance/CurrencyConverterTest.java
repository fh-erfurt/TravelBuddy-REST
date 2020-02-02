package de.travelbuddy.finance;

import de.travelbuddy.finance.exception.NotSupportedCurrencyException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConverterTest {
    @Test
    public void shouldConvertRUBToEUR () throws NotSupportedCurrencyException {

        //Given
        CurrencyConverter CC = new CurrencyConverter();
        Currency RUB = Currency.getInstance("RUB");
        Currency EUR = Currency.getInstance("EUR");
        Money testMoney = new Money(RUB, BigDecimal.valueOf(70));

        //When
        Money newMoney = CC.convert(testMoney,EUR);

        //Then
        assertEquals(testMoney.getValue().divide(new BigDecimal(70),2, RoundingMode.HALF_UP),newMoney.getValue());


    }

    @Test
    public void shouldGiveRightConversionRateToEUR () throws NotSupportedCurrencyException {

        //Given
        Currency RUB = Currency.getInstance("CHF");
        Currency EUR = Currency.getInstance("EUR");
        CurrencyConverter CC = new CurrencyConverter();
        BigDecimal Rate;

        //When
        Rate = CC.getRate(RUB,EUR);

        //Then

        assertEquals(Rate,BigDecimal.valueOf(0.9345794393).setScale(10,RoundingMode.HALF_UP));

    }

    @Test
    public void shouldGiveRightConversionRateFromEUR () throws NotSupportedCurrencyException {

        //Given
        Currency RUB = Currency.getInstance("EUR");
        Currency EUR = Currency.getInstance("RUB");
        CurrencyConverter CC = new CurrencyConverter();
        BigDecimal Rate;

        //When
        Rate = CC.getRate(RUB,EUR);

        //Then

        assertEquals(Rate,BigDecimal.valueOf(70).setScale(10,RoundingMode.HALF_UP));

    }

    @Test
    public void shouldGiveRightConversionRateOtherThenEUR () throws NotSupportedCurrencyException {

        //Given
        Currency RUB = Currency.getInstance("CZK");
        Currency EUR = Currency.getInstance("RUB");
        CurrencyConverter CC = new CurrencyConverter();
        BigDecimal Rate;

        //When
        Rate = CC.getRate(RUB,EUR);

        //Then

        assertEquals(Rate,BigDecimal.valueOf(2.7833001988).setScale(10,RoundingMode.HALF_UP));

    }

    @Test
    public void isSupportedCurrency_should_return_correct_value()
    {
        //Given
        Currency notSupported = Currency.getInstance("NLG");
        Currency supported = Currency.getInstance("EUR");
        ICurrencyConverter converter = CurrencyConverterFactory.create();

        //When
        boolean shouldBeFalse = converter.isSupportedCurrency(notSupported);
        boolean shouldBeTrue = converter.isSupportedCurrency(supported);

        //Then
        assertFalse(shouldBeFalse);
        assertTrue(shouldBeTrue);
    }

    @Test
    public void should_throw_NotSupportedCurrencyException()
    {
        //Given
        ICurrencyConverter converter = CurrencyConverterFactory.create();

        //When
        Exception exception = assertThrows(NotSupportedCurrencyException.class, () -> converter.getRate(Currency.getInstance("EUR"), Currency.getInstance("NLG") ));

        //Then
        assertTrue(exception.getMessage().contains("The currency 'NLG' is not supported by this CurrencyConverter-Class"));
    }
}
