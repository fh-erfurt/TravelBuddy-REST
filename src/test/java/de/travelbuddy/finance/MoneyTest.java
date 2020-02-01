package de.travelbuddy.finance;

import de.travelbuddy.finance.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    public void should_convert_RUB_to_EUR() throws NotSupportedCurrencyException {

        //Given
        Currency RUB = Currency.getInstance("RUB");
        Currency EUR = Currency.getInstance("EUR");
        Money testMoney = new Money(RUB, BigDecimal.valueOf(70));

        //When
        Money newMoney = testMoney.convert(EUR);

        //Then
        assertEquals(newMoney.getCurrency().getCurrencyCode(),"EUR");
        assertEquals(newMoney.getValue(),testMoney.getValue().divide(new BigDecimal(70),2,RoundingMode.HALF_UP));
    }
    @Test
    public void subtract_with_same_currency(){
        //Given
        Money startVal = new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(100));
        Money subtrVal = new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(25));

        //When
        Money newVal = startVal.subtract(subtrVal);

        //Then
        assertEquals(newVal.getValue(), BigDecimal.valueOf(75));
        assertEquals(newVal.getCurrency(), startVal.getCurrency());
    }

    @Test
    public void subtract_with_different_currency(){
        //Given
        Money startMoney = new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(100).setScale(2,RoundingMode.HALF_UP));
        Money subtrMoney = new Money(Currency.getInstance("RUB"), BigDecimal.valueOf(1750).setScale(2,RoundingMode.HALF_UP));

        //When
        Money newMoney = startMoney.subtract(subtrMoney);

        //Then
        assertEquals(BigDecimal.valueOf(75).setScale(2,RoundingMode.HALF_UP), newMoney.getValue());
        assertEquals(startMoney.getCurrency(),newMoney.getCurrency());
    }

    @Test
    public void add_with_same_currency(){
        //Given
        Money startVal = new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(100).setScale(2,RoundingMode.HALF_UP));
        Money subtrVal = new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(25).setScale(2,RoundingMode.HALF_UP));

        //When
        Money newVal = startVal.add(subtrVal);

        //Then
        assertEquals(BigDecimal.valueOf(125).setScale(2,RoundingMode.HALF_UP),newVal.getValue());
        assertEquals(startVal.getCurrency(),newVal.getCurrency());
    }

    @Test
    public void add_with_different_currency(){
        //Given
        Money startVal = new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(100).setScale(2,RoundingMode.HALF_UP));
        Money subtrVal = new Money(Currency.getInstance("RUB"), BigDecimal.valueOf(1750).setScale(2,RoundingMode.HALF_UP));

        //When
        Money newVal = startVal.add(subtrVal);

        //Then
        assertEquals(BigDecimal.valueOf(125).setScale(2,RoundingMode.HALF_UP),newVal.getValue());
        assertEquals(startVal.getCurrency(),newVal.getCurrency());
    }
}