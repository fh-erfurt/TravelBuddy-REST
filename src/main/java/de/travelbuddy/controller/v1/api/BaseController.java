package de.travelbuddy.controller.v1.api;

import de.travelbuddy.model.BaseModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BaseController<T extends BaseModel> {
    protected Class<T> type;

    protected BaseController(){
    }

    protected List<?> toList(Iterable<?> iter)
    {
        return StreamSupport.stream(iter.spliterator(), false)
                .collect(Collectors.toList());
    }

    protected List<T> toListT(Iterable<T> iter)
    {
        return StreamSupport.stream(iter.spliterator(), false)
                .collect(Collectors.toList());
    }
}
