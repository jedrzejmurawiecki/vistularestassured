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

public class PetPostGetScenarioTest extends RestAssuredTest {

    @Test
    public void shouldGetPet() {
        JSONObject requestParams = new JSONObject();
        int value = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE);
        requestParams.put("id", value);
        String randomName = RandomStringUtils.randomAlphabetic(7);
        requestParams.put("name", randomName);

        createNewPet(requestParams);

        getPet(value, randomName);

        DeletePet(value);


    }

    private void DeletePet(int value) {
        given().delete("/pet/4")
                .then()
                .log().all()
                .statusCode(204);
    }

    private void getPet(int value, String randomName) {
        given().get("/pet/" + value)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(value))
                .body("name", equalTo(randomName));
    }

    private void createNewPet(JSONObject requestParams) {
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
    }

}