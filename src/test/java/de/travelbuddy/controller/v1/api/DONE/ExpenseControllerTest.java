package de.travelbuddy.controller.v1.api.DONE;

import de.travelbuddy.controller.v1.api.RestAssuredTestBase;
import de.travelbuddy.controller.v1.api.finance.ExpenseController;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.storage.repositories.IGenericRepo;
import de.travelbuddy.utilities.InstanceHelper;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;
import static org.junit.jupiter.api.Assertions.*;


public class ExpenseControllerTest extends RestAssuredTestBase {

    @Autowired
    ExpenseController expenseController;

    @Autowired
    IGenericRepo<Expense> repo;
    IGenericRepo<Person> repoPerson;


    //<editor-fold desc="CRUD">

    //###################
    //##### CREATE ######
    //###################

    @Test
    public void create_expense_test () {

        //Create
        Expense post = InstanceHelper.createExpense();

        given().
                contentType(ContentType.JSON).body(post).
                when().
                post("/expenses").
                then().
                statusCode(201).assertThat().body("id", equalTo(post.getId()));
    }

    //###################
    //###### READ #######
    //###################

    @Test
    public void read_expense_test () {

        //Read
        Expense inital = InstanceHelper.createExpense();
        repo.save(inital);

        when().
                get("/expenses/" + inital.getId()).
                then().
                statusCode(200).assertThat().body("id", equalTo(inital.getId()));
    }


    //TODO Check if all Expenses are there
    @Test
    public void read_expenses_test () {

        //Read
        Expense inital1 = InstanceHelper.createExpense();
        Expense inital2 = InstanceHelper.createExpense();
        repo.save(inital1);
        repo.save(inital2);

        when().
                get("/expenses" ).
                then().
                statusCode(200);
    }

    //###################
    //##### UPDATE ######
    //###################

    @Test
    public void update_expense_test () {

        //Update
        Expense inital = InstanceHelper.createExpense();
        repo.save(inital);
        Expense update = InstanceHelper.createExpense();

        given().
                contentType(ContentType.JSON).body(update).
                when().
                put("/expenses/" + inital.getId()).
                then().
                statusCode(200).assertThat().body("title", equalTo(update.getTitle()));

    }

    //###################
    //##### DELETE ######
    //###################

    @Test
    public void delete_expense_test () {

        //Delete
        Expense inital = InstanceHelper.createExpense();
        repo.save(inital);

        when().
                delete("/expenses/" + inital.getId()).
                then().
                statusCode(200);

        when().
                get("/expenses/" + inital.getId()).then().statusCode(404);


    }
    //</editor-fold>


    @Test
    public void show_persons_of_expense_test () {

        Expense inital = InstanceHelper.createExpense();
        repo.save(inital);

        when().
                get("/expenses/"+inital.getId()+"/persons").
                then().
                statusCode(200);


    }

    @Test
    public void add_person_to_expense_test (){

        Expense inital = InstanceHelper.createExpense();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                put("/expenses/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);

        when().
                get("/expenses/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);

    }

    @Test
    public void remove_person_from_expense_test (){

        Expense inital = InstanceHelper.createExpense();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                delete("/expenses/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(200);

        when().
                get("/expenses/" + inital.getId() + "/persons/" + person.getId()).
                then().
                statusCode(404);

    }

    @Test
    public void get_cost_Pp_for_expense_test (){

        Expense inital = InstanceHelper.createExpense();
        repo.save(inital);
        Person person = InstanceHelper.createPerson();
        repoPerson.save(person);

        when().
                get("/expenses/" + inital.getId() + "/costpp").
                then().
                statusCode(200).assertThat().body("currency",equalTo(inital.getMoneyPerPerson().getCurrency())).
                assertThat().body("value",equalTo(inital.getMoneyPerPerson().getValue()));

    }

    //TODO muss noch gebaut werden
    @Test
    public void get_cost_of_expense_test (){

        Expense inital = InstanceHelper.createExpense();
        repo.save(inital);

        when().
                get("/expenses/" + inital.getId() + "/money").
                then().
                statusCode(200).assertThat().body("currency",equalTo(inital.getPrice().getCurrency())).
                assertThat().body("value",equalTo(inital.getPrice().getValue()));

    }





}
