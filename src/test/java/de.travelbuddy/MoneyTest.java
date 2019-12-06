package de.travelbuddy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    public void shouldConvertRUBToEUR (){

        //Given
        Currency RUB = Currency.getInstance("RUB");
        Currency EUR = Currency.getInstance("EUR");
        Money testMoney = new Money(RUB, BigDecimal.valueOf(100));

        //When
        testMoney.ConvertTo(EUR);


        //Then
        assertEquals(testMoney.getCurrency().getCurrencyCode(),"EUR");
        assertEquals(testMoney.getValue(),testMoney.getValue().divide(new BigDecimal(70.8), RoundingMode.UP));
    }

}