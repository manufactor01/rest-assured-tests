import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class AppTest {
  @Before
  public void setUp() {
    baseURI = "https://jsonplaceholder.typicode.com";
  }

  @Test
  public void getUsers() {
    given().when().get("/users").then().statusCode(200);
  }

  @Test
  public void getUserById() {
    given().log().all().
            when().get("/users/1").then().statusCode(200);
  }

  @Test
  public void getUsersById() {
    Response response = given().when().get("/users").then().statusCode(200).extract().response();
    Integer user_id = response.path("[0].id");

    given().
            when().get("/users/{id}", user_id).then().statusCode(200);
  }

  @Test
  public void getUserByIdFixed() {
    Integer id = 1;
    given().when().get("/users/{id}", id).then().statusCode(200);
  }

  @Test
  public void createUser() {
    JSONObject requestParams = new JSONObject();
    JSONObject address = new JSONObject();

    requestParams.put("name", "Test User");
    requestParams.put("username", "testuser");
    requestParams.put("email", "test@user.com");

    address.put("street", "Test Street");
    address.put("suite", "Apt. 123");
    address.put("city", "Electri");
    address.put("zipcode", "1234-1234");

    requestParams.put("address", address);

    System.out.println(requestParams.toString());

    given().
            body(requestParams.toString()).
    when().
          post("/users").
    then().
       assertThat().
       statusCode(201);
  }
}
