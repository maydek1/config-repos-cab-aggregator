package com.software.modsen.end2end.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@CucumberContextConfiguration
public class RideCreationSteps {

    private int statusCode;
    private int passengerId;
    private int driverId;
    private int rideId;
    private int passengerBalance;

    @After
    public void deleteTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:8088/api/v1/drivers/" + driverId)
                .then();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:8088/api/v1/passenger/" + passengerId)
                .then();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:8088/api/v1/ride/" + rideId)
                .then();
    }

    @Given("A driver with id {long}, firstName {string}, carId {int}, email {string}, phone {string}")
    public void createDriver(long id, String firstName, int carId, String email, String phone) {
        String requestBody = "{"
                + "\"email\": \"rhrergwe6565fw24@gmail.com\", "
                + "\"id\": " + id + ", "
                + "\"firstName\": \"" + firstName + "\", "
                + "\"phone\": \"" + phone + "\", "
                + "\"carId\": " + carId + " "
                + "}";

        driverId = (RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8088/api/v1/drivers")
                .then()
                .statusCode(201).extract().path("id"));

    }


    @Given("A passenger with email {string}, phone {string}, firstName {string}, secondName {string}")
    public void createPassenger(String email, String phone, String firstName, String secondName) {
        String requestBody = "{"
                + "\"email\": \"" + email + "\", "
                + "\"firstName\": \"" + firstName + "\", "
                + "\"secondName\": \"" + secondName + "\", "
                + "\"phone\": \"" + phone + "\" "
                + "}";

        passengerId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8088/api/v1/passenger")
                .then()
                .statusCode(201)
                .extract().path("id");
    }

    @When("I send a request to create a ride")
    public void createRide() {
        String requestBody = "{"
                + "\"passengerId\": \"" + passengerId + "\", "
                + "\"pickupAddress\": \"A\", "
                + "\"destinationAddress\": \"B\", "
                + "\"price\": 0 "
                + "}";

        rideId = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://localhost:8088/api/v1/ride/ride")
                .then()
                .extract().path("id");
    }

    @Then("The ride should be successfully created")
    public void verifyRideCreation() {
        statusCode = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:8088/api/v1/ride/" + rideId)
                .getStatusCode();

        Assertions.assertEquals(HttpStatus.OK.value(), statusCode);
    }

    @And("The driver accepted the ride")
    public void aDriverWithIdLongAcceptedRide() {
        String requestBody = "{"
                + "\"status\": \"ACCEPTED\", "
                + "\"rideId\": \"" + rideId + "\""
                + "}";

        statusCode = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .log().all()
                .post("http://localhost:8088/api/v1/ride/update-status")
                .getStatusCode();
    }

    @Then("The ride should be have ACCEPTED status and startDate")
    public void theRideShouldBeHaveACCEPTEDStatusAndStartDate() {
        Assertions.assertEquals(HttpStatus.OK.value(), statusCode);
    }

    @And("A passenger with email {string}, phone {string}, firstName {string}, lastName {string}, and balance {int}")
    public void createPassenger(String email, String phone, String firstName, String secondName, int balance) {
        String requestBody = "{"
                + "\"email\": \"" + email + "\", "
                + "\"firstName\": \"" + firstName + "\", "
                + "\"secondName\": \"" + secondName + "\", "
                + "\"hone\": \"" + phone + "\", "
                + "\"money\": \"" + balance + "\" "
                + "}";

        passengerId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .log().all()
                .post("http://localhost:8088/api/v1/passenger")
                .then()
                .statusCode(201)
                .extract().path("id");
    }


    @And("I send a request to create a ride with a price of {int}")
    public void iSendARequestToCreateARideWithAPriceOf(int price) {
        String requestBody = "{"
                + "\"passengerId\": \"" + passengerId + "\", "
                + "\"pickupAddress\": \"A\", "
                + "\"destinationAddress\": \"B\", "
                + "\"money\": \"" + price + "\" "
                + "}";

        rideId = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://localhost:8088/api/v1/ride/ride")
                .then()
                .extract().path("id");
    }


    @When("The driver completes the ride")
    public void theDriverCompletesTheRide() {
        String requestBody = "{"
                + "\"status\": \"COMPLETED\", "
                + "\"rideId\": \"" + rideId + "\""
                + "}";

        statusCode = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .log().all()
                .post("http://localhost:8088/api/v1/ride/update-status")
                .getStatusCode();

    }

    @Then("The passenger's balance should be {int}")
    public void thePassengerSBalanceShouldBe(int money) {
        passengerBalance = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8088/api/v1/passenger/"+passengerId)
                .then()
                .extract().path("money");

        Assertions.assertEquals(passengerBalance, 20);
    }

}
