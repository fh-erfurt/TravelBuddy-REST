package de.travelbuddy.controller.v1.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
public class HealthController {

    protected static final String HEALTH_RESPONSE = "Still alive...";
    private static final Logger LOG = LoggerFactory.getLogger(HealthController.class);

    @GetMapping("")
    public String areYouAlive() {
        LOG.info("Health check called");
        return HEALTH_RESPONSE;
    }

}