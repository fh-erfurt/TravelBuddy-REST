package de.travelbuddy.controller.v1.api;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class HealthControllerTest extends RestAssuredTestBase {

    @Test
    public void pingTest_should_return_200() {

        given().log().all()
                .when().get("/health")
                .then().statusCode(200);
    }
}
