package de.travelbuddy.utilities;

import de.travelbuddy.*;
import de.travelbuddy.finance.Expense;
import de.travelbuddy.finance.Money;
import de.travelbuddy.journey.Journey;
import de.travelbuddy.place.Connection;
import de.travelbuddy.place.Coordinates;
import de.travelbuddy.place.Place;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Creates instances with random values
 */
public class InstanceHelper {

    public static Journey createJourney()
    {
        return new Journey(rndString(), new ArrayList<Place>(), new ArrayList<Person>());
    }

    public static Place createPlace()
    {
        return createPlace(LocalDateTime.now());
    }

    public static Place createPlace(LocalDateTime start)
    {
        return new Place(rndString(), createCoordinate(), createContactDetails(), start,
                start.plusHours(rndInt(1, 8)), new ArrayList<Expense>(), new ArrayList<Connection>(),
                new ArrayList<Person>());
    }


    public static Coordinates createCoordinate()
    {
        return new Coordinates(rndDouble(-90, 90), rndDouble(-180, 180));
    }


    public static ContactDetails createContactDetails()
    {
        return new ContactDetails(String.valueOf(rndLong(111111, 9999999)), rndEmail(), rndString(), rndString(),
                rndInt(1,100), rndString(), rndCountry());
    }

    public static Expense createExpense()
    {
        return createExpense(rndCurrency());
    }

    public static Expense createExpense(Currency currency)
    {
        return new Expense(rndString(), rndString() + rndString() + rndString() + rndString(),
                createMoney(currency), new ArrayList<Person>(), Expense.planned.PLANNED, false);
    }

    public static Money createMoney()
    {
        return createMoney(rndCurrency());
    }

    public static Money createMoney(Currency currency)
    {
        return new Money(currency, BigDecimal.valueOf(rndDouble(0, 100)).setScale(2, RoundingMode.HALF_UP));
    }

    public static Person createPersonMale()
    {
        return new Person(rndFirstname(false),rndLastname(),
                rndLocalDate(LocalDate.now().minusYears(60).getYear(), LocalDate.now().minusYears(20).getYear()),
                createContactDetails());
    }

    public static Person createPersonFemale()
    {
        return new Person(rndFirstname(true),rndLastname(),
                rndLocalDate(LocalDate.now().minusYears(60).getYear(), LocalDate.now().minusYears(20).getYear()),
                createContactDetails());
    }

    public static Person createPerson()
    {
        return (rndInt(1,2) == 1) ? createPersonFemale() : createPersonMale();
    }

    public static Connection createConnection()
    {
        return createConnection(createPlace(LocalDateTime.now()), createPlace(LocalDateTime.now().plusHours(rndInt(1,8))));
    }

    public static Connection createConnection(Place from)
    {
        return createConnection(from, createPlace(from.getDeparture()));
    }

    public static Connection createConnection(Place from, Place to)
    {
        return new Connection(rndString(), to.getArrive(), from.getDeparture(), from, to, createExpense());
    }

    /* ######### HELPERS ####### */
    private static String rndString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static int rndInt(int min, int max)
    {
        return java.util.concurrent.ThreadLocalRandom.current().nextInt(min, max);
    }

    private static long rndLong(long min, long max)
    {
        return java.util.concurrent.ThreadLocalRandom.current().nextLong(min, max);
    }

    private static double rndDouble(double min, double max)
    {
        return java.util.concurrent.ThreadLocalRandom.current().nextDouble(min, max);
    }

    private static String rndEmail()
    {
        return rndString() + "@example.com";
    }

    private static String rndCountry()
    {
        String[] name = {"Germany", "England", "USA", "France", "Netherlands", "Belgium", "Denmark", "Swiss", "Canada"};
        return name[new Random().nextInt(name.length)];
    }

    private static Currency rndCurrency()
    {
        String[] name = {"EUR", "CHF", "CNY", "PLN", "CZK", "RUB", "USD"};
        return Currency.getInstance(name[new Random().nextInt(name.length)]);
    }

    private static String rndFirstname(Boolean female)
    {
        String[] maleName = {"Jan", "Lukas", "Frieder", "Tim", "Tom", "Peter", "Niclas", "Danny", "Kevin", "Noel", "Hans",
                        "Marcel", "Jonas"};
        String[] femaleName = {"Franziska", "Jenny", "Petra", "Ines", "Manuela", "Olga", "Kim", "Nadia"};
        return female ? femaleName[new Random().nextInt(femaleName.length)] : maleName[new Random().nextInt(maleName.length)];
    }

    private static String rndLastname()
    {
        String[] name = {"Vogel", "Müller", "Schmidt", "Ullmann", "Mustermann", "Hecht", "Schröder", "Fischer", "Schneider"};
        return name[new Random().nextInt(name.length)];
    }

    private static LocalDate rndLocalDate(int minyear, int maxyear)
    {
        return LocalDate.of(rndInt(minyear, maxyear), rndInt(1,12), rndInt(1, 28));
    }
    private static LocalDate rndLocalDate(int minyear, int maxyear, int minmonth, int maxmonth)
    {
        return LocalDate.of(rndInt(minyear, maxyear), rndInt(minmonth, maxmonth), rndInt(1, 28));
    }
    private static String rndPlaceTitle()
    {
        String[] title = {"Erfurter Dom", "Kölner Dom", "Brandenburger Tor", "Berlin Mitte", "FH-Erfurt", "Uni Erfurt", "Veste Coburg", "Nürnberger Flughafen", "Erfurter HBF", "Münchner HBF", "Berlin HBF", "Berlin Westbahnhof"};
        return title[new Random().nextInt(title.length)];
    }
}
/*
fehlt Connection
Person funktioniert Geb. nicht
 */
