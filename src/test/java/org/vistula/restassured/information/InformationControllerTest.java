package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

public class InformationControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/information")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void shouldCreateNewPlayer() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);


        requestParams.put("name", myName);
        String myNationality = "Polish";
        requestParams.put("nationality", myNationality);
        int mySalary = 94000;
        requestParams.put("salary", mySalary);


        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo(myName))
                .body("nationality", equalTo(myNationality))
                .body("salary", equalTo(94000))
                .body("id", greaterThan(0));



    }
    @Test
    public void deletePlayer() {
        given().delete("/information/4")
                .then()
                .log().all();

    }


}
