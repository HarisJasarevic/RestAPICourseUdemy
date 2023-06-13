package com.restAssured.restApiPractice.tests;

import com.restAssured.restApiPractice.files.ReusableMethods;
import com.restAssured.restApiPractice.files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DynamicJsonTest {

    @Test (priority = 4, dataProvider = "BooksData", description = "Dynamic JSON testing, Add Book.")

    public void addBook (String isbn, String aisle) {

        RestAssured.baseURI = "http://216.10.245.166";
        String response =

        given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(payload.AddBook(isbn, aisle))
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

    @Test (priority = 5, description = "Dynamic JSON Testing, Delete Book.")

    public void deleteBook () {

        String response =
        RestAssured.baseURI = "http://216.10.245.166";

        given()
                .log().all()
                .queryParam("ID", "1487GM-101-Book")
                .header("Content-Type", "application/json")
        .when()
                .post("/Library/DeleteBook.php?ID=1487GM-101-Book")
        .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @DataProvider (name = "BooksData")
    public Object [][] getData () {

        //Array = Collection of Elements
        //Multidimensional Array = Collection of Arrays

        return new Object[][] {
                {"GB-101-Book", "3373"},
                {"MO-10-Book", "2237"},
                {"GM-101-Book", "1487"}
        };
    }
}
