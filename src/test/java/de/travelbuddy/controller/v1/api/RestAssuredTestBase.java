package de.travelbuddy.controller.v1.api;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public abstract class RestAssuredTestBase {
    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        RestAssured.port = port == null ? 8080 : Integer.parseInt(port);

        String basePath = System.getProperty("server.base");
        if ( basePath == null ){
            basePath = "/api/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;
    }

    public abstract void PingTest_should_return_200();
}
