package org.restassured;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Services {
    String apiKey;
    String apiKeySecret;
    String accessToken;
    String accessTokenSecret;
    RequestSpecification request;
    Response response;
    RequestLoggingFilter reqLoggingFilter;
    ResponseLoggingFilter resLoggingFilter;
    OutputStream outputStream;
    PrintStream printStream;
    Logger logger;
    List<String> tweets;

    public Services(){
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";

        this.outputStream = new ByteArrayOutputStream();
        this.printStream = new PrintStream(outputStream);
        this.reqLoggingFilter = new RequestLoggingFilter(printStream);
        this.resLoggingFilter = new ResponseLoggingFilter(printStream);

        logger = Logger.getLogger(Services.class.getName());
    }

    public void loadTokens() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("keys"));
        apiKey = properties.getProperty("api_key");
        apiKeySecret = properties.getProperty("api_key_secret");
        accessToken = properties.getProperty("access_token");
        accessTokenSecret = properties.getProperty("access_token_secret");
    }


    public void getTimeLine(String screenName) throws IOException {
        loadTokens();
        Response response = given()
                .auth()
                .oauth(apiKey,apiKeySecret,accessToken,accessTokenSecret)
                .contentType("application/json")
                .log()
                .all()
        .when()
                .get("/user_timeline.json?screen_name="+screenName)
        .then()
                .extract().response();

        System.out.println(new JsonPath(response.asString()).get("text"));
    }

    public void getTimeLineFilter(String screenName) throws IOException{
        loadTokens();
        request = given()
                .auth()
                .oauth(apiKey,apiKeySecret,accessToken,accessTokenSecret)
                .log()
                .all();
        response = request.filters(reqLoggingFilter,resLoggingFilter).when().get("/user_timeline.json?screen_name="+ screenName);
        tweets = new JsonPath(response.asString()).get("text");
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

    public void printLatestTweet() {
        logger.log(Level.INFO, tweets.get(0));
    }
}
