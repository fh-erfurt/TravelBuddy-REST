package de.travelbuddy.controller.v1.api.journey;

import de.travelbuddy.controller.v1.api.RestAssuredTestBase;

import io.restassured.http.ContentType;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class JourneyControllerTest extends RestAssuredTestBase {

    @Test
    public void pingTest_should_return_200() {

        given().log().all()
            .when().get("/journeys")
            .then().statusCode(200);
    }

    @Test
    public  void tst()
    {
        JSONObject bodyparam = new JSONObject();
        bodyparam.put("title", "abc");

        given().log().all()
                .contentType(ContentType.JSON)
                .body(bodyparam.toJSONString())
                .when().post("/journeys")
                .then().statusCode(201);
    }

    @Test
    public void tst2()
    {
        JSONObject bodyparam = new JSONObject();
        bodyparam.put("lastname", "abc");

        given().log().all()
                .contentType(ContentType.JSON)
                .body(bodyparam.toJSONString())
                .when().post("/persons")
                .then().statusCode(201);
    }
}
