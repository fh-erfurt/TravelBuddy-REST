package de.travelbuddy.controller.v1.api;

import de.travelbuddy.controller.v1.api.PersonController;
import de.travelbuddy.controller.v1.api.RestAssuredTestBase;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.PersonRepo;
import de.travelbuddy.utilities.InstanceHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class PersonControllerTest extends RestAssuredTestBase {

    @Autowired
    PersonController personController;

    @Autowired
    PersonRepo repo;

    String controllerBasePath = "/persons";

    //<editor-fold desc="CRUD">

    //###################
    //##### CREATE ######
    //###################

    @Test
    public void create_person_test () {

        //Create
        Person post = InstanceHelper.createPerson();

        given().log().all().
                contentType(ContentType.JSON).body(post).
        when().
                post(controllerBasePath).
        then().
                statusCode(201).assertThat().body("name", equalTo(post.getName()));
    }

    //###################
    //###### READ #######
    //###################

    @Test
    public void read_person_test () {

        //Read
        Person inital = InstanceHelper.createPerson();
        Response response;

        response = given().log().all().
                contentType(ContentType.JSON).body(inital).
                when().
                post(controllerBasePath).then().statusCode(201).extract().response();

        given().log().all().
                when().
                get(controllerBasePath + "/" + response.jsonPath().getInt("id") ).
                then().log().body().
                statusCode(200);
    }


    @Test
    public void read_persons_test () {

        //Read
        Person inital1 = InstanceHelper.createPerson();
        Person inital2 = InstanceHelper.createPerson();

        given().log().all().
                contentType(ContentType.JSON).body(inital1).
                when().
                post(controllerBasePath).
                then().
                statusCode(201).assertThat().body("name", equalTo(inital1.getName()));

        given().log().all().
                contentType(ContentType.JSON).body(inital2).
                when().
                post(controllerBasePath).
                then().
                statusCode(201).assertThat().body("name", equalTo(inital2.getName()));

        given().log().all().
                when().
                get(controllerBasePath).
                then().
                statusCode(200);
    }

    //###################
    //##### UPDATE ######
    //###################

    @Test
    public void update_person_test () {

        //Update
        Person inital = InstanceHelper.createPerson();
        repo.save(inital);
        Person update = InstanceHelper.createPerson();

        given().log().all().
                contentType(ContentType.JSON).body(update).
        when().
                put(controllerBasePath + "/" + inital.getId()).
        then().
                statusCode(200).assertThat().body("name", equalTo(update.getName()));

    }

    //###################
    //##### DELETE ######
    //###################

    @Test
    public void delete_person_test () {

        //Delete
        Person inital = InstanceHelper.createPerson();
        Response response;
        response = given().log().all().
                contentType(ContentType.JSON).body(inital).
                when().
                post(controllerBasePath).
                then().
                statusCode(201).assertThat().body("name", equalTo(inital.getName())).extract().response();

        given().log().all().
                when().
                delete(controllerBasePath + "/" + response.jsonPath().getLong("id")).
                then().
                statusCode(200);

        given().log().all().
                when().
                get(controllerBasePath + "/" + response.jsonPath().getLong("id")).
                then().
                statusCode(404);

    }
    //</editor-fold>




}
