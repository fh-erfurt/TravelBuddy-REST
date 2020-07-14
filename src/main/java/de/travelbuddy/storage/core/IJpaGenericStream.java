package de.travelbuddy.storage.core;

import org.jinq.orm.stream.JinqStream;

/**
 * Todo Text
 * @param <T>
 */
public interface IJpaGenericStream<T> {

    /**
     * Creates a stream to retrieve and alter data
     * @return The queryable stream
     */
    JinqStream<T> getStream();

    IJpaGenericStream<T> setType(Class<T> type);
    Class<T> getType();
}
