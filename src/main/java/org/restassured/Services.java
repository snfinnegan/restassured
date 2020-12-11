package org.restassured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Services {
    String apiKey;
    String apiKeySecret;
    String accessToken;
    String accessTokenSecret;

    public Services(){
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";
    }

    public void loadTokens() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("keys"));
        apiKey = properties.getProperty("api_key");
        apiKeySecret = properties.getProperty("api_key_secret");
        accessToken = properties.getProperty("access_token");
        accessTokenSecret = properties.getProperty("access_token_secret");
    }

    public void getTimeLine() throws IOException {
        loadTokens();
        Response response = given()
                .auth()
                .oauth(apiKey,apiKeySecret,accessToken,accessTokenSecret)
                .contentType("application/json")
        .when()
                .get("/user_timeline.json?screen_name=sfinneganauto")
        .then()
                .extract().response();

        System.out.println(new JsonPath(response.asString()).get("text"));
    }

    public void postStatus() throws IOException {
        loadTokens();
        given()
                .auth()
                .oauth(apiKey,apiKeySecret,accessToken,accessTokenSecret)
                .queryParam("status", new Timestamp(System.currentTimeMillis()))
        .when()
                .post("/update.json")
        .then()
                .statusCode(200);


    }

}
