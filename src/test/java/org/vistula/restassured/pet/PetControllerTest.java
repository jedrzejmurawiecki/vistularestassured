package org.vistula.restassured.pet;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class PetControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    public void shouldGetFirstPet() {
        Pet pet = given().get("/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(2))
                .body("name", equalTo("Dog"))
                .extract().body().as(Pet.class);

        assertThat(pet.getId()).isEqualTo("2");
        assertThat(pet.getName()).isEqualTo("Dog");
    }

    @Test
    public void shouldCreateNewPet() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", 4);
        requestParams.put("name", "Wolf");

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
    }
    @Test
    public void shouldntGet100() {
        int statusCode = given().get("/pet/100")
                .then()
                .log().all()
                .statusCode(404)
                .body(equalTo("There is not Pet with such id"))
                .extract().statusCode();

        assertThat(statusCode).isEqualTo(404);

    }

    @Test
    public void deletePet() {
                given().delete("/pet/4")
                .then()
                .log().all()
                .statusCode(204);

    }

    @Test
    public void shouldCreateNewerPet() {
        JSONObject requestParams = new JSONObject();
        int value = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE);
        requestParams.put("id", value);
        requestParams.put("name", RandomStringUtils.randomAlphabetic(10));

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
        given().delete("/pet/"+value)
                .then()
                .log().all()
                .statusCode(204);


    }
}
