package com.restAssured.restApiPractice.tests;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class oAuthTest {


    @Test (priority = 7, description = "OAuth 2.0 authorization testing")

    public void oAuthTesting () {

        //GET AUTH CODE

//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\haris\\OneDrive\\Desktop\\Workspace\\RestAPIUdemyCourse\\src\\test\\resources\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss&service=lso&o2v=2&flowName=GeneralOAuthFlow");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(email);
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(password);
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//        Thread.sleep(4000);
//        String url = driver.getCurrentUrl();        //Capture current URL to extraxt code - Google doesn't allow, so we manualy enter url in line below

        String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AZEOvhXH-8BdQG_Y3ka_bqIHAI_4v6WfOHeYA42VzEXCbeKUyN7o845MG81YEOgbNPifHA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";

        String partialCode = url.split("code=")[1];         //Parsing URL to extract code
        String code = partialCode.split("&scope")[0];
        System.out.println("This is extracted code from URL :" + code);     //Extracted code




        //GET ACCESS TOKEN REQUEST

        String accessTokenResponse =        //Save response to variable

        given()
                .urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
        .when()
                .log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();     //Convert response to string

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");       //Extract token from response and save as a variable
        System.out.println("This is access token: " + accessToken);



        //ACTUAL REQUEST

        String response =   //Capture response as a String

        given()
                .queryParam("access_token", accessToken)
        .when()
                .log().all()
                .get("https://rahulshettyacademy.com/getCourse.php").asString();    //Convert response to String

        System.out.println(response);       //Print converted response
    }
}
