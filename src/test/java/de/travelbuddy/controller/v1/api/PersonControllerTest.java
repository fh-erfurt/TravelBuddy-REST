package de.travelbuddy.controller.v1.api;

import de.travelbuddy.controller.v1.api.PersonController;
import de.travelbuddy.controller.v1.api.RestAssuredTestBase;
import de.travelbuddy.model.Person;
import de.travelbuddy.storage.repositories.PersonRepo;
import de.travelbuddy.utilities.InstanceHelper;
import io.restassured.http.ContentType;
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
                post("/persons").
        then().
                statusCode(201).assertThat().body("id", equalTo(post.getId()));
    }

    //###################
    //###### READ #######
    //###################

    @Test
    public void read_person_test () {

        //Read
        Person inital = InstanceHelper.createPerson();
        repo.save(inital);

        given().log().all().
        when().
                get("/persons/" + inital.getId()).
        then().
                statusCode(200).assertThat().body("id", equalTo(inital.getId()));
    }


    //TODO Check if all Persons are there
    @Test
    public void read_persons_test () {

        //Read
        Person inital1 = InstanceHelper.createPerson();
        Person inital2 = InstanceHelper.createPerson();
        repo.save(inital1);
        repo.save(inital2);

        given().log().all().
        when().
                get("/persons" ).
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
                put("/persons/" + inital.getId()).
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
        repo.save(inital);

        given().log().all().
        when().
                delete("/persons/" + inital.getId()).
        then().
                statusCode(200);

        given().log().all().
        when().
                get("/persons/" + inital.getId()).then().statusCode(404);


    }
    //</editor-fold>




}
