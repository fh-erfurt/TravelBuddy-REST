package de.travelbuddy.controller.v1.api.defaults;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
/**
  Adds the name of the an exception to the json result
 */
public class TravelBuddyExtendedErrorAttributes extends DefaultErrorAttributes {


    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options)
    {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        //Check if error is available
        Throwable ex = super.getError(webRequest);
        Class<?> clazz = null;

        // Check if exception class is available
        if (ex != null)
            clazz = super.getError(webRequest).getClass();

        if (clazz == null)
            errorAttributes.put("exception", "Unknown Exception");
        else
            errorAttributes.put("exception", super.getError(webRequest).getClass().getSimpleName());

        return errorAttributes;
    }


}
