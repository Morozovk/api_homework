import models.UserRequestBodyModel;
import models.CreateUserResponseBodyModel;
import models.ResponseUserUpdateBodyModel;
import models.UserListBodyModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.BaseSpec.baseResponseSpec;
import static specs.BaseSpec.requestBaseSpec;


public class UserList extends TestBase {

    String apiKey = "reqres-free-v1";

    @DisplayName("Не работающий тест")
    @Tag ("Smoke")
    @Disabled
    @Test
    void amountUsersTest() {
        UserListBodyModel response = step("Отправляем запрос", () -> given()
                .filter(withCustomTemplates())
                .log().uri()
                .header("x-api-key", apiKey)

                .when()
                .queryParam("page", "2")
                .get("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(UserListBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals(6, response.getPer_page());
        });
    }
    // требуется подсказка, как лучше реализовать

    @Tag ("Smoke")
    @Test
    void createUsersTest() {

        UserRequestBodyModel authData = new UserRequestBodyModel("Kir", "QA");

        CreateUserResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                .header("x-api-key", apiKey)
                .body(authData)

                .when()
                .post("/users")

                .then()
                .spec(baseResponseSpec(201))
                .extract().as(CreateUserResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Kir", response.getName());
            assertEquals("QA", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }


    @Tag ("Smoke")
    @Test
    void updateUserTest() {
        UserRequestBodyModel authData = new UserRequestBodyModel("Kirill", "QA Engineer");

        ResponseUserUpdateBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                .header("x-api-key", apiKey)
                .body(authData)

                .when()
                .put("/users/2")

                .then()
                .spec(baseResponseSpec(200))
                .extract().as(ResponseUserUpdateBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Kirill", response.getName());
            assertEquals("QA Engineer", response.getJob());
            assertNull(response.getId());
            assertNull(response.getCreatedAt());
            assertNotNull(response.getUpdatedAt());
        });
    }
}
