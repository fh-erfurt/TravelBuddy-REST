package de.travelbuddy.controller.v1.api;

import de.travelbuddy.model.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BaseController<T extends BaseModel> {
    protected Class<T> type;
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    protected BaseController(){ }

    /**
     * Create a list from Iterable
     * @param iter Iterable to convert
     * @return Converted List
     */
    protected List<?> toList(Iterable<?> iter)
    {
        LOG.debug("Create list from Iterable");
        return StreamSupport.stream(iter.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Create a list of type T from Iterable
     * @param iter Iterable to convert
     * @return Converted List
     */
    protected List<T> toListT(Iterable<T> iter)
    {
        LOG.debug("Create list from Iterable");
        return StreamSupport.stream(iter.spliterator(), false)
                .collect(Collectors.toList());
    }
}
