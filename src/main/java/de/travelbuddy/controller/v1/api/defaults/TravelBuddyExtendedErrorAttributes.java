package de.travelbuddy.controller.v1.api.defaults;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        // Check if exception class is available
        Class<?> clazz = super.getError(webRequest).getClass();
        if (clazz == null)
            errorAttributes.put("exception", "Unknown Exception");
        else
            errorAttributes.put("exception", super.getError(webRequest).getClass().getSimpleName());

        return errorAttributes;
    }


}
