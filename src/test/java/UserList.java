import models.UserBodyModel;
import models.UserListBodyModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.createUsersSpec.RequestCreateUsersSpec;
import static specs.createUsersSpec.createUsersResponseSpec;
import static specs.updateUserSpec.RequestUpdateUsersSpec;
import static specs.updateUserSpec.updateUsersResponseSpec;

public class UserList extends TestBase {

    String apiKey = "reqres-free-v1";

    @DisplayName("Не работающий тест")
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

    @Test
    void createUsersTest() {

        UserBodyModel authData = new UserBodyModel();
        authData.setName("Kir");
        authData.setJob("QA");

        UserBodyModel responce = step("Отправляем запрос", () ->
                given(RequestCreateUsersSpec)
                .header("x-api-key", apiKey)
                .body(authData)

                .when()
                .post("/users")

                .then()
                .spec(createUsersResponseSpec)
                .extract().as(UserBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Kir", responce.getName());
            assertEquals("QA", responce.getJob());
            assertNotNull(responce.getId());
            assertNotNull(responce.getCreatedAt());
        });
    }

    @Test
    void updateUserTest() {
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Kirill");
        authData.setJob("QA Engineer");

        UserBodyModel responce = step("Отправляем запрос", () ->
                given(RequestUpdateUsersSpec)
                .header("x-api-key", apiKey)
                .body(authData)

                .when()
                .put("/users/2")

                .then()
                .spec(updateUsersResponseSpec)
                .extract().as(UserBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Kirill", responce.getName());
            assertEquals("QA Engineer", responce.getJob());
            assertNull(responce.getId());
            assertNull(responce.getCreatedAt());
            assertNotNull(responce.getUpdatedAt());
        });
    }
}
