package com.restAssured.restApiPractice.tests;

import com.restAssured.restApiPractice.files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test (priority = 3, description = "Validate if SUM of sold courses matches with Total Purchase Amount.")

    public void sumOfCourses () {

        int sum = 0;
        JsonPath js = new JsonPath(payload.CoursePrice());
        int count = js.getInt("courses.size()");

        for (int i = 0; i < count; i++) {
            int prices = js.getInt("courses [" + i + "].price");
            int copies = js.getInt("courses [" + i + "].copies");
            int amount = prices * copies;
            System.out.println("Amount of sold courses for every course: Course " + i + " = " + amount);
            sum = sum + amount;
        }

        System.out.println("Sum of all sold courses: " + sum);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaseAmount, "Validate that sum of all courses sold equals to Purchase Amount");
    }
}
