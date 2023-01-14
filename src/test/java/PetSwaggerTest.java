import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class PetSwaggerTest {
  @Before
  public void setUp() {
    baseURI = "https://petstore.swagger.io/v2";
  }

  @Test
  public void test01AddNewPetToTheStore() {
    JSONObject requestParams = new JSONObject();
    JSONObject category = new JSONObject();

    requestParams.put("id", 1);
    category.put("id", 10);
    category.put("name", "newest");
    requestParams.put("category", category);
    requestParams.put("name", "Macedonio");
    List<String> photoUrls = new ArrayList<String>();
    photoUrls.add("https:localhost:8000");
    requestParams.put("photoUrls", photoUrls);
    List<JSONObject> tags = new ArrayList<JSONObject>();
    JSONObject tag1 = new JSONObject();
    tag1.put("id", 1);
    tag1.put("name", "sold");
    tags.add(tag1);
    requestParams.put("tags", tags);
    requestParams.put("status", "available");

    given()
        .contentType("application/json\r\n")
        .body(requestParams.toString())
        .when()
        .post("/pet")
        .then()
        .assertThat()
        .statusCode(200);
  }

  private void findByStatus(String status) {
    given()
        .when()
        .get("pet/findByStatus?Status={status}", status)
        .then()
        .assertThat()
        .statusCode(200);
  }

  @Test
  public void test02FindPetsByAvailableStatus() {
    this.findByStatus("available");
  }

  @Test
  public void test03FindPetsByPendingStatus() {
    this.findByStatus("pending");
  }

  @Test
  public void test04FindPetsBySoldStatus() {
    this.findByStatus("sold");
  }

  @Test
  public void test05FindPetById() {
    int id = 1;
    given().when().get("/pet/{id}", id).then().assertThat().statusCode(200);
  }
}
