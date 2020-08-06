package de.travelbuddy.controller.v1.api.DONE;

import de.travelbuddy.controller.v1.api.RestAssuredTestBase;

import de.travelbuddy.controller.v1.api.journey.JourneyController;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.IGenericRepo;
import de.travelbuddy.utilities.InstanceHelper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Currency;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;


public class JourneyControllerTest extends RestAssuredTestBase {


    @Autowired
    JourneyController journeyController;

    @Autowired
    IGenericRepo<Journey> repo;
    IGenericRepo<Person> repoPerson;
    IGenericRepo<Place> repoPlace;


    //<editor-fold desc="CRUD">

    //###################
    //##### CREATE ######
    //###################

    @Test
    public void create_journey_test () {

        //Create
        Journey post = InstanceHelper.createJourney();

        given().
                contentType(ContentType.JSON).body(post).
                when().
                post("/journeys").
                then().
                statusCode(201).assertThat().body("id", equalTo(post.getId()));
    }

    //###################
    //###### READ #######
    //###################

    @Test
    public void read_journey_test () {

        //Read
        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);

        when().
                get("/journeys/" + inital.getId()).
                then().
                statusCode(200).assertThat().body("id", equalTo(inital.getId()));
    }


    //TODO Check if all Journeys are there
    @Test
    public void read_journeys_test () {

        //Read
        Journey inital1 = InstanceHelper.createJourney();
        Journey inital2 = InstanceHelper.createJourney();
        repo.save(inital1);
        repo.save(inital2);

        when().
                get("/journeys" ).
                then().
                statusCode(200);
    }


    //###################
    //##### UPDATE ######
    //###################

    @Test
    public void update_journey_test () {

        //Update
        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);
        Journey update = InstanceHelper.createJourney();

        given().
                contentType(ContentType.JSON).body(update).
                when().
                put("/journeys/" + inital.getId()).
                then().
                statusCode(200).assertThat().body("title", equalTo(update.getTitle()));

    }

    //###################
    //##### DELETE ######
    //###################

    @Test
    public void delete_journey_test () {

        //Delete
        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);

        when().
                delete("/journeys/" + inital.getId()).
                then().
                statusCode(200);

        when().
                get("/journeys/" + inital.getId()).then().statusCode(404);

    }
    //</editor-fold>


    @Test
    public void show_persons_of_journey_test () {

        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);

        when().
                get("/journeys/"+inital.getId()+"/persons").
                then().
                statusCode(200);

    }

    @Test
    public void add_person_to_journey_test (){

        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                put("/journeys/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);
        when().
                get("/journeys/" + inital.getId() + "/persons/" + person.getId()).
        then().
                statusCode(200);

    }

    @Test
    public void remove_person_from_journey_test (){

        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                delete("/journeys/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);

        when().
                get("/journeys/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(404);

    }


    @Test
    public void show_locations_of_journey_test () {

        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);

        when().
                get("/journeys/"+inital.getId()+"/places").
                then().
                statusCode(200);

    }

    @Test
    public void add_location_to_journey_test (){

        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);
        Place place = InstanceHelper.createPlace();
        repoPlace.save(place);

        when().
                put("/journeys/" + inital.getId() + "/places/" + place.getId()).
                then().
                statusCode(200);
        when().
                get("/journeys/" + inital.getId() + "/places/" + place.getId()).
                then().
                statusCode(200);

    }

    @Test
    public void remove_location_from_journey_test (){

        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);
        Place place = InstanceHelper.createPlace();
        repoPlace.save(place);

        when().
                delete("/journeys/" + inital.getId() + "/places/" + place.getId()).
                then().
                statusCode(200);

        when().
                get("/journeys/" + inital.getId() + "/places/" + place.getId()).
                then().
                statusCode(404);

    }

    @Test
    public void get_cost_Pp_for_journey_in_EUR_test (){

        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                get("/journeys/" + inital.getId() + "/costspp" + person.getId() +"/EUR").
                then().
                statusCode(200).
                assertThat().body("currency",equalTo("EUR")).
                assertThat().
                body("value",equalTo(inital.totalCostOfPerson(Currency.getInstance("EUR"), person).getValue()));

    }

    //TODO muss noch gebaut werden
    @Test
    public void get_cost_of_journey_in_EUR_test (){

        Journey inital = InstanceHelper.createJourney();
        repo.save(inital);

        when().
                get("/journeys/" + inital.getId() + "/money").
                then().
                statusCode(200).
                assertThat().body("currency",equalTo(inital.totalCost(Currency.getInstance("EUR")).getCurrency())).
                assertThat().body("value",equalTo(inital.totalCost(Currency.getInstance("EUR")).getValue()));

    }









    /*

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
     */

}
