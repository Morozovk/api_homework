import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class UserList {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    String apiKey = "reqres-free-v1";

    @Test
    void amountUsersTest() {
        given()
                .log().uri()
                .header("x-api-key", apiKey)

                .when()
                .queryParam("page", "2")
                .get("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("per_page", is(6));
    }

    @Test
    void createUsersTest() {
        String userInfo = "{\"name\": \"Kir\", \"job\": \"QA\"}";

        given()
                .log().uri()
                .header("x-api-key", apiKey)
                .body(userInfo)
                .contentType(JSON)

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Kir"))
                .body("job", is("QA"));
    }

    @Test
    void updateUserTest() {
        String newUserInfo = "{\"name\": \"Kirill\", \"job\": \"QA Engineer\"}";

        given()
                .log().uri()
                .header("x-api-key", apiKey)
                .body(newUserInfo)
                .contentType(JSON)

                .when()
                .put("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Kirill"))
                .body("job", is("QA Engineer"));
    }
}
