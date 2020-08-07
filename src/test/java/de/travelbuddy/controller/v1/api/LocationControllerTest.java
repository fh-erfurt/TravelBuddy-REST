package de.travelbuddy.controller.v1.api;

import de.travelbuddy.controller.v1.api.RestAssuredTestBase;

import de.travelbuddy.controller.v1.api.place.LocationController;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.ExpenseRepo;
import de.travelbuddy.storage.repositories.PersonRepo;
import de.travelbuddy.storage.repositories.PlaceRepo;
import de.travelbuddy.utilities.InstanceHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Currency;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

//erst mal auf Place getrimmt
public class LocationControllerTest extends RestAssuredTestBase {

    @Autowired
    LocationController<Place> locationController;

    @Autowired
    PlaceRepo repo;
    @Autowired
    PersonRepo repoPerson;
    @Autowired
    ExpenseRepo repoExpense;

    String controllerBasePath = "/places";


    //<editor-fold desc="CRUD">

    //###################
    //##### CREATE ######
    //###################

    @Test
    public void create_place_test () {

        //Create
        Place post = InstanceHelper.createPlace();

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
    public void read_place_test () {

        //Read
        Place inital = InstanceHelper.createPlace();
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


    //TODO Check if all Journeys are there
    @Test
    public void read_places_test () {

        //Read
        Place inital1 = InstanceHelper.createPlace();
        Place inital2 = InstanceHelper.createPlace();

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

    // TODO update Funktion ersetzt created TimeStamp -> Fehlermeldung
    @Test
    public void update_place_test () {

        //Update
        Place inital = InstanceHelper.createPlace();
        Response response;
        response = given().log().all().
                contentType(ContentType.JSON).body(inital).
                when().
                post(controllerBasePath).
                then().
                statusCode(201).assertThat().body("name", equalTo(inital.getName())).extract().response();

        Place update = InstanceHelper.definedId(InstanceHelper.createPlace(),response.jsonPath().getLong("id"));

        given().log().all().
                when().
                get(controllerBasePath + "/" + response.jsonPath().getInt("id") ).
                then().log().body().
                statusCode(200);

        given().log().all().
                contentType(ContentType.JSON).body(update).
        when().
                put(controllerBasePath + "/" + response.jsonPath().getInt("id")).
        then().log().body().
                statusCode(200).assertThat().body("name", equalTo(update.getName()));

    }

    //###################
    //##### DELETE ######
    //###################

    @Test
    public void delete_place_test () {

        //Delete
        Place inital = InstanceHelper.createPlace();

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


    @Test
    public void show_persons_of_place_test () {

        Place inital = InstanceHelper.createPlace();

        given().log().all().
                contentType(ContentType.JSON).body(inital).
                when().
                post(controllerBasePath).
                then().
                statusCode(201).assertThat().body("name", equalTo(inital.getName()));

        given().log().all().
        when().
                get(controllerBasePath + "/" + inital.getId() + "/persons").
        then().
                statusCode(200);

    }

    @Test
    public void add_person_to_place_test (){

        Place inital = InstanceHelper.createPlace();
        Response responseInital;
        responseInital = given().log().all().
                contentType(ContentType.JSON).body(inital).
                when().
                post(controllerBasePath).
                then().
                statusCode(201).assertThat().body("name", equalTo(inital.getName())).extract().response();

        given().log().all().
                when().
                get(controllerBasePath + "/" + inital.getId() + "/persons").
                then().
                statusCode(200);

        Person person = InstanceHelper.createPerson();
        Response responsePerson;
        responsePerson = given().log().all().
                contentType(ContentType.JSON).body(person).
                when().
                post(controllerBasePath).
                then().
                statusCode(201).assertThat().body("name", equalTo(person.getName())).extract().response();

        given().log().all().
                when().
                get(controllerBasePath + "/" + person.getId() + "/persons").
                then().
                statusCode(200);


        given().log().all().
        when().
                put(controllerBasePath + "/" + responseInital.jsonPath().getLong("id") + "/persons/" + responsePerson.jsonPath().getLong("id")).
                then().
                statusCode(200);

        given().log().all().
        when().
                get(controllerBasePath + "/" + responseInital.jsonPath().getLong("id") + "/persons/" + responsePerson.jsonPath().getLong("id")).
                then().
                statusCode(200);

    }

    @Test
    public void remove_person_from_place_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        given().log().all().
        when().
                delete(controllerBasePath + "/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);

        given().log().all().
        when().
                get(controllerBasePath + "/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(404);

    }


    //TODO EXPENSES
    @Test
    public void show_expenses_of_place_test () {

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);

        given().log().all().
        when().
                get(controllerBasePath + "/" +inital.getId()+"/expenses").
                then().
                statusCode(200);

    }

    @Test
    public void add_expense_to_place_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Expense expense = InstanceHelper.createExpense();
        repoExpense.save(expense);

        given().log().all().
        when().
                put(controllerBasePath + "/" + inital.getId() + "/expenses/" + expense.getId()).
                then().
                statusCode(200);

        given().log().all().
        when().
                get(controllerBasePath + "/" + inital.getId() + "/expenses/" + expense.getId()).
                then().
                statusCode(200);

    }

    @Test
    public void remove_expense_from_place_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Expense expense = InstanceHelper.createExpense();
        repoExpense.save(expense);

        given().log().all().
        when().
                delete(controllerBasePath + "/" + inital.getId() + "/expenses/" + expense.getId()).
                then().
                statusCode(200);

        given().log().all().
        when().
                get(controllerBasePath + "/" + inital.getId() + "/expenses/" + expense.getId()).
                then().
                statusCode(404);

    }


    @Test
    public void get_cost_Pp_for_place_in_EUR_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        given().log().all().
        when().
                get(controllerBasePath + "/" + inital.getId() + "/costspp" + person.getId() +"/EUR").
                then().
                statusCode(200).
                assertThat().body("currency",equalTo("EUR")).
                assertThat().
                body("value",equalTo(inital.totalCostOfPerson(Currency.getInstance("EUR"), person).getValue()));

    }

    //TODO muss noch gebaut werden
    @Test
    public void get_cost_of_place_in_EUR_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);

        given().log().all().
        when().
                get(controllerBasePath + "/" + inital.getId() + "/money").
                then().
                statusCode(200).
                assertThat().body("currency",equalTo(inital.totalCost(Currency.getInstance("EUR")).getCurrency())).
                assertThat().body("value",equalTo(inital.totalCost(Currency.getInstance("EUR")).getValue()));

    }


}
