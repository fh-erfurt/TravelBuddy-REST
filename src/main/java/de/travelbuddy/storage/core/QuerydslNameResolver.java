/*
package de.travelbuddy.storage.core;

import com.querydsl.core.types.dsl.EntityPathBase;
import de.travelbuddy.model.BaseModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static de.travelbuddy.model.QContactDetails.contactDetails;
import static de.travelbuddy.model.QPerson.person;
import static de.travelbuddy.model.finance.QExpense.expense;
import static de.travelbuddy.model.journey.QJourney.journey;
import static de.travelbuddy.model.place.QAccommodation.accommodation;
import static de.travelbuddy.model.place.QConnection.connection;
import static de.travelbuddy.model.place.QCoordinates.coordinates;
import static de.travelbuddy.model.place.QPlace.place;
import static de.travelbuddy.model.place.QSight.sight;

//QPerson extends EntityPathBase<Person>
public class QuerydslNameResolver {

    public static <T extends BaseModel> EntityPathBase<T> resolve(Class<T> clazz) throws NoSuchMethodException,
            ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        switch (clazz.getName()) {
            case "de.travelbuddy.model.Person": return (EntityPathBase<T>) person;
            case "de.travelbuddy.model.finance.Expense": return (EntityPathBase<T>) expense;
            case "de.travelbuddy.model.journey.Journey": return (EntityPathBase<T>) journey;
            case "de.travelbuddy.model.place.Accommodation": return (EntityPathBase<T>) accommodation;
            case "de.travelbuddy.model.place.Connection": return (EntityPathBase<T>) connection;
            case "de.travelbuddy.model.place.Coordinates": return (EntityPathBase<T>) coordinates;
            case "de.travelbuddy.model.place.Place": return (EntityPathBase<T>) place;
            case "de.travelbuddy.model.place.Sight": return (EntityPathBase<T>) sight;
            case "de.travelbuddy.model.ContactDetails": return (EntityPathBase<T>) contactDetails;
            default:
                // TODO LOG WARNING... "Fetching Q-Type for type '%s'. Consider expanding the QuerydslNameResolver for better perfomance!"
            return createInstance(clazz);
        }
    }

    private static <T extends BaseModel> EntityPathBase<T> createInstance(Class<T> tClass) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return resolveQConstructor(tClass).newInstance(resolveTypeName(tClass));
    }

    private static <T extends BaseModel> Constructor<? extends EntityPathBase<T>> resolveQConstructor(Class<T> tClass)
            throws NoSuchMethodException, ClassNotFoundException {
        String str = tClass.getName();
        Class<?> c = Class.forName(new StringBuilder(str).insert(str.lastIndexOf('.') +1, "Q" ).toString());
        Class<? extends EntityPathBase<T>> clazz = (Class<? extends EntityPathBase<T>>) Class.forName(new StringBuilder(str).insert(str.lastIndexOf('.') +1, "Q" ).toString());
        return clazz.getDeclaredConstructor(String.class);
    }

    private static <T extends BaseModel> String resolveTypeName(Class<T> tClass)
    {
        char[] charTypeName = tClass.getSimpleName().toCharArray();
        charTypeName[0] = Character.toLowerCase(charTypeName[0]);
        return new String(charTypeName);
    }
}
*/
