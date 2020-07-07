package de.travelbuddy.storage.core;

import org.jinq.orm.stream.JinqStream;

/**
 * Todo Text
 * @param <T>
 */
public interface IJpaGenericStream<T> {

    /**
     * Creates a stream to retrieve and alter data
     * @param type The class of the model
     * @return The queryable stream
     */
    JinqStream<T> getStream(Class<T> type);
}
