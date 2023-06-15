package com.restAssured.restApiPractice.tests;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;

public class JiraTest {

    @Test (priority = 6, description = "Automating JIRA API tests.")

    public void jiraTests () {

        RestAssured.baseURI = "http://localhost:8080";     //Jira software localhost URI

        //Login Scenario useing Cookie

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

        String expectedMessage = "Hi how are you";

        // Add comment scenario

        String addCommentResponse =

        given()
                .pathParam("key", "10007")      //Values collected from Postman AddIssue request
                .log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"" + expectedMessage + "\",\n" +
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
                .assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(addCommentResponse);
        String commentID = js.getString("id");      //Extract comment id from JSON response and save it to variable commentID

        //Add Attachment scenario to existing Jira issue

        given()
                .header("X-Atlassian-Token", "no-check")
                .filter(session)
                .pathParam("key", "10007")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("jiraAttachment.txt"))
        .when()
                .post("/rest/api/2/issue/{key}/attachments")
        .then()
                .log().all()
                .assertThat().statusCode(200);

        //Get Issue scenario - Retrieve info about issue from Jira

        String issueDetails =

        given()
                .filter(session)
                .pathParam("key", "10007")
                .queryParam("fields", "comment")
                .log().all()
        .when()
                .get("rest/api/2/issue/{key}")
        .then()
                .log().all()
                .extract().response().asString();       //Extract issue detail from JSON response

        System.out.println("Issue details :" + issueDetails);

        JsonPath js1 = new JsonPath(issueDetails);
        int commentsCount = js1.getInt("fields.comment.comments.size()");     //Iterate trough comments Array and retrieve comment id's
        for (int i=0; i < commentsCount; i++) {
            String commentIdIssues = js1.get("fields.comment.comments [" + i + "].id").toString();
            if (commentIdIssues.equalsIgnoreCase(commentID)) {
                String message = js1.get("fields.comment.comments[" + i + "].body").toString();
                System.out.println(message);
                Assert.assertEquals(message, expectedMessage);
            }
            break;
        }
    }
}
