package de.travelbuddy.controller.v1.api;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class RestAssuredTestBase {
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
}
