package de.travelbuddy.controller.v1.api.DONE;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Currency;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

//erst mal auf Place getrimmt
public class LocationControllerTest extends RestAssuredTestBase {

    @Autowired
    LocationController locationController;

    @Autowired
    PlaceRepo repo;
    PersonRepo repoPerson;
    ExpenseRepo repoExpense;


    //<editor-fold desc="CRUD">

    //###################
    //##### CREATE ######
    //###################

    @Test
    public void create_location_test () {

        //Create
        Place post = InstanceHelper.createPlace();

        given().
                contentType(ContentType.JSON).body(post).
        when().
                post("/places").
        then().
                statusCode(201).assertThat().body("id", equalTo(post.getId()));
    }

    //###################
    //###### READ #######
    //###################

    @Test
    public void read_place_test () {

        //Read
        Place inital = InstanceHelper.createPlace();
        repo.save(inital);

        when().
                get("/places/" + inital.getId()).
                then().
                statusCode(200).assertThat().body("id", equalTo(inital.getId()));
    }


    //TODO Check if all Journeys are there
    @Test
    public void read_places_test () {

        //Read
        Place inital1 = InstanceHelper.createPlace();
        Place inital2 = InstanceHelper.createPlace();
        repo.save(inital1);
        repo.save(inital2);

        when().
                get("/places" ).
        then().
                statusCode(200);
    }


    //###################
    //##### UPDATE ######
    //###################

    @Test
    public void update_place_test () {

        //Update
        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Place update = InstanceHelper.createPlace();

        given().
                contentType(ContentType.JSON).body(update).
        when().
                put("/places/" + inital.getId()).
        then().
                statusCode(200).assertThat().body("name", equalTo(update.getName()));

    }

    //###################
    //##### DELETE ######
    //###################

    @Test
    public void delete_place_test () {

        //Delete
        Place inital = InstanceHelper.createPlace();
        repo.save(inital);

        when().
                delete("/places/" + inital.getId()).
                then().
                statusCode(200);

        when().
                get("/places/" + inital.getId()).
        then().
                statusCode(404);

    }
    //</editor-fold>


    @Test
    public void show_persons_of_place_test () {

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);

        when().
                get("/places/"+inital.getId()+"/persons").
        then().
                statusCode(200);

    }

    @Test
    public void add_person_to_place_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                put("/places/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);
        when().
                get("/places/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);

    }

    @Test
    public void remove_person_from_place_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                delete("/places/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);

        when().
                get("/places/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(404);

    }


    //TODO EXPENSES
    @Test
    public void show_expenses_of_place_test () {

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);

        when().
                get("/places/"+inital.getId()+"/expenses").
                then().
                statusCode(200);

    }

    @Test
    public void add_expense_to_place_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Expense expense = InstanceHelper.createExpense();
        repoExpense.save(expense);

        when().
                put("/places/" + inital.getId() + "/expenses/" + expense.getId()).
                then().
                statusCode(200);
        when().
                get("/places/" + inital.getId() + "/expenses/" + expense.getId()).
                then().
                statusCode(200);

    }

    @Test
    public void remove_expense_from_place_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Expense expense = InstanceHelper.createExpense();
        repoExpense.save(expense);

        when().
                delete("/places/" + inital.getId() + "/expenses/" + expense.getId()).
                then().
                statusCode(200);

        when().
                get("/places/" + inital.getId() + "/expenses/" + expense.getId()).
                then().
                statusCode(404);

    }


    @Test
    public void get_cost_Pp_for_place_in_EUR_test (){

        Place inital = InstanceHelper.createPlace();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                get("/places/" + inital.getId() + "/costspp" + person.getId() +"/EUR").
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

        when().
                get("/places/" + inital.getId() + "/money").
                then().
                statusCode(200).
                assertThat().body("currency",equalTo(inital.totalCost(Currency.getInstance("EUR")).getCurrency())).
                assertThat().body("value",equalTo(inital.totalCost(Currency.getInstance("EUR")).getValue()));

    }


}
