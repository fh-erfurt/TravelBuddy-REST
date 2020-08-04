package de.travelbuddy.controller.v1.api.journey;

//import de.travelbuddy.controller.v1.api.RestAssuredTestBase;
import de.travelbuddy.controller.v1.api.RestAssuredTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static 	io.restassured.RestAssured.*;
import static            io.restassured.matcher.RestAssuredMatchers.*;
import static          org.hamcrest.Matchers.*;

@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JourneyControllerTest extends RestAssuredTestBase {

    @Test
    public void PingTest_should_return_200() {
        given().when().get("/journeys").then().statusCode(200);
    }
}
