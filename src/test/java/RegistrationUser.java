import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegistrationUser extends TestBase {

    String apiKey = "reqres-free-v1";

    @Test
    void registrationSuccessfulTest() {
        String registerUser = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .log().uri()
                .header("x-api-key", apiKey)
                .body(registerUser)
                .contentType(JSON)

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());
    }

    @Test
    void registrationUnSuccessfulNotPasswordTest() {
        String failRegisterUser = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .log().uri()
                .header("x-api-key", apiKey)
                .body(failRegisterUser)
                .contentType(JSON)

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }


    @Test
    void registrationUnSuccessfulNotEmailTest() {
        String failRegisterUser = "{}";

        given()
                .log().uri()
                .header("x-api-key", apiKey)
                .body(failRegisterUser)
                .contentType(JSON)

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

}
