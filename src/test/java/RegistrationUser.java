import models.ErrorResponseBodyModel;
import models.RegisterNotPasswordRequestBodyModel;
import models.RegisterRequestBodyModel;
import models.RegisterResponseBodyModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.BaseSpec.baseResponseSpec;
import static specs.BaseSpec.requestBaseSpec;


public class RegistrationUser extends TestBase {

    String apiKey = "reqres-free-v1";

    @Tag("Smoke")
    @Test
    void registrationSuccessfulLombokTest() {

        RegisterRequestBodyModel authData = new RegisterRequestBodyModel("eve.holt@reqres.in", "pistol");

        RegisterResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                .header("x-api-key", apiKey)
                .body(authData)

                .when()
                .post("/register")

                .then()
                .spec(baseResponseSpec(200))
                .extract().as(RegisterResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
            assertEquals("4", response.getId());
        });
    }


    @Tag ("Smoke")
    @Test
    void registrationUnSuccessfulNotPasswordLombokTest() {
        RegisterNotPasswordRequestBodyModel authData =
                new RegisterNotPasswordRequestBodyModel("eve.holt@reqres.in");

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                .header("x-api-key", apiKey)
                .body(authData)

                .when()
                .post("/register")

                .then()
                .spec(baseResponseSpec(400))
                .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @Tag ("Smoke")
    @Test
    void registrationUnSuccessfulNotEmailLombokTest() {
        String failRegisterUser = "{}";

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                .header("x-api-key", apiKey)
                .body(failRegisterUser)

                .when()
                .post("/register")

                .then()
                .spec(baseResponseSpec(400))
                .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }
}
