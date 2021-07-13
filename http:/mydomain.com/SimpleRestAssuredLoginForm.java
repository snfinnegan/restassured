import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Timestamp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleRestAssuredLoginForm {
  static Response response, response2;

  @BeforeAll
  public static void login() {
    Map loginForm = new HashMap();
    loginForm.put("username","myusername");
    loginForm.put("password","mypassword");

    response = RestAssured
            .given()
            .contentType("application/x-www-form-urlencoded; charset=utf-8")
            .formParams(loginForm)
            .post("http://mydomain.com/myLoginForm");
  }

  @Test
  public void makeAuthenicatedGet(){
    response2 = RestAssured
    .given()
    .cookies(response.getCookies())
    .headers(response.getHeaders())
    .get("http://mydomain.com/getUserDetails");

    assertTrue(response2.getStatus == 200);
  }
}
