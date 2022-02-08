package com.andres.insulinicpump.unit;

import com.andres.insulinicpump.device.MainControlLoop;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BolusRestControllerTest {

    @BeforeAll
    public static void setBaseUri(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @MockBean
    private MainControlLoop mainControlLoop;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void calculateBolus(){
        given().
                queryParam("gramsOfCarbs",60)
        .when().post("/calculateBolus")
        .then().statusCode(200);
    }
}
