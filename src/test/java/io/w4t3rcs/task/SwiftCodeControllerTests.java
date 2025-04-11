package io.w4t3rcs.task;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwiftCodeControllerTests {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15");
    @Container
    @ServiceConnection
    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.2.5")
            .withExposedPorts(6379);
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void prepare() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void testPostHeadquarterSwiftCodeEndpoint() {
        final String requestBody = """
                {
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/swift-codes")
                .then()
                .statusCode(Matchers.is(200));
    }

    @Test
    @Order(2)
    void testGetUserEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1.0/users/1")
                .then()
                .statusCode(Matchers.is(200))
                .body("name", Matchers.is("j0hny"))
                .body("email",  Matchers.is("j0hny@gmail.com"))
                .body("role", Matchers.is("DEFAULT"));
    }

    @Test
    @Order(3)
    void testDeleteUserEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1.0/users/1")
                .then()
                .statusCode(Matchers.is(200));
    }
}
