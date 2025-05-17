import models.ErrorResponseBodyModel;
import models.RegisterBodyModel;
import models.RegisterResponseBodyModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.registrationSuccessfulSpec.registrationRequestSpec;
import static specs.registrationSuccessfulSpec.registrationResponseSpec;
import static specs.registrationUnSuccessfulSpec.*;

public class RegistrationUser extends TestBase {

    String apiKey = "reqres-free-v1";

    @Tag("Smoke")
    @Test
    void registrationSuccessfulLombokTest() {

        RegisterBodyModel authData = new RegisterBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        RegisterResponseBodyModel response = step("Отправляем запрос", () ->
                given(registrationRequestSpec)
                .header("x-api-key", apiKey)
                .body(authData)

                .when()
                .post("/register")

                .then()
                .spec(registrationResponseSpec)
                .extract().as(RegisterResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
            assertEquals("4", response.getId());
        });
    }

    @Tag ("Smoke")
    @Test
    void registrationUnSuccessfulNotPasswordLombokTest() {
        RegisterBodyModel authData = new RegisterBodyModel();
        authData.setEmail("eve.holt@reqres.in");

        ErrorResponseBodyModel responce = step("Отправляем запрос", () ->
                given(registrationRequestUnSuccessfulSpec)
                .header("x-api-key", apiKey)
                .body(authData)

                .when()
                .post("/register")

                .then()
                .spec(registrationResponseUnSuccessfulSpec)
                .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Missing password", responce.getError());
        });
    }

    @Tag ("Smoke")
    @Test
    void registrationUnSuccessfulNotEmailLombokTest() {
        String failRegisterUser = "{}";

        ErrorResponseBodyModel responce = step("Отправляем запрос", () ->
                given(registrationRequestUnSuccessfulSpec)
                .header("x-api-key", apiKey)
                .body(failRegisterUser)

                .when()
                .post("/register")

                .then()
                .spec(registrationResponseUnSuccessfulSpec)
                .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Missing email or username", responce.getError());
        });
    }
}
