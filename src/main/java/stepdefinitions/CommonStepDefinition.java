package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.restassured.enums.EndpointMethod;
import org.restassured.Services;
import org.restassured.enums.Endpoint;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CommonStepDefinition {
    Services services;

    @Given("^I am using user (.*)")
    public CommonStepDefinition iAmUsingUser(String user) {
        services = new Services();
        services.getScenarioMap().set("screen_name",user);
        return this;
    }

    @When("^I make a (.*) request to the (.*) endpoint with a (.*) value of (.*)$")
    public void iMakeARequestToEndpoint(EndpointMethod endpointMethod, Endpoint endpoint) throws IOException {
        switch(endpointMethod) {
            case GET:
                services.get(endpoint.getUrl());
                break;
            case POST:
                services.post(endpoint.getUrl());
                break;
            default:
                System.out.println("INVALID method");
        }
    }

    @When("^add query parameter with key (.*) and value (.*) to scenario map$")
    public void addQueryParameterToScenarioMap(String key, String value){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(key, value);
        services.getScenarioMap().set("queryParams",queryParams);
    }

    @When("I send a tweet with a timestamp")
    public CommonStepDefinition iSendATweetWithATimestamp() throws IOException {
        services.getScenarioMap().set("timestamp",getTimeStamp());
        addQueryParameterToScenarioMap("status",services.getScenarioMap().get("timestamp").toString());
        iMakeARequestToEndpoint(EndpointMethod.POST,Endpoint.UPDATE_STATUS);
        return this;
    }

    @When("^I send a tweet with text (.*)$")
    public void iSendATweetWithText(String tweetText) throws IOException {
        addQueryParameterToScenarioMap("status",tweetText);
        iMakeARequestToEndpoint(EndpointMethod.POST,Endpoint.UPDATE_STATUS);
    }

    public String getTimeStamp(){
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        return simpleDate.format(new Timestamp(System.currentTimeMillis()));
    }

    @When("I read the latest text")
    public CommonStepDefinition iReadTheLatestText() throws IOException {
        services.getScenarioMap().clear("queryParam");
        addQueryParameterToScenarioMap("screen_name",services.getScenarioMap().get("screen_name").toString());
        iMakeARequestToEndpoint(EndpointMethod.GET,Endpoint.USER_TIMELINE);
        return this;
    }

    @Then("the timestamp is from today")
    public CommonStepDefinition theTimestampIsFromToday() {
        Assert.assertEquals(services.getScenarioMap().get("timestamp"),services.getLatestTweet());
        return this;
    }
}
