package de.travelbuddy.controller.v1.api.journey;

import de.travelbuddy.controller.v1.api.RestAssuredTestBase;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class JourneyControllerTest extends RestAssuredTestBase {

    @Test
    public void pingTest_should_return_200() {

        given().log().all()
            .when()
            .get("/journeys")
            .then()
            .statusCode(200);
    }
}
