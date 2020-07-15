package de.travelbuddy.storage.core;

import de.travelbuddy.model.BaseModel;
import org.jinq.orm.stream.JinqStream;

/**
 * Todo Text
 * @param <T>
 */
public interface IJpaGenericStream<T extends BaseModel> {

    /**
     * Creates a stream to retrieve data
     * @return The queryable stream
     */
    JinqStream<T> getStream();

    IJpaGenericStream<T> setType(Class<T> type);
    Class<T> getType();
}
