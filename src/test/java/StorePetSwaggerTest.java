import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

import java.util.Date;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class StorePetSwaggerTest {
  @Before
  public void setUp() {
    baseURI = "https://petstore.swagger.io/v2";
  }

  @Test
  public void test01ReturnPetInventoriesByStatus() {
    given().when().get("/store/inventory").then().assertThat().statusCode(200);
  }

  @Test
  public void test02PlaceAndOrderForAPet() {
    Date date = new Date();
    JSONObject requestParams = new JSONObject();

    requestParams.put("id", 1);
    requestParams.put("petId", 10);
    requestParams.put("quantity", 2);
    requestParams.put("shipDate", "2022-02-05T02:25:32.947Z");
    requestParams.put("status", "placed");
    requestParams.put("complete", false);

    given().log().all().
            contentType("application/json\r\n").
            body(requestParams.toString()).
            when().
              post("/store/order").
            then().
              assertThat().
              statusCode(200);
  }

}
