import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ApiTest extends TestBase {

    @DisplayName("Проверка получения данных пользователя")
    @Test
    void getUserDataTest() {
        given()
                .contentType(JSON)
                .log().uri()
                .when()
                .get("/user/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", equalTo(2),
                        "data.name", equalTo("fuchsia rose"),
                        "data.year", equalTo(2001),
                        "data.color", equalTo("#C74375"),
                        "data.pantone_value", equalTo("17-2031"),
                        "support.url", equalTo("https://reqres.in/#support-heading"),
                        "support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!")
                        );
    }

    @DisplayName("Проверка отсутствия данных пользователя")
    @Test
    void getMissingUserDataTest() {
        given()
                .contentType(JSON)
                .log().uri()
                .when()
                .get("/user/50")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @DisplayName("Проверка создания пользователя")
    @Test
    void createUserTest() {
        given()
                .body("{\n" +
                        "    \"name\": \"Mary\",\n" +
                        "    \"job\": \"secretary\"\n" +
                        "}")
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", equalTo("Mary"),
                        "job", equalTo("secretary"),
                        "id", notNullValue(),
                        "createdAt", notNullValue()
                        );
    }

    @DisplayName("Проверка редактирования данных пользователя")
    @Test
    void updateUserDataTest() {
        given()
                .body("{\n" +
                        "    \"name\": \"Mary Jay\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .contentType(JSON)
                .log().uri()
                .when()
                .put("/users/645")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("Mary Jay"),
                        "job", equalTo("leader"),
                        "updatedAt", notNullValue()
                );
    }

    @DisplayName("Проверка удаления пользователя")
    @Test
    void deleteUserTest() {
        given()
                .contentType(JSON)
                .log().uri()
                .when()
                .delete("/users/645")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }
}
