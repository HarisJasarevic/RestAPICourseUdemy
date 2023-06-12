package com.restAssured.restApiPractice.tests;

import com.restAssured.restApiPractice.files.ReusableMethods;
import com.restAssured.restApiPractice.files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

    @Test (priority = 1, description = "API tests for https://rahulshettyacademy.com using given, When, Then method")

    public void basicTest () {

        //Validate if AddPlace API is working as expected

        //GIVEN - All input details, WHEN - Submit the API, THEN - validate response

        // AddPlace -> UpdatePlace with new address -> GetPlace to validate if new address is present in response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response =

        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.AddPlace())
        .when()
                .post("maps/api/place/add/json")
        .then()
                .assertThat().statusCode(200).body("scope", equalTo("APP")).extract().response().asString();


        System.out.println(response);
        JsonPath js = new JsonPath(response); //Method for parsing JSON
        String placeID = js.getString("place_id");

        System.out.println(placeID);

        //Update Place

        String newAddress = "Summer Walk, Africa";

        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"place_id\":\"" + placeID + "\",\n" +     //Here we provided a placeID variable for new created place_id
                        "    \"address\":\"" + newAddress + "\",\n" +
                        "    \"key\":\"qaclick123\"\n" +
                        "}")
        .when()
                .put("maps/api/place/update/json")
        .then()
                .assertThat().log().all().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        //Get Place

        String getPlaceResponse =

        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeID)
        .when()
                .get("maps/api/place/get/json")
        .then()
                .assertThat()
                .log().all()
                .statusCode(200)
                .extract().response().asString();

        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        Assert.assertEquals(actualAddress, newAddress);
    }
}
