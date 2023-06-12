package com.restAssured.restApiPractice.files;

public class payload {

    public static String AddPlace() {

            //Hardcoded JSON dummy API

        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Rahul Shatty Academy\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"29, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"education\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"https://rahulshettyacademy.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}";
    }

    public static String CoursePrice () {

            //Hardcoded JSON dummy API

        return "{\n" +
                "  \"dashboard\": {\n" +
                "    \"purchaseAmount\": 1162,\n" +
                "    \"website\": \"rahulshettyacademy.com\"\n" +
                "  },\n" +
                "  \"courses\": [\n" +
                "    {\n" +
                "      \"title\": \"Selenium Python\",\n" +
                "      \"price\": 50,\n" +
                "      \"copies\": 6\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Cypress\",\n" +
                "      \"price\": 40,\n" +
                "      \"copies\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"RPA\",\n" +
                "      \"price\": 45,\n" +
                "      \"copies\": 10\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Appium\",\n" +
                "      \"price\": 36,\n" +
                "      \"copies\": 7\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public static String AddBook (String aisle, String isbn) {
        String payload = "{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\"" + isbn + "\",\n" +
                "\"aisle\":\"" + aisle + "\",\n" +
                "\"author\":\"Gabriel Jesus\"\n" +
                "}\n";
        return payload;
    }
}
