package com.restAssured.restApiPractice.tests;

import com.restAssured.restApiPractice.files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class ComplexJsonParse {

    @Test (priority = 2, description = "Testing dummy API provided from payload class, Testing JSON parsing")

    public void parsingJsonTests () {

        JsonPath js = new JsonPath(payload.CoursePrice());

        //Print number of courses returned by API

        int count = js.getInt("courses.size()");
        System.out.println("Number of elements in courses array: " + count);

        //Print purchase amount

        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Total course purchase amount: " + totalAmount);

        //Print title of the first course

        String titleOfFirstCourse = js.get("courses[0].title");
        System.out.println("Title od first course: " + titleOfFirstCourse);

        //Print title of the second course

        String titleOfSecondCourse = js.get("courses[1].title");
        System.out.println("Title of second course: " + titleOfSecondCourse);

        //Print title of the third course

        String titleOfThirdCourse = js.get("courses[2].title");
        System.out.println("Title of third course: " + titleOfThirdCourse);

        //Print out all course titles and their prices

        for (int i = 0; i < count; i++) {
            String courseTitles = js.get("courses[" + i + "].title");
            String coursePrices = js.get("courses[" + i + "].price").toString();
            System.out.println("Course title: " + courseTitles + ", " + "Course price: " + coursePrices);
        }

        //Print number of copies sold by RPA Course

            //Hardcoded method

        //String numOfCopiesSoldByRPA = js.get("courses[2].copies").toString();
        //System.out.println("Number of copies sold by RPA course: " + numOfCopiesSoldByRPA);

            //Loop iteration method -> no hardcoding

        for (int i = 0; i < count; i++) {
            String courseTitles = js.get("courses [" + i + "].title");
            if (courseTitles.equalsIgnoreCase("RPA")) {
                int copies = js.get("courses [" + i + "].copies");
                System.out.println("Number of copies sold by RPA course: " + copies);
                break;
            }
        }
    }
}
