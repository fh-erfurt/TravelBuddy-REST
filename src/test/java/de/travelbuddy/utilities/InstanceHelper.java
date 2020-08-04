package de.travelbuddy.utilities;

import de.travelbuddy.model.BaseModel;
import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.model.place.*;
import de.travelbuddy.model.place.exception.InvalidLatitudeException;
import de.travelbuddy.model.place.exception.InvalidLongitudeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Random;
import java.util.UUID;

/**
 * Creates instances with random values
 */
@SuppressWarnings("SameParameterValue")
public class InstanceHelper {

    public static Journey createJourney()
    {
        Journey journey = new Journey();
        journey.setTitle(rndString());
        return rndId(journey);
    }

    public static Place createPlace()
    {
        return createPlace(LocalDateTime.now());
    }

    public static Place createPlace(LocalDateTime start)
    {
        Place pl = new Place();
        pl.setName(rndString());
        pl.setCoordinates(createCoordinate());
        pl.setContactDetails(createContactDetails());
        pl.setArrive(start);
        pl.setDeparture(start.plusHours(rndInt(1, 8)));
        return rndId(pl);
    }

    public static Accommodation createAccommodation()
    {
        return createAccommodation(LocalDateTime.now());
    }

    public static Accommodation createAccommodation(LocalDateTime start)
    {
        Accommodation pl = new Accommodation();
        pl.setName(rndString());
        pl.setCoordinates(createCoordinate());
        pl.setContactDetails(createContactDetails());
        pl.setArrive(start);
        pl.setDeparture(start.plusHours(rndInt(1, 8)));
        pl.setType(Accommodation.accommodationType.HOSTEL);
        return rndId(pl);
    }

    public static Sight createSight()
    {
        return createSight(LocalDateTime.now());
    }

    public static Sight createSight(LocalDateTime start)
    {
        Sight pl = new Sight();
        pl.setName(rndString());
        pl.setCoordinates(createCoordinate());
        pl.setContactDetails(createContactDetails());
        pl.setArrive(start);
        pl.setDeparture(start.plusHours(rndInt(1, 8)));
        pl.setIndoor(true);
        return rndId(pl);
    }

    public static Coordinates createCoordinate()
    {
        Coordinates ret = null;
        try {
            ret = new Coordinates();
            ret.setLatitude(rndDouble(-90, 90));
            ret.setLongitude(rndDouble(-180, 180));
            rndId(ret);
        }
        catch (InvalidLatitudeException | InvalidLongitudeException ex)
        {
            //Can't happen, unless Coordinate Class is broken
        }
        return ret;
    }


    public static ContactDetails createContactDetails()
    {
        ContactDetails cd = new ContactDetails();
        cd.setEmail(rndEmail());
        cd.setPhone(String.valueOf(rndLong(111111, 9999999)));
        cd.setTown(rndString());
        cd.setStreet(rndString());
        cd.setStreetNumber(rndInt(1,100));
        cd.setZIP(rndString());
        cd.setCountry(rndString());
        return rndId(cd);
    }

    public static Expense createExpense()
    {
        return createExpense(rndCurrency());
    }

    public static Expense createExpense(Currency currency)
    {
        Expense exp = new Expense();
        exp.setTitle(rndString());
        exp.setDescription(rndString() + rndString() + rndString() + rndString());
        exp.setPrice(createMoney(currency));
        exp.setStatus(Expense.planned.PLANNED);
        exp.setPerPerson(false);
        return rndId(exp);
    }

    public static Money createMoney()
    {
        return createMoney(rndCurrency());
    }

    public static Money createMoney(Currency currency)
    {
        Money mon = new Money();
        mon.setCurrency(currency);
        mon.setValue(BigDecimal.valueOf(rndDouble(0, 100)).setScale(2, RoundingMode.HALF_UP));
        return mon;
    }

    public static Person createPersonMale()
    {
        Person p = new Person();
        p.setFirstName(rndFirstname(false));
        p.setName(rndLastname());
        p.setBirthdate(rndLocalDate(LocalDate.now().minusYears(60).getYear(), LocalDate.now().minusYears(20).getYear()));
        p.setContactDetails(createContactDetails());
        return rndId(p);
    }

    public static Person createPersonFemale()
    {
        Person p = new Person();
        p.setFirstName(rndFirstname(true));
        p.setName(rndLastname());
        p.setBirthdate(rndLocalDate(LocalDate.now().minusYears(60).getYear(), LocalDate.now().minusYears(20).getYear()));
        p.setContactDetails(createContactDetails());
        return rndId(p);
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
        Connection con = new Connection();
        con.setTitle(rndString());
        con.setArrive(to.getArrive());
        con.setDeparture(from.getDeparture());
        con.setEnd(to);
        con.setStart(from);
        con.setExpense(createExpense());
        return rndId(con);
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

    private static <T extends BaseModel> T initId(T inst, Long value)  {
        try {
            Method privMeth = inst.getClass().getSuperclass().getDeclaredMethod("setId", Long.class);
            privMeth.setAccessible(true);
            privMeth.invoke(inst, value);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return inst;
    }

    public static <T extends BaseModel> T rndId(T inst)
    {
        return initId(inst, rndLong(1,99999));
    }

    public static <T extends BaseModel> T clearId(T inst)
    {
        return initId(inst, null);
    }
}

