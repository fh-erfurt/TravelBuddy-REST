package de.travelbuddy.finance;

import de.travelbuddy.finance.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyConverterTest {
    @Test
    public void shouldConvertRUBToEUR (){

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
    public void shouldGiveRightConversionRateToEUR (){

        //Given
        Currency RUB = Currency.getInstance("CHF");
        Currency EUR = Currency.getInstance("EUR");
        CurrencyConverter CC = new CurrencyConverter();
        BigDecimal Rate;

        //When
        Rate = CC.getRate(RUB,EUR);

        //Then

        assertEquals(Rate,BigDecimal.valueOf(0.93).setScale(2,RoundingMode.HALF_UP));

    }

    @Test
    public void shouldGiveRightConversionRateFromEUR (){

        //Given
        Currency RUB = Currency.getInstance("EUR");
        Currency EUR = Currency.getInstance("RUB");
        CurrencyConverter CC = new CurrencyConverter();
        BigDecimal Rate;

        //When
        Rate = CC.getRate(RUB,EUR);

        //Then

        assertEquals(Rate,BigDecimal.valueOf(70).setScale(2,RoundingMode.HALF_UP));

    }

    @Test
    public void shouldGiveRightConversionRateOtherThenEUR (){

        //Given
        Currency RUB = Currency.getInstance("CZK");
        Currency EUR = Currency.getInstance("RUB");
        CurrencyConverter CC = new CurrencyConverter();
        BigDecimal Rate;

        //When
        Rate = CC.getRate(RUB,EUR);

        //Then

        assertEquals(Rate,BigDecimal.valueOf(2.78).setScale(2,RoundingMode.HALF_UP));

    }
}
