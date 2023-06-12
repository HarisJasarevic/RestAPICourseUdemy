package com.restAssured.restApiPractice.tests;

import com.restAssured.restApiPractice.files.ReusableMethods;
import com.restAssured.restApiPractice.files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DynamicJsonTest {

    @Test (priority = 4, description = "Dynamic JSON testing.")

    public void addBook () {

        RestAssured.baseURI = "http://216.10.245.166";

        String response =

        given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(payload.AddBook("GB-10-Book", "3373"))
        .when()
                .post("/Library/Addbook.php")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println("Created book ID is: " + id);





    }
}
