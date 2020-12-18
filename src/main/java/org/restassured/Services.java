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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.Data;

import static io.restassured.RestAssured.given;

@Data
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
    protected ScenarioMap scenarioMap;

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
        scenarioMap = new ScenarioMap();
    }

    public void loadTokens() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("keys"));
        apiKey = properties.getProperty("api_key");
        apiKeySecret = properties.getProperty("api_key_secret");
        accessToken = properties.getProperty("access_token");
        accessTokenSecret = properties.getProperty("access_token_secret");
    }


    public void get(String endpoint) throws IOException{
        loadTokens();
        request = given()
                .queryParams((Map) scenarioMap.get("queryParams"))
                .auth()
                .oauth(apiKey,apiKeySecret,accessToken,accessTokenSecret)
                .log()
                .all();
        response = request.filters(reqLoggingFilter,resLoggingFilter).when().get(endpoint);
        tweets = new JsonPath(response.asString()).get("text");
    }

    public void post(String endpoint) throws IOException {
        loadTokens();
        given()
                .auth()
                .oauth(apiKey,apiKeySecret,accessToken,accessTokenSecret)
                .queryParams((Map) scenarioMap.get("queryParams"))
                .log().all()
        .when()
                .post(endpoint)
        .then()
                .statusCode(200);

    }

    public String getLatestTweet() {
        logger.log(Level.INFO, tweets.get(0));
        return tweets.get(0);
    }
}
