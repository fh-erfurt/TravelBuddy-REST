package de.travelbuddy.controller.v1.api;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8889")
public abstract class RestAssuredTestBase {

    @LocalServerPort
    private static int port;

    @BeforeClass
    @BeforeAll
    public static void setup() {
        String port = System.getProperty("server.port");
        RestAssured.port = port == null ? 8889 : Integer.parseInt(port);

        String basePath = System.getProperty("server.base");
        if ( basePath == null ){
            basePath = "/api/v1";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;
    }

    public abstract void pingTest_should_return_200();
}
