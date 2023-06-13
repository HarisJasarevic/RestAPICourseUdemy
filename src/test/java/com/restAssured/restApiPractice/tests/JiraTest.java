package com.restAssured.restApiPractice.tests;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;

public class JiraTest {

    @Test (priority = 6, description = "Automating JIRA API tests.")

    public void jiraTests () {

        RestAssured.baseURI = "http://localhost:8080";     //Jira software localhost URI

        //Login Scenario

        SessionFilter session = new SessionFilter();
        String response =

        given()
                .header("Content-Type", "application/json")
                .body("{ \n" +
                        "    \"username\": \"haris\", \"password\": \"Tijana0306!?!?\" \n" +
                        "}")
                .log().all()
                .filter(session)
        .when()
                .post("/rest/auth/1/session")
        .then()
                .log().all()
                .extract().response().toString();

        // Add comment scenario

        given()
                .pathParam("key", "10006")      //Values collected from Postman AddIssue request
                .header("Content-Type", "application/json")
                .log().all()
                .body("{\n" +
                        "    \"body\": \"This is my first comment sent from REST API\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .filter(session)
        .when()
                .post("/rest/api/2/issue/{key}/comment")      // {key} is a path parameter and will collect walue of "key" from line 16
        .then()
                .log().all()
                .assertThat().statusCode(201);

        //Add Attachment scenario to existing Jira issue

        given()
                .header("X-Atlassian-Token", "no-check")
                .filter(session)
                .pathParam("key", "10006")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("jiraAttachment.txt"))
        .when()
                .post("/rest/api/2/issue/{key}/attachments")
        .then()
                .log().all()
                .assertThat().statusCode(200);



    }
}
